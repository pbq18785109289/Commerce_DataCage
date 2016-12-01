package com.dhcc.datacage.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.dhcc.datacage.R;
import com.dhcc.datacage.activity.synergy.TeamWork_Activity;
import com.dhcc.datacage.base.BaseListViewAdapter;
import com.dhcc.datacage.utils.DataUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * 协同Fragment
 *
 * @author pengbangqin
 */
public class Fragment_Synerg extends Fragment {
    @Bind(R.id.lv_synerg)
    ListView lvSynerg;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_synerg, null);
        ButterKnife.bind(this, view);
        Synerg_Lv_Adapter adapter = new Synerg_Lv_Adapter(getActivity(), DataUtil.imgs, DataUtil.str);
        lvSynerg.setAdapter(adapter);
        return view;
    }

    @OnItemClick(R.id.lv_synerg)
    public void onItemClick(int position){
        switch (position) {
            case 0:
                break;
            case 1:
                Intent i1 = new Intent(getActivity(), TeamWork_Activity.class);
                startActivity(i1);
                break;
        }
    }
    public class Synerg_Lv_Adapter extends BaseListViewAdapter {
        /**
         * 构造器
         * @param context
         * @param imgs
         * @param str
         **/
        public Synerg_Lv_Adapter(Context context, int[] imgs, String[] str) {
            super(context, imgs, str);
        }
        @Override
        public int initView() {
            return R.layout.workbench_lv_item;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
