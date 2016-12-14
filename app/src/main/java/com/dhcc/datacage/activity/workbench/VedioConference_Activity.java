package com.dhcc.datacage.activity.workbench;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.dhcc.datacage.R;
import com.dhcc.datacage.base.BaseActivity;
import com.dhcc.datacage.view.ClearEditText;
import com.dhcc.datacage.view.ExpandGridView;
import com.pbq.pickerlib.activity.PhotoMediaActivity;
import com.pbq.pickerlib.entity.PhotoVideoDir;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pengbangqin on 16-10-27.
 * 音视频会议Activity
 */
public class VedioConference_Activity extends BaseActivity {
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_person)
    ClearEditText etPerson;
    @Bind(R.id.et_locate)
    ClearEditText etLocate;
    @Bind(R.id.et_content)
    ClearEditText etContent;
    @Bind(R.id.et_time)
    ClearEditText etTime;
    @Bind(R.id.btn_movie)
    Button btnMovie;
    @Bind(R.id.egv)
    ExpandGridView egv;
    @Bind(R.id.btn_upload)
    Button btnUpload;
    @Bind(R.id.btn_cancel)
    Button btnCancel;
    /**
     * 上传的文件集合
     */
    private ArrayList<File> files = new ArrayList<>();
    /**
     * 存储视频路径的集合
     */
    private ArrayList<String> selectedVedioPaths = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workbench_conference);
        ButterKnife.bind(this);
        initToolBar(toolbar,toolbarTitle,true,"音视频会议");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            selectedVedioPaths = data.getStringArrayListExtra("pickerPaths");
            MyAdapter adapter = new MyAdapter(selectedVedioPaths);
            egv.setAdapter(adapter);
            //将选择的视频路径放入文件中
            //清空视频文件
            files.clear();
            for (int i = 0; i < selectedVedioPaths.size(); i++) {
                File fileVedio = new File(selectedVedioPaths.get(i));
                files.add(fileVedio);
                Log.i("TGA", selectedVedioPaths.get(i));
                Log.i("TGA", fileVedio + "");
            }
            //上传
            showToast(selectedVedioPaths + "");
        }

    }

    @OnClick({R.id.btn_movie, R.id.btn_upload, R.id.btn_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_movie://添加视频
                Intent i = new Intent(this, PhotoMediaActivity.class);
                i.putExtra("loadType", PhotoVideoDir.Type.VEDIO.toString());
                startActivityForResult(i, 1);
                break;
            case R.id.btn_upload://上传
                break;
            case R.id.btn_cancel://取消
                break;
        }
    }

    private class MyAdapter extends BaseAdapter {

        private List<String> items;

        public MyAdapter(List<String> items) {
            this.items = items;
        }

        public void setData(List<String> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public String getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int size = egv.getWidth() / 3;
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(VedioConference_Activity.this, R.layout.item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //把选中图片的资源放在imageView中
            Glide.with(VedioConference_Activity.this).load(getItem(position)).placeholder(R.mipmap.default_image).into(holder.imageView);
            return convertView;
        }
    }

    private class ViewHolder {

        private ImageView imageView;

        public ViewHolder(View convertView) {
            imageView = (ImageView) convertView.findViewById(R.id.imageView);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, egv.getWidth() / 3);
            imageView.setLayoutParams(params);
        }
    }

}
