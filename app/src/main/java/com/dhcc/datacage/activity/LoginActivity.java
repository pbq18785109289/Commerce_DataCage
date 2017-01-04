package com.dhcc.datacage.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dhcc.datacage.R;
import com.dhcc.datacage.activity.law.Check_Activity;
import com.dhcc.datacage.base.ActivityCollector;
import com.dhcc.datacage.base.BaseActivity;
import com.dhcc.datacage.callback.DialogCallback;
import com.dhcc.datacage.callback.JsonCallback;
import com.dhcc.datacage.callback.StringDialogCallback;
import com.dhcc.datacage.model.DhcResponse;
import com.dhcc.datacage.model.SimpleResponse;
import com.dhcc.datacage.utils.MyApp;
import com.dhcc.datacage.utils.UrlUtils;
import com.dhcc.datacage.view.ClearEditText;
import com.dhcc.datacage.view.PasswordEditText;
import com.lzy.okgo.OkGo;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;


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
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        //申请运行时的权限
        AndPermission.with(this)
                .requestCode(100)
                .permission(Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .send();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // 只需要调用这一句，剩下的AndPermission自动完成。
        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
    // 成功回调的方法，用注解即可，里面的数字是请求时的requestCode。
    @PermissionYes(100)
    private void getAllYes() {
        init();
        // 申请权限成功，可以去做点什么了。
        Toast.makeText(this, "获取权限成功", Toast.LENGTH_SHORT).show();
    }

    // 失败回调的方法，用注解即可，里面的数字是请求时的requestCode。
    @PermissionNo(100)
    private void getAllNo() {
        // 申请权限失败，可以提醒一下用户。
        Toast.makeText(this, "获取权限失败", Toast.LENGTH_SHORT).show();
        //退出应用
        ActivityCollector.finishAll();
    }
    /**
     * 初始化操作
     */
    private void init(){
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
        OkGo.get("http://192.168.0.109/hello/get_data.json")
                .tag(this)
//                .params("cmd","0")
//                .params("name",username)
//                .params("password", password)
                .execute(new StringDialogCallback(this) {
                    //请求成功
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        JSONObject jsonObject= null;
                        try {
                            jsonObject = new JSONObject(s);
                            String code=jsonObject.getString("code");
                            if(code.equals("0")){
                                showToast("登录成功");
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                finish();
                                startActivity(intent);
                                //登录成功和记住密码框为选中状态才保存用户信息
                                if(cbPwd.isChecked()){
                                    //记住用户名和密码
                                    SharedPreferences.Editor editor=sp.edit();
                                    editor.putString("name", username);
                                    editor.putString("pwd", password);
                                    editor.commit();
                                }
                            }else{
                                showToast("用户名或密码错误");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
