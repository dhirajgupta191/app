package com.example.laptop.status.Connectivity;

import android.content.Context;
import android.os.AsyncTask;

import com.example.laptop.status.AsyncResultInterface;
import com.example.laptop.status.ToastMessage;
import com.example.laptop.status.Constants.Url;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Laptop on 15-Sep-15.
 */
public class DataBaseServer extends AsyncTask<String, Void, Boolean> {
    Context context;
    BufferedWriter bufferedWriter = null;
    OutputStream outputStream = null;
    private static final String register_url = Url.getRegisterUrl();
    private static final String insertStatus = Url.getInsertStatus();
    private static final String contactNumber = Url.getContactNumber();
    HttpURLConnection httpURLConnection = null;
    InputStream inputStream = null;
    Boolean successful = false;
    String userName, phoneNo, countryCode;
    AsyncResultInterface asyncResultInterface;
    SharePreference sharePreference;
    String message = "";
    /*make flag = -1, if message = "not success"
    * make flag=1, if registration is successful
    * make flag = 2,  if insert status is successful*/
    int flag = 0;

    public DataBaseServer(Context context) {
        this.context = context;
    }

    /* declare interface for success result or fail result..*/
    public void SetOnAsyncResult(AsyncResultInterface asyncResultInterface) {
        if (asyncResultInterface != null) {
            this.asyncResultInterface = asyncResultInterface;
        }
    }


    @Override
    protected Boolean doInBackground(String... params) {

        String method = params[0];
        sharePreference = new SharePreference();

        /* for the registration */
        if (method.equals("register")) {
            userName = params[1];
            phoneNo = params[2];

            try {
                URL url = new URL(register_url);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                outputStream = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                // encode the username and phone_no
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8") + "&" +
                        URLEncoder.encode("phone_no", "UTF-8") + "=" + URLEncoder.encode(phoneNo, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();

                // getting wrong message if the php script is wrong. this wrong message will get by echo in php..
                // inputStream makes a pipe between php and emulator...
                inputStream = httpURLConnection.getInputStream();
                int read = -1;
                while ((read = inputStream.read()) != -1) {
                    char s = (char) read;
                    message = message + s;
                }



            } catch (MalformedURLException e) {
//                Log.e("error", e.toString());
            } catch (ProtocolException e) {

            } catch (UnsupportedEncodingException e) {
            } catch (IOException e) {

            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (bufferedWriter != null) {
                    try {
                        bufferedWriter.close();
                    } catch (IOException e) {
                    }
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                    }
                }

            }
        }

        /* for the insert status..*/
        if (method.equals("insert_status")) {

            String userId = sharePreference.getValue(context, SharePreference.USER_ID);
            String statusMessage = params[1];

            try {
                URL url = new URL(insertStatus);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                outputStream = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                // encode the status_id and status_message
                String data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(userId, "UTF-8") + "&" +
                        URLEncoder.encode("status_message", "UTF-8") + "=" + URLEncoder.encode(statusMessage, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();

                inputStream = httpURLConnection.getInputStream();
                int read = -1;
                while ((read = inputStream.read()) != -1) {
                    char s = (char) read;
                    message = message + s;
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (bufferedWriter != null) {
                    try {
                        bufferedWriter.close();
                    } catch (IOException e) {
                    }
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                    }
                }
            }
        }

        // for check contact number
        if (method.equals("contact_numbers")) {

            String contact_number = params[1];
            String contact_name = params[2];
            String userId = sharePreference.getValue(context, SharePreference.USER_ID);

            try {
                URL url = new URL(contactNumber);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                outputStream = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                // encode the status_id and status_message
                String data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(userId, "UTF-8") + "&" +
                        URLEncoder.encode("contact_number", "UTF-8") + "=" + URLEncoder.encode(contact_number, "UTF-8") + "&" +
                        URLEncoder.encode("contact_name", "UTF-8") + "=" + URLEncoder.encode(contact_name, "UTF-8");


                bufferedWriter.write(data);
                bufferedWriter.flush();

                inputStream = httpURLConnection.getInputStream();
                int read = -1;
                while ((read = inputStream.read()) != -1) {
                    char s = (char) read;
                    message = message + s;
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (bufferedWriter != null) {
                    try {
                        bufferedWriter.close();
                    } catch (IOException e) {
                    }
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                    }
                }
            }
        }

        return successful;
    }


    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        try {
            JSONObject jsonObject = new JSONObject(message);

            asyncResultInterface.OnResultSuccess(message);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
