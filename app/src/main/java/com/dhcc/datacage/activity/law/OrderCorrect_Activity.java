package com.dhcc.datacage.activity.law;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;

import com.dhcc.datacage.R;
import com.dhcc.datacage.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by pengbangqin on 2016/12/5.
 * 责令整改通知书
 */

public class OrderCorrect_Activity extends BaseActivity {
    @Bind(R.id.detail_img)
    ImageView mDetailImg;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @Bind(R.id.app_bar)
    AppBarLayout mAppBar;
    @Bind(R.id.fab)
    FloatingActionButton mFab;
    @Bind(R.id.detail_content)
    CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law_ordercorrect);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //使mToolbar取代原来的ActionBar
        setSupportActionBar(mToolbar);
        //设置mToolbar左上角显示返回图标
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setNavigationIcon(R.mipmap.icon_back);
        //设置返回图标的点击事件
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置还没收缩时状态下字体颜色
        mToolbarLayout.setExpandedTitleColor(Color.RED);
        mToolbarLayout.setExpandedTitleGravity(Gravity.CENTER);
        mToolbarLayout.setTitle("责令整改");
        //设置收缩后Toolbar上字体的颜色
        mToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        //设置image背景
        mDetailImg.setBackgroundColor(Color.parseColor("#76b1cc"));
//        mDetailImg.setBackgroundResource(R.mipmap.bg);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(mCoordinatorLayout, "点我分享哦！", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
