package com.a0x03.wythe.easytransport.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.a0x03.wythe.easytransport.Model.IndentList;
import com.a0x03.wythe.easytransport.R;
import com.a0x03.wythe.easytransport.Utils.Data;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;

public class IndentActivity extends AppCompatActivity {

    private MapView mIndentMap;
    private AMap mAMapIndent;
    private Intent mIntent;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indent);
        init(savedInstanceState);
        mIntent = getIntent();
        bundle = mIntent.getBundleExtra(Data.DATASETNAME.BUNDLECONVERTED);
        String tmp = bundle.getString(Data.INDENT.IDINDENT);
        Log.i("DebugClick", tmp);
    }

    private void init(Bundle saveInstanceState){
        mIndentMap = (MapView) findViewById(R.id.map_indent_location);
        mIndentMap.onCreate(saveInstanceState);
        mAMapIndent = mIndentMap.getMap();
        mAMapIndent.getUiSettings().setZoomControlsEnabled(false);
    }

    public void onEvaluate(View view) {
        Intent intent = new Intent(getApplicationContext(),EvaluateActivity.class);
        startActivity(intent);
    }

    public void onToggleBack(View view) {
        Intent intent = new Intent(getApplicationContext(),IndentAllActivity.class);
        startActivity(intent);
    }

    private void setIndentInfo(){

    }

    @Override
    protected void onStop(){
        super.onStop();
        this.finish();
    }
}
