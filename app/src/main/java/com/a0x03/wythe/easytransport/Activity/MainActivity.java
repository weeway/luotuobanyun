package com.a0x03.wythe.easytransport.Activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a0x03.wythe.easytransport.MapUtils.LocationTask;
import com.a0x03.wythe.easytransport.MapUtils.OnLocationGetListener;
import com.a0x03.wythe.easytransport.MapUtils.PositionEntity;
import com.a0x03.wythe.easytransport.MapUtils.RegeocodeTask;
import com.a0x03.wythe.easytransport.MapUtils.RouteTask;
import com.a0x03.wythe.easytransport.R;
import com.a0x03.wythe.easytransport.Utils.BitmapUtils;
import com.a0x03.wythe.easytransport.Utils.MapFragment;
import com.a0x03.wythe.easytransport.Utils.Utils;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.autonavi.aps.amapapi.model.AmapLoc;

import static com.a0x03.wythe.easytransport.R.id.id_select_time;


public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
         AMap.OnMapLoadedListener,
        AMap.OnCameraChangeListener, OnLocationGetListener,
        RouteTask.OnRouteCalculateListener{

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private AMap aMap;
    private MapView mapView;
    private TextView mDestinationText;
    private Marker mPositionMark;
    private LatLng mStartPosition;
    private RegeocodeTask mRegeocodeTask;
    private LinearLayout mSelectTime;
    private TextView mCurLoc;
    private Button mPostButton;
    private boolean mIsFirst = true;
    private LocationTask mLocationTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRegeocodeTask = new RegeocodeTask(getApplicationContext());
        mLocationTask = LocationTask.getInstance(getApplicationContext());
        mLocationTask.setOnLocationGetListener(this);
        RouteTask.getInstance(getApplication()).addRouteCalculateListener(this);
        initViews(savedInstanceState);
    }


    private void initViews(Bundle savedInstanceState){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDestinationText = (TextView) findViewById(R.id.tvDest);
        mCurLoc = (TextView) findViewById(R.id.tvMyLoc);
        mPostButton = (Button) findViewById(R.id.id_post);

        mDestinationText.setClickable(true);
//        mPostButton.setOnClickListener(this);
        setSupportActionBar(mToolbar);


        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
        mToolbar.setNavigationIcon(R.drawable.icon_nav_menu);

        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.setOnMapLoadedListener(this);
        aMap.setOnCameraChangeListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notifications) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_payment) {
            // Handle the camera action
        }else if(id == R.id.nav_indent){
            Intent intent = new Intent(getApplicationContext(),IndentAllActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onPause(){
        super.onPause();
        mapView.onPause();
        mLocationTask.onPause();
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
        mLocationTask.onDestroy();
    }

//    @Override
//    public void onClick(View v) {
//        Intent intent = null;
//        switch(v.getId()){
//            case R.id.tvDest:
////                intent = new Intent(getApplicationContext(),DestinationActivity.class);
////                startActivity(intent);
//                break;
//            case R.id.id_post:
//
//                break;
//        }
//    }

    @Override
    public void onMapLoaded() {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.setFlat(false);
        markerOptions.anchor(0.5f, 0.5f);
        markerOptions.position(new LatLng(0, 0));
        markerOptions
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(),R.drawable.icon_location)));

        mPositionMark = aMap.addMarker(markerOptions);
        mPositionMark.setToTop();
        mPositionMark.setVisible(true);

        mPositionMark.setPositionByPixels(mapView.getWidth()/2,mapView.getHeight()/2);
        mLocationTask.startSingleLocate();
        Log.i("map","onMapLoaded");
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        mStartPosition = cameraPosition.target;
		mRegeocodeTask.setOnLocationGetListener(this);
//        Toast.makeText(getApplicationContext(),cameraPosition.target.toString(),Toast.LENGTH_SHORT).show();
		mRegeocodeTask
				.search(mStartPosition.latitude, mStartPosition.longitude);
		if (mIsFirst) {
			Utils.addEmulateData(aMap, mStartPosition);
			if (mPositionMark != null) {
				mPositionMark.setToTop();
			}
			mIsFirst = false;
		}
    }

    @Override
    public void onLocationGet(PositionEntity entity) {
        mCurLoc.setText(entity.address);
		RouteTask.getInstance(getApplicationContext()).setStartPoint(entity);

		mStartPosition = new LatLng(entity.latitue, entity.longitude);
		CameraUpdate cameraUpate = CameraUpdateFactory.newLatLngZoom(
				mStartPosition, aMap.getCameraPosition().zoom);
        aMap.animateCamera(cameraUpate);
    }

    @Override
    public void onRegecodeGet(PositionEntity entity) {
//        Toast.makeText(getApplication(),"onRegecodeGet",Toast.LENGTH_SHORT).show();
        mCurLoc.setText(entity.address);
		entity.latitue = mStartPosition.latitude;
		entity.longitude = mStartPosition.longitude;
		RouteTask.getInstance(getApplicationContext()).setStartPoint(entity);
		RouteTask.getInstance(getApplicationContext()).search();
    }

    public void onSelectDest(View view) {
        Intent intent = new Intent(getApplicationContext(),DestinationActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRouteCalculate(float cost, float distance, int duration) {
        mDestinationText.setText(RouteTask
                .getInstance(getApplicationContext()).getEndPoint().address);
    }

    public void onPost(View view) {
        if(RouteTask.getInstance(getApplicationContext()).getEndPoint()!=null
                && RouteTask.getInstance(getApplication()).getStartPoint()!=null){
            Intent intent = new Intent(getApplicationContext(),PostActivity.class);
            startActivity(intent);
        }else if(RouteTask.getInstance(getApplicationContext()).getStartPoint() == null){
            showToast("起始地址不能为空");
        }else if(RouteTask.getInstance(getApplicationContext()).getEndPoint() == null){
            showToast("目的地不能为空");
        }
    }

    private void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}