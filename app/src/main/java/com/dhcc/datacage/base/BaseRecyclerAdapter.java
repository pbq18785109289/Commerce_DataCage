package com.dhcc.datacage.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.dhcc.datacage.R;
import com.dhcc.datacage.listener.OnItemClickListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * RecycleView适配器的基类
 * @param <T> 实体数据的泛型
 * @param <VH> ViewHolder泛型
 */
public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    /** viewtype **/
    protected static final int VIEW_TYPE = -1;
    /** 定义Item的点击事件 **/
    protected OnItemClickListener mOnItemClickListener;
    /** 上下文 **/
    protected Context mContext;
    /** 装实体的list集合数据 **/
    protected List<T> mDatas;
    /** 反射器 **/
    protected LayoutInflater inflater;

    /** 构造器 **/
    public BaseRecyclerAdapter(Context context) {
        this.mContext = context;
        this.mDatas = new ArrayList<>();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public BaseRecyclerAdapter(Context context, List<T> datas) {
        if (datas == null) datas = new ArrayList<>();
        this.mContext = context;
        this.mDatas = datas;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public BaseRecyclerAdapter(Context context, T[] datas) {
        this.mContext = context;
        this.mDatas = new ArrayList<T>();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Collections.addAll(mDatas, datas);
    }

    /**
     * 设置点击事件
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    /** 加载数量,在父类实现了 **/
    @Override
    public int getItemCount() {
        return mDatas.size() > 0 ? mDatas.size() : 1;
    }
    /** 获取条目 View填充的类型 **/
    public int getItemViewType(int position) {
        if (mDatas.size() <= 0) {
            return VIEW_TYPE;
        }
        return super.getItemViewType(position);
    }
    /** ViewHodler 子view的点击事件 子类需要重写该方法 **/
    public static class MyVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnItemClickListener mOnItemClickListener;

        public MyVH(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

    /**
     * 没有数据的时候
     */
    public class MyEmptyHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_empty)
        TextView tv_empty;

        public MyEmptyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    /** 更新数据，替换原有数据 */
    public void updateItems(List<T> items) {
        mDatas = items;
        notifyDataSetChanged();
    }

    /** 插入一条数据 */
    public void addItem(T item) {
        mDatas.add(0, item);
        notifyItemInserted(0);
    }

    /** 插入一条数据 */
    public void addItem(T item, int position) {
        position = Math.min(position, mDatas.size());
        mDatas.add(position, item);
        notifyItemInserted(position);
    }

    /** 在列表尾添加一串数据 */
    public void addItems(List<T> items) {
        int start = mDatas.size();
        mDatas.addAll(items);
        notifyItemRangeChanged(start, items.size());
    }

    /** 移除一条数据 */
    public void removeItem(int position) {
        if (position > mDatas.size() - 1) {
            return;
        }
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    /** 移除一条数据 */
    public void removeItem(T item) {
        int position = 0;
        ListIterator<T> iterator = mDatas.listIterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (next == item) {
                iterator.remove();
                notifyItemRemoved(position);
            }
            position++;
        }
    }

    /** 清除所有数据 */
    public void removeAllItems() {
        mDatas.clear();
        notifyDataSetChanged();
    }
}