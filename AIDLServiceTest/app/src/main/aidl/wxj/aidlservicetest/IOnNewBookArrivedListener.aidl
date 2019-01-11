// IOnNewBookArrivedListener.aidl
package wxj.aidlservicetest;

// Declare any non-default types here with import statements
import wxj.aidlservicetest.Book;

interface IOnNewBookArrivedListener {

void onNewBookArrived(in Book newBook);
}
