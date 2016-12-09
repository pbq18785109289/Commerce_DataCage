package com.dhcc.datacage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.dhcc.datacage.R;
import com.dhcc.datacage.base.BaseActivity;
import com.dhcc.datacage.fragments.Fragment_Setting;
import com.dhcc.datacage.fragments.Fragment_Synerg;
import com.dhcc.datacage.fragments.Fragment_Workbench;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import cn.jpush.android.api.JPushInterface;


/**
 * 主界面
 *
 * @author pengbangqin
 */
public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fl_content)
    FrameLayout flContent;
    @Bind(R.id.rb_workbench)
    RadioButton rbWorkbench;
    @Bind(R.id.rb_synergy)
    RadioButton rbSynergy;
    @Bind(R.id.rb_setting)
    RadioButton rbSetting;
    @Bind(R.id.rg)
    RadioGroup rg;
    private List<Fragment> list;
    /**
     * 选中的Fragment的对应的位置
     */
    private int position;
    /**
     * 记录上次切换的Fragment
     */
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBar(toolbar,toolbarTitle,false,"数据铁笼");
        //初始化Fragment
        initFragment();
        //注册监听
        rg.setOnCheckedChangeListener(this);
        //设置默认选中第一个
        rg.check(R.id.rb_workbench);
    }

    private void initFragment() {
        list = new ArrayList<Fragment>();
        list.add(new Fragment_Workbench());
        list.add(new Fragment_Synerg());
        list.add(new Fragment_Setting());
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //切换Fragment
        changeFragment(checkedId);
    }

    public void changeFragment(int checkedId) {
        switch (checkedId) {
            case R.id.rb_workbench:
                position = 0;
                break;
            case R.id.rb_synergy:
                position = 1;
                break;
            case R.id.rb_setting:
                position = 2;
                break;
        }
        //根据位置得到对应的Fragment
        Fragment to = list.get(position);
        switchFragment(mFragment, to);
    }

    /**
     * @param from 刚显示的Fragment,马上就要被隐藏了
     * @param to   马上要切换到的Fragment，一会要显示
     */
    private void switchFragment(Fragment from, Fragment to) {
        if (from != to) {
            mFragment = to;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //才切换 ,判断有没有被添加
            if (!to.isAdded()) {
                //to没有被添加,from隐藏
                if (from != null) ft.hide(from);
                //添加to
                if (to != null) ft.add(R.id.fl_content, to).commit();
            } else {
                //to已经被添加,from隐藏
                if (from != null) ft.hide(from);
                //显示to
                if (to != null) ft.show(to).commit();
            }
        }
    }
    //两次点击退出
    private long mExitTime;//点击时间
    @Override
    public void onBackPressed() {

        if ((System.currentTimeMillis() - mExitTime) > 2000) {//两次点击的时间间隔
            Toast.makeText(this, "再按一次退出",Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();//结束当前Activity
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            System.exit(0);// 退出程序
        }
    }
}
