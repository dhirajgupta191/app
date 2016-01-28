package com.example.laptop.status.Connectivity;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Laptop on 17-Dec-15.
 */
public class SharePreference {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    // default value of profile pic
    public static final String DEFAULT_VALUE = "N/A";
    private static final String SHARED_PREFERENCE_NAME = "status";

    public static final String PROFILE_PIC = "profile_pic";
    public static final String USER_NAME = "user_name";
    public static final String PHONE_NO = "phone_no";
    public static final String USER_ID= "user_id";
    public static final String COUNTRY_CODE = "country_code";


    public  void setValue(Context context, String Key,String value)
    {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(Key,value);
        editor.commit();
    }

    public  String getValue(Context context, String Key)
    {
        String value="";
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        value = sharedPreferences.getString(Key,DEFAULT_VALUE);
        return value;
    }

    public void clearData(Context context, String Key)
    {
        sharedPreferences = context.getSharedPreferences(Key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }


}
