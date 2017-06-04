package com.a0x03.wythe.easytransport.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.a0x03.wythe.easytransport.R;
import com.a0x03.wythe.easytransport.Utils.NotificationData;

import java.util.List;

/**
 * Created by wythe on 2016/9/19.
 */
public class NotificationAdapter extends RecyclerView.
        Adapter<NotificationAdapter.ItemHolder>{

    private Context mContext;
    private List<NotificationData> mData;



    public NotificationAdapter(Context context, List<NotificationData> data) {
        this.mContext = context;
        this.mData = data;
    }


    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                mContext).inflate(R.layout.notification_list_item,
                parent,false);

        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        NotificationData item = mData.get(position);

        holder.itemView.setTag(item);
        holder.mTitle.setText(item.title);
        holder.mTime.setText(item.time);
        holder.mMsg.setText(item.msg);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        TextView mTime;
        TextView mMsg;

        public ItemHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.tv_title_notification);
            mTime = (TextView) itemView.findViewById(R.id.tv_time_notification);
            mMsg = (TextView) itemView.findViewById(R.id.tv_msg_notification);
        }
    }
}
