package io.github.phonechan.xman.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.phonechan.xman.R;
import io.github.phonechan.xman.fragment.ExploreFragment;
import io.github.phonechan.xman.fragment.MyFragment;
import io.github.phonechan.xman.fragment.NewsFragment;
import io.github.phonechan.xman.fragment.TweetFragment;
import io.github.phonechan.xman.log.LogUtil;
import io.github.phonechan.xman.widget.ChangeColorIconWithText;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    private static final String TAG = "MainActivity";
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.tab_1)
    ChangeColorIconWithText tab1;
    @Bind(R.id.tab_2)
    ChangeColorIconWithText tab2;
    @Bind(R.id.tab_3)
    ChangeColorIconWithText tab3;
    @Bind(R.id.tab_4)
    ChangeColorIconWithText tab4;


    private List<Fragment> fragmentList = new ArrayList<>();
    private FragmentPagerAdapter adapter;

    private List<ChangeColorIconWithText> tabIndicators = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();

    }


    private void initView() {
        initFragments();
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);
        navView.setNavigationItemSelectedListener(this);

        tabIndicators.add(tab1);
        tabIndicators.add(tab2);
        tabIndicators.add(tab3);
        tabIndicators.add(tab4);

        tab1.setIconAlpha(1.0f);
    }


    private void initFragments() {

        fragmentList.add(new NewsFragment());
        fragmentList.add(new TweetFragment());
        fragmentList.add(new ExploreFragment());
        fragmentList.add(new MyFragment());

        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick({R.id.tab_1, R.id.tab_2, R.id.tab_3, R.id.tab_4})
    public void onClick(View v) {

        resetOtherTabs();

        switch (v.getId()) {
            case R.id.tab_1:

                tab1.setIconAlpha(1.0f);
                viewPager.setCurrentItem(0, true);
                break;
            case R.id.tab_2:

                tab2.setIconAlpha(1.0f);
                viewPager.setCurrentItem(1, true);
                break;
            case R.id.tab_3:

                tab3.setIconAlpha(1.0f);
                viewPager.setCurrentItem(2, true);
                break;
            case R.id.tab_4:

                tab4.setIconAlpha(1.0f);
                viewPager.setCurrentItem(3, true);
                break;
        }
    }

    /**
     * 重置其他的TabIndicator的颜色
     */
    private void resetOtherTabs() {

        for (ChangeColorIconWithText tab : tabIndicators) {
            tab.setIconAlpha(0);
            tab.setTextColor(R.color.green);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        LogUtil.i(TAG, "position : " + position + " ,positionOffset : " + positionOffset + " ,positionOffsetPixels : " + positionOffsetPixels);
        if (positionOffset > 0) {
            ChangeColorIconWithText left = tabIndicators.get(position);
            ChangeColorIconWithText right = tabIndicators.get(position + 1);
            left.setIconAlpha(1 - positionOffset);
            right.setIconAlpha(positionOffset);
        }

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
