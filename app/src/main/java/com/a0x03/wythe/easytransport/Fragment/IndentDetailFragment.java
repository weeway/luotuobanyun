package com.a0x03.wythe.easytransport.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.a0x03.wythe.easytransport.API.APICustomer;
import com.a0x03.wythe.easytransport.Model.IndentDetail;
import com.a0x03.wythe.easytransport.R;
import com.a0x03.wythe.easytransport.Utils.Data;
import com.a0x03.wythe.easytransport.Utils.SERVER_INFO;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wythe on 2016/8/1.
 */
public class IndentDetailFragment extends Fragment{

    private MapView mIndentMap;
    private AMap mAMapIndent;
    private View mView;
    private TextView mCost;
    private TextView mCarType;
    private TextView mDriverPhone;
    private TextView mIdIndent;
    private TextView mPublishDateTime;
    private TextView mDoneDateTime;
    private TextView mSetoutDateTime;
    private TextView mIdPayment;
    private TextView mStartAdress;
    private TextView mEndAdress;
    private OnGetIndentStatusListener mOnGetIndentStatusListener;
    private Button mBtnEvaluate;


    public interface OnGetIndentStatusListener{
        void onGetIndentStatus(int status);
    }

    public void setOnGetIndentStatusListener(OnGetIndentStatusListener listener){
        mOnGetIndentStatusListener = listener;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mOnGetIndentStatusListener = (OnGetIndentStatusListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        mView = inflater.inflate(R.layout.fragment_detail,container,false);
        init(savedInstanceState);
        initView(mView);
        setInfo();
        return mView;
    }

    private void init(Bundle saveInstanceState){
        mIndentMap = (MapView) mView.findViewById(R.id.map_indent_location);
        mIndentMap.onCreate(saveInstanceState);
        mAMapIndent = mIndentMap.getMap();
        mAMapIndent.getUiSettings().setZoomControlsEnabled(false);
    }

    private void initView(View view) {
        mCost = (TextView) view.findViewById(R.id.tv_indent_detail_cost);
        mCarType = (TextView) view.findViewById(R.id.tv_indent_detail_cartype);
        mDoneDateTime = (TextView) view.findViewById(R.id.tv_indent_detail_donedatetime);
        mPublishDateTime = (TextView) view.findViewById(R.id.tv_indent_detail_publishdatetime);
        mSetoutDateTime = (TextView) view.findViewById(R.id.tv_indent_detail_setoutdatetime);
        mStartAdress = (TextView) view.findViewById(R.id.tv_indent_detail_start);
        mEndAdress = (TextView) view.findViewById(R.id.tv_indent_detail_end);
        mIdIndent = (TextView) view.findViewById(R.id.tv_indent_detail_idIndent);
        mIdPayment = (TextView) view.findViewById(R.id.tv_indent_detail_idpayment);
        mDriverPhone = (TextView) view.findViewById(R.id.tv_indent_detail_phone);
        mBtnEvaluate = (Button) view.findViewById(R.id.btn_indent_evaluate);
    }

    private void setInfo(){
        final Bundle bundle = getArguments();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_INFO.host)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APICustomer server = retrofit.create(APICustomer.class);
        Call<IndentDetail> call = server.getIndentDetail(bundle.getString(Data.INDENT.IDINDENT));

        call.enqueue(new Callback<IndentDetail>() {
            @Override
            public void onResponse(Call<IndentDetail> call, Response<IndentDetail> response) {
                IndentDetail indentDetail = response.body();
                mIdIndent.setText(bundle.getString(Data.INDENT.IDINDENT));
                mCarType.setText(indentDetail.getCarType());
                mDriverPhone.setText(indentDetail.getPhoneDriver());
                mStartAdress.setText(bundle.getString(Data.INDENT.STARTLOC));
                mEndAdress.setText(bundle.getString(Data.INDENT.ENDLOC));
                mCost.setText(String.valueOf(bundle.getDouble(Data.INDENT.COST)));
                mSetoutDateTime.setText(bundle.getString(Data.INDENT.SETOUTDATE)+"  "+
                        bundle.getString(Data.INDENT.SETOUTTIME));
//                mDoneDateTime.setText(indentDetail.getDoneDate()+ " "+ indentDetail.getDoneTime());
                mDoneDateTime.setText("2016-09-18" + " "+ "19:03:16");
                mPublishDateTime.setText(bundle.getString(Data.INDENT.PUBLISH_DATE)+ "  "+
                        bundle.getString(Data.INDENT.PUBLISH_TIME));
//                mIdPayment.setText(indentDetail.getIdPayment());
                mOnGetIndentStatusListener = (OnGetIndentStatusListener)getActivity();
                mIdPayment.setText("20160903350125303");
                mOnGetIndentStatusListener.onGetIndentStatus(indentDetail.getStatus());

                if(indentDetail.getStatus()!=3){
                    mBtnEvaluate.setEnabled(false);
                    mBtnEvaluate.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<IndentDetail> call, Throwable t) {

            }
        });
    }
}
