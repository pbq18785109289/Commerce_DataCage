package com.dhcc.datacage.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.dhcc.datacage.R;
import com.dhcc.datacage.activity.law.OrderCorrect_Activity;
import com.dhcc.datacage.activity.workbench.InfoNotification_Activity;
import com.dhcc.datacage.activity.workbench.IntegrateQuery_Activity;
import com.dhcc.datacage.activity.workbench.VedioConference_Activity;
import com.dhcc.datacage.base.BaseListViewAdapter;
import com.dhcc.datacage.utils.DataUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * 工作台Fragment
 *
 * @author pengbangqin
 */
public class Fragment_Workbench extends Fragment{
    @Bind(R.id.lv_workbench)
    ListView lvWorkbench;
    /**
     * View视图
     */
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_workbench, null);
        ButterKnife.bind(this, view);
        Workbench_Lv_Adapter adapter = new Workbench_Lv_Adapter(getActivity(), DataUtil.imgs0, DataUtil.str0);
        lvWorkbench.setAdapter(adapter);
        return view;
    }

    @OnItemClick(R.id.lv_workbench)
    public void onItemClick(int position) {
        switch (position) {
            case 0:
                Intent i0 = new Intent(getActivity(), InfoNotification_Activity.class);
                startActivity(i0);
                break;
            case 1:
                Intent i1 = new Intent(getActivity(), VedioConference_Activity.class);
                startActivity(i1);
                break;
            case 2:
                Intent i2 = new Intent(getActivity(), OrderCorrect_Activity.class);
                startActivity(i2);
                break;
            case 3:
                break;
            case 4:
                Intent i4 = new Intent(getActivity(), IntegrateQuery_Activity.class);
                startActivity(i4);
                break;
        }
    }

    public class Workbench_Lv_Adapter extends BaseListViewAdapter {

        /**
         * 构造器
         * @param context
         * @param imgs
         * @param str
         **/
        public Workbench_Lv_Adapter(Context context, int[] imgs, String[] str) {
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