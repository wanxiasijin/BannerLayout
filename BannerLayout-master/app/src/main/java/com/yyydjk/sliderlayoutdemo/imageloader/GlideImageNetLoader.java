package com.yyydjk.sliderlayoutdemo.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yyydjk.library.BannerLayout;

/**
 * Created by Administrator on 2016/12/21 0021.
 */

public class GlideImageNetLoader implements BannerLayout.ImageLoader {
    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context).load(path).centerCrop().into(imageView); //加载网络图片
    }
    @Override
    public void displayFileImage(Context context, String name, ImageView imageView) {

    }

}
