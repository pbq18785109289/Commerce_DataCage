package com.dhcc.datacage.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhcc.datacage.R;

/**
 * Created by pengbangqin on 16-11-9.
 * listview
 */
public abstract class BaseListViewAdapter extends BaseAdapter{
    /** 上下文 **/
    protected Context context;
    /** 图片数据 **/
    protected int[] imgs;
    /** 文字数据 **/
    protected String[] str;
    /** 反射器 **/
    protected LayoutInflater inflater;

    /** 构造器 **/
    public BaseListViewAdapter(Context context, int[] imgs, String[] str) {
        this.context=context;
        this.imgs=imgs;
        this.str=str;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=inflater.inflate(initView(), null);
        ImageView iv_img=(ImageView) v.findViewById(R.id.iv_img);
        TextView tv_title=(TextView) v.findViewById(R.id.tv_title);
        iv_img.setImageResource(imgs[position]);
        tv_title.setText(str[position]);
        return v;
    }
    public abstract int initView();
}
