package com.a0x03.wythe.easytransport.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.a0x03.wythe.easytransport.Fragment.IndentDoneFragment;
import com.a0x03.wythe.easytransport.Fragment.IndentOnTWFragment;
import com.a0x03.wythe.easytransport.R;

public class IndentAllActivity extends AppCompatActivity {


    private RadioButton mIndentOnTheWay;
    private RadioButton mIndentDoen;
    private IndentOnTWFragment mIndentOnTWFragment;
    private IndentDoneFragment mIndentDoneFragment;
    private ImageButton mBackToMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indnet_all);
        init();
        setDefaultFragment();
    }



    private void init(){
        mIndentOnTheWay = (RadioButton) findViewById(R.id.btn_state_OnTheWay);
        mIndentDoen = (RadioButton) findViewById(R.id.btn_state_done);
//        mBackToMain = (ImageButton) findViewById(R.id.btn_back_to_main);
//        mBackToMain.setOnClickListener(this);
        mIndentOnTheWay.setChecked(true);

        mIndentDoen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.FragmentManager fm =  getFragmentManager();
                android.app.FragmentTransaction transaction = fm.beginTransaction();

//                if(mIndentOnTWFragment!=null)
//                    transaction.hide(mIndentOnTWFragment);
//                if(mIndentDoneFragment==null){
//                    mIndentDoneFragment = new IndentDoneFragment();
//                    transaction.add(R.id.frag_indent_list,mIndentDoneFragment);
//                }else{
//                    transaction.show(mIndentDoneFragment);
//                }
                if(mIndentOnTWFragment!=null) transaction.remove(mIndentOnTWFragment);
                mIndentDoneFragment = new IndentDoneFragment();
                transaction.add(R.id.frag_indent_list,mIndentDoneFragment);
                transaction.commit();
            }
        });
        mIndentOnTheWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.FragmentManager fm =  getFragmentManager();
                android.app.FragmentTransaction transaction = fm.beginTransaction();

//                if(mIndentDoneFragment!=null)
//                    transaction.hide(mIndentDoneFragment);
//                if(mIndentOnTWFragment == null){
//                    mIndentOnTWFragment= new IndentOnTWFragment();
//                    transaction.add(R.id.frag_indent_list,mIndentOnTWFragment);
//                }else{
//                    transaction.show(mIndentOnTWFragment);
//                }
                if(mIndentDoneFragment!=null) transaction.remove(mIndentDoneFragment);
                mIndentOnTWFragment= new IndentOnTWFragment();
                transaction.replace(R.id.frag_indent_list,mIndentOnTWFragment);
                transaction.commit();
            }
        });
    }

    private void setDefaultFragment(){
        android.app.FragmentManager fm =  getFragmentManager();
        android.app.FragmentTransaction transaction = fm.beginTransaction();

//        if(mIndentOnTWFragment!=null)
//            transaction.hide(mIndentOnTWFragment);
//        if(mIndentDoneFragment==null){
//            mIndentDoneFragment = new IndentDoneFragment();
//            transaction.add(R.id.frag_indent_list,mIndentDoneFragment);
//        }else{
//            transaction.show(mIndentDoneFragment);
//        }

        mIndentOnTWFragment= new IndentOnTWFragment();
        transaction.replace(R.id.frag_indent_list,mIndentOnTWFragment);
        transaction.commit();
    }

    public void onToggleBack(View view) {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop(){
        super.onStop();
        this.finish();
    }
}
