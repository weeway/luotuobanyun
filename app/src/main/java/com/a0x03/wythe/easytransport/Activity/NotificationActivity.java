package com.a0x03.wythe.easytransport.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.a0x03.wythe.easytransport.Adapter.NotificationAdapter;
import com.a0x03.wythe.easytransport.R;
import com.a0x03.wythe.easytransport.Utils.NotificationData;
import com.a0x03.wythe.easytransport.Utils.SuperSwipeRefreshLayout;
import com.a0x03.wythe.easytransport.Utils.SwipeDismissRecyclerViewTouchListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class NotificationActivity extends AppCompatActivity
        implements SuperSwipeRefreshLayout.OnPullRefreshListener,
        SuperSwipeRefreshLayout.OnPushLoadMoreListener{

    private RecyclerView mRecyclerView;
    private SuperSwipeRefreshLayout mSwipeRefreshLayout;
    private List<NotificationData> mData;
    private NotificationAdapter mNotificationAdapter;

//    @BindView(R.id.chb_notification_edit)
//    CheckBox mCheckBox;


    /**
     * Header View
     */
    private ProgressBar mProgressBar;
    private TextView mDesText;
    private ImageView mImage;

    /**
     * Food View
     */
    private ProgressBar mFooterProgressBar;
    private TextView mFooterTextView;
    private ImageView mFooterImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        ButterKnife.bind(this);

        initRecyclerView();
        enableSwipeRight();
        settingsForSwipeRefreshLayout();
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
                mData.remove(position);
                mNotificationAdapter.notifyItemRemoved(position);
            }
        };
        return mCallback;
    }

    private void enableSwipeRight(){
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(implementCallback());
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void initRecyclerView(){
        initData();
        mNotificationAdapter = new NotificationAdapter(getApplicationContext(),mData);
        mRecyclerView = (RecyclerView) findViewById(R.id.recy_notification_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setAdapter(mNotificationAdapter);
    }

    private void initData() {
        mData = new ArrayList<NotificationData>();
        for(int i=0; i < 10; i++){
            NotificationData item = new NotificationData("您发布的需求订单" +
                    "3501252016092103，司机已经接单。（点击查看详情）",
                    "货车司机已经接单咯！","07-14  13.21");
            mData.add(item);
        }
    }

    private void settingsForSwipeRefreshLayout(){
        mSwipeRefreshLayout = (SuperSwipeRefreshLayout)
                findViewById(R.id.refresh_notification);
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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                mProgressBar.setVisibility(View.GONE);
            }
        },1000);
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
    public void onPushDistance(int distance) {

    }

    @Override
    public void onPushEnable(boolean enable) {
        mFooterTextView.setText(enable?"松开加载":"上拉加载");
        mFooterImageView.setVisibility(View.VISIBLE);
        mFooterImageView.setRotation(enable?0:180);
    }

    public void onToggleBack(View view) {
        Intent intent = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(intent);
    }

//    public void onChkClick(View view) {
//
//    }
}
