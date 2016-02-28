package com.joy.ep.myokhttptext.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.joy.ep.myokhttptext.R;
import com.joy.ep.myokhttptext.fragment.AllFragment;
import com.joy.ep.myokhttptext.fragment.FuliFragment;

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
    private ActionBar ab;
    private ActionBarDrawerToggle abdt;

    private Fragment currentFragment;

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
        setSupportActionBar(toolbar);
        toolbar.setTitle("干货集中营");
        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);

        //开启汉堡动画
        abdt = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(abdt);
        abdt.syncState();

        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        switchFragment(new AllFragment());


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
                //    switchContent(currentFragment,new AndroidFragment());
                break;
            case R.id.nav_ios:
                //      switchFragment(new IosFragment());
                break;
        }
    }


    private void switchFragment(Fragment fragment) {
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
}
