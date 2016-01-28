package com.example.laptop.status;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Laptop on 19-Sep-15.
 */
public class DataBaseServerService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }


}
