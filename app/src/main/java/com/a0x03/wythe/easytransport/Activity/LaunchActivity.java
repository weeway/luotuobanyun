package com.a0x03.wythe.easytransport.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.a0x03.wythe.easytransport.API.APICustomer;
import com.a0x03.wythe.easytransport.Model.Status;
import com.a0x03.wythe.easytransport.R;
import com.a0x03.wythe.easytransport.Upush.MyApplication;
import com.a0x03.wythe.easytransport.Utils.ErrorCode;
import com.a0x03.wythe.easytransport.Utils.LocalSharedPref;
import com.a0x03.wythe.easytransport.Utils.SERVER_INFO;
import com.a0x03.wythe.easytransport.Utils.ToastAssistant;
import com.umeng.message.IUmengCallback;
import com.umeng.message.MessageSharedPrefs;
import com.umeng.message.PushAgent;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LaunchActivity extends AppCompatActivity {

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            preHandleLogin();
        }
    };

    private PushAgent mPushAgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Handler handler = new Handler();
        handler.postDelayed(runnable, 3000);

//        mPushAgent = PushAgent.getInstance(this);
//        mPushAgent.onAppStart();
//
//        if(((MyApplication) this.getApplication()).isPush_status()){
//            mPushAgent.enable(mEnableCallback);
//        }
    }

    Handler handler = new Handler();

    public IUmengCallback mEnableCallback = new IUmengCallback() {

        @Override
        public void onSuccess() {
            MessageSharedPrefs.getInstance(LaunchActivity.this).setIsEnabled(true);
            ((MyApplication) LaunchActivity.this.getApplication()).setPush_status(true);

            handler.post(new Runnable() {

                @Override
                public void run() {
                    ToastAssistant.showToast(getApplicationContext(),
                            "回调开启成功");
                }
            });
        }

        @Override
        public void onFailure(String s, String s1) {

        }
    };

    private void preHandleLogin(){
        LocalSharedPref mSharedPref = LocalSharedPref.getInstance
                (getApplicationContext());
        if(mSharedPref.mData.contains("phone")
                && mSharedPref.isChecked()) {

            loginAsCustomer(mSharedPref.getPhone(),
                    mSharedPref.getPasswd());
        }else{
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(),
                    LoginActivity.class);
            startActivity(intent);
        }
    }

    private void loginAsCustomer(final String phone, final String passwd){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_INFO.host)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APICustomer server = retrofit.create(APICustomer.class);
        Call<Status> call = server.loginAsCustomer(phone, passwd);

        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call,
                                   Response<Status> response) {
                Status status = response.body();
                switch (status.getStatus()){
                    case ErrorCode.Succee:
                        Intent intent = new Intent(getApplicationContext(),
                                MainActivity.class);
                        startActivity(intent);
                        ToastAssistant.showToast(getApplicationContext(),
                                "登陆成功");
                        break;

                    case ErrorCode.PasswdIncorrect:
                        ToastAssistant.showToast(getApplicationContext(),
                                "密码错误");
                        break;

                    case ErrorCode.AccountInvalid:
                        ToastAssistant.showToast(getApplicationContext(),
                            "账号不存在");
                        break;
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                ToastAssistant.showToast(getApplicationContext(),
                        "网络错误");
            }
        });
    }

    @Override
    protected void onStop(){
        super.onStop();
        this.finish();
    }
}
