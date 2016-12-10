package com.dhcc.datacage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dhcc.datacage.R;
import com.dhcc.datacage.base.BaseRecyclerAdapter;
import com.dhcc.datacage.model.Law;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.R.id.list;

/**
 * Created by pengbangqin on 16-10-27.
 * 待办已办综合查询的适配器
 */
public class MyQueryAdapter extends BaseRecyclerAdapter<Law,RecyclerView.ViewHolder>{
    public MyQueryAdapter(Context context, List<Law> datas) {
        super(context, datas);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (VIEW_TYPE == viewType) {
            view = inflater.inflate(R.layout.item_empty, parent, false);
            return new MyEmptyHolder(view);
        }
        view = inflater.inflate(R.layout.my_query_item, parent, false);
        return  new MyViewHolder(view);
    }

    /**
     * 筛选后的list
     * @param laws
     */
    public void setFilter(List<Law> laws) {
        mDatas = new ArrayList<>();
        mDatas.addAll(laws);
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).setOnItemClickListener(mOnItemClickListener);
            ((MyViewHolder) holder).tv_name.setText("执法人员:" + mDatas.get(position).getName());
            ((MyViewHolder) holder).tv_desc.setText("全部信息:" + mDatas.get(position).getDesc());
            ((MyViewHolder) holder).tv_time.setText("检查时间:" + mDatas.get(position).getTime());
            ((MyViewHolder) holder).tv_result.setText("监督结果:" + mDatas.get(position).getResult());
            ((MyViewHolder) holder).tv_place.setText("执法地点:" + mDatas.get(position).getPlace());
        }

    }
    static class MyViewHolder extends MyVH{
        @Bind(R.id.tv_name)
        TextView tv_name;
        @Bind(R.id.tv_desc)
        TextView tv_desc;
        @Bind(R.id.tv_time)
        TextView tv_time;
        @Bind(R.id.tv_result)
        TextView tv_result;
        @Bind(R.id.tv_place)
        TextView tv_place;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    //    public void animateTo(List<Law> laws) {
//        applyAndAnimateRemovals(laws);
//        applyAndAnimateAdditions(laws);
//        applyAndAnimateMovedItems(laws);
//    }
//
//    private void applyAndAnimateRemovals(List<Law> peoples) {
//        for (int i = mDatas.size() - 1; i >= 0; i--) {
//            final Law law = mDatas.get(i);
//            if (!peoples.contains(law)) {
//                removeItem(i);
//            }
//        }
//    }
//
//    private void applyAndAnimateAdditions(List<Law> peoples) {
//        for (int i = 0, count = peoples.size(); i < count; i++) {
//            final Law law = mDatas.get(i);
//            if (!mDatas.contains(law)) {
//                addItem(i, law);
//            }
//        }
//    }
//    public void addItem(int position, Law law) {
//        mDatas.add(position, law);
//        notifyItemInserted(position);
//    }
//
//    private void applyAndAnimateMovedItems(List<Law> peoples) {
//        for (int toPosition = peoples.size() - 1; toPosition >= 0; toPosition--) {
//            final Law law = peoples.get(toPosition);
//            final int fromPosition = mDatas.indexOf(law);
//            if (fromPosition >= 0 && fromPosition != toPosition) {
//                moveItem(fromPosition, toPosition);
//            }
//        }
//    }
//    public void moveItem(int fromPosition, int toPosition) {
//        final Law people = mDatas.remove(fromPosition);
//        mDatas.add(toPosition, people);
//        notifyItemMoved(fromPosition, toPosition);
//    }
}
