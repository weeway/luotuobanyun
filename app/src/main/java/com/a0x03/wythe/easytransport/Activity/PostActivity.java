package com.a0x03.wythe.easytransport.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.a0x03.wythe.easytransport.API.APICustomer;
import com.a0x03.wythe.easytransport.MapUtils.RouteTask;
import com.a0x03.wythe.easytransport.Model.Succee;
import com.a0x03.wythe.easytransport.R;
import com.a0x03.wythe.easytransport.Utils.Data;
import com.a0x03.wythe.easytransport.Utils.ServerInfo;
import com.a0x03.wythe.easytransport.Utils.ToastAssistant;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressLint("SimpleDateFormat")
public class PostActivity extends AppCompatActivity implements
        RadioGroup.OnCheckedChangeListener{

    private LinearLayout mSelectTime;
    private Spinner mSpinTruckType;
    private Spinner mSpinTruckSpecLen;
    private Spinner mSpinGoodsType;
    private Spinner mSpinTruckSpecWeight;
    private TextView mStartLoc;
    private TextView mDestLoc;
    private TextView mCost;
    private TextView mDate;
    private TextView mName;
    private TextView mPhone;
    private RadioGroup mRadioGroup2;
    private EditText mGoodsWei;
    private EditText mGoodsVol;
    private SimpleDateFormat mDateFormatter = new SimpleDateFormat("yyyy/MM/dd");
    private SimpleDateFormat mTimeFormatter = new SimpleDateFormat("HH:mm");


    private boolean flag = false;


    //发布的数据
    private double cost;
    private String phoneCusomter;
    private String setoutDate;
    private String setoutTime;
    private String publishTime;
    private String publishDate;
    private String carType;
    private String startLoc;
    private String endLoc;
    private String isNeedLoad;
    private double sLat;
    private double sLon;
    private double eLat;
    private double eLon;
    private double carLen;
    private double carWei;
    private String goodsType;
    private double goodsWei;
    private double goodsVol;


    Pattern pattern = Pattern.compile("[0-9]+", Pattern.CASE_INSENSITIVE);
    Matcher matcher1;
    Matcher matcher2;

    private SlideDateTimeListener mListener = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date)
        {
            flag = true;
            mDate.setText(mDateFormatter.format(date)+"  "+mTimeFormatter.format(date));
            setoutDate = mDateFormatter.format(date);
            setoutTime = mTimeFormatter.format(date);
            Log.i("publish", setoutDate);
            Log.i("publish", setoutTime);
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel()
        {
            Toast.makeText(PostActivity.this,
                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        init();
    }

    private void init(){
        mSpinGoodsType = (Spinner) findViewById(R.id.spin_goods_type);
        mSpinTruckSpecLen = (Spinner) findViewById(R.id.spin_truck_spec_len);
        mSpinTruckSpecWeight = (Spinner) findViewById(R.id.spin_truck_spec_weight);
        mSpinTruckType = (Spinner) findViewById(R.id.spin_truck_type);

        mGoodsVol = (EditText) findViewById(R.id.edv_post_goods_vol);
        mGoodsWei = (EditText) findViewById(R.id.edv_post_goods_wei);
        mSelectTime = (LinearLayout) findViewById(R.id.id_select_time);
        mStartLoc = (TextView) findViewById(R.id.tvMyLoc);
        mDestLoc = (TextView) findViewById(R.id.tvDest);
        mCost = (TextView) findViewById(R.id.tv_post_cost);
        mRadioGroup2 = (RadioGroup) findViewById(R.id.radio_select_upload);
        mDate = (TextView) findViewById(R.id.tv_post_date);
        mName = (TextView) findViewById(R.id.tv_post_name);
        mPhone = (TextView) findViewById(R.id.tv_post_phone);
        mRadioGroup2.setOnCheckedChangeListener(this);

        setInfo();
        initSpins();
    }

    private void initSpins(){
        String mTruckType[] = getResources().getStringArray(R.array.spinnerTruckType);
        String mTruckSpecLen[] = getResources().getStringArray(R.array.spinnerTruckSpecLen);
        String mTruckSpecWei[] = getResources().getStringArray(R.array.spinnerTruckSpecWei);
        String mGoodsType[] = getResources().getStringArray(R.array.spinnerGoodsType);
        mSpinTruckSpecLen.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mTruckSpecLen));
        mSpinTruckType.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mTruckType));
        mSpinGoodsType.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mGoodsType));
        mSpinTruckSpecWeight.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mTruckSpecWei));

        mSpinTruckType.setOnItemSelectedListener(l0);
        mSpinTruckSpecLen.setOnItemSelectedListener(l1);
        mSpinTruckSpecWeight.setOnItemSelectedListener(l2);
        mSpinGoodsType.setOnItemSelectedListener(l3);

        carType = mSpinTruckType.getSelectedItem().toString();
        carLen = Double.valueOf(mSpinTruckSpecLen.getSelectedItem().toString());
        carWei = Double.valueOf(mSpinTruckSpecWeight.getSelectedItem().toString());
        goodsType = mSpinGoodsType.getSelectedItem().toString();

    }

    private void setInfo(){
        setPrice();
        setStartAndEndLoc();
        setIndividualInfo();
    }

    private void setIndividualInfo() {
        SharedPreferences sharedPre = getSharedPreferences(Data.LOCAL_DATA, Context.MODE_PRIVATE);
        mName.setText(sharedPre.getString("nickname",""));
        mPhone.setText(sharedPre.getString("phone",""));
    }

    private void setStartAndEndLoc(){
        String startPoint = RouteTask.getInstance(getApplicationContext()).getStartPoint().address;
        String endPoint = RouteTask.getInstance(getApplicationContext()).getEndPoint().address;
        mStartLoc.setText(startPoint);
        mDestLoc.setText(endPoint);
    }

    private void setPrice(){
        String price = String.valueOf("约"+RouteTask.getInstance(getApplicationContext()).cost+"元");
        mCost.setText(price);
    }

    public void onToggleBack(View view) {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    public void onSend(View view) {
        getInfo();
        if(!flag){
            ToastAssistant.showToast(getApplicationContext(),"请选择时间");
        }
        matcher1 = pattern.matcher(mGoodsWei.getText().toString());
        matcher2 = pattern.matcher(mGoodsVol.getText().toString());
        if(matcher2.matches() && matcher2.matches() && flag){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            publish();
        }
    }

    private void publish(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerInfo.host)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APICustomer server = retrofit.create(APICustomer.class);
        Call<Succee> call = server.publishIndent(cost,phoneCusomter,publishTime,publishDate,setoutTime,setoutDate,carType,startLoc,
                sLat,sLon,endLoc,eLat,eLon,isNeedLoad,carLen,carWei,goodsWei,goodsVol,goodsType);
        Log.i("publish",cost+"\n"+phoneCusomter+"\n"+ setoutTime +"\n"+ setoutDate +"\n"+carType+"\n"
                +startLoc+"\n"+endLoc+"\n"+sLat+"\n"+sLon+"\n"+eLat+"\n"+eLon+"\n"+isNeedLoad);

        call.enqueue(new Callback<Succee>() {
            @Override
            public void onResponse(Call<Succee> call, Response<Succee> response) {
                Log.i("publish","success");
                if(response.body()!=null){
                    Log.i("publish",response.body().getSuccee());
                }
            }

            @Override
            public void onFailure(Call<Succee> call, Throwable t) {
                Log.i("publish","fail");
            }
        });
    }

    private void getInfo(){
        cost = RouteTask.getInstance(getApplicationContext()).cost;
        phoneCusomter = mPhone.getText().toString();
        startLoc = RouteTask.getInstance(getApplicationContext()).getStartPoint().address;
        endLoc = RouteTask.getInstance(getApplicationContext()).getEndPoint().address;
        matcher1 = pattern.matcher(mGoodsWei.getText().toString());
        matcher2 = pattern.matcher(mGoodsVol.getText().toString());

        Date date = new Date();
        publishDate = mDateFormatter.format(date);
        publishTime = mTimeFormatter.format(date);

        if(matcher2.matches() && matcher2.matches()){
            goodsWei = Double.valueOf(mGoodsWei.getText().toString());
            goodsVol = Double.valueOf(mGoodsVol.getText().toString());
        }else{
            ToastAssistant.showToast(getApplicationContext(),"请注意重量和体积");
        }


        sLat = RouteTask.getInstance(getApplicationContext()).getStartPoint().latitue;
        sLon = RouteTask.getInstance(getApplicationContext()).getStartPoint().longitude;
        eLat = RouteTask.getInstance(getApplicationContext()).getEndPoint().latitue;
        eLon = RouteTask.getInstance(getApplicationContext()).getEndPoint().longitude;
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.radio_yes:
                Log.i("publish","radio yes");
                isNeedLoad = "yes";
                break;
            case R.id.radio_no:
                Log.i("publish","radio no");
                isNeedLoad = "no";
                break;
        }
    }

    public void onPickTime(View view) {
        Date date = new Date();
        new SlideDateTimePicker.Builder(getSupportFragmentManager())
                .setListener(mListener)
                .setInitialDate(date)
                .setMinDate(date)
                .setIs24HourTime(true)
                .build()
                .show();
    }

    AdapterView.OnItemSelectedListener l0 = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            carType = getResources().getStringArray(R.array.spinnerTruckType)[position];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            carType = getResources().getStringArray(R.array.spinnerTruckType)[0];
        }
    };

    AdapterView.OnItemSelectedListener l1 = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            carLen = Double.valueOf(getResources().getStringArray(R.array.spinnerTruckSpecLen)[position]);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            carLen = Double.valueOf(getResources().getStringArray(R.array.spinnerTruckSpecLen)[0]);
        }
    };

    AdapterView.OnItemSelectedListener l2 = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            carWei = Double.valueOf(getResources().getStringArray(R.array.spinnerTruckSpecWei)[position]);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            carWei = Double.valueOf(getResources().getStringArray(R.array.spinnerTruckSpecWei)[0]);
        }
    };

    AdapterView.OnItemSelectedListener l3 = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            goodsType = getResources().getStringArray(R.array.spinnerGoodsType)[position];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            goodsType = getResources().getStringArray(R.array.spinnerTruckType)[0];
        }
    };
}
