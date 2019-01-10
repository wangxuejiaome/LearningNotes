package wxj.ipclearn.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import wxj.ipclearn.Constants;

/**
 * 服务端：被发起方
 * 1.该服务拥有一个信箱 Messenger
 * 2.还有一个相当于邮递员的角色 Handler 用来处理邮件信息，邮递员会和信息绑在一起使用
 *
 * 客户端：发起方
 * 客户端拥有的成员: MessengerOfService（持有的信箱）
 *                 Messenger（自己也需要拥有自己的信箱） 如果需要服务端给回复，在发消息给服务端的时候告诉服务端回信往这个信箱里写
 *                 ServiceConnection  服务器连接成功的接口实现，连接服务器成功后，会回调该实现
 * 1.通过 bindService 连接上服务方，连接成功后，回得到服务方这个信箱
 */

public class MessengerService extends Service {

    public static final String TAG = "MessengerService";

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MSG_FROM_CLIENT: // message from client
                    Log.i(TAG, "receive msg form client: " + msg.getData().getString("msg"));
                    Messenger messengerOfClient = msg.replyTo;
                    Message replyMessage = Message.obtain(null,Constants.MSG_FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply","嗯，你的消息我收到了，稍后我会回复你");
                    replyMessage.setData(bundle);
                    try {
                        messengerOfClient.send(replyMessage);
                    }catch (RemoteException e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }
    
    private final Messenger mMessenger = new Messenger(new MessengerHandler());


    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
