package com.dhcc.datacage.activity.workbench;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.dhcc.datacage.R;
import com.dhcc.datacage.base.BaseActivity;
import com.dhcc.datacage.view.ClearEditText;
import com.lzy.widget.ExpandGridView;
import com.pbq.imagepicker.VideoPicker;
import com.pbq.imagepicker.bean.VideoItem;
import com.pbq.imagepicker.ui.video.VideoGridActivity;
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
        if (resultCode == VideoPicker.RESULT_VIDEO_ITEMS) {
            if (data != null && requestCode == 300) {
                ArrayList<VideoItem> videos = (ArrayList<VideoItem>) data.getSerializableExtra(VideoPicker.EXTRA_RESULT_VIDEO_ITEMS);
                MyVideoAdapter adapter = new MyVideoAdapter(videos);
                egv.setAdapter(adapter);
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @OnClick({R.id.btn_movie, R.id.btn_upload, R.id.btn_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_movie://添加视频
                VideoPicker videoPicker = VideoPicker.getInstance();
                videoPicker.setMultiMode(true);
                Intent i1 = new Intent(this, VideoGridActivity.class);
                startActivityForResult(i1, 300);
                break;
            case R.id.btn_upload://上传
                break;
            case R.id.btn_cancel://取消
                break;
        }
    }

    private class MyVideoAdapter extends BaseAdapter {

        private List<VideoItem> items;

        public MyVideoAdapter(List<VideoItem> items) {
            this.items = items;
        }

        public void setData(List<VideoItem> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public VideoItem getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            int size = egv.getWidth() / 3;
            FrameLayout layout= new FrameLayout(VedioConference_Activity.this);//定义框架布局器

            ImageView imageView = new ImageView(VedioConference_Activity.this);
            FrameLayout.LayoutParams abParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, size);
            imageView.setLayoutParams(abParams);
            imageView.setBackgroundColor(Color.parseColor("#88888888"));

            ImageButton btnPlay=new ImageButton(VedioConference_Activity.this);
            btnPlay.setBackgroundResource(R.mipmap.play);
            FrameLayout.LayoutParams bparams=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);//定义显示组件参数
            //此处相当于布局文件中的Android:layout_gravity属性
            bparams.gravity = Gravity.CENTER;
            btnPlay.setLayoutParams(bparams);

            layout.addView(imageView, abParams);//添加组件
            layout.addView(btnPlay, bparams);
//            videoPicker.getImageLoader().displayImage(ImagePickerActivity.this, getItem(position).path, imageView, size, size);
            Glide.with(VedioConference_Activity.this)
                    .load(getItem(position).path)
                    .placeholder(R.mipmap.default_image)
                    .into(imageView);
            /**
             * 点击播放播放视频
             */
            btnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse(getItem(position).path);
                    //调用系统自带的播放器
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(uri, "video/mp4");
                    startActivity(intent);
                }
            });
            return layout;
        }
    }
}
