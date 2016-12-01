package com.dhcc.datacage.activity.law;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dhcc.datacage.R;
import com.dhcc.datacage.base.BaseActivity;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * 调查材料取证Activity
 *
 * @author pengbangqin
 */
public class Investigation_Activity extends BaseActivity {
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_time)
    EditText etTime;
    @Bind(R.id.et_place)
    EditText etPlace;
    @Bind(R.id.et_method)
    EditText etMethod;
    @Bind(R.id.et_process)
    EditText etProcess;
    @Bind(R.id.et_result)
    EditText etResult;
    @Bind(R.id.et_desc)
    EditText etDesc;
    @Bind(R.id.et_idea)
    EditText etIdea;
    @Bind(R.id.et_plot)
    EditText etPlot;
    @Bind(R.id.content)
    LinearLayout content;
    @Bind(R.id.vedio)
    RadioButton vedio;
    @Bind(R.id.photo)
    RadioButton photo;
    @Bind(R.id.movie)
    RadioButton movie;
    @Bind(R.id.rg)
    RadioGroup rg;
    @Bind(R.id.btn_upload)
    Button btnUpload;
    @Bind(R.id.btn_cancel)
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law_investigation);
        initToolBar(toolbar,toolbarTitle,true,"调查取证材料填报");

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                switch (arg1) {
                    case R.id.vedio:
                        Toast.makeText(getApplicationContext(), "点击了音频", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.photo:
                        Toast.makeText(getApplicationContext(), "点击了图片", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.movie:
                        Toast.makeText(getApplicationContext(), "点击了视频", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
    @OnClick({R.id.btn_upload, R.id.btn_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_upload:
                break;
            case R.id.btn_cancel:
                break;
        }
    }
}

