package com.dhcc.datacage.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allen.supertextviewlibrary.SuperTextView;
import com.dhcc.datacage.R;
import com.dhcc.datacage.activity.law.Check_Activity;
import com.dhcc.datacage.activity.law.OrderCorrect_Activity;
import com.dhcc.datacage.activity.workbench.InfoNotification_Activity;
import com.dhcc.datacage.activity.workbench.IntegrateQuery_Activity;
import com.dhcc.datacage.activity.workbench.VedioConference_Activity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

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
    /**
     * View视图
     */
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_workbench, null);
        ButterKnife.bind(this, view);
        return view;
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
                Intent i2 = new Intent(getActivity(), OrderCorrect_Activity.class);
                startActivity(i2);
                break;
            case R.id.stv_gzt4:
                Intent i3 = new Intent(getActivity(), Check_Activity.class);
                startActivity(i3);
                break;
            case R.id.stv_gzt5:
                Intent i4 = new Intent(getActivity(), IntegrateQuery_Activity.class);
                startActivity(i4);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}