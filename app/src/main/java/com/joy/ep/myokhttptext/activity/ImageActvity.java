package com.joy.ep.myokhttptext.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.joy.ep.myokhttptext.R;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * author   Joy
 * Date:  2016/4/22.
 * version:  V1.0
 * Description:
 */
public class ImageActvity extends AppCompatActivity {
    private Toolbar toolbar;
    private AppBarLayout mbar;
    private ImageView mImageView;
    PhotoViewAttacher attacher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizhi);
        initToolBar();
        initView();
    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.meizhi_img);
        attacher = new PhotoViewAttacher(mImageView);
        Glide.with(this).load(getIntent().getStringExtra("url")).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                mImageView.setImageBitmap(resource);
                attacher.update();
//                girl = resource;
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                mImageView.setImageDrawable(errorDrawable);
            }
        });
    }

    private void initToolBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        mbar = (AppBarLayout) findViewById(R.id.app_bar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("MeiZhi");
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

    }
}
