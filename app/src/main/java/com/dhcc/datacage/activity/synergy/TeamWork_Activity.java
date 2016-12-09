package com.dhcc.datacage.activity.synergy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.dhcc.datacage.R;
import com.dhcc.datacage.adapter.TeamWorkAdapter;
import com.dhcc.datacage.base.BaseActivity;
import com.dhcc.datacage.base.DividerItemDecoration;
import com.dhcc.datacage.listener.OnItemClickListener;
import com.dhcc.datacage.model.TeamWork;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by pengbangqin on 16-10-31.
 * 团队互助Activity
 */
public class TeamWork_Activity extends BaseActivity {
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    List<TeamWork> list = new ArrayList<>();
    @Bind(R.id.rv)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    private TeamWorkAdapter adapter;

    private TeamWork[] teamWorks={
            new TeamWork("贵阳市云岩区发生了爆炸", "你想要了解真相吗?", "2015-09-12"),
            new TeamWork("贵阳市花溪区一家餐馆出现臭", "经群众举报,发现花溪区某餐馆...", "2015-09-12"),
            new TeamWork("小河区美发店一直未嚼蜡", "经过检查,一家美发店尚未...", "2015-09-12"),
            new TeamWork("saaaaaaaaaaaaaaaaaa", "ggggggggfdssdsdsdsds", "2015-09-12"),
            new TeamWork("asfqqgbnnnn", "qwrewteqwvzxzxz", "2015-09-12"),
            new TeamWork("lkhgffdss", "lllllllllllllllllllllll", "2015-09-12"),
            new TeamWork("poiuyrreewwww", "ppppppppppppppppppppppppp", "2015-09-12")
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synergy_teamwork);
        ButterKnife.bind(this);
        initToolBar(toolbar, toolbarTitle, true, "团队互助");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        mRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        initData();
        list.add(new TeamWork("贵阳市xx区发生了xx重大事件", "简短的文字描述简短的文字", "2015-09-12"));
        list.add(new TeamWork("贵阳市xx区发生了xx重大事件", "简短的文字描述简短的文字", "2015-09-12"));
        list.add(new TeamWork("贵阳市xx区发生了xx重大事件", "简短的文字描述简短的文字", "2015-09-12"));
        list.add(new TeamWork("贵阳市xx区发生了xx重大事件", "简短的文字描述简短的文字", "2015-09-12"));
        list.add(new TeamWork("贵阳市xx区发生了xx重大事件", "简短的文字描述简短的文字", "2015-09-12"));
        list.add(new TeamWork("贵阳市xx区发生了xx重大事件", "简短的文字描述简短的文字", "2015-09-12"));
        list.add(new TeamWork("贵阳市xx区发生了xx重大事件", "简短的文字描述简短的文字", "2015-09-12"));
        adapter = new TeamWorkAdapter(this, list);
        adapter.setOnItemClickListener(onItemClickListener);
        mRecyclerView.setAdapter(adapter);
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
                        initData();
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
     * 获取数据  测试随机挑选50中水果,保证每次都不一样
     */
    private void initData() {
        list.clear();
        for(int i=0;i<50;i++){
            Random random=new Random();
            int index=random.nextInt(teamWorks.length);
            list.add(teamWorks[index]);
        }
    }

    /**
     * item的点击事件
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            showToast("dianji" + position);
        }
    };
}
