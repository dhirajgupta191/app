package com.example.laptop.status;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.laptop.status.Connectivity.SharePreference;
import com.example.laptop.status.Constants.Url;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Laptop on 13-Nov-15.
 */
public class ProfileSettings extends AppCompatActivity implements View.OnClickListener {

    ImageView profilePic,coverPic;
    
    Button chooseImageButton;
    LinearLayout profileSettingsLinearLayout;
    // get the width of the linear layout, that layout is the profile settings layout.
    int profileSettingLayoutWidth = 0;
    // image choose request code
    private int PICK_IMAGE_CODE = 1;
    Bitmap bitmap;
    private Uri imagePathUri,mImageCaptureUri;
    private static final int CAMERA_CODE = 100;
    private static final int GALLERY_CODE = 101;
    private static final int CROP_CODE = 102;

    // URL connection
    URL url;
    HttpURLConnection connection = null;
    InputStream inputStream = null;
    OutputStream outputStream = null;
    BufferedWriter bufferedWriter = null;
    String msg = "";
    // shared preference
    SharePreference sharePreference;


    private static  int windowX = 0;
    private static  int windowY = 0 ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_settings);

        chooseImageButton = (Button) findViewById(R.id.chooseImageButton);
            // profile pic image view id
        profilePic = (ImageView) findViewById(R.id.profilePic);
        coverPic = (ImageView) findViewById(R.id.coverPic);

//      set the cover pic size according to the size of the window
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        windowX = displayMetrics.widthPixels;
        windowY = windowX/2;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(windowX,windowY);
        profilePic.setLayoutParams(layoutParams);


        //      set the profile pic size according to the size of the window
        RelativeLayout.LayoutParams profile_pic = new RelativeLayout.LayoutParams(170,170);
        profile_pic .topMargin = windowY-85;
        profile_pic.leftMargin = 20;
        coverPic.setLayoutParams(profile_pic);




        /*sharePreference = new SharePreference(getBaseContext());
        String encoded_profile_pic_value = sharePreference.getValue(SharePreference.PROFILE_PIC);*/
        sharePreference = new SharePreference();
        String encoded_profile_pic_value = sharePreference.getValue(getBaseContext(),SharePreference.PROFILE_PIC);
        getImage(encoded_profile_pic_value);


            // choose image from gallery
        chooseImageButton.setOnClickListener(this);


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_CODE && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
//            Toast.makeText(ProfileSettings.this,"done",Toast.LENGTH_LONG).show();
            // for camera pic
            imagePathUri = data.getData();
            CropImage(imagePathUri);

        }
        if(requestCode == GALLERY_CODE && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            // for gallery pic
            imagePathUri = data.getData();
            CropImage(imagePathUri);


        }

        if(requestCode == CROP_CODE && resultCode == RESULT_OK && data != null)
        {
            Bundle bundle = data.getExtras();

            bitmap = bundle.getParcelable("data");

                // get the path of the image
//                String imagePath = getPath(filePath);

                // upload the image to the php..
            uploadProfilePic up =  new uploadProfilePic(bitmap);
                up.execute();


        }
    }
//
//    public String getPath(Uri filePath)
//    {
//        String imagepath = "";
//        String[] projection = { MediaStore.Images.Media.DATA };
//        Cursor cursor = getContentResolver().query(filePath,projection,null,null,null);
//        if(cursor.moveToFirst())
//        {
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            imagepath = cursor.getString(column_index);
//        }
//
//        return imagepath;
//    }


    public void CropImage(Uri imagePath)
    {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(imagePath,"image/*");
        int x = windowX / 4;
        int y = x/2;
        cropIntent.putExtra("aspectX",x);
        cropIntent.putExtra("aspectY",y);
        cropIntent.putExtra("outputX",windowX);
        cropIntent.putExtra("outputY",windowX/2);
        cropIntent.putExtra("return-data", true);
        startActivityForResult(cropIntent,CROP_CODE);

    }
    AsyncResultInterface asyncResultInterface = new AsyncResultInterface() {


        @Override
        public void OnResultSuccess(String message) {

        }

        @Override
        public void OnResultSuccess(int flag, String server_id) {
            // insert Status successfully..
        }

        @Override
        public void OnResultSuccess() {
            // upload profile pic..

        }

        @Override
        public void OnResultFail() {

        }
    };

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.chooseImageButton:
                // call the alert dialog options
                selectImageOption();
                break;
        }
    }

    public void selectImageOption()
    {
        final CharSequence[] items  = {"Camera","Gallery","Remove Pic"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileSettings.this);
        builder.setTitle("Choose profile photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Camera")) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, CAMERA_CODE);
                    }

                }
                if (items[which].equals("Gallery")) {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, GALLERY_CODE);
                }
                if (items[which].equals("Remove Pic")) {
                    getImage("remove");
                }

            }
        });
        builder.show();
    }

    public void getImage(String _value)
    {
        if(_value.equals("remove"))
        {
            /*sharedPreferences = getSharedPreferences(SharePreference.PROFILE_PIC, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();*/
            sharePreference.clearData(getBaseContext(),sharePreference.PROFILE_PIC);
            profilePic.setImageResource(R.drawable.defaultprofilepicicon);
        }
        else if (_value.equals(sharePreference.DEFAULT_VALUE))
        {

            profilePic.setImageResource(R.drawable.defaultprofilepicicon);
        }
        else{
            // store the image into the share preferrences.
            /*sharedPreferences = getSharedPreferences(SharePreference.PROFILE_PIC, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(SharePreference.PROFILE_PIC, _value);
            editor.commit();*/
            sharePreference.setValue(getBaseContext(),sharePreference.PROFILE_PIC,_value);

            byte[] imageAsByte = Base64.decode(_value, Base64.DEFAULT);
            profilePic.setImageBitmap(BitmapFactory.decodeByteArray(imageAsByte, 0, imageAsByte.length));

        }
    }

    class uploadProfilePic extends AsyncTask<Void, Void, Void> {
        String message = "";
        Bitmap image;
        String encodedImage="";
        uploadProfilePic(Bitmap _image)
        {
            image = _image;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {



            try {
                url = new URL(Url.getUploadImage());
                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                outputStream = connection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data = URLEncoder.encode("profile_pic", "UTF-8") + "=" + URLEncoder.encode(encodedImage, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();

                inputStream = connection.getInputStream();
                int read = -1;
//                byte[] buffer = new byte[1024];
//                while((read = inputStream.read(buffer)) != -1)
                while((read = inputStream.read()) != -1)
                {
                    char s ;
                    s = (char) read;
                    msg = msg + s;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (connection != null) {
                    connection.disconnect();
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

            return null;
        }

            @Override
        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected void onPostExecute(Void aVoid) {
//             ToastMessage.showLogMessages("post successfully");

            // get the json object
            try {
                JSONObject jsonObject = new JSONObject(msg);
                if(jsonObject.get("status").equals(1))
                {
                    String message = jsonObject.get("msg").toString();
                    getImage(encodedImage);
                }
                else
                {
                    Toast.makeText(ProfileSettings.this, "Cannot upload a profile picture", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                ToastMessage.showToastMessage(getBaseContext(),"Some error in json query");
            }
        }
    }

}
