package com.joy.ep.myokhttptext.base;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Created by Administrator on 2015/10/15.
 */
public class BaseActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void closeProDialog()
    {
        if(progressDialog != null)
        {
            progressDialog.dismiss();
        }
    }

    public void showProDialog(String str)
    {
        if(progressDialog != null)
        {
            progressDialog.setMessage(str);
            progressDialog.show();
        }
    }

    public void showProDialog()
    {
        if(progressDialog != null)
        {
            progressDialog.show();
        }
    }
}
