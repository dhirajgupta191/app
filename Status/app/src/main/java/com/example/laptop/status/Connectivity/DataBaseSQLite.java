package com.example.laptop.status.Connectivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.laptop.status.ToastMessage;

/**
 * Created by Laptop on 15-Sep-15.
 */
public class DataBaseSQLite extends SQLiteOpenHelper {

    SQLiteDatabase db;
    ContentValues contentValues;

    public static final String DATABASE_NAME = "STATUS.db";
    public static final int DATABASE_VERSION = 1;
    public static final String ID = "_id";
    /* for the my_status table */
    public static final String MY_STATUS = "my_status"; // status table...
    public static final String USER_STATUS_ID = "user_status_id";  // this is used for deleting the status from the server..
    public static final String MY_STATUS_TEXT = "my_status_text"; //  message posted as a status...
    public static final String CURRENT_STATUS = "current_status"; // this is used for choosing which status is selected...
    public static final String TIME = "time"; // when user uploaded..

    /* for the phoneno and status_id*/
    public static final String MY_DETAILS = "my_details";
    public static final String OTHER_CONTACTS_STATUS_ID = "phone_no";
    /*for the login status*/
   /* public static final String LOGIN_TABLE = "login_table";
    public static final String USER_NAME = "username";
    public static final String COUNTRY_CODE = "country_code";
    public static final String PHONE_NO = "phone_no";
    public static final String LOGIN = "login";*/


    public DataBaseSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            /*saving all the status offline.. for that i am using MY_STATUS..*/
            String createTable = "create table " + MY_STATUS + " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_STATUS_ID + " INTEGER, " +
                                  MY_STATUS_TEXT + " TEXT,"+CURRENT_STATUS+" INTEGER," + TIME + " INTEGER);";
            /*saving the status_id which is unique and the phone number..
            * this is used because i am getting the phone no and status_id
            * quickly and no need to search in the server database*/
            String createTable2 = "create table " +MY_DETAILS +" ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ OTHER_CONTACTS_STATUS_ID + "INTEGER);";
            /* create login table to check whether the user login
            * first time or not....   here ID = user_id.*/
//            String createTable3 = "create table " +LOGIN_TABLE+" ( " +ID+" INTEGER," +USER_NAME+" TEXT, " +COUNTRY_CODE+" TEXT, " +PHONE_NO+ " TEXT, "+LOGIN +" INTEGER);";
            db.execSQL(createTable);
            db.execSQL(createTable2);
//            db.execSQL(createTable3);
        }
        catch (Exception error)
        {
            Log.e("error",error.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + MY_STATUS);
            db.execSQL("DROP TABLE IF EXITS " + MY_DETAILS);
//            db.execSQL("DROP TABLE IF EXITS " + LOGIN_TABLE);

            onCreate(db);
        }
        catch (Exception error)
        {
            Log.e("error",error.toString());
        }
    }

    /*FOR CHECKING IF THE USER LOGIN OR NOT*/
   /* public Cursor checkLoginOrNot(DataBaseSQLite db_connection)
    {
        db = db_connection.getWritableDatabase();
        Cursor cursor = null;
        try {
            String st = "select * from "+LOGIN_TABLE;
            cursor = db.rawQuery(st,null);
            if(cursor != null && cursor.moveToFirst())
            {
                cursor.moveToFirst();
            }
        }
        catch (Exception error)
        {
            Log.e("error",error.toString());
        }
        return cursor; // if the user is not login

    }*/

   /* public void insertIntoLoginTable(DataBaseSQLite db_connection,String user_id,String username, String phoneno, String countryCode)
    {
        try {
            db = db_connection.getWritableDatabase();
            contentValues = new ContentValues();
            contentValues.put(ID, Integer.parseInt(user_id));
            contentValues.put(USER_NAME, username);
            contentValues.put(COUNTRY_CODE,countryCode);
            contentValues.put(PHONE_NO, phoneno);
            contentValues.put(LOGIN, 1); // 1 means user is not deleted his profile..... 0 means user is deleted his profile....
            db.insert(LOGIN_TABLE, null, contentValues);
        }
        catch(Exception error)
        {
            Log.e("error",error.toString());
        }
    }
*/

    public void insertIntoStatusTable(DataBaseSQLite db_connection,String server_id, String text, String time) {
        db = db_connection.getWritableDatabase();
        contentValues = new ContentValues();
        try {
            /* put the values into content */
            contentValues.put(USER_STATUS_ID, Integer.parseInt(server_id));
            contentValues.put(MY_STATUS_TEXT, text);
            contentValues.put(CURRENT_STATUS, 1);
            contentValues.put(TIME, time);

            // insert status text.
            db.insert(MY_STATUS, null, contentValues);
        } catch (Exception error) {
            Log.e("error", error.toString());
        }
    }

    /*change the status id from 1 to 0 bec. to make all the current status to 0..
    * this is called when  when send status button click */
    public void changeCurrentStatus(DataBaseSQLite db_connection) {
        db = db_connection.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(CURRENT_STATUS,0);
        String[] update = {"1"};
        db.update(MY_STATUS,contentValues,CURRENT_STATUS +" =?",update );
    }
    /*change the status  id from 1 to 0...
    * this is called when  when list view of status text click*/
    public void changeCurrentStatus(DataBaseSQLite db_connection,String status_id)
    {
        try {
            db = db_connection.getWritableDatabase();
            contentValues = new ContentValues();
            contentValues.put(CURRENT_STATUS, 1);
            String[] update = {status_id};
            db.update(MY_STATUS, contentValues, ID + " =?", update);
        }
        catch (Exception e)
        {
            ToastMessage.showLogMessages(e.toString());
        }
    }

     /* get the status for the list view...*/
    public Cursor getStatusTable(DataBaseSQLite db_connection) {
        Cursor cursor = null;
        try {
            db = db_connection.getWritableDatabase();

            String st = "select * from " + MY_STATUS + " order by " + ID + " desc";
            cursor = db.rawQuery(st, null);

            if (cursor != null && cursor.moveToFirst()) {
            }


        } catch (Exception error) {
            Log.e("error", error.toString());
        }
        return cursor;
    }

    public Cursor getValidContacts(DataBaseSQLite db_connection)
    {
        Cursor cursor  = null;
        try
        {

        }
        catch (Exception error)
        {
            Log.e("error",error.toString());
        }
        return cursor;
    }

    /* get the current status into the CurrentStatusTextView */
    public Cursor getCurrentStatus(DataBaseSQLite db_connection)
    {
        db = db_connection.getWritableDatabase();
        Cursor cursor = null;
        try
        {
            String[] columns = {MY_STATUS_TEXT, TIME};
            cursor = db.query(MY_STATUS,columns,CURRENT_STATUS +" = 1",null,null,null,null);
            if(cursor != null && cursor.moveToFirst())
            {
                cursor.moveToFirst();
            }
        }
        catch (Exception e)
        {
            ToastMessage.showLogMessages(e.toString());
        }
        return cursor;
    }

}
