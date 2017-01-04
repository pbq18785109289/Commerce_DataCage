package com.dhcc.datacage.activity.law;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.dhcc.datacage.R;
import com.dhcc.datacage.base.BaseActivity;
import com.dhcc.datacage.listener.GlideImageLoader;
import com.dhcc.datacage.view.ClearEditText;
import com.lzy.widget.ExpandGridView;
import com.pbq.imagepicker.ImagePicker;
import com.pbq.imagepicker.bean.ImageItem;
import com.pbq.imagepicker.ui.image.ImageGridActivity;
import com.pbq.imagepicker.ui.image.ImagePreviewDelActivity;

import java.util.ArrayList;
import java.util.List;

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
    @Bind(R.id.egv)
    ExpandGridView egv;
    @Bind(R.id.btn_rpic)
    Button btnRpic;
    private ImagePicker imagePicker;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law_check);
        ButterKnife.bind(this);
        String title = getIntent().getStringExtra("title");
        initToolBar(toolbar, toolbarTitle, true, title + "检查情况记录");
        rgCtype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

            }
        });
    }

    @OnCheckedChanged({R.id.rb1, R.id.rb2})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.rb1:
                showToast("rb1" + isChecked);
                break;
            case R.id.rb2:
                showToast("rb2" + isChecked);
                break;
        }
    }

    @OnClick({R.id.btn_save, R.id.btn_cancel,R.id.btn_rpic})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                Intent intent = new Intent(Check_Activity.this, OrderCorrect_Activity.class);
                finish();
                startActivity(intent);
                break;
            case R.id.btn_cancel:
                break;
            case R.id.btn_rpic:
                //对ImagePicker的初始化 可以放到全局Application中
                imagePicker = ImagePicker.getInstance();
                //设置图片加载的方式
                imagePicker.setImageLoader(new GlideImageLoader());
                //设置是否显示相机
                imagePicker.setShowCamera(true);
                //设置选择图片的最大数量
                imagePicker.setSelectLimit(9);
                //设置不允许使用裁剪
                imagePicker.setCrop(false);
                //启动选择图片意图
                Intent picIntent = new Intent(Check_Activity.this, ImageGridActivity.class);
                startActivityForResult(picIntent, 100);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_IMAGE_ITEMS) {
            if (data != null && requestCode == 100) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                MyAdapter adapter = new MyAdapter(images);
                egv.setAdapter(adapter);
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class MyAdapter extends BaseAdapter {

        private List<ImageItem> items;

        public MyAdapter(List<ImageItem> items) {
            this.items = items;
        }

        public void setData(List<ImageItem> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public ImageItem getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            int size = egv.getWidth() / 3;
            if (convertView == null) {
                imageView = new ImageView(Check_Activity.this);
                AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, size);
                imageView.setLayoutParams(params);
                imageView.setBackgroundColor(Color.parseColor("#88888888"));
            } else {
                imageView = (ImageView) convertView;
            }
            imagePicker.getImageLoader().displayImage(Check_Activity.this, getItem(position).path, imageView, size, size);
            return imageView;
        }
    }
}
