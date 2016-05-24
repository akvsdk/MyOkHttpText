package com.joy.ep.myokhttptext.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.joy.ep.myokhttptext.R;
import com.joy.ep.myokhttptext.util.ShareElement;

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
    boolean isToolBarHiding = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizhi);
        initToolBar();
        initView();
    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.meizhi_img);

        mImageView.setImageDrawable(ShareElement.shareDrawable);
        ViewCompat.setTransitionName(mImageView, "share");

        attacher = new PhotoViewAttacher(mImageView);

        attacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                hideOrShowToolBar();
            }

            @Override
            public void onOutsidePhotoTap() {
                hideOrShowToolBar();
            }
        });


        Glide.with(this)
                .load(getIntent().getStringExtra("url"))
                .asBitmap()
                .animate(R.anim.image_load)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        mImageView.setImageBitmap(resource);
                        attacher.update();
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        mImageView.setImageDrawable(errorDrawable);
                    }
                });
    }

    private void initToolBar() {

        mbar = (AppBarLayout) findViewById(R.id.app_bar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("MeiZhi");
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void hideOrShowToolBar() {
        mbar.animate()
                .translationY(isToolBarHiding ? 0 : -mbar.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        isToolBarHiding = !isToolBarHiding;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareElement.shareDrawable = null;
    }
}
