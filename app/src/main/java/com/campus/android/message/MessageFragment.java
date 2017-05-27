package com.campus.android.message;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.campus.android.R;
import com.campus.android.common.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by lebron on 17-5-27.
 */

public class MessageFragment extends BaseFragment {
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private MessageAdapter mMessageAdapter;

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initViewsAndData(View view) {
        mMessageAdapter = new MessageAdapter(getActivity().getSupportFragmentManager(), new String[]{"通知", "公告"});
        mViewPager.setAdapter(mMessageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    public class MessageAdapter extends FragmentPagerAdapter {
        private String[] tabs;

        public MessageAdapter(FragmentManager fm, String[] tabs) {
            super(fm);
            this.tabs = tabs;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new NewsFragment();
                    break;
                case 1:
                    fragment = new NotificationFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            if (tabs != null) {
                return tabs.length;
            }
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }
}
