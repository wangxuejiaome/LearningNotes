package wxj.ipclearn.contentprovider;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import wxj.ipclearn.R;

public class ProviderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        Uri uri = Uri.parse("content://wxj.ipclearn.contentprovider.BookProvider");
        getContentResolver().query(uri,null,null,null,null);
        getContentResolver().query(uri,null,null,null,null);
        getContentResolver().query(uri,null,null,null,null);
    }
}
