package wxj.me.practice.datastore;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import wxj.me.practice.R;

public class DataStoreActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "DataStoreActivity";

    private Button btnFileWrite;
    private Button btnFileRead;
    private Button btnPSRead;
    private Button btnPSWrite;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_store);
        initView();
        storeWithFile();
    }

    private void initView() {
        btnFileWrite = findViewById(R.id.btn_file_write);
        btnFileRead = findViewById(R.id.btn_file_read);
        btnPSWrite = findViewById(R.id.btn_sp_write);
        btnPSRead = findViewById(R.id.btn_sp_read);
        btnFileWrite.setOnClickListener(this);
        btnFileRead.setOnClickListener(this);
        btnPSRead.setOnClickListener(this);
        btnPSWrite.setOnClickListener(this);
    }


    private void storeWithFile() {
        String str = "store data with file";
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput("file_data", Context.MODE_PRIVATE);
            fileOutputStream.write(str.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void getDataWithFile() {
        StringBuilder content = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            FileInputStream fileInputStream = openFileInput("file_data");
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String contentData = content.toString();
        Log.d(TAG, "getDataWithFile: " + contentData);
    }

    private void getDataWithFile1() {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedInputStream bufferedInputStream = null;
        try {
            FileInputStream fileInputStream = openFileInput("file_data");
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            byte[] bytes = new byte[100];
            while (bufferedInputStream.read(bytes) != -1) {
                stringBuilder.append(bytes);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d(TAG, "getDataWithFile1: " + stringBuilder.toString());
    }

    private void storeWithSharedPreferences(){
        mSharedPreferences = getSharedPreferences("sp_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("test","store data with sharedPreferences");
        editor.apply();
    }

    private void getWithSharedPreferences(){
        String contentData = mSharedPreferences.getString("test",null);
        Log.d(TAG, "getWithSharedPreferences: " + contentData);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_file_write:
                storeWithFile();
                break;
            case R.id.btn_file_read:
                getDataWithFile();
                break;
            case R.id.btn_sp_write:
                storeWithSharedPreferences();
                break;
            case R.id.btn_sp_read:
                getWithSharedPreferences();
                break;

        }
    }
}
