package com.a0x03.wythe.easytransport.Utils;


import java.io.File;
import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by hbs on 2016/3/19.
 */
public class CustomPost {
    public static  String post(String url, String username, String password, String nickname, String email) throws IOException {

        FormBody.Builder body = new FormBody.Builder();
        FormBody formBody = body
                .add("username", username)
                .add("pwd", password)
                .add("nickname",nickname)
                .add("email",email)
                .add("openid","")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    String post(String url, File avatar) throws IOException {
        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("avatar",avatar.getName(), RequestBody.create(null,
                        avatar))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public static String post(String url,String username,String today,String total) throws IOException {
        FormBody.Builder body = new FormBody.Builder();
        FormBody formBody = body
                .add("my", username)
                .add("today",today)
                .add("total",total)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public static String post(String url, String phone, String passwd) throws IOException {
        FormBody.Builder body = new FormBody.Builder();
        FormBody formBody = body
                .add("phone", phone)
                .add("passwd",passwd)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public static String post(String url, String nickname, String phone,
                              String passwd, String gender, String credit) throws IOException {
        FormBody.Builder body = new FormBody.Builder();
        FormBody formBody = body
                .add("nickname", nickname)
                .add("gender",gender)
                .add("phone",phone)
                .add("credit", credit)
                .add("passwd", passwd)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    static String postMyFeed(String url,byte[] image, String content)
            throws IOException {
        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("haspic","y")
                .addFormDataPart("image","image",
                        RequestBody.create(MultipartBody.FORM,
                        image))
                .addFormDataPart("content",content)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public static String postRank(String url, String username) throws IOException {
        FormBody.Builder body = new FormBody.Builder();
        FormBody formBody = body
                .add("my", username)
                .add("user",username)
                .add("username",username)
//                .add("page","1")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
}
