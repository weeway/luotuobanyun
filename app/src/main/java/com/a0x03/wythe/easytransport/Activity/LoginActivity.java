package com.a0x03.wythe.easytransport.Activity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.a0x03.wythe.easytransport.API.APICustomer;
import com.a0x03.wythe.easytransport.Model.Status;
import com.a0x03.wythe.easytransport.R;
import com.a0x03.wythe.easytransport.Utils.Data;
import com.a0x03.wythe.easytransport.Utils.ErrorCode;
import com.a0x03.wythe.easytransport.Utils.LocalSharedPref;
import com.a0x03.wythe.easytransport.Utils.SERVER_INFO;
import com.a0x03.wythe.easytransport.Utils.ToastAssistant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends Activity implements
        View.OnClickListener{

    private EditText etPhone;
    private EditText etPwd;
    private Button btLogin;
    private Button btRegister;
    private CheckBox mCheckbox;
    ProgressDialog mProgressDialog;

    Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message inputMessage){
            mProgressDialog.dismiss();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViewAndSetListener();
//        preHandleLogin();
    }

    private void preHandleLogin(){
        LocalSharedPref mSharedPref = LocalSharedPref.getInstance
                (getApplicationContext());
        if(mSharedPref.mData.contains("phone")
                && mSharedPref.isChecked()) {

            loginAsCustomer(mSharedPref.getPhone(),
                    mSharedPref.getPasswd());
        }
    }

    private void initViewAndSetListener(){
        btLogin = (Button) findViewById(R.id.bt_login);
        btRegister = (Button) findViewById(R.id.bt_register);
        etPhone = (EditText) findViewById(R.id.et_username);
        etPwd = (EditText) findViewById(R.id.et_password);
        mCheckbox = (CheckBox) findViewById(R.id.id_checkbox);
        mCheckbox.setChecked(true);
        btRegister.setOnClickListener(this);
        btLogin.setOnClickListener(this);
        setDefaultAccount();
    }

    private void setDefaultAccount(){
        LocalSharedPref mSharedPref = LocalSharedPref.getInstance
                (getApplicationContext());

        if(mSharedPref.mData.contains("phone")){
            etPhone.setText(mSharedPref.getPhone());
            etPwd.setText(mSharedPref.getPasswd());
        }
    }

    private void loginAsCustomer(final String phone, final String passwd){
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show(this, "", "", true, false);

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
                        Message msg = Message.obtain();
                        mHandler.dispatchMessage(msg);
                        Intent intent = new Intent(getApplicationContext(),
                                MainActivity.class);
                        startActivity(intent);
                        ToastAssistant.showToast(getApplicationContext(),
                                "登陆成功");
                        LocalSharedPref mSharePref = LocalSharedPref.
                                getInstance(getApplicationContext());
                        mSharePref.setPasswd(passwd);
                        mSharePref.setPhone(phone);
                        mSharePref.setChecked(mCheckbox.isChecked());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login:
                String phone = etPhone.getText().toString();
                String pwd = etPwd.getText().toString();
                loginAsCustomer(phone, pwd);
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etPwd.getWindowToken(), 0);
                break;
            case R.id.bt_register:
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
