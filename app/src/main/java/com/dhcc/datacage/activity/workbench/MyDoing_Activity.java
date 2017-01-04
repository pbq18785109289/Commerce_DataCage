package com.dhcc.datacage.activity.workbench;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.dhcc.datacage.R;
import com.dhcc.datacage.activity.law.Check_Activity;
import com.dhcc.datacage.adapter.InfoNotificationAdapter;
import com.dhcc.datacage.adapter.MyDoAdapter;
import com.dhcc.datacage.base.BaseActivity;
import com.dhcc.datacage.listener.OnItemClickListener;
import com.dhcc.datacage.model.InfoNotify;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by pengbangqin on 2016/12/14.
 * 我的待办
 */

public class MyDoing_Activity extends BaseActivity{
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
        initToolBar(toolbar,toolbarTitle,true,"我的待办");
        init();
        //设置布局管理器
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        //设置默认动画
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
        adapter=new MyDoAdapter(this, list);
        adapter.setOnItemClickListener(onItemClickListener);
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
        list.add(new InfoNotify("贵阳市云岩区某某超市","经群众举报,该超市发现存在卫生不合格,需尽快处理","2016-12-01"));
        list.add(new InfoNotify("贵阳市云岩区某某美发店","该店发现违规行为,请尽快处理","2016-12-03"));
        list.add(new InfoNotify("贵阳市云岩区某某KTV","经群众举报,该KTV存在乱收费行为,请相关人员调查处理","2016-12-05"));
        list.add(new InfoNotify("贵阳市乌当区某某鸡排","发现该店员工偷工减料,需尽快处理","2016-12-06"));
        list.add(new InfoNotify("贵阳市花溪区某某化妆品","发现该店的化妆品不合格,需尽快处理","2016-12-07"));
        list.add(new InfoNotify("贵阳市小河区某某网咖","发现该店的机子存在安全隐患,请相关人员调查核实","2016-12-13"));
        list.add(new InfoNotify("贵阳市南明区某某某某超市","经群众举报,该超市乱收费情况严重,需尽快处理","2016-12-14"));
    }
    /**
     * item的点击事件
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Intent intent = new Intent(MyDoing_Activity.this, Check_Activity.class);
            intent.putExtra("title",list.get(position).getTitle());
            startActivity(intent);
        }
    };
}
