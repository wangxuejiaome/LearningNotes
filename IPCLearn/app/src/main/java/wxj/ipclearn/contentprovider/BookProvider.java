package wxj.ipclearn.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class BookProvider extends ContentProvider {

    public static final String TAG = "BookProvider";
    public static final String AUTHORITY = "wxj.ipclearn.contentprovider.BookProvider";
    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");

    private Context mContext;
    private SQLiteDatabase mDb;

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate, current thread:" + Thread.currentThread());
        mContext = getContext();
        // ContentProvider 创建时，初始化数据完毕。注意：这里仅仅是为了演示，实际使用中不推荐在主线程中做耗时操作
        initProviderData();
        return true;
    }

    private void initProviderData() {
        mDb = new DbOpenHelper(mContext).getWritableDatabase();
        mDb.execSQL("delete from " + DbOpenHelper.BOOK_TABLE_NAME);
        mDb.execSQL("delete from " + DbOpenHelper.USER_TABLE_NAME);
        mDb.execSQL("insert into book values(3,'Android')");
        mDb.execSQL("insert into book values(4,'IOS')");
        mDb.execSQL("insert into book values(5,'Html5')");
        mDb.execSQL("insert into user values(1,'jake',1)");
        mDb.execSQL("insert into user values(2,'jasmine',0)");

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query, current thread:" + Thread.currentThread());
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        return mDb.query(table, projection, selection, selectionArgs, null, null, sortOrder, null);
    }


    @Override
    public String getType(Uri uri) {
        Log.d(TAG, "getType, current thread:" + Thread.currentThread());
        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "insert, current thread:" + Thread.currentThread());
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        mDb.insert(table, null, values);
        mContext.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "delete, current thread:" + Thread.currentThread());
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int count = mDb.delete(table, selection, selectionArgs);
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(TAG, "update, current thread:" + Thread.currentThread());
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int row = mDb.update(table, values, selection, selectionArgs);
        if (row > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return row;
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        if (uri.equals(BOOK_CONTENT_URI)) {
            return DbOpenHelper.BOOK_TABLE_NAME;
        } else if (uri.equals(USER_CONTENT_URI)) {
            return DbOpenHelper.USER_TABLE_NAME;
        }
        return tableName;
    }
}
