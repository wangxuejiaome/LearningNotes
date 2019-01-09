// IBookManager.aidl
package wxj.ipclearn.aidl;

// Declare any non-default types here with import statements
import wxj.ipclearn.aidl.Book;

interface IBookManager {
    /**
     * Demonstrates some basic t1ypes that you can use as parameters
     * and return values in AIDL.
     */
    List<Book> getBookList();
    void addBook(in Book book);
}
