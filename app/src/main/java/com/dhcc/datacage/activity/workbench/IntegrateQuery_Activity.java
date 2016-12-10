package com.dhcc.datacage.activity.workbench;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.dhcc.datacage.R;
import com.dhcc.datacage.adapter.MyQueryAdapter;
import com.dhcc.datacage.base.BaseActivity;
import com.dhcc.datacage.listener.OnItemClickListener;
import com.dhcc.datacage.model.Law;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by pengbangqin on 16-10-27.
 * 待办已办综合查询Activity
 */
public class IntegrateQuery_Activity extends BaseActivity {
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    @Bind(R.id.search_view)
    MaterialSearchView searchView;
    private MyQueryAdapter adapter;
    List<Law> list = new ArrayList<>();
    private Law[] laws = {
            new Law("peng", "这是什么是?", "2016-10-27", "待办", "贵阳"),
            new Law("zsssa", "你知道他们么?", "2016-10-27", "待办", "贵阳"),
            new Law("sasa", "我还有事情?", "2016-10-27", "待办", "贵阳"),
            new Law("qqwqw", "请不啊哟找我?", "2016-10-27", "待办", "贵阳"),
            new Law("uyt", "这只是个而是?", "2016-10-27", "待办", "贵阳"),
            new Law("yuun", "是个测试哦?", "2016-10-27", "待办", "贵阳"),
            new Law("llll", "我还有事情没办呢?", "2016-10-27", "待办", "贵阳")
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workbench_qurey);
        ButterKnife.bind(this);
        initToolBar(toolbar, toolbarTitle, true, "待办已办综合查询");
        initData();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        mRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行
        adapter = new MyQueryAdapter(this, list);
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
        //初始化SearchView
        initSearchView();
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
     * 获取数据  测试随机挑选50中,保证每次都不一样
     */
    private void initData() {
        list.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(laws.length);
            list.add(laws[index]);
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

    /**
     * 定义搜索框 搜索按钮
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }
    /**
     * 初始化SearchView
     */
    private void initSearchView() {
        //不是用语音搜索
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                final List<Law> filteredModelList = filter(list, newText);
                //reset
                adapter.setFilter(filteredModelList);
//                adapter.animateTo(filteredModelList);
                mRecyclerView.scrollToPosition(0);
                return true;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
            }

            @Override
            public void onSearchViewClosed() {
                adapter.setFilter(list);
            }
        });
    }

    /**
     * 筛选逻辑
     * @param laws
     * @param query
     * @return
     */
    private List<Law> filter(List<Law> laws, String query) {
        query = query.toLowerCase();

        final List<Law> filteredModelList = new ArrayList<>();
        for (Law law : laws) {
            final String nameEn = law.getName().toLowerCase();
            final String desEn = law.getDesc().toLowerCase();
            final String name = law.getName();
            final String des = law.getDesc();
            if (name.contains(query) || des.contains(query) || nameEn.contains(query) || desEn.contains(query)) {
                filteredModelList.add(law);
            }
        }
        return filteredModelList;
    }

    /**
     * 返回时关闭搜索
     */
    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }
}
