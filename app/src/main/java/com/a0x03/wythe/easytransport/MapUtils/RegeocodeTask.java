package com.a0x03.wythe.easytransport.MapUtils;

import android.content.Context;
import android.util.Log;

import com.amap.api.maps.LocationSource;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;

/**
 * Created by wythe on 2016/7/15.
 */
public class RegeocodeTask implements GeocodeSearch.OnGeocodeSearchListener{
    private static final float SEARCH_RADIUS = 50;
    private GeocodeSearch mGeocodeSearch;
    private OnLocationGetListener mOnLocationGetListener;

    public RegeocodeTask(Context context){
        mGeocodeSearch = new GeocodeSearch(context);
        mGeocodeSearch.setOnGeocodeSearchListener(this);
    }


    public void search(double latitude, double longitude){
        Log.i("map","search");
        RegeocodeQuery regeocodeQuery = new RegeocodeQuery(new LatLonPoint(
                latitude, longitude), SEARCH_RADIUS, GeocodeSearch.AMAP);
        mGeocodeSearch.getFromLocationAsyn(regeocodeQuery);
    }


    public void setOnLocationGetListener(
            OnLocationGetListener onLocationGetListener){
        Log.i("map","setOnLocationGetListener");
        mOnLocationGetListener = onLocationGetListener;
    }


    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int resultCode) {
        Log.i("map","onRegeocodeSearched");
        if(resultCode == 1000){
            if(regeocodeResult != null
                    && regeocodeResult.getRegeocodeAddress() != null
                    && mOnLocationGetListener != null){
                String address = regeocodeResult.getRegeocodeAddress()
                        .getFormatAddress();
                String city = regeocodeResult.getRegeocodeAddress()
                        .getCity();

                PositionEntity entity = new PositionEntity();
                entity.address = address;
                entity.city = city;
                mOnLocationGetListener.onRegecodeGet(entity);
            }
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
}
