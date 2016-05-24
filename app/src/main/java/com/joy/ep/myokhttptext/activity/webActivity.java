package com.joy.ep.myokhttptext.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.joy.ep.myokhttptext.R;
import com.joy.ep.myokhttptext.enity.TnGou;
import com.joy.ep.myokhttptext.util.GlideProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * author   Joy
 * Date:  2016/5/12.
 * version:  V1.0
 * Description:
 */
public class WebActivity extends AppCompatActivity {
    private WebView webView;
    private SmoothProgressBar progressBar;
    private static final String baseUrl = "http://tnfs.tngou.net/image";
    private static final String SIZE = "_350x500";
    private String imgUrl = null;
    private ImageView mImage;
    private List<TnGou> bean = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            switch (getIntent().getFlags()) {
                case 1:
                    getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
                    getWindow().setEnterTransition(new Fade());

                    break;
                case 2:
                    getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
                    getWindow().setEnterTransition(new Slide(Gravity.BOTTOM));
                    getWindow().setReenterTransition(new Slide(Gravity.START));
                    break;
            }
        }

        setContentView(R.layout.web_activity);

        initView();
        getImg();
    }

    private void getImg() {
        Random ra = new Random();
        int i = ra.nextInt(50);
        bean = getIntent().getParcelableArrayListExtra("bean");
        imgUrl = bean.get(i).getImg();
        GlideProxy.getInstance().loadImage(WebActivity.this, baseUrl + imgUrl, mImage);

    }


    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mImage = (ImageView) findViewById(R.id.img);
        //  GlideProxy.getInstance().loadImage(WebActivity.this, "http://ww4.sinaimg.cn/large/610dc034jw1f3litmfts1j20qo0hsac7.jpg", mImage);
        collapsingToolbar.setTitle(getIntent().getStringExtra("title"));
        //collapsingToolbar.setExpandedTitleColor(getColor(R.color.material_white70));
        webView = (WebView) findViewById(R.id.webView);
        progressBar = (SmoothProgressBar) findViewById(R.id.progressbar);
        webView.loadUrl(getIntent().getStringExtra("url"));
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.setWebViewClient(new MyWebClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);//关键点

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setLoadWithOverviewMode(true);

    }

    class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.VISIBLE);
            }
        }
    }

    private class MyWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            webView.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }
        webView.setVisibility(View.GONE);
        super.onBackPressed();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
