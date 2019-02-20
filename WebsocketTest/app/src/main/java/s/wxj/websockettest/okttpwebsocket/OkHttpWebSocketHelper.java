package s.wxj.websockettest.okttpwebsocket;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class OkHttpWebSocketHelper {

    private static final String TAG = "OkHttpWebSocketHelper";

    private static final int ON_OPEN = 1;
    private static final int ON_MESSAGE_STRING = 2;
    private static final int ON_MESSAGE_BYTE_STRING = 3;
    private static final int ON_CLOSING = 4;
    private static final int ON_CLOSED = 5;
    private static final int ON_FAILURE = 6;

    private static final int RECONNECT_MESSAGE = 7;
    private static final int HEART_MESSAGE = 8;
    /**
     * 主动关闭 code,
     */
    private static final int CLOSED_CODE = 1000;


    private OkHttpClient okHttpClient;
    private WebSocket webSocket;
    private String url;
    private long heartBeatInterval;
    private int maxRetryCount;
    /**
     * 已重连次数
     */
    private int alReadyConnectCount = 0;
    private WebSocketListener webSocketListener;
    private WebSocketCallback webSocketCallback;

    /**
     * 通过 mainHandler 切换到主线程，调用用户实现的接口，让用户实现自己的逻辑
     */
    private Handler mainHandler;

    /**
     * 处理心跳和失败重连
     */
    private Handler workHandler;


    private OkHttpWebSocketHelper(Config config) {
        this.okHttpClient = config.okHttpClient;
        this.url = config.url;
        this.heartBeatInterval = config.heartBeatInterval;
        this.maxRetryCount = config.maxRetryCount;
        this.webSocketCallback = config.webSocketCallback;

        mainHandler = new Handler(Looper.getMainLooper(), mainHandlerCallback);
        HandlerThread workThread = new HandlerThread("OkHttpWebSocketHelper");
        workThread.start();
        workHandler = new Handler(workThread.getLooper(), workHandlerCallback);
    }

    /**
     * @param webSocketListener 使用提供的 EchoWebSocketListener，帮助实现了心跳和失败重连的触发
     */
    public void connect(WebSocketListener webSocketListener) {
        this.webSocketListener = webSocketListener;
        Request request = new Request.Builder()
                .url(url)
                .build();
        if (webSocket != null) {
            webSocket.close(CLOSED_CODE, null);
        }
        okHttpClient = okHttpClient == null ? new OkHttpClient() : okHttpClient;
        webSocket = okHttpClient.newWebSocket(request, webSocketListener);
    }

    public void sendMessage(String message) {
        if (webSocket != null) {
            webSocket.send(message);
            Log.d(TAG, "client send:" + message + " Thread: " + Thread.currentThread());
        }
    }

    private void reconnect() {
        workHandler.removeMessages(HEART_MESSAGE);
        if (alReadyConnectCount < maxRetryCount) {
            // 启动重连机制是: 1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒 ...
            long nextReconnectInterval = (long) Math.pow(2, alReadyConnectCount);
            Log.d(TAG, nextReconnectInterval + "秒后尝试第" + (alReadyConnectCount + 1) + "重连");
            workHandler.sendEmptyMessageDelayed(RECONNECT_MESSAGE, nextReconnectInterval * 1000);
            alReadyConnectCount++;
        }
    }

    public void close(String reason) {
        if (webSocket != null) {
            webSocket.close(CLOSED_CODE, TextUtils.isEmpty(reason) ? reason : "{\"deviceId\":\"445454564654\"}");
            webSocket = null;
        }
        mainHandler.removeCallbacksAndMessages(null);
        workHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 参数配置
     */
    public static class Config {
        private OkHttpClient okHttpClient;
        private String url;
        private long heartBeatInterval = 10 * 1000;
        private int maxRetryCount = -1;
        private WebSocketCallback webSocketCallback;

        public Config(String url) {
            this.url = url;
        }

        public Config setOkHttpClient(OkHttpClient okHttpClient) {
            this.okHttpClient = okHttpClient;
            return this;
        }

        public Config heartBeatInterval(long millisecond) {
            this.heartBeatInterval = millisecond;
            return this;
        }

        public Config maxRetryCount(int maxRetryCount) {
            this.maxRetryCount = maxRetryCount;
            return this;
        }

        public Config setWebSocketCallback(WebSocketCallback webSocketCallback) {
            this.webSocketCallback = webSocketCallback;
            return this;
        }

        public OkHttpWebSocketHelper build() {
            return new OkHttpWebSocketHelper(this);
        }
    }


    public class EchoWebSocketListener extends WebSocketListener {

        OkHttpWebSocketHelper okHttpWebSocketHelper;

        public EchoWebSocketListener(OkHttpWebSocketHelper okHttpWebSocketHelper) {
            this.okHttpWebSocketHelper = okHttpWebSocketHelper;
        }

        @Override
        public void onOpen(WebSocket webSocket, Response response) {

            Message message = mainHandler.obtainMessage();
            message.what = ON_OPEN;
            message.obj = response;
            mainHandler.sendMessage(message);
            // 开启心跳
            workHandler.sendEmptyMessage(HEART_MESSAGE);
            // 重连次数重置为 0
            alReadyConnectCount = 0;
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            Message message = mainHandler.obtainMessage();
            message.what = ON_MESSAGE_STRING;
            message.obj = text;
            mainHandler.sendMessage(message);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            Message message = mainHandler.obtainMessage();
            message.what = ON_MESSAGE_STRING;
            message.obj = bytes;
            mainHandler.sendMessage(message);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {

            Message message = mainHandler.obtainMessage();
            message.what = ON_CLOSING;
            message.arg1 = code;
            message.obj = reason;
            mainHandler.sendMessage(message);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            Message message = mainHandler.obtainMessage();
            message.what = ON_CLOSED;
            message.arg1 = code;
            message.obj = reason;
            mainHandler.sendMessage(message);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            Message message = mainHandler.obtainMessage();
            message.what = ON_FAILURE;
            message.obj = response;
            mainHandler.sendMessage(message);
            reconnect();
        }
    }


    private Handler.Callback mainHandlerCallback = new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (webSocketCallback == null) {
                return true;
            }

            int what = msg.what;
            switch (what) {
                case ON_OPEN:
                    Response response = (Response) msg.obj;
                    webSocketCallback.onOpen(webSocket, response);
                    break;
                case ON_MESSAGE_STRING:
                    String text = (String) msg.obj;
                    webSocketCallback.onMessage(webSocket, text);
                    break;
                case ON_MESSAGE_BYTE_STRING:
                    ByteString byteString = (ByteString) msg.obj;
                    webSocketCallback.onMessage(webSocket, byteString);
                    break;
                case ON_CLOSING:
                    int code = msg.arg1;
                    String reason = (String) msg.obj;
                    webSocketCallback.onClosing(webSocket, code, reason);
                    break;
                case ON_CLOSED:
                    int closeCode = msg.arg1;
                    String closeReason = (String) msg.obj;
                    webSocketCallback.onClosed(webSocket, closeCode, closeReason);
                    break;
                case ON_FAILURE:
                    Response failureResponse = (Response) msg.obj;
                    webSocketCallback.onFailure(webSocket, failureResponse);
                    break;
            }
            return true;
        }
    };


    private Handler.Callback workHandlerCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case RECONNECT_MESSAGE:
                    connect(webSocketListener);
                    break;
                case HEART_MESSAGE:
                    sendMessage("{\"heartBeat\":\"heart bump\"}");
                    workHandler.sendEmptyMessageDelayed(HEART_MESSAGE, heartBeatInterval);
                    break;
            }
            return true;
        }
    };


    public static abstract class WebSocketCallback {

        public void onOpen(WebSocket webSocket, Response response) {
        }

        public void onMessage(WebSocket webSocket, String text) {
        }

        public void onMessage(WebSocket webSocket, ByteString bytes) {
        }

        public void onClosing(WebSocket webSocket, int code, String reason) {
        }

        public void onClosed(WebSocket webSocket, int code, String reason) {
        }

        public void onFailure(WebSocket webSocket, Response response) {
        }
    }
}

