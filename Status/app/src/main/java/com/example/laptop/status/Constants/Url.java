package com.example.laptop.status.Constants;

/**
 * Created by Laptop on 04-Dec-15.
 */
public class Url {
    //    private static final String baseUrl = "http://192.168.137.1:80/status/";
//    private static final String baseUrl = "http://192.168.137.65:80/status/";
    private static final String baseUrl = "http://172.20.79.26:80/status/";
//    private static final String baseUrl = "http://192.168.1.109:80/status/";

    private static final String registerUrl = baseUrl + "registration.php";
    private static final String insertStatus = baseUrl + "insert_status.php";
    private static final String uploadImage = baseUrl + "upload_profile_pic.php";
    private static final String contactNumber = baseUrl + "registered_number_info.php";


    public static String getContactNumber() {return contactNumber;}

    public static String getInsertStatus() {
        return insertStatus;
    }

    public static String getRegisterUrl() {
        return registerUrl;
    }


    public static String getUploadImage() {
        return uploadImage;
    }

}
