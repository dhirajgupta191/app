package com.example.laptop.status;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Laptop on 04-Oct-15.
 */
public class ToastMessage {

    public static void showToastMessage(Context context, String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    public  static void showLogMessages(String message)
    {
        Log.e("error",message);
    }
}
