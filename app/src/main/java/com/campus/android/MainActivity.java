package com.campus.android;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.campus.android.common.base.BaseActivity;
import com.campus.android.message.MessageFragment;
import com.campus.android.search.SearchFragment;
import com.campus.android.user.UserFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    private static final String[] TOOLBAR_TITLES = new String[]{"首页", "通知", "我的"};
    @BindView(R.id.main_content_bottombar)
    BottomNavigationView mBottomBar;
    @BindView(R.id.main_content_viewpager)
    ViewPager mViewPager;

    private MainPagerAdapter mMainPagerAdapter;
    private MenuItem prevMenuItem;

    private BottomNavigationView.OnNavigationItemSelectedListener mListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.main_bottom_nav_home:
                    mViewPager.setCurrentItem(0);
                    mToolbar.setTitle(TOOLBAR_TITLES[0]);
                    break;
                case R.id.main_bottom_nav_subscribe:
                    mViewPager.setCurrentItem(1);
                    mToolbar.setTitle(TOOLBAR_TITLES[1]);
                    break;
                case R.id.main_bottom_nav_account:
                    mViewPager.setCurrentItem(2);
                    mToolbar.setTitle(TOOLBAR_TITLES[2]);
                    break;
            }
            return true;
        }
    };

    private ViewPager.OnPageChangeListener mChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mToolbar.setTitle(TOOLBAR_TITLES[position]);
            if (prevMenuItem != null) {
                prevMenuItem.setChecked(false);
            } else {
                mBottomBar.getMenu().getItem(0).setChecked(false);
            }
            mBottomBar.getMenu().getItem(position).setChecked(true);
            prevMenuItem = mBottomBar.getMenu().getItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(TOOLBAR_TITLES[0]);

        mMainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mMainPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(mChangeListener);
        mBottomBar.setOnNavigationItemSelectedListener(mListener);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_main;
    }

    private class MainPagerAdapter extends FragmentStatePagerAdapter {
        private Map<Integer, Fragment> mPageReferenceMap = new HashMap<>();

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new SearchFragment();
                    break;
                case 1:
                    fragment = new MessageFragment();
                    break;
                case 2:
                    fragment = new UserFragment();
                    break;
                default:
                    break;
            }
            mPageReferenceMap.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            mPageReferenceMap.remove(position);
        }

        @Override
        public int getCount() {
            return TOOLBAR_TITLES.length;
        }

        public Fragment getFragment(int key) {
            return mPageReferenceMap.get(key);
        }
    }
}
