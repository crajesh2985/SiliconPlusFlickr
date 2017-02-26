package com.app.siliconplus.flickr.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.app.siliconplus.flickr.appconstant.AppConstant;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;

public class CommonUtils {

    private CommonUtils() {
    }


    public static String getSignatureMD5(String value) {
        String md5Text = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            md5Text = new BigInteger(1, digest.digest((value).getBytes())).toString(16);
        } catch (Exception e) {
            System.out.println("Error in call to MD5");
        }

        if (md5Text.length() == 31) {
            md5Text = "0" + md5Text;
        }
        return md5Text;
    }

    public static String callHttpConnectivity(String serviceUrl, String serviceMethod) {
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        String response = null;
        try {
            URL url = new URL(serviceUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            //urlConnection.setRequestProperty("Content-Type", "application/json");
            //urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod(serviceMethod);
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == 200) {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                response = convertInputStreamToString(inputStream);
            }
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
        urlConnection.disconnect();
        return response;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }
        if (null != inputStream) {
            inputStream.close();
        }
        return result;
    }

    public static String authenticationURL(String frob) {
        String sig = AppConstant.FLICKR_SECRET +
                AppConstant.API_KEY + AppConstant.FLICKR_KEY +
                AppConstant.FROB + frob +
                AppConstant.PERMS + AppConstant.WRITE;

        String signature = getSignatureMD5(sig);
        String requestURL = AppConstant.OAUTH_BASE_URL +
                AppConstant.API_KEY + "=" + AppConstant.FLICKR_KEY +
                "&" + AppConstant.PERMS + "=" + AppConstant.WRITE +
                "&" + AppConstant.FROB + "=" + frob +
                "&" + AppConstant.API_SIG + "=" + signature;
        return requestURL;

    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        boolean flag = netInfo != null && netInfo.isConnected();
        if(!flag) {
            Toast.makeText(context, "Please check the network...", Toast.LENGTH_SHORT).show();
        }
        return flag;

    }

}
