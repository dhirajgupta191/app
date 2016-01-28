package com.example.laptop.status;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by Laptop on 13-Sep-15.
 */
public class OutgoingBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        /* get the phone no */
        try {
            String phoneNo = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            intent = new Intent(context,OutgoingCallService.class);
//            context.startService(new Intent(context,OutgoingCallService.class));
            intent.putExtra("phoneNo",phoneNo);
            context.startService(intent);

        }
        catch (Exception error)
        {
            Log.e("error",error.toString());
        }


    }
}