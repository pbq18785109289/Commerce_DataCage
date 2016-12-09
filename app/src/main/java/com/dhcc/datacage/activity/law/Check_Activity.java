package com.dhcc.datacage.activity.law;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dhcc.datacage.R;
import com.dhcc.datacage.base.BaseActivity;
import com.dhcc.datacage.view.ClearEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;


/**
 * Created by pengbangqin on 2016/12/2.
 */

public class Check_Activity extends BaseActivity {
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_company)
    ClearEditText etCompany;
    @Bind(R.id.et_address)
    ClearEditText etAddress;
    @Bind(R.id.et_person)
    ClearEditText etPerson;
    @Bind(R.id.et_areas)
    ClearEditText etAreas;
    @Bind(R.id.et_result)
    ClearEditText etResult;
    @Bind(R.id.rb1)
    RadioButton rb1;
    @Bind(R.id.rb2)
    RadioButton rb2;
    @Bind(R.id.rg_ctype)
    RadioGroup rgCtype;
    @Bind(R.id.btn_save)
    Button btnSave;
    @Bind(R.id.btn_cancel)
    Button btnCancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law_check);
        ButterKnife.bind(this);
        initToolBar(toolbar,toolbarTitle,true,"检查情况记录");
        rgCtype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

            }
        });
    }
    @OnCheckedChanged({R.id.rb1,R.id.rb2})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
        switch (buttonView.getId()){
            case R.id.rb1:
                showToast("rb1"+isChecked);
                break;
            case R.id.rb2:
                showToast("rb2"+isChecked);
                break;
        }
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
