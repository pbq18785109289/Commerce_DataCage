package com.dhcc.datacage.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dhcc.datacage.R;
import com.dhcc.datacage.base.BaseRecyclerAdapter;
import com.dhcc.datacage.model.TeamWork;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by pengbangqin on 16-10-31.
 * 协同模块中的团队互助Activity的适配器
 */
public class TeamWorkAdapter extends BaseRecyclerAdapter<TeamWork, RecyclerView.ViewHolder> implements View.OnClickListener {

    String[] img={"http://www.jcrb.com/xztpd/2012zt/201212/jqfzjs/fxplfz/201212/W020121225310117091832.jpg",
            "http://www.jcrb.com/xztpd/2012zt/201212/jqfzjs/fxplfz/201212/W020121225310117091832.jpg",
            "http://www.jcrb.com/xztpd/2012zt/201212/jqfzjs/fxplfz/201212/W020121225310117091832.jpg"};
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.nineGrid)
    NineGridView nineGrid;
    @Bind(R.id.tv_Time)
    TextView tvTime;
    @Bind(R.id.more)
    ImageView more;

    public TeamWorkAdapter(Context context, List<TeamWork> datas) {
        super(context, datas);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE) {
            view = inflater.inflate(R.layout.item_empty, parent, false);
            return new MyEmptyHolder(view);
        }
        view = inflater.inflate(R.layout.teamwork_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).setOnItemClickListener(mOnItemClickListener);
            ((MyViewHolder) holder).tvName.setText(mDatas.get(position).getTitle());
            ((MyViewHolder) holder).tvContent.setText(mDatas.get(position).getContent());
            ((MyViewHolder) holder).tvTime.setText(mDatas.get(position).getTime());

            ((MyViewHolder) holder).more.setOnClickListener(this);

            ArrayList<ImageInfo> imageInfo = new ArrayList<>();
            for (int i = 0; i < img.length; i++) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(img[i]);
                info.setBigImageUrl(img[i]);
                imageInfo.add(info);
            }
            ((MyViewHolder) holder).nineGrid.setImageLoader(new GlideImageLoader());
            ((MyViewHolder) holder).nineGrid.setAdapter(new NineGridViewClickAdapter(mContext, imageInfo));
        }
    }
    public void onClick(View v) {
        View popupView = inflater.inflate(R.layout.popup_reply, null);
        final PopupWindow window = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupView.findViewById(R.id.favour).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "赞+1", Toast.LENGTH_SHORT).show();
                if (window != null) window.dismiss();
            }
        });
        popupView.findViewById(R.id.comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "评论", Toast.LENGTH_SHORT).show();
                if (window != null) window.dismiss();
            }
        });
        window.setOutsideTouchable(true);
        window.setFocusable(true);
        window.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        popupView.measure(0, 0);
        int xoff = -popupView.getMeasuredWidth();
        int yoff = -(popupView.getMeasuredHeight() + v.getHeight()) / 2;
        window.showAsDropDown(v, xoff, yoff);
    }

    static class MyViewHolder extends MyVH {
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.nineGrid)
        NineGridView nineGrid;
        @Bind(R.id.tv_Time)
        TextView tvTime;
        @Bind(R.id.more)
        ImageView more;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * Glide 加载
     */
    private class GlideImageLoader implements NineGridView.ImageLoader {

        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            Glide.with(context).load(url)//
                    .placeholder(R.mipmap.default_image)//
                    .error(R.mipmap.default_image)//
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//
                    .into(imageView);
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }
}
