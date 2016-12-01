package com.dhcc.datacage.activity.law;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dhcc.datacage.R;
import com.dhcc.datacage.base.BaseActivity;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by pengbangqin on 16-10-27.
 * 结案归档提交审批
 */
public class CaseFiling_Activity extends BaseActivity {
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_caseInfo)
    EditText etCaseInfo;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.et_caseDesc)
    EditText etCaseDesc;
    @Bind(R.id.et_time)
    EditText etTime;
    @Bind(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law_casefiling);
        initToolBar(toolbar,toolbarTitle,true,"结案归档提交审批");
    }

    @OnClick(R.id.btn_submit)
    public void onClick() {
    }
}
