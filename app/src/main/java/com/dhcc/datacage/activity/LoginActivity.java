package com.dhcc.datacage.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.dhcc.datacage.R;
import com.dhcc.datacage.base.BaseActivity;
import com.dhcc.datacage.view.ClearEditText;
import com.dhcc.datacage.view.PasswordEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;
import static com.dhcc.datacage.R.drawable.cb;
import static java.lang.Boolean.FALSE;

/**
 * Created by pengbangqin on 16-10-27.
 * 登录界面
 */
public class LoginActivity extends BaseActivity {
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.cb_pwd)
    CheckBox cbPwd;
    @Bind(R.id.cb_auto)
    CheckBox cbAuto;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.et_username)
    ClearEditText etUsername;
    @Bind(R.id.et_pwd)
    PasswordEditText etPwd;
    private SharedPreferences sp;
    //用户名
    String username;
    //密码
    String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        sp=getSharedPreferences("user",MODE_PRIVATE);
        initToolBar(toolbar, toolbarTitle, false, "登录");
        //判断记住密码多选框的状态
        if(sp.getBoolean("ISCHECK",false)){
            //设置默认记住密码
            cbPwd.setChecked(true);
            etUsername.setText(sp.getString("name",""));
            etPwd.setText(sp.getString("pwd",""));
        }
        //判断自动登陆多选框状态
        if(sp.getBoolean("AUTO_ISCHECK",false)){
            //设置默认是自动登录状态
            cbAuto.setChecked(false);
            //跳转界面
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }


    @OnCheckedChanged({R.id.cb_pwd, R.id.cb_auto})
    public void OnCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_pwd:
                if(cbPwd.isChecked()){
                    //设置选中的记住密码为true
                    sp.edit().putBoolean("ISCHECK",true).commit();
                }else{
                    //设置选中的记住密码为false
                    sp.edit().putBoolean("ISCHECK",false).commit();
                }
                break;
            case R.id.cb_auto:
                if(cbAuto.isChecked()){
                    //设置选中的自动登录为true
                    sp.edit().putBoolean("AUTO_ISCHECK",true).commit();
                }else{
                    //设置选中的自动登录为false
                    sp.edit().putBoolean("AUTO_ISCHECK",false).commit();
                }
                break;
        }

    }

    @OnClick(R.id.btn_login)
    public void onClick(View view) {
        //获取用户名和密码
        username=etUsername.getText().toString().trim();
        password=etPwd.getText().toString().trim();
        login(username,password);
    }

    /**
     * 登录逻辑
     * @param username
     * @param password
     */
    private void login(String username, String password) {
        if(username.equals("admin") && password.equals("admin")){
            //登录成功和记住密码框为选中状态才保存用户信息
            if(cbPwd.isChecked()){
                //记住用户名和密码
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("name",username);
                editor.putString("pwd",password);
                editor.commit();
            }
            Intent intent = new Intent(this, MainActivity.class);
            finish();
            showToast("登录成功");
            startActivity(intent);
        }else{
            showToast("用户名或密码错误");
        }
    }
}
