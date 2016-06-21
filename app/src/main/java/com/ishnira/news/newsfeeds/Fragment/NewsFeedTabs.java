package com.ishnira.news.newsfeeds.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ishnira.news.newsfeeds.R;


/**
 * Created by on 4/24/2016.
 */
public class NewsFeedTabs extends Fragment {

    View contact_setupview;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        contact_setupview = inflater.inflate(R.layout.pagersetup,null);

        viewPager = (ViewPager) contact_setupview.findViewById(R.id.tabanim_viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) contact_setupview.findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(viewPager);

        return contact_setupview;
    }

    private void setupViewPager(ViewPager viewPager) {
        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager());
        adapter.addFrag(new News(), " Social ");
        adapter.addFrag(new AboutUs(), " Educational ");
        adapter.addFrag(new MyFavorite(), " Sports ");
       // adapter.addFrag(new Weather()," Weather");


        viewPager.setAdapter(adapter);
    }
}
