package com.a0x03.wythe.easytransport.Activity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.a0x03.wythe.easytransport.API.APICustomer;
import com.a0x03.wythe.easytransport.Model.IndentList;
import com.a0x03.wythe.easytransport.Model.Status;
import com.a0x03.wythe.easytransport.R;
import com.a0x03.wythe.easytransport.Utils.CustomPost;
import com.a0x03.wythe.easytransport.Utils.Data;
import com.a0x03.wythe.easytransport.Utils.ServerInfo;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.text.ParseException;

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

    //public  static String  USER_NAME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViewAndSetListener();
        preHandleLogin();
    }

    private void preHandleLogin(){
        SharedPreferences sharePref = getSharedPreferences(
                Data.LOCAL_DATA,
                Context.MODE_PRIVATE);
        if(sharePref.contains("phone")
                && sharePref.getBoolean("checked",false)) {
            loginAsCustomer(sharePref.getString("phone",""),
                    sharePref.getString("passwd", ""));
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
        SharedPreferences sharedPre = getSharedPreferences(Data.LOCAL_DATA,Context.MODE_PRIVATE);
        if(sharedPre.contains("phone")){
            etPhone.setText(sharedPre.getString("phone"," "));
            etPwd.setText(sharedPre.getString("passwd"," "));
        }
    }

    private void loginAsCustomer(final String phone, final String passwd){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerInfo.host)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APICustomer server = retrofit.create(APICustomer.class);
        Call<Status> call = server.loginAsCustomer(phone, passwd);
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Status status = response.body();

                if(status.getStatus().equals(0)){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "登陆成功",
                            Toast.LENGTH_SHORT)
                            .show();
                    SharedPreferences sharedPref = getSharedPreferences(
                            Data.LOCAL_DATA,
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("phone",phone);
                    editor.putString("passwd",passwd);
                    editor.putBoolean("checked",mCheckbox.isChecked());
                    editor.commit();
                }else if(status.getStatus().equals(1)){
                    Toast.makeText(getApplicationContext(), "密码错误",
                            Toast.LENGTH_SHORT)
                            .show();
                }else if(status.getStatus().equals(2)){
                    Toast.makeText(getApplicationContext(), "账号不存在",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

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
                break;
            case R.id.bt_register:
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
