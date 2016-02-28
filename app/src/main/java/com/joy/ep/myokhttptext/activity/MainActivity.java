package com.joy.ep.myokhttptext.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjj.http.Http;
import com.cjj.listener.CallbackListener;
import com.joy.ep.myokhttptext.R;
import com.joy.ep.myokhttptext.common.BaseActivity;
import com.joy.ep.myokhttptext.enity.NewBean;
import com.joy.ep.myokhttptext.http.AppDao;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private TextView tv;
    private Handler handler = new Handler();
    private Runnable myRunnable = new Runnable() {
        public void run() {
            handler.postDelayed(this, 1000);
        }
    };
    private ImageView mImageView;
    private List<String> fuli = new ArrayList<>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        mImageView = (ImageView) findViewById(R.id.img);
    }

    public void showimg(View v) {
        if (mImageView.getVisibility() == View.GONE) {
            mImageView.setVisibility(View.VISIBLE);
            String url = "http://img3.imgtn.bdimg.com/it/u=3685376609,1792172700&fm=15&gp=0.jpg";
            Http.showimg(this, mImageView, url);
        }

    }

    public void postHttp(View v) {
        handler.post(myRunnable);
        AppDao.getInstance().getNewsList(new CallbackListener<String>() {
            @Override
            public void onError(Exception e) {
                tv.setText(e.toString());
            }

            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onStringResult(final String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject s = jsonObject.optJSONObject("record");
                    String sb = s.optString("newsList");
                    //List<NewBean.NewsListEntity> bList= new NewBean.NewsListEntity().fromArray(sb);
                    NewBean.NewsListEntity b = new NewBean.NewsListEntity().from(result);
                    tv.setText(b.getUserName());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    public void postBean(View v) {
        /*AppDao.getInstance().gankIo(new CallbackListener<List<GanHuo>>() {
            @Override
            public void onSuccess(List<GanHuo> result) {
                tv.setText(result.get(0).getWho());
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getType().equals("福利")) {
                        fuli.add(result.get(i).getUrl());
                    }
                }

                Http.showimg(MainActivity.this, mImageView, fuli.get(0));

            }
        });*/


    }

}
