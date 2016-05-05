package com.joy.ep.myokhttptext.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.joy.ep.myokhttptext.R;
import com.joy.ep.myokhttptext.fragment.AllFragment;
import com.joy.ep.myokhttptext.fragment.AndroidFragment;
import com.joy.ep.myokhttptext.fragment.FuliFragment;
import com.joy.ep.myokhttptext.fragment.IosFragment;
import com.joy.ep.myokhttptext.fragment.PlayFragment;
import com.joy.ep.myokhttptext.fragment.ResFragment;

/**
 * author   Joy
 * Date:  2016/2/26.
 * version:  V1.0
 * Description:
 */
public class GankMainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private CoordinatorLayout lay_content;
    //    private ActionBar ab;
    private ActionBarDrawerToggle abdt;

    private Fragment currentFragment;
    private long exitTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initview();
    }

    private void initview() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        lay_content = (CoordinatorLayout) findViewById(R.id.content);
        toolbar.setTitle("干货集中营");
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Gank.Io");

//        ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);
//        ab.setHomeButtonEnabled(true);

        exitTime = System.currentTimeMillis();

        //开启汉堡动画
        abdt = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(abdt);
        abdt.syncState();

        switchFragment(new AllFragment());

        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setupDrawerContent(NavigationView navigationView) {     //设置自动关闭
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem menuItem) {
                menuItem.setChecked(true);
                selectDrawerItem(menuItem);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

    }

    private void selectDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_fuli:
                switchContent(currentFragment, new FuliFragment());
                break;
            case R.id.nav_home:
                switchContent(currentFragment, new AllFragment());
                break;
            case R.id.nav_android:
                switchContent(currentFragment, new AndroidFragment());
                break;
            case R.id.nav_ios:
                switchContent(currentFragment, new IosFragment());
                break;
            case R.id.nav_play:
                switchContent(currentFragment, new PlayFragment());
                break;
            case R.id.nav_res:
                switchContent(currentFragment, new ResFragment());
                break;
            case R.id.nav_about:
                startActivity(new Intent(this, AboutActivity.class));
        }
    }


    private void switchFragment(Fragment fragment) {
        Slide slideTransition;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //Gravity.START部分机型崩溃java.lang.IllegalArgumentException: Invalid slide direction
            slideTransition = new Slide(Gravity.LEFT);
            slideTransition.setDuration(700);
            fragment.setEnterTransition(slideTransition);
            fragment.setExitTransition(slideTransition);
        }
        if (currentFragment == null || !fragment.getClass().getName().equals(currentFragment.getClass().getName())) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            currentFragment = fragment;
        }
    }

    public void switchContent(Fragment from, Fragment to) {
        if (currentFragment == null || !to.getClass().getName().equals(currentFragment.getClass().getName())) {
            currentFragment = to;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(
                    android.R.anim.fade_in, android.R.anim.fade_out);
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.container, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawers();
            }
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
