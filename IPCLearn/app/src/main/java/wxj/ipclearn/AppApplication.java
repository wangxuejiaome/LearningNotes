package wxj.ipclearn;

import android.app.Application;
import android.util.Log;

public class AppApplication extends Application {
    
    public static final String TAG = "AppApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }
}

