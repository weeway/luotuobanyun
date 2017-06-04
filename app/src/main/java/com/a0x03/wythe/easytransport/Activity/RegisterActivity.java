package com.a0x03.wythe.easytransport.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.a0x03.wythe.easytransport.API.APICustomer;
import com.a0x03.wythe.easytransport.Model.Status;
import com.a0x03.wythe.easytransport.R;
import com.a0x03.wythe.easytransport.Utils.Data;
import com.a0x03.wythe.easytransport.Utils.SMSSDKData;
import com.a0x03.wythe.easytransport.Utils.SERVER_INFO;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegisterActivity extends AppCompatActivity implements
        View.OnClickListener, OnSendMessageHandler{

    private int LAST_TIME_LENGTH = 60;
    private EditText etNickname;
    private EditText etPasswd;
    private EditText etconfirmPass;
    private Button btSubmit;
    private EditText etPhone;
    private EditText etVerifyCode;
    private Button btRequestCode;
    private RadioGroup mRadioGroup;
    private ImageButton mButtonBack;
    private TextView mTimer;


    Handler mTimerHandler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(LAST_TIME_LENGTH > 0){
                LAST_TIME_LENGTH--;
                mTimer.setText("("+LAST_TIME_LENGTH+")"+"s");
                mTimerHandler.postDelayed(this, 1000);
            }
            if(LAST_TIME_LENGTH == 0){
                LAST_TIME_LENGTH = 60;
                mTimer.setText("");
                etPhone.setEnabled(true);
                btRequestCode.setEnabled(true);
                btRequestCode.setBackgroundResource(R.drawable.login_button_shape);
                mTimerHandler.removeCallbacks(this);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initSDK();
    }


    private void initView(){
        etNickname = (EditText) findViewById(R.id.et_nickname);
        etPasswd = (EditText) findViewById(R.id.et_passwd);
        mButtonBack = (ImageButton) findViewById(R.id.id_reg_to_log);
        etconfirmPass = (EditText) findViewById(R.id.et_confirmPasswd);
        btSubmit = (Button) findViewById(R.id.bt_submit);
        mTimer = (TextView) findViewById(R.id.id_timer);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etVerifyCode = (EditText) findViewById(R.id.et_verifyCode);
        btRequestCode = (Button) findViewById(R.id.bt_requestCode);
        mRadioGroup = (RadioGroup) findViewById(R.id.id_radio);
        btRequestCode.setOnClickListener(this);
        btSubmit.setOnClickListener(this);
        mButtonBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String password = etPasswd.getText().toString();
        String confirmPass = etconfirmPass.getText().toString();
        String phone = etPhone.getText().toString();
        String verifyCode = etVerifyCode.getText().toString();
        switch (v.getId()){
            case R.id.bt_submit:
                if(confirmPass.equals(password)){
                    SMSSDK.submitVerificationCode(SMSSDKData.COUNTRY_CHINA,phone,verifyCode);
                }else{
                    Toast.makeText(getApplicationContext(),
                            "密码不一致",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_requestCode:
                SMSSDK.getVerificationCode(SMSSDKData.COUNTRY_CHINA,phone);
                etPhone.setEnabled(false);
                btRequestCode.setEnabled(false);
                btRequestCode.setBackgroundResource(R.drawable.login_button_pressed);
                mTimerHandler.postDelayed(runnable,1000);
                break;
            case R.id.id_reg_to_log:
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
        }
    }

    private void registerAsCustomer(final String nickname, final String phone,
                                    final String passwd, final String gender)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_INFO.host)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final APICustomer server = retrofit.create(APICustomer.class);
        Call<Status> call = server.registerAsCustomer(nickname, gender, phone, passwd);
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Status status = response.body();

                if(status.getStatus().equals("0")){
                    Intent intent = new Intent(getApplicationContext(),
                            LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "注册成功",
                            Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPre = getSharedPreferences(Data.LOCAL_DATA,Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPre.edit();
                    editor.putString(Data.INDIVIDUAL.PASSWD,passwd);
                    editor.putString(Data.INDIVIDUAL.PHONE,phone);
                    editor.putString(Data.INDIVIDUAL.GENDER,gender);
                    editor.putString(Data.INDIVIDUAL.NICKNAME,nickname);
                    editor.commit();
                }else if(status.getStatus().equals("1")){
                    Toast.makeText(getApplicationContext(), "注册失败",
                            Toast.LENGTH_SHORT).show();
                }else if(status.getStatus().equals("2")){
                    Toast.makeText(getApplicationContext(), "手机机号已被注册",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"请检查网络",
                        Toast.LENGTH_SHORT).show();
            }
        });
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
                HashMap<String,String> info = (HashMap<String,String>)(msg.obj);
                String nickname = info.get("nickname");
                String phone = info.get("phone");
                String password = info.get("password");
                String gender = info.get("gender");
                String credit = info.get("credit");
                Log.i("resister",nickname+"\n"+phone+"\n"+password+"\n"+gender+"\n"+credit);
//                register(nickname,phone,password,gender,credit);
                registerAsCustomer(nickname,phone,password,gender);
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
//                msg.obj = data;
                HashMap<String,String> info = new HashMap<>();
                RadioButton radioButton = (RadioButton) findViewById(mRadioGroup.getCheckedRadioButtonId());
                String gender = radioButton.getText().toString();
                String password = etPasswd.getText().toString();
                String phone = etPhone.getText().toString();
                String nickname = etNickname.getText().toString();
                String credit = "0";
                info.put("gender",gender);
                info.put("password",password);
                info.put("phone",phone);
                info.put("nickname",nickname);
                info.put("credit",credit);
                msg.obj = info;
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
                            "获取验证码失败",
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

    @Override
    protected void onStop(){
        super.onStop();
        this.finish();
    }
}
