package com.a0x03.wythe.easytransport.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.a0x03.wythe.easytransport.R;
import com.a0x03.wythe.easytransport.Utils.Data;
import com.a0x03.wythe.easytransport.Utils.SMSSDKData;

import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

public class VerifiyActivity extends AppCompatActivity implements
        View.OnClickListener, OnSendMessageHandler{
    private EditText etPhone;
    private EditText etVerifyCode;
    private Button btConfirm;
    private Button btRequestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifiy);
        initView();
        initSDK();
    }

    private void handleSharedPreData(String phone){
        SharedPreferences sharedPre = getSharedPreferences(
                Data.LOCAL_DATA,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPre.edit();
        editor.putString("phone",phone);
        editor.commit();
    }

    private void initView(){
        etPhone = (EditText) findViewById(R.id.et_phone);
        etVerifyCode = (EditText) findViewById(R.id.et_verifyCode);
//        btConfirm = (Button) findViewById(R.id.bt_confirm);
        btRequestCode = (Button) findViewById(R.id.bt_requestCode);
        btConfirm.setOnClickListener(this);
        btRequestCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String phone = etPhone.getText().toString();
        String verifyCode = etVerifyCode.getText().toString();

        switch (v.getId()){
            case R.id.bt_requestCode:
                SMSSDK.getVerificationCode(SMSSDKData.COUNTRY_CHINA,phone);
                break;

//            case R.id.bt_confirm:
//                SMSSDK.submitVerificationCode(SMSSDKData.COUNTRY_CHINA,phone,verifyCode);
//                break;
        }
    }


    //用于接收SMSSDK回发的验证码
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                Toast.makeText(getApplicationContext(),
                        "获取验证码成功",
                        Toast.LENGTH_SHORT)
                        .show();
            }else if(msg.what == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                Toast.makeText(getApplicationContext(),
                        "发送验证码成功",
                        Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);

                handleSharedPreData(etPhone.getText().toString());
            }
        }
    };


    private void initSDK() {
        SMSSDK.initSDK(this, SMSSDKData.APPKEY,
                SMSSDKData.APPSECRET);
        EventHandler eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Message msg = Message.obtain();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    Log.i("SMSSDK","回调成功");
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        msg.what = SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE;
                        Log.i("SMSSDK","验证码验证成功");
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        msg.what = SMSSDK.EVENT_GET_VERIFICATION_CODE;
                        Log.i("SMSSDK","获取验证码成功");
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                        msg.what = SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES;
                    }
                    handler.sendMessage(msg);
                } else {
                    Log.i("SMSSDK","回调失败");
                    Looper.prepare();
                    Toast.makeText(getApplication(),
                            "验证码错误",
                            Toast.LENGTH_SHORT)
                            .show();
                    Looper.loop();
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    public boolean onSendMessage(String s, String s1) {
        return false;
    }
}
