package com.yyydjk.sliderlayoutdemo;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.yyydjk.library.BannerLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private File file1 ;
    private List<String> urls ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        file1=new File("/sdcard"); //这是自己定义的目录 供adb push 到此目录例如 adb push /Users/tanlin/Desktop/picture/one.jpg /sdcard/one.jpg
        urls = new ArrayList<>();

        BannerLayout bannerLayout = (BannerLayout) findViewById(R.id.banner);
        initData();
        bannerLayout.setImageLoader(new GlideImagFileLoader());
        bannerLayout.setViewFileUrls(urls);
        bannerLayout.setWHAT_AUTO_PLAY(3000);  //需要设置时间，不然不会动

        //添加监听事件
        bannerLayout.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void initData() {
        urls.clear();
        urls.add("one.jpg");
        urls.add("two.jpg");
        urls.add("three.jpg");
        urls.add("four.jpg");
    }
    public class GlideImagFileLoader implements BannerLayout.ImageLoader {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            Glide.with(context).load(path).centerCrop().into(imageView);
        }
        @Override
        public void displayFileImage(final Context context, final String name, final ImageView imageView) {
            XXPermissions.with(MainActivity.this)
                    //.constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                    //.permission(Permission.SYSTEM_ALERT_WINDOW, Permission.REQUEST_INSTALL_PACKAGES) //支持请求6.0悬浮窗权限8.0请求安装权限
                    .permission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .request(new OnPermission() {
                        @Override
                        public void hasPermission(List<String> granted, boolean isAll) {
                            File file = new File(file1, name);
                            Glide.with(MainActivity.this).load(file).into(imageView);
                        }

                        @Override
                        public void noPermission(List<String> denied, boolean quick) {
                        }
                    });
        }
    }
}
