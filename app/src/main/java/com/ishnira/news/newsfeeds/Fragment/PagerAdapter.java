package com.ishnira.news.newsfeeds.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ravi on 2/1/2016.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    //int i=0;
    //Bundle bundle;
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();


    public PagerAdapter(FragmentManager manager) {
        super(manager);
    }
    @Override
    public Fragment getItem(int position) {

        Fragment frag = mFragmentList.get(position);
//        Bundle bundle = new Bundle();
//        bundle.putInt("Key",i);
//        i++;
//        frag.setArguments(bundle);
        return frag;
    }
    @Override
    public int getCount() {
        return mFragmentList.size();
    }
    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }


}
