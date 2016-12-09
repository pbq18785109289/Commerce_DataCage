package com.dhcc.datacage.activity.workbench;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.dhcc.datacage.R;
import com.dhcc.datacage.adapter.InfoNotificationAdapter;
import com.dhcc.datacage.base.BaseActivity;
import com.dhcc.datacage.base.DividerItemDecoration;
import com.dhcc.datacage.model.InfoNotify;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by pengbangqin on 2016/12/5.
 */

public class InfoNotification_Activity extends BaseActivity {
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private InfoNotificationAdapter adapter;
    List<InfoNotify> list;
    Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workbench_info);
        ButterKnife.bind(this);
        initToolBar(toolbar,toolbarTitle,true,"消息通知");
        bundle=getIntent().getExtras();
        if(bundle!=null){
            initData();
        }
        init();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        list=new ArrayList<>();
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Log.d("zll", " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        Log.d("zll", "message : " + message);
        //设置日期格式
        SimpleDateFormat format=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        String time=format.format(new Date());
        //将收到的消息装入实体类InfoNotify中
        InfoNotify notify=new InfoNotify();
        notify.setTitle(title);
        notify.setMessage(message);
        notify.setTime(time);
        list.add(notify);
    }

    /**
     * 初始化操作
     */
    private void init() {
        //添加分割线
//        mRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        //设置布局管理器
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        //设置默认动画
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
        adapter=new InfoNotificationAdapter(this, list);
        mRecyclerview.setAdapter(adapter);
    }
}
