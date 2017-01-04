package com.dhcc.datacage.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allen.supertextviewlibrary.SuperTextView;
import com.dhcc.datacage.R;
import com.dhcc.datacage.activity.workbench.InfoNotification_Activity;
import com.dhcc.datacage.activity.workbench.IntegrateQuery_Activity;
import com.dhcc.datacage.activity.workbench.MyDoing_Activity;
import com.dhcc.datacage.activity.workbench.MyDone_Activity;
import com.dhcc.datacage.activity.workbench.VedioConference_Activity;
import com.dhcc.datacage.client.Constants;
import com.dhcc.datacage.client.ServiceManager;
import com.dhcc.datacage.view.BadgeView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 工作台Fragment
 *
 * @author pengbangqin
 */
public class Fragment_Workbench extends Fragment {
    @Bind(R.id.stv_gzt1)
    SuperTextView stvGzt1;
    @Bind(R.id.stv_gzt2)
    SuperTextView stvGzt2;
    @Bind(R.id.stv_gzt3)
    SuperTextView stvGzt3;
    @Bind(R.id.stv_gzt4)
    SuperTextView stvGzt4;
    @Bind(R.id.stv_gzt5)
    SuperTextView stvGzt5;
    @Bind(R.id.tv_tishi)
    BadgeView tvTishi;
    private DataChangeReceiver receiver;

    /**
     * View视图
     */
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_workbench, null);
        ButterKnife.bind(this, view);
        updateView();
        //注册广播 监听推送的数量发送改变时
        IntentFilter filter=new IntentFilter();
        filter.addAction("com.dhcc.datacage.DATA_CHANGE");
        receiver=new DataChangeReceiver();
        getActivity().registerReceiver(receiver,filter);
        return view;
    }

    /**
     * 更新接收的消息数
     */
    private void updateView(){
        int result= ServiceManager.sharedPrefs.getInt(Constants.NOTIFICATION_RESULT,0);
        if(result==0){
            tvTishi.setVisibility(View.GONE);
        }else{
            tvTishi.setVisibility(View.VISIBLE);
            tvTishi.setText(result+ "");
        }
    }
    @OnClick({R.id.stv_gzt1, R.id.stv_gzt2, R.id.stv_gzt3, R.id.stv_gzt4, R.id.stv_gzt5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.stv_gzt1:
                Intent i0 = new Intent(getActivity(), InfoNotification_Activity.class);
                startActivity(i0);
                break;
            case R.id.stv_gzt2:
                Intent i1 = new Intent(getActivity(), VedioConference_Activity.class);
                startActivity(i1);
                break;
            case R.id.stv_gzt3:
                Intent i2 = new Intent(getActivity(), MyDoing_Activity.class);
                startActivity(i2);
                break;
            case R.id.stv_gzt4:
                Intent i3 = new Intent(getActivity(), MyDone_Activity.class);
                startActivity(i3);
                break;
            case R.id.stv_gzt5:
                Intent i4 = new Intent(getActivity(), IntegrateQuery_Activity.class);
                startActivity(i4);
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        updateView();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if(receiver!=null){
            getActivity().unregisterReceiver(receiver);
            receiver=null;
        }
    }

    class DataChangeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            updateView();
        }
    }
}