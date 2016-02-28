package com.joy.ep.myokhttptext.common;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.joy.ep.myokhttptext.R;

/**
 * Created by Administrator on 2015/10/15.
 */
public class BaseActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        initToolbar();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }
    public void setContentView(int layoutId) {
        setContentView(View.inflate(this, layoutId, null));
    }

    public void setTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
