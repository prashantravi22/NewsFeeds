package com.ishnira.news.newsfeeds.Fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ishnira.news.newsfeeds.Adapter.NewsDataAdapter;
import com.ishnira.news.newsfeeds.Model.RSSFeed;
import com.ishnira.news.newsfeeds.R;
import com.ishnira.news.newsfeeds.Utils.ConnectionDetector;
import com.ishnira.news.newsfeeds.Utils.Constants;
import com.ishnira.news.newsfeeds.Utils.NewsFeedParser;

import java.util.ArrayList;
import java.util.List;


public class NewsFeeds extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private NewsFeedParser mNewsFeeder;
    private NewsDataAdapter newsDataAdapter;
    private List<RSSFeed> mRssFeedList = new ArrayList<RSSFeed>();

    public static NewsFeeds newInstance(CharSequence title, int position) {
        Bundle bundle = new Bundle();
        bundle.putCharSequence("title", title);
        bundle.putInt("position", position);
        NewsFeeds fragment = new NewsFeeds();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_about_us, container, false);
        settingAdapter();
        System.out.println("********* goes into news feed list");
        Log.i("Fragment", "OncreateView()");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.i("Fragment", "onStart()");
        swipeRefreshLayout =(SwipeRefreshLayout)getActivity().findViewById(R.id.swipwreferesh);
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
         settingAdapter();
        // use a linear layout manager for vertical listview like items
        //  mLayoutManager = new LinearLayoutManager(getActivity());
        // StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2,1);
        // mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(getResources().getInteger(R.integer.gridspan), 1));
        // Grid Layout
        //mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        swipeRefreshLayout.setColorSchemeResources(R.color.black, R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        settingAdapter();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2500);
            }
        });
        System.out.println("********* goes into news feed recycler view list");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        performAsyncTask();
    }
   /* private void setupAdapter() {
        newsDataAdapter = new NewsDataAdapter(getActivity(),mRssFeedList);
        mRecyclerView.setAdapter(newsDataAdapter);
    }*/

    public void performAsyncTask() {


        if (mRssFeedList.size() > 0) {
            settingAdapter();
        } else {

            boolean isNetworkAvailable = ConnectionDetector.isConnectingToInternet(getActivity().getApplicationContext());

            if (isNetworkAvailable) {

                Bundle args = getArguments();

                if (args != null)
                {
                    new DoRssFeedTask().execute(Constants.TOPSTORIES[args.getInt("position")]);
                    //     RSSFeed rssFeed = new RSSFeed("title","http://www.google.com", "description","category", "pub date","guid","http://www.google.com");
                    //      mRssFeedList.add(rssFeed);
                    System.out.println("********* goes into news feed and set the link url");
                }
                else
                {
                    Toast.makeText(getActivity(), "Oops!!! News Fragment", Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        }
    }

    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        Log.i("Fragment", "onActivityCreated()");
    }*/

    public class DoRssFeedTask extends AsyncTask<String, Void, List<RSSFeed>> {
        ProgressDialog prog;
        String jsonStr = null;


        @Override
        protected void onPreExecute() {
           /* prog = new ProgressDialog(getActivity());
            prog.setMessage("Loading...");
            prog.setCancelable(false);
            prog.show();*/
            Log.i("Fragment ASyncTask", "onPreExecute()");
        }

        @Override
        protected List<RSSFeed> doInBackground(String... params) {
            for (String urlVal : params) {
                mNewsFeeder = new NewsFeedParser(urlVal);
            }
            mRssFeedList = mNewsFeeder.parseXmlData();
            System.out.println("********* goes doinbackground process");
            Log.i("Fragment ASyncTask", "doInBackground()");
            return mRssFeedList;
        }

        @Override
        protected void onPostExecute(List<RSSFeed> result) {
           /* prog.dismiss();*/
            settingAdapter();
            Log.i("Fragment ASyncTask", "onPostExecute()");
        }

    }

    public void settingAdapter() {
        mAdapter = new NewsDataAdapter(getActivity(), mRssFeedList);
        int count = mRssFeedList.size();
        if (count != 0 && mAdapter != null) {
            System.out.println("********* goes into news feed recycler list adapter");
            mRecyclerView.setAdapter(mAdapter);
        }
    }

}
