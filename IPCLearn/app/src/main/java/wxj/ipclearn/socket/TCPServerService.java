package wxj.ipclearn.socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class TCPServerService extends Service {


    public static final String TAG = "TCPServerService";

    private boolean mIsServiceDestroyed;
    private String[] mDefinedMessage = new String[]{
            "你好啊，哈哈",
            "你叫什么名字呀？",
            "今天南京天气不错啊，shy",
            "你知道吗？我可是可以和多个人同时聊天的哦",
            "给你讲个笑话吧：据说爱笑的人运气不会太差，不知道真假。"
    };

    @Override
    public void onCreate() {
        new Thread(new TcpServer()).start();
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroyed = true;
        super.onDestroy();
    }

    private class TcpServer implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8688);
            } catch (IOException e) {
                Log.d(TAG, "run: establish tcp server failed,port:8688");
                e.printStackTrace();
            }

            while (!mIsServiceDestroyed) {
                // 接收客户端请求
                try {
                    final Socket socket = serverSocket.accept();
                    Log.d(TAG, "accept");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                responseClient(socket);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void responseClient(Socket client) throws IOException {
            // 用于接收客户端消息
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    client.getInputStream()
            ));
            // 用于向客户端发送消息
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
            out.println("欢迎来到聊天室！");
            while (!mIsServiceDestroyed) {
                String str = in.readLine();
                Log.d(TAG, "received client message: " + str);
                if (str == null) {
                    // 客户端断开连接
                    break;
                }
                int i = new Random().nextInt(mDefinedMessage.length);
                String msg = mDefinedMessage[i];
                out.println(msg);
                Log.d(TAG, "server send: " + msg);
            }
            Log.d(TAG, "client quit");

            in.close();
            out.close();
            client.close();
        }
    }
}
