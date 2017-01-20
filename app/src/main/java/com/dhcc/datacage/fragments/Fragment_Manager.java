package com.dhcc.datacage.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allen.supertextviewlibrary.SuperTextView;
import com.dhcc.datacage.R;
import com.dhcc.datacage.activity.synergy.TeamWork_Activity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 协同Fragment
 * @author pengbangqin
 */
public class Fragment_Manager extends Fragment {
    @Bind(R.id.stv_xt1)
    SuperTextView stvXt1;
    @Bind(R.id.stv_xt2)
    SuperTextView stvXt2;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_synerg, null);
        ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    @OnClick({R.id.stv_xt1, R.id.stv_xt2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.stv_xt1:
                break;
            case R.id.stv_xt2:
                Intent i1 = new Intent(getActivity(), TeamWork_Activity.class);
                startActivity(i1);
                break;
        }
    }
}
