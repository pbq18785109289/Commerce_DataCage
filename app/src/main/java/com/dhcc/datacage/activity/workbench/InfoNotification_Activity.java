package com.dhcc.datacage.activity.workbench;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.dhcc.datacage.R;
import com.dhcc.datacage.adapter.InfoNotificationAdapter;
import com.dhcc.datacage.base.BaseActivity;
import com.dhcc.datacage.client.NotificationHistory;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 历史消息通知
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
    private List<NotificationHistory> mList = new ArrayList<NotificationHistory>();
    Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workbench_info);
        ButterKnife.bind(this);
        initToolBar(toolbar,toolbarTitle,true,"消息通知");
        //获取数据库中的数据
        mList = DataSupport.findAll(NotificationHistory.class);
        init();
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
        adapter=new InfoNotificationAdapter(this, mList);
        mRecyclerview.setAdapter(adapter);
    }
}
