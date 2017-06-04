package com.a0x03.wythe.easytransport.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.a0x03.wythe.easytransport.Model.Result;
import com.a0x03.wythe.easytransport.R;

import java.util.List;

/**
 * Created by wythe on 2016/7/22.
 */
public class IndentListAdapter extends RecyclerView.
        Adapter<IndentListAdapter.ItemHolder>
        implements View.OnClickListener {

    private Context mContext;
    private List<Result> mDataSet;
    private OnRecyclerViewItemClickListener mOnRecyclerViewClickListener = null;

    @Override
    public void onClick(View v) {
        if(mOnRecyclerViewClickListener != null){
            mOnRecyclerViewClickListener.onItemClick(v);
            Log.i("DebugClick","onClick");
        }
    }

    public void noticefyDataRefresh(List<Result> dataSet){
        mDataSet = dataSet;
        notifyDataSetChanged();
    }

    public void noticefyDataAdd(List<Result> dataSet){
        int position = getItemCount()-1;
        mDataSet.addAll(position,dataSet);
        notifyItemRangeInserted(position,dataSet.size());
    }

    public interface OnRecyclerViewItemClickListener{
        void onItemClick(View view);
    }


    public IndentListAdapter(Context context,List<Result> dataSet){
        this.mDataSet = dataSet;
        this.mContext = context;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                mContext).inflate(R.layout.indent_list_item,parent,
                false);
        ItemHolder holder = new ItemHolder(view);
//
//        view.setClickable(true);
//        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, final int position) {
        Result item = mDataSet.get(position);

        holder.itemView.setTag(mDataSet.get(position));

        holder.mPrice.setText(String.valueOf(item.getCost()));
        holder.mDate.setText(item.getSetoutDate()+"   "+item.getSetoutTime());
        holder.mIndentCode.setText(item.getIdIndnet());
        holder.mDest.setText(item.getEndLoc().getAddress());
        holder.mStartLoc.setText(item.getStartLoc().getAddress());

        Log.i("indent", " "+ item.getEndLoc().getAddress());
        Log.i("indent"," "+ holder.mDest.getTextColors().toString());
        if(item.getStatus()!=3){
            holder.mImgStatus.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        TextView mPrice;
        TextView mDest;
        TextView mStartLoc;
        TextView mDate;
        TextView mIndentCode;
        Button mDeleteIndent;
        ImageView mImgStatus;

        public ItemHolder(View itemView) {
            super(itemView);
            mPrice = (TextView) itemView.findViewById(R.id.tv_indent_price);
            mDate = (TextView) itemView.findViewById(R.id.tv_indent_date);
            mStartLoc = (TextView) itemView.findViewById(R.id.tv_indent_start_loc);
            mDest = (TextView) itemView.findViewById(R.id.tv_indent_dest);
            mIndentCode = (TextView) itemView.findViewById(R.id.tv_indent_code);
            mImgStatus = (ImageView) itemView.findViewById(R.id.img_indent_status);
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener){
        this.mOnRecyclerViewClickListener = listener;
    }
}
