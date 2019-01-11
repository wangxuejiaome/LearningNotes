package wxj.aidlservicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerService extends Service {

    private static final String TAG = "BookManagerService";
    private AtomicBoolean mIsServiceDestroyed = new AtomicBoolean(false);
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<>();

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) {
            mListenerList.register(listener);
            int listenerSize = mListenerList.beginBroadcast();
            Log.d(TAG, "registerListener: listener: " + listener + "current size: " + listenerSize);
            // beginBroadCast() 要和 finishBroadcast() 配合使用,即使只为了获取 listener 个数
            mListenerList.finishBroadcast();
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) {
            mListenerList.unregister(listener);
            int listenerSize = mListenerList.beginBroadcast();
            Log.d(TAG, "unregisterListener: listener: " + listener + "current size: " + listenerSize);
            mListenerList.finishBroadcast();
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1,"Android"));
        mBookList.add(new Book(2,"IOS"));
        new Thread(new ServiceWorker()).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private void onNewBookArrived(Book book) throws RemoteException{
        mBookList.add(book);
        final int N = mListenerList.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IOnNewBookArrivedListener iOnNewBookArrivedListener = mListenerList.getBroadcastItem(i);
            if(iOnNewBookArrivedListener != null){
                try {
                    iOnNewBookArrivedListener.onNewBookArrived(book);
                }catch (RemoteException e){
                    e.printStackTrace();
                }
            }
        }
        mListenerList.finishBroadcast();
    }

    private class ServiceWorker implements Runnable{

        @Override
        public void run() {
            while (!mIsServiceDestroyed.get()){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = mBookList.size() + 1;
                Book newBook = new Book(bookId,"new book#" + bookId);
                try {
                    Log.i(TAG, "run: ThreadCurrentName" + Thread.currentThread());
                    onNewBookArrived(newBook);
                }catch (RemoteException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
