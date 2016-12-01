package com.dhcc.datacage.activity.synergy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.dhcc.datacage.R;
import com.dhcc.datacage.adapter.TeamWorkAdapter;
import com.dhcc.datacage.base.BaseActivity;
import com.dhcc.datacage.base.DividerItemDecoration;
import com.dhcc.datacage.model.TeamWork;
import com.dhcc.datacage.listener.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;
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
    private TeamWorkAdapter adapter;
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

        list.add(new TeamWork("贵阳市xx区发生了xx重大事件", "简短的文字描述简短的文字","2015-09-12"));
        list.add(new TeamWork("贵阳市xx区发生了xx重大事件", "简短的文字描述简短的文字","2015-09-12"));
        list.add(new TeamWork("贵阳市xx区发生了xx重大事件", "简短的文字描述简短的文字","2015-09-12"));
        list.add(new TeamWork("贵阳市xx区发生了xx重大事件", "简短的文字描述简短的文字","2015-09-12"));
        list.add(new TeamWork("贵阳市xx区发生了xx重大事件", "简短的文字描述简短的文字","2015-09-12"));
        list.add(new TeamWork("贵阳市xx区发生了xx重大事件", "简短的文字描述简短的文字","2015-09-12"));
        list.add(new TeamWork("贵阳市xx区发生了xx重大事件", "简短的文字描述简短的文字","2015-09-12"));
        adapter = new TeamWorkAdapter(this, list);
        adapter.setOnItemClickListener(onItemClickListener);
        mRecyclerView.setAdapter(adapter);
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
