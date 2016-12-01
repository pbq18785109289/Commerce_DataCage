package com.dhcc.datacage.activity.law;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dhcc.datacage.R;
import com.dhcc.datacage.base.BaseActivity;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by pengbangqin on 16-10-27.
 * 案件登记材料填报Activity
 */
public class CaseRegister_Activity extends BaseActivity {
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_caseOrigi)
    EditText etCaseOrigi;
    @Bind(R.id.et_caseOrg)
    EditText etCaseOrg;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_place)
    EditText etPlace;
    @Bind(R.id.et_time)
    EditText etTime;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_address)
    EditText etAddress;
    @Bind(R.id.et_method)
    EditText etMethod;
    @Bind(R.id.et_per)
    EditText etPer;
    @Bind(R.id.textView1)
    TextView textView1;
    @Bind(R.id.et_registerTime)
    EditText etRegisterTime;
    @Bind(R.id.content)
    LinearLayout content;
    @Bind(R.id.btn_register)
    Button btnRegister;
    @Bind(R.id.btn_cancel)
    Button btnCancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law_caseregister);
        initToolBar(toolbar,toolbarTitle,true,"案件登记材料填报");
    }

    @OnClick({R.id.btn_register, R.id.btn_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                break;
            case R.id.btn_cancel:
                break;
        }
    }
}
