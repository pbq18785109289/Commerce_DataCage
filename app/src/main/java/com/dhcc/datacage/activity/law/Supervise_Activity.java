package com.dhcc.datacage.activity.law;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;
import com.dhcc.datacage.R;
import com.dhcc.datacage.base.BaseActivity;
import com.dhcc.datacage.base.BaseListViewAdapter;
import com.dhcc.datacage.utils.DataUtil;
import butterknife.Bind;
import butterknife.OnItemClick;

/**
 * Created by pengbangqin on 16-11-10.
 * 监督检查信息登记,上报,查询Activity
 */
public class Supervise_Activity extends BaseActivity {
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.lv)
    ListView lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law_doubt);
        initToolBar(toolbar,toolbarTitle,true,"监督检查信息");
        lv.setAdapter(new MyAdapter(this,DataUtil.lawImgs,DataUtil.lawStr));
    }

    @OnItemClick(R.id.lv)
    public void onItemClick(int position){
        switch (position){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
        }
    }


    private class MyAdapter extends BaseListViewAdapter {
        /**
         * 构造器
         *
         * @param context
         * @param imgs
         * @param str
         **/
        public MyAdapter(Context context, int[] imgs, String[] str) {
            super(context, imgs, str);
        }

        @Override
        public int initView() {
            return R.layout.workbench_lv_item;
        }
    }
}
