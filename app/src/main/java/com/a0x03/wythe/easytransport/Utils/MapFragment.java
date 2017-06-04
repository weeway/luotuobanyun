package com.a0x03.wythe.easytransport.Utils;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a0x03.wythe.easytransport.R;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;

import java.util.Map;


/**
 * Created by wythe on 2016/7/14.
 */
public class MapFragment extends Fragment {

    private View mapLayout;
    private AMap aMap;
    private MapView mapView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mapLayout = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = (MapView) mapLayout.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        } else {
            if (mapLayout.getParent() != null) {
                ((ViewGroup) mapLayout.getParent()).removeView(mapLayout);
            }
        }
        return mapLayout;
    }


    @Override
    public void onPause(){
        super.onPause();
        mapView.onPause();
    }


    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }
}
