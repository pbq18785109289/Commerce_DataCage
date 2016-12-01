package com.dhcc.datacage.activity.workbench;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;

import com.dhcc.datacage.R;
import com.dhcc.datacage.adapter.MyQueryAdapter;
import com.dhcc.datacage.base.BaseActivity;
import com.dhcc.datacage.model.Law;
import com.dhcc.datacage.listener.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by pengbangqin on 16-10-27.
 * 待办已办综合查询Activity
 */
public class IntegrateQuery_Activity extends BaseActivity {
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_query)
    EditText etQuery;
    @Bind(R.id.rv)
    RecyclerView mRecyclerView;
    private MyQueryAdapter adapter;
    List<Law> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workbench_qurey);
        initToolBar(toolbar,toolbarTitle,true,"待办已办综合查询");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        mRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行

        list.add(new Law("王青", "我还有事情没办呢?", "2016-10-27", "待办", "贵阳"));
        list.add(new Law("王青1", "我还有事情没办呢1?", "2016-10-27", "待办", "贵阳"));
        list.add(new Law("王青2", "我还有事情没办呢2?", "2016-10-27", "已办", "贵阳"));
        list.add(new Law("王青3", "我还有事情没办呢3?", "2016-10-27", "已办", "贵阳"));
        list.add(new Law("王青4", "我还有事情没办呢4?", "2016-10-27", "待办", "贵阳"));
        list.add(new Law("王青5", "我还有事情没办呢5?", "2016-10-27", "待办", "贵阳"));
        adapter = new MyQueryAdapter(this, list);
        adapter.setOnItemClickListener(onItemClickListener);
        mRecyclerView.setAdapter(adapter);
        //刷新完成
    }

    /**
     * item的点击事件
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            showToast("dianji"+position);
        }
    };
}
