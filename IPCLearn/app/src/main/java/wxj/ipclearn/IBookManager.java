package wxj.ipclearn;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import java.util.List;

import wxj.ipclearn.aidl.Book;

public interface IBookManager extends IInterface {

    static final String DESCRIPTOR = "wxj.ipclearn.manualbinder.IBookManager";
    static final int TRANSACTION_getBookList = IBinder.FIRST_CALL_TRANSACTION + 0;
    static final int TRANSACTION_addBook = IBinder.FIRST_CALL_TRANSACTION + 1;

    public List<Book> getBookList() throws RemoteException;
    public void addBook(Book book) throws  RemoteException;
}
