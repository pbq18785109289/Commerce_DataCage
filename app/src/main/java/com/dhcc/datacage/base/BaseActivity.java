package com.dhcc.datacage.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import com.dhcc.datacage.R;
import com.dhcc.datacage.utils.SnackbarUtils;
import com.jaeger.library.StatusBarUtil;

/**
 * Created by pengbangqin on 16-11-9.
 * 所有Activity的基类
 */
public abstract class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        StatusBarUtil.setTranslucent(this,60);
    }
    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    /** 初始化 Toolbar */
    public void initToolBar(Toolbar toolbar, TextView toolbarTitle, boolean homeAsUpEnabled, String title) {
        toolbar.setTitle("");
        toolbarTitle.setText(title);
        setSupportActionBar(toolbar);
        //让导航按钮显示出来
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /** 获取系统状态栏颜色 **/
    public int getColorPrimary(){
        return getResources().getColor(R.color.colorPrimary);
    }

    /**
     * 显示Toast
     * @param msg
     */
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    public void showSnack(View v,String msg) {
        SnackbarUtils.Short(v,msg).backColor(getColorPrimary()).show();
    }
    private ProgressDialog dialog;

    public void showLoading() {
        if (dialog != null && dialog.isShowing()) return;
        dialog = new ProgressDialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("请求网络中...");
        dialog.show();
    }
    public void dismissLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
