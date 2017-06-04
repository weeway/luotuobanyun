package com.a0x03.wythe.easytransport.Components;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a0x03.wythe.easytransport.R;

/**
 * Created by wythe on 2016/4/30.
 */
public class InfoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        return inflater.inflate(R.layout.info_fragment,container,false);
    }
}
