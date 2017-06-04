package com.a0x03.wythe.easytransport.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.a0x03.wythe.easytransport.API.APICustomer;
import com.a0x03.wythe.easytransport.Activity.IndentActivity;
import com.a0x03.wythe.easytransport.Activity.TestActivity;
import com.a0x03.wythe.easytransport.Adapter.IndentListAdapter;
import com.a0x03.wythe.easytransport.Adapter.IndentListItem;
import com.a0x03.wythe.easytransport.Model.EndLoc;
import com.a0x03.wythe.easytransport.Model.IndentList;
import com.a0x03.wythe.easytransport.Model.Result;
import com.a0x03.wythe.easytransport.Model.StartLoc;
import com.a0x03.wythe.easytransport.R;
import com.a0x03.wythe.easytransport.Utils.Data;
import com.a0x03.wythe.easytransport.Utils.ServerInfo;
import com.a0x03.wythe.easytransport.Utils.SuperSwipeRefreshLayout;

import java.util.ArrayList;

import cn.smssdk.gui.layout.Res;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wythe on 2016/7/22.
 */
public class IndentDoneFragment extends Fragment implements
        SuperSwipeRefreshLayout.OnPullRefreshListener,
        SuperSwipeRefreshLayout.OnPushLoadMoreListener{

    private int currentPage = 0;
    private RecyclerView mRecyclerView;
    private IndentListAdapter mIndentListAdapter;
    private ArrayList<Result> mDataSet;

    private SuperSwipeRefreshLayout mSwipeRefreshLayout;

     // Header View
    private ProgressBar mProgressBar;
    private TextView mDesText;
    private ImageView mImage;

    // Footer View
    private ProgressBar mFooterProgressBar;
    private TextView mFooterTextView;
    private ImageView mFooterImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_indent,container,false);
        initRecyclerView(view);
        enableSwipeRight();
        settingsForSwipeRefreshLayout(view);
        return view;
    }

    private ItemTouchHelper.Callback implementCallback(){
        ItemTouchHelper.Callback mCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                mDataSet.remove(position);
                mIndentListAdapter.notifyItemRemoved(position);
            }
        };
        return mCallback;
    }

    private void enableSwipeRight(){
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(implementCallback());
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void initRecyclerView(View view){
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recy_indent_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        initData();
    }

    private void initData(){
        mDataSet = new ArrayList<Result>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerInfo.host)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APICustomer server = retrofit.create(APICustomer.class);
        Call<IndentList> call = server.getIndentPage(3,0);
        call.enqueue(callback);
    }


    private void settingsForSwipeRefreshLayout(View view){
        mSwipeRefreshLayout = (SuperSwipeRefreshLayout) view.
                findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setHeaderViewBackgroundColor(0xfff2f2f2);
        mSwipeRefreshLayout.setHeaderView(createHeaderView());
        mSwipeRefreshLayout.setFooterView(createFooterView());
        mSwipeRefreshLayout.setTargetScrollWithLayout(true);
        mSwipeRefreshLayout.setOnPullRefreshListener(this);
        mSwipeRefreshLayout.setOnPushLoadMoreListener(this);
    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(mSwipeRefreshLayout.getContext())
                .inflate(R.layout.layout_footer, null);
        mFooterProgressBar = (ProgressBar) footerView
                .findViewById(R.id.footer_pb_view);
        mFooterImageView = (ImageView) footerView
                .findViewById(R.id.footer_image_view);
        mFooterTextView = (TextView) footerView
                .findViewById(R.id.footer_text_view);
        mFooterProgressBar.setVisibility(View.GONE);
        mFooterImageView.setVisibility(View.VISIBLE);
        mFooterImageView.setImageResource(R.drawable.down_arrow);
        mFooterTextView.setText("上拉加载更多...");
        return footerView;
    }

    private View createHeaderView() {
        View headerView = LayoutInflater.from(mSwipeRefreshLayout.getContext())
                .inflate(R.layout.layout_head, null);
        mProgressBar = (ProgressBar) headerView.findViewById(R.id.progress_bar_refresh);
        mDesText = (TextView) headerView.findViewById(R.id.des_text_refresh);
        mDesText.setText("下拉刷新");
        mImage = (ImageView) headerView.findViewById(R.id.image_refresh);
        mImage.setVisibility(View.VISIBLE);
        mImage.setImageResource(R.drawable.down_arrow);
        mProgressBar.setVisibility(View.GONE);
        return headerView;
    }

    @Override
    public void onRefresh() {
        mDesText.setText("正在刷新");
        mImage.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerInfo.host)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APICustomer server = retrofit.create(APICustomer.class);
        Call<IndentList> call = server.getIndentPage(3,0);
        call.enqueue(new Callback<IndentList>() {
            @Override
            public void onResponse(Call<IndentList> call, Response<IndentList> response) {
                mIndentListAdapter.noticefyDataRefresh(response.body().getResult());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mProgressBar.setVisibility(View.GONE);
                    }
                },1000);
            }

            @Override
            public void onFailure(Call<IndentList> call, Throwable t) {

            }
        });
    }

    @Override
    public void onPullDistance(int distance) {

    }

    @Override
    public void onPullEnable(boolean enable) {
        mDesText.setText(enable?"松开刷新":"下拉刷新");
        mImage.setVisibility(View.VISIBLE);
        mImage.setRotation(enable?180:0);
    }

    @Override
    public void onLoadMore() {
        mFooterTextView.setText("正在加载...");
        mFooterImageView.setVisibility(View.GONE);
        mFooterProgressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerInfo.host)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APICustomer server = retrofit.create(APICustomer.class);
        Call<IndentList> call = server.getIndentPage(3,++currentPage);
        call.enqueue(new Callback<IndentList>() {
            @Override
            public void onResponse(Call<IndentList> call, Response<IndentList> response) {
                mIndentListAdapter.noticefyDataAdd(response.body().getResult());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mFooterImageView.setVisibility(View.GONE);
                        mFooterProgressBar.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setLoadMore(false);
                    }
                },2000);
            }

            @Override
            public void onFailure(Call<IndentList> call, Throwable t) {

            }
        });
    }

    @Override
    public void onPushDistance(int distance) {

    }

    @Override
    public void onPushEnable(boolean enable) {
        mFooterTextView.setText(enable?"松开加载":"上拉加载");
        mFooterImageView.setVisibility(View.VISIBLE);
        mFooterImageView.setRotation(enable?0:180);
    }

    IndentListAdapter.OnRecyclerViewItemClickListener listener = new IndentListAdapter.OnRecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view) {
            Intent intent = new Intent(getActivity(), TestActivity.class);
            intent.putExtra(Data.DATASETNAME.BUNDLECONVERTED,((Result) view.getTag()).getDataBundle());
            startActivity(intent);
            Log.i("DebugClick","onItemClick "+((Result)(view.getTag())).getPublishTime());
        }
    };

    Callback<IndentList> callback = new Callback<IndentList>() {
        @Override
        public void onResponse(Call<IndentList> call, Response<IndentList> response) {
            mDataSet = (ArrayList<Result>) response.body().getResult();
            mRecyclerView.setAdapter(mIndentListAdapter=new IndentListAdapter(
                    getActivity().getApplicationContext(),mDataSet));
            mIndentListAdapter.setOnItemClickListener(listener);
        }

        @Override
        public void onFailure(Call<IndentList> call, Throwable t) {

        }
    };
}
