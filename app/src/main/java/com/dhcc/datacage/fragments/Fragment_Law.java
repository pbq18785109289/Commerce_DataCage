package com.dhcc.datacage.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.dhcc.datacage.R;
import com.dhcc.datacage.activity.law.CaseAudit_Activity;
import com.dhcc.datacage.activity.law.CaseFiling_Activity;
import com.dhcc.datacage.activity.law.CaseHearing_Activity;
import com.dhcc.datacage.activity.law.CaseRegister_Activity;
import com.dhcc.datacage.activity.law.Doubt_Activity;
import com.dhcc.datacage.activity.law.Investigation_Activity;
import com.dhcc.datacage.activity.law.LawLocation_Activity;
import com.dhcc.datacage.activity.law.Supervise_Activity;
import com.dhcc.datacage.base.BaseListViewAdapter;
import com.dhcc.datacage.utils.DataUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * 执法Fragment
 *
 * @author pengbangqin
 */
public class Fragment_Law extends Fragment {
    @Bind(R.id.gv_law)
    GridView gvLaw;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_law, container, false);
        ButterKnife.bind(this, view);
        LawGridViewAdapter adapter = new LawGridViewAdapter(getActivity(), DataUtil.imagesID, DataUtil.str1);
        gvLaw.setAdapter(adapter);
        return view;
    }

    @OnItemClick(R.id.gv_law)
    public void onItemClick(int position) {
        switch (position) {
            case 0://案件登记
                Intent i0 = new Intent(getActivity(), CaseRegister_Activity.class);
                getActivity().startActivity(i0);
                break;
            case 1://案件登记
                Intent i1 = new Intent(getActivity(), CaseAudit_Activity.class);
                getActivity().startActivity(i1);
                break;
            case 2://调查材料取证
                Intent i2 = new Intent(getActivity(), Investigation_Activity.class);
                getActivity().startActivity(i2);
                break;
            case 3://案件审理材料填报
                Intent i3 = new Intent(getActivity(), CaseHearing_Activity.class);
                getActivity().startActivity(i3);
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7://结案归档提交审批
                Intent i7 = new Intent(getActivity(), CaseFiling_Activity.class);
                getActivity().startActivity(i7);
                break;
            case 8://疑情疑点信息登记,上报,查询
                Intent i8 = new Intent(getActivity(), Doubt_Activity.class);
                getActivity().startActivity(i8);
                break;
            case 9:
                Intent i9 = new Intent(getActivity(), Supervise_Activity.class);
                getActivity().startActivity(i9);
                break;
            case 10:
                break;
            case 11://执法人员位置信息
                Intent intent11 = new Intent(getActivity(), LawLocation_Activity.class);
                startActivity(intent11);
                break;
        }
    }

    public class LawGridViewAdapter extends BaseListViewAdapter {
        /**
         * 构造器
         * @param context
         * @param imgs
         * @param str
         **/
        public LawGridViewAdapter(Context context, int[] imgs, String[] str) {
            super(context, imgs, str);
        }
        @Override
        public int initView() {
            return R.layout.law_gridview_item;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}