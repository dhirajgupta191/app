package com.example.laptop.status.values;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Laptop on 06-Dec-15.
 */
public class RobotoTextView extends TextView {
    public RobotoTextView(Context context) {
        super(context);
        init();
    }

    public RobotoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public RobotoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public RobotoTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();

    }

    public void init()
    {
        Typeface type = Typeface.createFromAsset(getContext().getAssets(),"Fonts/Roboto_Regular.ttf");
        setTypeface(type);
    }
}
