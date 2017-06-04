package com.a0x03.wythe.easytransport.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.a0x03.wythe.easytransport.Fragment.IndentDetailFragment;
import com.a0x03.wythe.easytransport.Fragment.IndentOnTWFragment;
import com.a0x03.wythe.easytransport.R;
import com.a0x03.wythe.easytransport.Utils.Data;

public class TestActivity extends AppCompatActivity implements IndentDetailFragment.OnGetIndentStatusListener{

    private ImageView mImgPublish;
    private ImageView mImgTaken;
    private ImageView mImgDeliver;
    private ImageView mImgDone;
    private android.app.Fragment mIndentDetailFragment;
    private Bundle bundle;
    private int[] mImgIDs = new int[8];
    private ImageView[] mImgs = new ImageView[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        init();
        Intent intent = getIntent();
        bundle = intent.getBundleExtra(Data.DATASETNAME.BUNDLECONVERTED);
        setDefaultFragment();
    }

    private void init(){
        mImgPublish = (ImageView) findViewById(R.id.img_indent_detail_status_publish);
        mImgTaken = (ImageView) findViewById(R.id.img_indent_detail_status_taken);
        mImgDeliver = (ImageView) findViewById(R.id.img_indent_detail_status_deliver);
        mImgDone = (ImageView) findViewById(R.id.img_indent_detail_status_done);
        setImageIDs();
    }

    private void setDefaultFragment(){
        android.app.FragmentManager fm =  getFragmentManager();
        android.app.FragmentTransaction transaction = fm.beginTransaction();

        mIndentDetailFragment = new IndentDetailFragment();
        mIndentDetailFragment.setArguments(bundle);
        transaction.replace(R.id.detail_place,mIndentDetailFragment);
        transaction.commit();
    }

    @Override
    public void onGetIndentStatus(int status) {
        changeStatus(status);
    }

    private void changeStatus(int status){
        for(int i = 0; i < 4; i++){
            Log.i("test","status="+status);
            if(i == status){
                mImgs[i].setImageResource(mImgIDs[i+4]);
            }else{
                mImgs[i].setImageResource(mImgIDs[i]);
            }
        }
    }

    private void setImageIDs(){
        mImgIDs[0] = R.drawable.ic_indent_publish;
        mImgIDs[1] = R.drawable.ic_indent_taken;
        mImgIDs[2] = R.drawable.ic_indent_deliver;
        mImgIDs[3] = R.drawable.ic_indent_done;
        mImgIDs[4] = R.drawable.ic_indent_publish_green;
        mImgIDs[5] = R.drawable.ic_indent_taken_green;
        mImgIDs[6] = R.drawable.ic_indent_deliver_green;
        mImgIDs[7] = R.drawable.ic_indent_done_green;
        mImgs[0] = mImgPublish;
        mImgs[1] = mImgTaken;
        mImgs[2] = mImgDeliver;
        mImgs[3] = mImgDone;
    }

    public void onToggleBack(View view) {
        Intent intent = new Intent(getApplicationContext(), IndentAllActivity.class);
        startActivity(intent);
    }

    public void onEvaluate(View view) {
        Intent intent = new Intent(getApplicationContext(),EvaluateActivity.class);
        intent.putExtra(Data.INDENT.IDINDENT,bundle.getString(Data.INDENT.IDINDENT));
        intent.putExtra(Data.INDENT.PHONEDRIVER,bundle.getString(Data.INDENT.PHONEDRIVER));
        startActivity(intent);
    }
}
