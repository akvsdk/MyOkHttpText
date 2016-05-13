package com.joy.ep.myokhttptext.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.joy.ep.myokhttptext.BuildConfig;
import com.joy.ep.myokhttptext.R;
import com.joy.ep.myokhttptext.util.StatusBarCompat;
import com.socks.library.KLog;
import com.youzan.titan.QuickAdapter;
import com.youzan.titan.TitanRecyclerView;
import com.youzan.titan.holder.AutoViewHolder;
import com.youzan.titan.internal.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * author   Joy
 * Date:  2016/4/19.
 * version:  V1.0
 * Description:
 */
public class AboutActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private TitanRecyclerView mRecyclerView;
    private QuickAdapter<String> mAdapter;
    // private TitanAdapter<String> mAdapter;
    private List<String> mStrings = new ArrayList<>();
    private TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        StatusBarCompat.compat(this, getResources().getColor(R.color.colorAccent));
        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mRecyclerView = (TitanRecyclerView) findViewById(R.id.myRecy);
        mTextView = (TextView) findViewById(R.id.tv_app_version);
        mTextView.setText(BuildConfig.VERSION_CODE + "");
        mCollapsingToolbarLayout.setTitle("Joy");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData();
        mAdapter = new QuickAdapter<String>(R.layout.item_github, mStrings) {
            @Override
            public void bindView(AutoViewHolder holder, int position, String model) {
                holder.getTextView(R.id.git_tv).setText(model);
            }
        };

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于list view


        mRecyclerView.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView recyclerView, View view, int position, long id) {
                KLog.e("position----" + position + "\n id------" + id);
                Toast.makeText(AboutActivity.this, mStrings.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView.setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(RecyclerView recyclerView, View view, int position, long id) {
                mAdapter.remove(position);
                return false;
            }
        });


        mRecyclerView.setOnLoadMoreListener(new TitanRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                final List<String> datas = new ArrayList<String>();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 3; i++) {
                            datas.add("new string   " + i);
                        }
                        mAdapter.addDataEnd(datas);
                    }
                }, 5000);
                if (mAdapter.getAdapterItemCount() < 20) {

                    mRecyclerView.setHasMore(true);
                } else {
                    mRecyclerView.setHasMore(false);
                }
            }
        });
    }

    private void initData() {
        mStrings.add("com.android.support:appcompat-v7:23.2.1");
        mStrings.add("com.android.support:design:23.2.1");
        mStrings.add("com.android.support:recyclerview-v7:23.2.1");
        mStrings.add("com.android.support:cardview-v7:23.2.1");
        mStrings.add("com.youzan:titan:0.1.0");
        mStrings.add("com.cjj.materialrefeshlayout:library:1.3.0");
        mStrings.add("com.github.zhaokaiqiang.klog:library:1.4.0");
        mStrings.add("com.squareup.okhttp:okhttp:2.5.0");
        mStrings.add("com.github.bumptech.glide:glide:3.7.0");
        mStrings.add("com.google.code.gson:gson:2.4");

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //在Action Bar的最左边，就是Home icon和标题的区域
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }
}
