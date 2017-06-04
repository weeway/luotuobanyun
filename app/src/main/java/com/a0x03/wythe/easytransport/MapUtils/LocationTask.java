/**  
 * Project Name:Android_Car_Example  
 * File Name:LocationTask.java  
 * Package Name:com.amap.api.car.example  
 * Date:2015年4月3日上午9:27:45  
 *  
 */

package com.a0x03.wythe.easytransport.MapUtils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.LocationSource;

/**
 * ClassName:LocationTask <br/>
 * Function: 简单封装了定位请求，可以进行单次定位和多次定位，注意的是在app结束或定位结束时注意销毁定位 <br/>
 * Date: 2015年4月3日 上午9:27:45 <br/>
 * 
 * @author yiyi.qi
 * @version
 * @since JDK 1.6
 * @see
 */
public class LocationTask implements AMapLocationListener,
		OnLocationGetListener {

	private AMapLocationClient mLocationManagerClient;

	private static LocationTask mLocationTask;

	private Context mContext;

	private OnLocationGetListener mOnLocationGetlisGetListener;

	private RegeocodeTask mRegecodeTask;

	private AMapLocationClientOption mLocationClientOption;

	private LocationTask(Context context) {
		mLocationManagerClient = new AMapLocationClient(context);
        mLocationClientOption = new AMapLocationClientOption();
		mRegecodeTask = new RegeocodeTask(context);
		mRegecodeTask.setOnLocationGetListener(this);
		mContext = context;
	}

	public void setOnLocationGetListener(
			OnLocationGetListener onGetLocationListener) {
		mOnLocationGetlisGetListener = onGetLocationListener;
	}

	public static LocationTask getInstance(Context context) {
		if (mLocationTask == null) {
			mLocationTask = new LocationTask(context);
		}
		return mLocationTask;
	}

	/**  
	 * 开启单次定位
	 */
	public void startSingleLocate() {
        Log.i("map","startSingleLocate");
        mLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
		mLocationClientOption.setOnceLocation(true);
        mLocationManagerClient.setLocationListener(this);
		mLocationManagerClient.startLocation();
	}

	/**  
	 * 开启多次定位
	 */
	public void startLocate() {
        Log.i("map","startLocate");
		mLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
		mLocationClientOption.setInterval(5*1000);
        mLocationManagerClient.setLocationListener(this);
		mLocationManagerClient.startLocation();
	}

	/**  
	 * 结束定位，可以跟多次定位配合使用
	 */
	public void stopLocate() {
        Log.i("map","stopLocate");
		mLocationManagerClient.stopLocation();
	}

	/**  
	 * 销毁定位资源
	 */
	public void onDestroy() {
        Log.i("map","onDestroy");
		mLocationManagerClient.stopLocation();
		mLocationManagerClient.onDestroy();
	}

    public void onPause(){
        Log.i("map","onPause");
        mLocationManagerClient.stopLocation();
    }


	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
        Log.i("map","onLocationChanged 1");
		if (amapLocation != null) {
            Log.i("map","onLocationChanged 2");
			PositionEntity entity = new PositionEntity();
			entity.latitue = amapLocation.getLatitude();
			entity.longitude = amapLocation.getLongitude();

			if (!TextUtils.isEmpty(amapLocation.getAddress())) {
				entity.address = amapLocation.getAddress();
			}
			mOnLocationGetlisGetListener.onLocationGet(entity);
		}
        mLocationManagerClient.stopLocation();
	}

	@Override
	public void onLocationGet(PositionEntity entity) {

		// TODO Auto-generated method stub

	}

	@Override
	public void onRegecodeGet(PositionEntity entity) {

		// TODO Auto-generated method stub

	}

}
