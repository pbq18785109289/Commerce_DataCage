package com.dhcc.datacage.activity.law;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dhcc.datacage.R;
import com.dhcc.datacage.base.BaseActivity;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by pengbangqin on 16-10-27.
 * 案件审理材料填报Activity
 */
public class CaseHearing_Activity extends BaseActivity {
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_caseInfo)
    EditText etCaseInfo;
    @Bind(R.id.et_hearingTime)
    EditText etHearingTime;
    @Bind(R.id.et_hearingOrg)
    EditText etHearingOrg;
    @Bind(R.id.et_hearingPer)
    EditText etHearingPer;
    @Bind(R.id.et_hearingRes)
    EditText etHearingRes;
    @Bind(R.id.et_hearingDesc)
    EditText etHearingDesc;
    @Bind(R.id.et_hearingIdea)
    EditText etHearingIdea;
    @Bind(R.id.btn_save)
    Button btnSave;
    @Bind(R.id.btn_cancel)
    Button btnCancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law_casehearing);
        initToolBar(toolbar,toolbarTitle,true,"案件审理材料填报");
    }

    @OnClick({R.id.btn_save, R.id.btn_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                break;
            case R.id.btn_cancel:
                break;
        }
    }
}
