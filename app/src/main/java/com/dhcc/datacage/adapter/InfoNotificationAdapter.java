package com.dhcc.datacage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dhcc.datacage.R;
import com.dhcc.datacage.base.BaseRecyclerAdapter;
import com.dhcc.datacage.model.InfoNotify;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by pengbangqin on 2016/12/5.
 * 工作台中消息通知的Adapter
 */
public class InfoNotificationAdapter extends BaseRecyclerAdapter<InfoNotify, RecyclerView.ViewHolder> {

    public InfoNotificationAdapter(Context context, List<InfoNotify> datas) {
        super(context, datas);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (VIEW_TYPE == viewType) {
            view = inflater.inflate(R.layout.item_empty, parent, false);
            return new MyEmptyHolder(view);
        }
        view = inflater.inflate(R.layout.info_notity_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            InfoNotify infoNotify= mDatas.get(position);
            ((MyViewHolder) holder).setOnItemClickListener(mOnItemClickListener);
            ((MyViewHolder) holder).tvTitle.setText(infoNotify.getTitle());
            ((MyViewHolder) holder).tvMessage.setText(infoNotify.getMessage());
            ((MyViewHolder) holder).tvTime.setText(infoNotify.getTime());
        }
    }

    class MyViewHolder extends MyVH {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_message)
        TextView tvMessage;
        @Bind(R.id.tv_time)
        TextView tvTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
