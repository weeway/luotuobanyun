package com.a0x03.wythe.easytransport.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.a0x03.wythe.easytransport.API.APICustomer;
import com.a0x03.wythe.easytransport.Model.Succee;
import com.a0x03.wythe.easytransport.R;
import com.a0x03.wythe.easytransport.Utils.Data;
import com.a0x03.wythe.easytransport.Utils.SERVER_INFO;
import com.a0x03.wythe.easytransport.Utils.ToastAssistant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EvaluateActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    //checkbox
    private CheckBox[][] mChkBoxs;

    //values
    private int[] mCredit = new int[3];
    private String mIdIndent;
    private String mComment;
    private CheckBox[] mLabel;
    private int[] a = new int[4];
    private String phoneDriver;
    private EditText mEdtComment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        initCheckBoxs();
        init();
    }

    public void onToggleBack(View view) {
        Intent intent = new Intent(getApplicationContext(),IndentAllActivity.class);
        startActivity(intent);

    }

    private void init(){
        mEdtComment = (EditText) findViewById(R.id.edt_evaluate_comment);
        initCheckBoxs();
    }

    private void initCheckBoxs(){
        mChkBoxs = new CheckBox[3][5];
        mChkBoxs[0][0] = (CheckBox) findViewById(R.id.chk_evaluate_attitude_1);
        mChkBoxs[0][1] = (CheckBox) findViewById(R.id.chk_evaluate_attitude_2);
        mChkBoxs[0][2] = (CheckBox) findViewById(R.id.chk_evaluate_attitude_3);
        mChkBoxs[0][3] = (CheckBox) findViewById(R.id.chk_evaluate_attitude_4);
        mChkBoxs[0][4] = (CheckBox) findViewById(R.id.chk_evaluate_attitude_5);

        mChkBoxs[1][0] = (CheckBox) findViewById(R.id.chk_evaluate_speed_1);
        mChkBoxs[1][1] = (CheckBox) findViewById(R.id.chk_evaluate_speed_2);
        mChkBoxs[1][2] = (CheckBox) findViewById(R.id.chk_evaluate_speed_3);
        mChkBoxs[1][3] = (CheckBox) findViewById(R.id.chk_evaluate_speed_4);
        mChkBoxs[1][4] = (CheckBox) findViewById(R.id.chk_evaluate_speed_5);

        mChkBoxs[2][0] = (CheckBox) findViewById(R.id.chk_evaluate_price_1);
        mChkBoxs[2][1] = (CheckBox) findViewById(R.id.chk_evaluate_price_2);
        mChkBoxs[2][2] = (CheckBox) findViewById(R.id.chk_evaluate_price_3);
        mChkBoxs[2][3] = (CheckBox) findViewById(R.id.chk_evaluate_price_4);
        mChkBoxs[2][4] = (CheckBox) findViewById(R.id.chk_evaluate_price_5);

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 5; j++){
                mChkBoxs[i][j].setOnCheckedChangeListener(this);
            }
        }

        mLabel = new CheckBox[4];
        mLabel[0] = (CheckBox) findViewById(R.id.chk_evaluate_label1);
        mLabel[1] = (CheckBox) findViewById(R.id.chk_evaluate_label2);
        mLabel[2] = (CheckBox) findViewById(R.id.chk_evaluate_label3);
        mLabel[3] = (CheckBox) findViewById(R.id.chk_evaluate_label4);

        for(int i = 0; i < 4; i++){
            mLabel[i].setOnCheckedChangeListener(this);
        }

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            switch (buttonView.getId()){
                case R.id.chk_evaluate_attitude_5:
                    mChkBoxs[0][4].setChecked(isChecked);
                case R.id.chk_evaluate_attitude_4:
                    mChkBoxs[0][3].setChecked(isChecked);
                case R.id.chk_evaluate_attitude_3:
                    mChkBoxs[0][2].setChecked(isChecked);
                case R.id.chk_evaluate_attitude_2:
                    mChkBoxs[0][1].setChecked(isChecked);
                case R.id.chk_evaluate_attitude_1:
                    mChkBoxs[0][0].setChecked(isChecked);
                    Log.i("checkbox","attitude");
                    break;

                case R.id.chk_evaluate_speed_5:
                    mChkBoxs[1][4].setChecked(isChecked);
                case R.id.chk_evaluate_speed_4:
                    mChkBoxs[1][3].setChecked(isChecked);
                case R.id.chk_evaluate_speed_3:
                    mChkBoxs[1][2].setChecked(isChecked);
                case R.id.chk_evaluate_speed_2:
                    mChkBoxs[1][1].setChecked(isChecked);
                case R.id.chk_evaluate_speed_1:
                    mChkBoxs[1][0].setChecked(isChecked);
                    Log.i("check","speed");
                    break;

                case R.id.chk_evaluate_price_5:
                    mChkBoxs[2][4].setChecked(isChecked);
                case R.id.chk_evaluate_price_4:
                    mChkBoxs[2][3].setChecked(isChecked);
                case R.id.chk_evaluate_price_3:
                    mChkBoxs[2][2].setChecked(isChecked);
                case R.id.chk_evaluate_price_2:
                    mChkBoxs[2][1].setChecked(isChecked);
                case R.id.chk_evaluate_price_1:
                    mChkBoxs[2][0].setChecked(isChecked);
                    Log.i("checkbox","price");
                    break;

                case R.id.chk_evaluate_label1:
                    a[0] = 1;
                    break;
                case R.id.chk_evaluate_label2:
                    a[1] = 1;
                    break;
                case R.id.chk_evaluate_label3:
                    a[2] = 1;
                    break;
                case R.id.chk_evaluate_label4:
                    a[3] = 1;
                    break;
            }
        }else{
            switch (buttonView.getId()){
                case R.id.chk_evaluate_attitude_1:
                    mChkBoxs[0][0].setChecked(isChecked);
                case R.id.chk_evaluate_attitude_2:
                    mChkBoxs[0][1].setChecked(isChecked);
                case R.id.chk_evaluate_attitude_3:
                    mChkBoxs[0][2].setChecked(isChecked);
                case R.id.chk_evaluate_attitude_4:
                    mChkBoxs[0][3].setChecked(isChecked);
                case R.id.chk_evaluate_attitude_5:
                    mChkBoxs[0][4].setChecked(isChecked);
                    Log.i("checkbox","u attitude");
                    break;

                case R.id.chk_evaluate_speed_1:
                    mChkBoxs[1][0].setChecked(isChecked);
                case R.id.chk_evaluate_speed_2:
                    mChkBoxs[1][1].setChecked(isChecked);
                case R.id.chk_evaluate_speed_3:
                    mChkBoxs[1][2].setChecked(isChecked);
                case R.id.chk_evaluate_speed_4:
                    mChkBoxs[1][3].setChecked(isChecked);
                case R.id.chk_evaluate_speed_5:
                    mChkBoxs[1][4].setChecked(isChecked);
                    Log.i("checkbox","u speed");
                    break;

                case R.id.chk_evaluate_price_1:
                    mChkBoxs[2][0].setChecked(isChecked);
                case R.id.chk_evaluate_price_2:
                    mChkBoxs[2][1].setChecked(isChecked);
                case R.id.chk_evaluate_price_3:
                    mChkBoxs[2][2].setChecked(isChecked);
                case R.id.chk_evaluate_price_4:
                    mChkBoxs[2][3].setChecked(isChecked);
                case R.id.chk_evaluate_price_5:
                    mChkBoxs[2][4].setChecked(isChecked);
                    Log.i("checkbox","u price");
                    break;

                case R.id.chk_evaluate_label1:
                    a[0] = 0;
                    break;
                case R.id.chk_evaluate_label2:
                    a[1] = 0;
                    break;
                case R.id.chk_evaluate_label3:
                    a[2] = 0;
                    break;
                case R.id.chk_evaluate_label4:
                    a[3] = 0;
                    break;
            }
        }
    }

    private void getCreditValues(){
        for(int i = 0; i < 3 ; i++){
            int j = 0;
            for(  ; j < 5; j++){
                if(!mChkBoxs[i][j].isChecked()){
                    break;
                }
            }
            mCredit[i]=j;
        }
    }


    public void onEvaluate(View view) {
        getInfo();
        getCreditValues();
        sendEvaluation();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void getInfo() {
        Intent intent = getIntent();
        phoneDriver = intent.getStringExtra(Data.INDENT.PHONEDRIVER);
        mIdIndent = intent.getStringExtra(Data.INDENT.IDINDENT);
        mComment = mEdtComment.getText().toString();
    }

    private void sendEvaluation() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_INFO.host)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APICustomer server = retrofit.create(APICustomer.class);
        Log.i("evaluate",mIdIndent+'\n'+phoneDriver);
        Call<Succee> call = server.evaluateIndent(mIdIndent,phoneDriver, mCredit[0], mCredit[1], mCredit[2],
                mComment,a[0],a[1],a[2],a[3]);

        call.enqueue(new Callback<Succee>() {
            @Override
            public void onResponse(Call<Succee> call, Response<Succee> response) {

            }

            @Override
            public void onFailure(Call<Succee> call, Throwable t) {
                ToastAssistant.showToast(getApplicationContext(), "网络异常");
            }
        });
    }

    @Override
    protected void onStop(){
        super.onStop();
        this.finish();
    }
}
