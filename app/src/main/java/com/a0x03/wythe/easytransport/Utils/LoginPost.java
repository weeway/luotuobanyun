package com.a0x03.wythe.easytransport.Utils;


import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hbs on 2016/3/18.
 */
public class LoginPost {
    OkHttpClient client = new OkHttpClient();
    public String post(String url, String username, String password) throws IOException {

//        RequestBody body =
//                .add("user", username)
//                .add("pwd", password)
//                .build();

        FormBody.Builder body = new FormBody.Builder();
        FormBody formBody = body.add("user", username)
                .add("pwd", password)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

}
