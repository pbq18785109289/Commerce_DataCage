package com.dhcc.datacage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import com.dhcc.datacage.R;
import com.dhcc.datacage.base.BaseActivity;
import butterknife.Bind;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by pengbangqin on 16-10-27.
 * 登录界面
 */
public class LoginActivity extends BaseActivity{
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_username)
    EditText etUsername;
    @Bind(R.id.et_pwd)
    EditText etPwd;
    @Bind(R.id.cb_pwd)
    CheckBox cbPwd;
    @Bind(R.id.cb_auto)
    CheckBox cbAuto;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initToolBar(toolbar,toolbarTitle, false, "登录");
    }

    @OnCheckedChanged({R.id.cb_pwd,R.id.cb_auto})
    public void OnCheckedChanged(CompoundButton buttonView, boolean isChecked){
        switch (buttonView.getId()){
            case R.id.cb_pwd:
                showToast("记住密码"+isChecked);
                break;
            case R.id.cb_auto:
                showToast("自动登录"+isChecked);
                break;
        }

    }

    @OnClick(R.id.btn_login)
    public void onClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        showToast("登录成功");
        startActivity(intent);
    }
}
