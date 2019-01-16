package wxj.ipclearn.socket;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import wxj.ipclearn.R;

public class TCPClientActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "TCPClientActivity";

    public static final int MESSAGE_RECEIVE_NEW_MSG = 1;
    public static final int MESSAGE_SOCKET_CONNECTED = 2;

    private Button btnSend;
    private TextView tvCommunication;
    private EditText etContent;

    private PrintWriter mPrintWriter;
    private Socket mClientSocket;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_RECEIVE_NEW_MSG:
                    tvCommunication.setText(tvCommunication.getText() + (String) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcpclient);
        initView();
        Intent serviceIntent = new Intent(this, TCPServerService.class);
        startService(serviceIntent);
        new Thread(new Runnable() {
            @Override
            public void run() {
                connectTcpServer();
            }
        }).start();
    }

    private void initView() {
        btnSend = findViewById(R.id.btn_send);
        etContent = findViewById(R.id.et_content);
        tvCommunication = findViewById(R.id.tv_communication);
        btnSend.setOnClickListener(this);
    }

    private void connectTcpServer() {
        Socket socket = null;
        while (socket == null) {
            try {
                socket = new Socket("localhost", 8688);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                Log.d(TAG, "connectTcpServer: ");
            } catch (IOException e) {
                SystemClock.sleep(1000);
                System.out.println("connect tcp server failed,retry...");
            }
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Log.d(TAG, "connectTcpServer: TCPClientActivity.this.isFinishing()" + TCPClientActivity.this.isFinishing());
            while (!TCPClientActivity.this.isFinishing()) {
                String msg = br.readLine();
                if (msg != null) {
                    String time = formatDateTime(System.currentTimeMillis());
                    final String showSendMsg = "server " + time + ":" + msg + "\n";
                    mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG, showSendMsg).sendToTarget();
                }
            }

            System.out.println("quit...");
            mPrintWriter.close();
            br.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendMessage() {
        if (TextUtils.isEmpty(etContent.getText().toString())) {
            Toast.makeText(this, "请输入发送内容！", Toast.LENGTH_SHORT).show();
        } else {
            String sendMessage = etContent.getText().toString();
            mPrintWriter.println(sendMessage);
            String time = formatDateTime(System.currentTimeMillis());
            String showMessage = "self " + time + ":" + sendMessage + "\n";
            tvCommunication.setText(tvCommunication.getText().toString() + showMessage);
        }
    }

    private String formatDateTime(long time) {
        return new SimpleDateFormat("(HH:mm:ss)").format(new Date(time));
    }

    @Override
    protected void onDestroy() {
        if (mClientSocket != null) {
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                sendMessage();
                break;
        }
    }
}
