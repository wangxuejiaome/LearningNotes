package wxj.ipclearn.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class BookProvider extends ContentProvider {

    public static final String TAG = "BookProvider";

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate, current thread:" + Thread.currentThread());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query, current thread:" + Thread.currentThread());
        return null;
    }


    @Override
    public String getType(Uri uri) {
        Log.d(TAG, "getType, current thread:" + Thread.currentThread());
        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "insert, current thread:" + Thread.currentThread());
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "delete, current thread:" + Thread.currentThread());
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(TAG, "update, current thread:" + Thread.currentThread());
        return 0;
    }
}
