package com.dhcc.datacage.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import com.dhcc.datacage.R;
import com.dhcc.datacage.base.BaseActivity;
import com.dhcc.datacage.callback.DialogCallback;
import com.dhcc.datacage.model.SimpleResponse;
import com.dhcc.datacage.utils.MyApp;
import com.dhcc.datacage.utils.UrlUtils;
import com.dhcc.datacage.view.ClearEditText;
import com.dhcc.datacage.view.PasswordEditText;
import com.lzy.okgo.OkGo;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static android.R.attr.name;

/**
 * Created by pengbangqin on 16-10-27.
 * 登录界面
 */
public class LoginActivity extends BaseActivity {
    @Bind(R.id.cb_pwd)
    CheckBox cbPwd;
    @Bind(R.id.cb_auto)
    CheckBox cbAuto;
    @Bind(R.id.btn_login)
    AppCompatButton btnLogin;
    @Bind(R.id.et_username)
    EditText etUsername;
    @Bind(R.id.et_pwd)
    EditText etPwd;
    private SharedPreferences sp;
    //用户名
    String username;
    //密码
    String password;
    //读取SharedPreferences中的自动登录
    private boolean isAtuoLogin;
    //读取SharedPreferences中的记住密码
    private boolean isRemeberPwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        //如果getIntent不为空,说明是从设置中跳转过来的  将isExitLogin传来为true  否则就为false
        if(getIntent()!=null){
            MyApp.isExitLogin=getIntent().getBooleanExtra("isExitLogin",false);
        }else{
            MyApp.isExitLogin=false;
        }
        sp=getSharedPreferences("user",MODE_PRIVATE);
        isAtuoLogin=sp.getBoolean("AUTO_ISCHECK",false);
        isRemeberPwd=sp.getBoolean("ISCHECK",false);
        //判断记住密码多选框的状态
        if(isRemeberPwd){
            //设置默认记住密码
            cbPwd.setChecked(true);
            etUsername.setText(sp.getString("name",""));
            etPwd.setText(sp.getString("pwd",""));
        }
        //设置默认是自动登录状态
        cbAuto.setChecked(isAtuoLogin);
        /**
         * isExitLogin为true 说明是从设置中跳转过来的
         * 否则就不是 直接跳转自动登录
         */
        if(!MyApp.isExitLogin){
            //判断自动登陆多选框状态
            if(isAtuoLogin){
                //执行登录逻辑
                login(sp.getString("name",""),sp.getString("pwd",""));
            }
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
        if(validate()) {
            login(username, password);
        }
    }

    /**
     * 登录逻辑
     * @param username
     * @param password
     */
    private void login(final String username, final String password) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
        OkGo.post(UrlUtils.URL_LOGIN)
                .tag(this)
                .params("cmd","0")
                .params("name",username)
                .params("password", password)
                .execute(new DialogCallback<SimpleResponse>(this) {
                    //请求成功
                    @Override
                    public void onSuccess(SimpleResponse simpleResponse, Call call, Response response) {
                        showToast("登录成功"+simpleResponse.uid);
                        //登录成功和记住密码框为选中状态才保存用户信息
                        if(cbPwd.isChecked()){
                            //记住用户名和密码
                            SharedPreferences.Editor editor=sp.edit();
                            editor.putString("name", username);
                            editor.putString("pwd", password);
                            editor.commit();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        showToast("登录失败");
                    }
                });
    }

    /**
     * 验证字段
     * @return
     */
    public boolean validate() {
        boolean valid = true;
        if (username.isEmpty()) {
            etUsername.setError("用户名不能为空");
            valid = false;
        } else {
            etUsername.setError(null);
        }
        if (password.isEmpty()) {
            etPwd.setError("密码不能为空");
            valid = false;
        } else {
            etPwd.setError(null);
        }

        return valid;
    }

    /**
     * 取消网络请求
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
