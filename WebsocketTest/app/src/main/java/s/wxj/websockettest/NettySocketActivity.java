package s.wxj.websockettest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import s.wxj.websockettest.nettysocket.NettySocketHelper;

public class NettySocketActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String TAG = "NettySocketActivity";
    private Button btnStart;
    private TextView tvMessage;
    private Button btnSend;
    NettySocketHelper nettySocketHelper;

    private NettySocketHelper.Observer observer = new NettySocketHelper.Observer() {
        @Override
        public void onConnected() {
            Log.d(TAG, "我连接上服务器了");
        }

        @Override
        public void onDataResponse(WebSocketFrame msg) {

            if (msg instanceof TextWebSocketFrame) {
                TextWebSocketFrame textFrame = (TextWebSocketFrame) msg;
                String text = textFrame.text();
                Log.d(TAG, "收到了数据：" + text);
                tvMessage.setText(String.format("%s\n%s", tvMessage.getText().toString(), text));
            }
        }

        @Override
        public void disConnected() {

            Log.d(TAG, "我disConnect");
        }

        @Override
        public void onFailed() {
            Log.d(TAG, "我onFailed");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_netty_socket);
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
        nettySocketHelper.disConnected();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                // ws://echo.websocket.org:80
                // ws://10.49.24.107:8080/push-agent/websocket
                /*ws://10.49.24.60:8888/ws*/
                nettySocketHelper = new NettySocketHelper.Config()
                        .host("10.49.24.60")
                        .port(80)
                        .heartBeatInterval(2000)
                        .maxRetryCount(8)
                        .setObserver(observer)
                        .build();
                nettySocketHelper.connect();
                break;
            case R.id.btn_send:

                Map<String, String> requestMap = new HashMap<String, String>();
                requestMap.put("deviceId", "AI-BOX-13");
                requestMap.put("userAgent", "BiuOS-TV/1.0");
                requestMap.put("custNo", "12345");
                JSONObject json = new JSONObject(requestMap);
                nettySocketHelper.sendMessage(json.toString());
                break;
        }
    }
}
