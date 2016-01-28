package com.example.laptop.status;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Laptop on 13-Sep-15.
 */
public class OutgoingCallService extends Service {

    WindowManager windowManager ;
    LinearLayout parentLayout;
    WindowManager.LayoutParams layoutParams;
    TextView textView;
    ImageView closeButton;
    Drawable closeButtonDrawable;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            /*  get the phone no.  */
            String phoneNo = intent.getStringExtra("phoneNo");


            windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            layoutParams = new WindowManager.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_SYSTEM_ALERT |
                    WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSPARENT);

            parentLayout = new LinearLayout(getBaseContext());
            parentLayout.setBackgroundColor(Color.rgb(24,174,66));
            parentLayout.setOrientation(LinearLayout.VERTICAL);
            /*  close button  */
            closeButton = new ImageView(getBaseContext());
            LinearLayout.LayoutParams closeButtonlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            closeButtonlp.gravity = Gravity.RIGHT;
            closeButtonlp.setMargins(0,0,2,0);
            closeButton.setLayoutParams(closeButtonlp);
            closeButtonDrawable = getResources().getDrawable(R.drawable.ic_clear_white_18dp);
            closeButton.setBackgroundColor(Color.TRANSPARENT);
            closeButton.setImageDrawable(closeButtonDrawable);
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    windowManager.removeView(parentLayout);
                    stopSelf();
                }
            });

            parentLayout.addView(closeButton);

            /* moving the layout */
            parentLayout.setOnTouchListener(new View.OnTouchListener() {
                /*update layout params on touch*/
                private WindowManager.LayoutParams updateLayoutParams = layoutParams;
                int x,y;
                float touchedX, touchedY;
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction())
                    {
                        case MotionEvent.ACTION_DOWN : {
                            x = updateLayoutParams.x;
                            y = updateLayoutParams.y;
                            touchedX = event.getRawX();
                            touchedY = event.getRawY();
                            break;
                        }
                        case MotionEvent.ACTION_MOVE : {
                            updateLayoutParams.x = (int) (x + (event.getRawX() - touchedX));
                            updateLayoutParams.y = (int) (y + (event.getRawY() - touchedY));
                            windowManager.updateViewLayout(parentLayout,updateLayoutParams);
                        }
                        default: break;
                    }

                    return false;
                }
            });

            textView = new TextView(getBaseContext());
            textView.setText(phoneNo);
            parentLayout.addView(textView);


            windowManager.addView(parentLayout, layoutParams);

        }
        catch (Exception e)
        {
            Log.e("error",e.toString());
        }
        return super.onStartCommand(intent, flags, startId);
    }
}