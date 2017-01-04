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
import com.dhcc.datacage.adapter.MyDoAdapter;
import com.dhcc.datacage.base.BaseActivity;
import com.dhcc.datacage.listener.OnItemClickListener;
import com.dhcc.datacage.model.InfoNotify;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


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
    private MyDoAdapter adapter;
    List<InfoNotify> list = new ArrayList<>();

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
        adapter = new MyDoAdapter(this, list);
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
//                        initData();
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
        list.add(new InfoNotify("贵阳市云岩区某某超市","经群众举报,该超市发现存在卫生不合格,需尽快处理","2016-12-01"));
        list.add(new InfoNotify("贵阳市云岩区某某美发店","该店发现违规行为,请尽快处理","2016-12-03"));
        list.add(new InfoNotify("贵阳市云岩区某某KTV","经群众举报,该KTV存在乱收费行为,请相关人员调查处理","2016-12-05"));
        list.add(new InfoNotify("贵阳市乌当区某某鸡排","发现该店员工偷工减料,需尽快处理","2016-12-06"));
        list.add(new InfoNotify("贵阳市花溪区某某化妆品","发现该店的化妆品不合格,需尽快处理","2016-12-07"));
        list.add(new InfoNotify("贵阳市某某超市","卫生情况已处理","2016-12-01"));
        list.add(new InfoNotify("贵阳市某某美发店","违规行为已处理","2016-12-03"));
        list.add(new InfoNotify("贵阳市某某KTV","已调查处理","2016-12-05"));
        list.add(new InfoNotify("贵阳市某某网咖","安全隐患已核实","2016-12-13"));
        list.add(new InfoNotify("贵阳市小河区某某网咖","发现该店的机子存在安全隐患,请相关人员调查核实","2016-12-13"));
        list.add(new InfoNotify("贵阳市南明区某某某某超市","经群众举报,该超市乱收费情况严重,需尽快处理","2016-12-14"));
    }
//    private void initData() {
//        list.clear();
//        for (int i = 0; i < 50; i++) {
//            Random random = new Random();
//            int index = random.nextInt(laws.length);
//            list.add(laws[index]);
//        }
//    }

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
                final List<InfoNotify> filteredModelList = filter(list, newText);
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
    private List<InfoNotify> filter(List<InfoNotify> laws, String query) {
        query = query.toLowerCase();

        final List<InfoNotify> filteredModelList = new ArrayList<>();
        for (InfoNotify notify : laws) {
            final String nameEn = notify.getTitle().toLowerCase();
            final String desEn = notify.getMessage().toLowerCase();
            final String name = notify.getTitle();
            final String des = notify.getMessage();
            if (name.contains(query) || des.contains(query) || nameEn.contains(query) || desEn.contains(query)) {
                filteredModelList.add(notify);
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
