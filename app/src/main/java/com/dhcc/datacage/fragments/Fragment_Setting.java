package com.dhcc.datacage.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import com.allen.supertextviewlibrary.SuperTextView;
import com.dhcc.datacage.R;
import com.dhcc.datacage.activity.LoginActivity;
import com.dhcc.datacage.activity.setting.UpdatePwd_Activity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

/**
 * 设置的Fragment
 *
 * @author pengbangqin
 */
public class Fragment_Setting extends Fragment implements View.OnClickListener {
    @Bind(R.id.cb_autoLogin)
    CheckBox cbAutoLogin;
    @Bind(R.id.tv_updatePwd)
    SuperTextView tvUpdatePwd;
    @Bind(R.id.tv_clearCache)
    SuperTextView tvClearCache;
    @Bind(R.id.tv_closeLocate)
    SuperTextView tvCloseLocate;
    @Bind(R.id.tv_version_update)
    SuperTextView tvVersionUpdate;
    @Bind(R.id.tv_exit)
    SuperTextView tvExit;
    private SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_setting, null);
        ButterKnife.bind(this, view);
        sp= getActivity().getSharedPreferences("user",getActivity().MODE_PRIVATE);
        //设置自动登录的选中状态
        cbAutoLogin.setChecked(sp.getBoolean("AUTO_ISCHECK",false));
        Toast.makeText(getContext(),sp.getBoolean("AUTO_ISCHECK",false)+"先",Toast.LENGTH_SHORT).show();
        return view;
    }

    @OnCheckedChanged(R.id.cb_autoLogin)
    public void OnCheckedChanged(CompoundButton buttonView, boolean isChecked){
        //更改自动登录状态
        sp.edit().putBoolean("AUTO_ISCHECK",isChecked).commit();
        Toast.makeText(getContext(),isChecked+"后",Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.tv_updatePwd, R.id.tv_clearCache, R.id.tv_closeLocate, R.id.tv_version_update, R.id.tv_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_updatePwd://修改密码
                Intent i1 = new Intent(getActivity(), UpdatePwd_Activity.class);
                startActivity(i1);
                break;
            case R.id.tv_clearCache:
                showClearDialog();
                break;
            case R.id.tv_closeLocate:
                closeGPS();
                break;
            case R.id.tv_version_update:
                showUpdateDialog();
                break;
            case R.id.tv_exit:
                showExitDialog();
                break;
        }
    }
    /**
     * 弹出确认清楚缓存对话框
     */
    private void showUpdateDialog() {
        new AlertDialog.Builder(getActivity())
//                .setIcon(R.mipmap.exit)
                .setTitle("提示")
                .setMessage("检测到有新的版本，是否更新？")
                .setNegativeButton("取消", null)
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "更新中...", Toast.LENGTH_SHORT).show();
                    }
                }).create().show();
    }

    /**
     * 弹出确认清楚缓存对话框
     */
    private void showClearDialog() {
        new AlertDialog.Builder(getActivity())
//                .setIcon(R.mipmap.exit)
                .setTitle("确认清除缓存?")
//                .setMessage("确认清除缓存?")
                .setNegativeButton("取消", null)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvClearCache.setRightString("0.0M");
                        Toast.makeText(getActivity(), "清除缓存中...", Toast.LENGTH_SHORT).show();
                    }
                }).create().show();
    }

    /**
     * 关闭GPS
     */
    private void closeGPS() {
        LocationManager locationManager = (LocationManager) getActivity()
                .getSystemService(Context.LOCATION_SERVICE);
        // 判断GPS模块是否开启，如果没有则开启
        if (locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            tvCloseLocate.setRightString("已打开");
            // 转到手机设置界面，用户设置GPS
            Intent intent = new Intent(
                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 0); // 设置完成后返回到原来的界面

        } else {
            tvCloseLocate.setRightString("GPS未打开");
            Toast.makeText(getActivity(), "GPS未打开", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 弹出确认退出对话框
     */
    private void showExitDialog() {
        new AlertDialog.Builder(getActivity())
//                .setIcon(R.mipmap.exit)
                .setTitle("退出应用")
                .setMessage("确认退出应用")
                .setNegativeButton("取消", null)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();//结束当前Activity
                        Intent startMain = new Intent(getActivity(), LoginActivity.class);
                        startActivity(startMain);
                    }
                }).create().show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
