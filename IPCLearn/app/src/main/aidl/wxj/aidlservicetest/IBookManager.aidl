// IBookManager.aidl
package wxj.aidlservicetest;

// Declare any non-default types here with import statements
import wxj.aidlservicetest.Book;
import wxj.aidlservicetest.IOnNewBookArrivedListener;

interface IBookManager {
List<Book> getBookList();
void addBook(in Book book);
void registerListener(IOnNewBookArrivedListener listener);
void unregisterListener(IOnNewBookArrivedListener listener);
}
