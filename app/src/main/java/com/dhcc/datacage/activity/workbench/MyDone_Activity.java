package com.dhcc.datacage.activity.workbench;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.dhcc.datacage.R;
import com.dhcc.datacage.adapter.InfoNotificationAdapter;
import com.dhcc.datacage.adapter.MyDoAdapter;
import com.dhcc.datacage.base.BaseActivity;
import com.dhcc.datacage.model.InfoNotify;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by pengbangqin on 2016/12/14.
 * 我的已办
 */

public class MyDone_Activity extends BaseActivity{
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    private MyDoAdapter adapter;
    List<InfoNotify> list=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workbench_mydoing);
        ButterKnife.bind(this);
        initToolBar(toolbar,toolbarTitle,true,"我的已办");
        init();
        //设置布局管理器
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        //设置默认动画
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
        adapter=new MyDoAdapter(this, list);
        mRecyclerview.setAdapter(adapter);
        //设置下拉刷新进度条的颜色
        mSwipeRefresh.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }
    /**
     * 下拉刷新
     */
    private void refresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //将线程切换到主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //重新生成数据
//                        init();
                        //通知数据发生了变化
                        adapter.notifyDataSetChanged();
                        //刷新事件结束 并隐藏刷新进度条
                        mSwipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
    /**
     * 初始化数据
     */
    private void init() {
        list.add(new InfoNotify("贵阳市某某超市","卫生情况已处理","2016-12-01"));
        list.add(new InfoNotify("贵阳市某某美发店","违规行为已处理","2016-12-03"));
        list.add(new InfoNotify("贵阳市某某KTV","已调查处理","2016-12-05"));
        list.add(new InfoNotify("贵阳市某某网咖","安全隐患已核实","2016-12-13"));
    }
}

