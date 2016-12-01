package com.dhcc.datacage.activity.setting;

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
 */
public class UpdatePwd_Activity extends BaseActivity {
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_old)
    EditText etOld;
    @Bind(R.id.et_new)
    EditText etNew;
    @Bind(R.id.tv_confirm)
    TextView tvConfirm;
    @Bind(R.id.et_confirm)
    EditText etConfirm;
    @Bind(R.id.btn_confirm)
    Button btnConfirm;
    @Bind(R.id.btn_cancel)
    Button btnCancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_updatepwd);
        initToolBar(toolbar,toolbarTitle,true,"修改密码");
    }

    @OnClick({R.id.btn_confirm, R.id.btn_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                break;
            case R.id.btn_cancel:
                break;
        }
    }
}
