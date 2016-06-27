package com.ishnira.news.newsfeeds.Adapter;

/**
 * Created by pratap.kesaboyina on 24-12-2014.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ishnira.news.newsfeeds.Activity.WebViewActivity;
import com.ishnira.news.newsfeeds.Model.RSSFeed;
import com.ishnira.news.newsfeeds.R;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class NewsDataAdapter extends RecyclerView.Adapter<NewsDataAdapter.FeedListRowHolder> {

    private List<RSSFeed> feedItemList;

    private Context mContext;

    public NewsDataAdapter(Context context, List<RSSFeed> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
      }

    @Override
    public FeedListRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_row, null);
        FeedListRowHolder mh = new FeedListRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(FeedListRowHolder feedListRowHolder, int i) {
        RSSFeed feedItem = feedItemList.get(i);

        feedListRowHolder.rssFeed = feedItem;
        feedListRowHolder.title.setText(feedItem.getTitle());
        feedListRowHolder.pubDate.setText(feedItem.getPubDate());
        try {
            String desc = feedItem.getDescription();
            feedListRowHolder.description.setText(desc.substring(0, 130) + "...");
        }catch(Exception e)
        {e.printStackTrace();}
        String imgLink = feedItem.getImage();
        ImageView imageView=feedListRowHolder.image;
        int height = mContext.getResources().getDisplayMetrics().heightPixels * 1 / 4;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.height = height;
        imageView.setLayoutParams(layoutParams);

        int width = mContext.getResources().getDisplayMetrics().widthPixels*1/2;
        System.out.println("*****************8 height "+height + "  width "+width);

       decideWhichImageOnListItem(feedListRowHolder, height, width, imgLink);
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    private void decideWhichImageOnListItem(FeedListRowHolder holder, int height, int width, String imgLink) {
        if (StringUtils.isBlank(imgLink)) {

            Glide.with(mContext)
                    .load(imgLink)
                    .override(width, height)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.image);

        }
            else{
                Glide.with(mContext)
                        .load(imgLink)
                        .override(width, height)
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(Color.TRANSPARENT)
                        .error(Color.TRANSPARENT)
                        .into(holder.image);
            }
    }

    public  class FeedListRowHolder extends RecyclerView.ViewHolder {

        protected TextView title;
        protected TextView pubDate;
        protected TextView description;
        public  ImageView image;
        protected RSSFeed rssFeed;

        public FeedListRowHolder(View view) {
            super(view);

            this.title = (TextView) view.findViewById(R.id.tvtitle);
            this.pubDate = (TextView) view.findViewById(R.id.tvpubdate);
            this.description=(TextView)view.findViewById(R.id.tvdiscription);
            this.image=(ImageView)view.findViewById(R.id.imageView2) ;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent webIntent = new Intent(v.getContext(), WebViewActivity.class);

                    webIntent.putExtra("title", rssFeed.getTitle().toString());

                    webIntent.putExtra("url", rssFeed.getLink().toString());


                    // String url=mRssFeedList.get(position).getLink().toString();

                    //   Log.i("onlcik Recycler",url);

                    v.getContext().startActivity(webIntent);

                }
            });
        }

    }



}