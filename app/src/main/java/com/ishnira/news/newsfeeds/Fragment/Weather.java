package com.ishnira.news.newsfeeds.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ishnira.news.newsfeeds.R;


public class Weather extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment]
        getActivity().setTitle("Weather");
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

}
