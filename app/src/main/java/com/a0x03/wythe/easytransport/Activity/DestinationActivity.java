/**
 * Project Name:Android_Car_Example  
 * File Name:DestinationActivity.java  
 * Package Name:com.amap.api.car.example  
 * Date:2015年4月3日上午10:52:03  
 *
 */

package com.a0x03.wythe.easytransport.Activity;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.a0x03.wythe.easytransport.Adapter.RecomandAdapter;
import com.a0x03.wythe.easytransport.MapUtils.InputTipTask;
import com.a0x03.wythe.easytransport.MapUtils.PoiSearchTask;
import com.a0x03.wythe.easytransport.MapUtils.PositionEntity;
import com.a0x03.wythe.easytransport.MapUtils.RouteTask;
import com.a0x03.wythe.easytransport.R;


public class DestinationActivity extends Activity implements OnClickListener,TextWatcher,OnItemClickListener{

	private ListView mRecommendList;

	private ImageView mBack_Image;

	private TextView mSearchText;

	private EditText mDestinaionText;

	private RecomandAdapter mRecomandAdapter;

	private RouteTask mRouteTask;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_destination);
		mRecommendList=(ListView) findViewById(R.id.recommend_list);
		mBack_Image=(ImageView) findViewById(R.id.destination_back);
		mBack_Image.setOnClickListener(this);

		mSearchText=(TextView) findViewById(R.id.destination_search);
		mSearchText.setOnClickListener(this);

		mDestinaionText=(EditText) findViewById(R.id.destination_edittext);
		mDestinaionText.addTextChangedListener(this);
		mRecomandAdapter=new RecomandAdapter(getApplicationContext());
		mRecommendList.setAdapter(mRecomandAdapter);
		mRecommendList.setOnItemClickListener(this);

		mRouteTask=RouteTask.getInstance(getApplicationContext());
	}

	@Override
	public void afterTextChanged(Editable arg0) {

		// TODO Auto-generated method stub  

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
								  int after) {

		// TODO Auto-generated method stub  

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		Log.i("map","onTextChanged");
		if(RouteTask.getInstance(getApplicationContext()).getStartPoint()==null){
			Log.i("map","onTextChanged startPoint is null");
			Toast.makeText(getApplicationContext(), "检查网络，Key等问题", Toast.LENGTH_SHORT).show();
			return;
		}
		Log.i("map","onTextChanged startPoint is not null");
		InputTipTask.getInstance(getApplicationContext(), mRecomandAdapter).searchTips(s.toString(),
				RouteTask.getInstance(getApplicationContext()).getStartPoint().city);
	}

	@Override
	public void onClick(View v) {

		switch(v.getId()){
			case R.id.destination_back:
				Intent intent =new Intent(DestinationActivity.this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
				finish();
				break;
			case R.id.destination_search:
				PoiSearchTask poiSearchTask=new PoiSearchTask(getApplicationContext(), mRecomandAdapter);
				poiSearchTask.search(mDestinaionText.getText().toString(),RouteTask.getInstance(getApplicationContext()).getStartPoint().city);
				break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
							long id) {

		Log.i("map","onItemClick "+position);
		PositionEntity entity = (PositionEntity) mRecomandAdapter.getItem(position);
		if (entity.latitue == 0 && entity.longitude == 0) {
			Log.i("map","OnItemClick entity is null");
			PoiSearchTask poiSearchTask=new PoiSearchTask(getApplicationContext(), mRecomandAdapter);
			poiSearchTask.search(entity.address,RouteTask.getInstance(getApplicationContext()).getStartPoint().city);
			Log.i("map","onItemClick "+"entity.add="+entity.address+"  start city="+
			RouteTask.getInstance(getApplicationContext()).getStartPoint().city);
		} else {
			Log.i("map","onItemClick entity is not null");
			Log.i("map","onItemClick entity="+entity.address);
			mRouteTask.setEndPoint(entity);
			mRouteTask.search();
			Intent intent = new Intent(DestinationActivity.this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			intent.putExtra("ENDLOC",entity.address);
			startActivity(intent);
			finish();
		}
	}

	@Override
    protected void onStop(){
        super.onStop();
        this.finish();
    }
}
  
