package com.zx.wfm;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.aspsine.fragmentnavigator.FragmentNavigator;
import com.google.android.material.navigation.NavigationView;
import com.zx.wfm.ui.BaseActivity;
import com.zx.wfm.ui.fragment.BaseToolbarFragment;
import com.zx.wfm.ui.view.FlakeView;
import com.zx.wfm.utils.ToastUtil;
import com.zx.wfm.utils.UKutils;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener
        , BaseToolbarFragment.ToggleDrawerCallBack {

    private static final Integer ID_ARRAY[] = {
            R.id.nav_Twitter_style
//            R.id.nav_google_style,
//            R.id.nav_yalantis_style,
//            R.id.nav_jd_style,
//            R.id.nav_set_header_footer_via_code
    };

    private static final List<Integer> IDS = Arrays.asList(ID_ARRAY);

    private static final int DEFAULT_POSITION = 0;

    private DrawerLayout drawerLayout;

    /**
     * https://github.com/Aspsine/FragmentNavigator
     */
    private FragmentNavigator mFragmentNavigator;

    FlakeView flakeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentNavigator = new FragmentNavigator(getSupportFragmentManager(), new MainFragmentAdapter(), R.id.container);
        mFragmentNavigator.setDefaultPosition(DEFAULT_POSITION);
        mFragmentNavigator.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(IDS.get(DEFAULT_POSITION));
        mFragmentNavigator.showFragment(mFragmentNavigator.getCurrentPosition());
        FrameLayout container = (FrameLayout) findViewById(R.id.icon_container);
        flakeView = new FlakeView(this);
        container.addView(flakeView);
        if (Integer.parseInt(Build.VERSION.SDK) >= Build.VERSION_CODES.HONEYCOMB) {
            flakeView.setLayerType(View.LAYER_TYPE_NONE,null);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mFragmentNavigator.onSaveInstanceState(outState);
    }

    @Override
    public void openDrawer() {
//        drawerLayout.openDrawer(GravityCompat.START);
        ToastUtil.showToastShort(this,"敬请期待");
    }

    @Override
    public boolean onNavigationItemSelected(final MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);
        drawerLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.nav_about) {
                    startActivity(new Intent(MainActivity.this, AboutActivity.class));
                } else {
                    mFragmentNavigator.showFragment(IDS.indexOf(itemId));
                }
            }
        }, 200);
        return true;
    }
    @Override
    protected void onPause() {
        super.onPause();
        flakeView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        flakeView.resume();
    }
}
