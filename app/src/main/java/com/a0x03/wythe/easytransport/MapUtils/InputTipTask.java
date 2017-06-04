/**  
 * Project Name:Android_Car_Example  
 * File Name:InputTipTask.java  
 * Package Name:com.amap.api.car.example  
 * Date:2015年4月7日上午10:42:41  
 *  
*/  
  
package com.a0x03.wythe.easytransport.MapUtils;

import android.content.Context;
import android.util.Log;

import com.a0x03.wythe.easytransport.Adapter.RecomandAdapter;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Inputtips.InputtipsListener;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;

import java.util.ArrayList;
import java.util.List;

/**  
 * ClassName:InputTipTask <br/>  
 * Function: 简单封装了Inputtips的搜索服务，将其余提示的adapter进行数据绑定  
 * Date:     2015年4月7日 上午10:42:41 <br/>  
 * @author   yiyi.qi  
 * @version    
 * @since    JDK 1.6  
 * @see        
 */
public class InputTipTask implements InputtipsListener {

	private static InputTipTask mInputTipTask;
	
	private Inputtips mInputTips;

	private InputtipsQuery mInputtipsQuery;
	
	private RecomandAdapter mAdapter;
	
	public static InputTipTask getInstance(Context context,RecomandAdapter adapter){
		if(mInputTipTask==null){
			mInputTipTask=new InputTipTask(context);
		}
		//单例情况，多次进入DestinationActivity传进来的RecomandAdapter对象会不是同一个
		mInputTipTask.setRecommandAdapter(adapter);
		return mInputTipTask;
	}
	
	public void setRecommandAdapter(RecomandAdapter adapter){
		mAdapter=adapter;
	}
	private InputTipTask(Context context ){
		mInputTips=new Inputtips(context, this);
	 
	
	}
	
	public void searchTips(String keyWord,String city){
		Log.i("map","searchTips");
		mInputtipsQuery = new InputtipsQuery(keyWord,city);
		mInputTips.setQuery(mInputtipsQuery);
		mInputTips.requestInputtipsAsyn();
	}
	@Override
	public void onGetInputtips(List<Tip> tips, int resultCode) {
		Log.i("map","onGetInputtips");
		if(resultCode==1000&&tips!=null){
			Log.i("map","onGetInputtips come in");
			ArrayList<PositionEntity>positions=new ArrayList<PositionEntity>();
			for(Tip tip:tips){
				positions.add(new PositionEntity(0, 0, tip.getName(),tip.getAdcode()));
			}
			mAdapter.setPositionEntities(positions);	 
			mAdapter.notifyDataSetChanged();
		}
		//TODO 可以根据app自身需求对查询错误情况进行相应的提示或者逻辑处理
	}
}
  
