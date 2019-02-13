package s.wxj.websockettest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Response;
import okhttp3.WebSocket;
import okio.ByteString;
import s.wxj.websockettest.okttpwebsocket.OkHttpWebSocketHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MainActivity";
    private Button btnStart;
    private TextView tvMessage;
    private Button btnSend;

    private OkHttpWebSocketHelper mHttpWebSocketHelper;

    private OkHttpWebSocketHelper.WebSocketCallback webSocketCallback = new OkHttpWebSocketHelper.WebSocketCallback() {
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            Log.d(TAG, "onOpen: 回调成功");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            Log.d(TAG, "onMessage: 回调成功" + text);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            Log.d(TAG, "onMessage byteString: 回调成功" + bytes.toString());
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            Log.d(TAG, "onClosing: 回调成功,关闭原因" + reason);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            Log.d(TAG, "onClosed: 回调成功,关闭原因" + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Response response) {
            Log.d(TAG, "onFailure: 回调成功");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btnStart = findViewById(R.id.btn_start);
        tvMessage = findViewById(R.id.tv_message);
        btnSend = findViewById(R.id.btn_send);
        btnStart.setOnClickListener(this);
        btnSend.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHttpWebSocketHelper.close("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                /*"ws://echo.websocket.org"*/
                /*"ws://10.49.24.61:8083/push-agent/websocket"*/
                mHttpWebSocketHelper = new OkHttpWebSocketHelper.Config("ws://biuoscnsit-h2.cnsuning.com/push-agent/websocket")
                        .maxRetryCount(8)
                        .heartBeatInterval(5000)
                        .setWebSocketCallback(webSocketCallback)
                        .build();
                mHttpWebSocketHelper.connect(mHttpWebSocketHelper.new EchoWebSocketListener(mHttpWebSocketHelper));
                break;
            case R.id.btn_send:
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("deviceId", "445454564654")
                            .put("userAgent", "BiuOS-TV/1.0")
                            .put("custNo", "1234567890");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mHttpWebSocketHelper.sendMessage(jsonObject.toString());
                break;
        }
    }
}
