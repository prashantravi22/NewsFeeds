package com.ishnira.news.newsfeeds.Utils;

import android.util.Log;

import com.ishnira.news.newsfeeds.Model.RSSFeed;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsFeedParser {
    private InputStream urlStream;
    private XmlPullParserFactory factory;
    private XmlPullParser parser;

    private List<RSSFeed> rssFeedList;
    private RSSFeed rssFeed;

    private String urlString;
    private String tagName;
    private String title;
    private String link;
    private String description;
    private String category;
    private String pubDate;
    private String guid;
    private String feedburner;
    private String image;


    public static final String ITEM = "item";
    public static final String CHANNEL = "channel";
    public static final String TITLE = "title";
    public static final String LINK = "link";
    public static final String DESCRIPTION = "description";
    public static final String CATEGORY = "category";
    public static final String PUBLISHEDDATE = "pubDate";
    public static final String GUID = "guid";
    public static final String FEEDBURNERORIGLINK = "feedburner:origLink";
    public static final String IMAGE = "img";


    public NewsFeedParser(String urlString) {
        this.urlString = urlString;
    }

    public static InputStream downloadStream(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        InputStream stream = conn.getInputStream();
        return stream;
    }


    public List<RSSFeed> parseXmlData() {
        try {
            int count = 0;
            factory = XmlPullParserFactory.newInstance();
            parser = factory.newPullParser();
            //---new
            urlStream = downloadStream(urlString);

            parser.setInput(urlStream, null);
            int eventType = parser.getEventType();
            rssFeed = new RSSFeed();
            boolean done = true;
            String tagValue = null;
            rssFeedList = new ArrayList<RSSFeed>();


            do{
                String tagName=parser.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:

                        if(tagName.equalsIgnoreCase(ITEM)) {
                            rssFeed = new RSSFeed();
                            done = false;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        tagValue=parser.getText();
                        break;
                    case XmlPullParser.END_TAG:

                        if(!done)
                        {
                            if(tagName.equalsIgnoreCase(TITLE))
                            {
                                title=tagValue;
                                // rssFeed.setTitle(tagValue);
                            }
                            else if(tagName.equalsIgnoreCase(DESCRIPTION))
                            {
                                String desc=tagValue;
                                description=desc.substring(desc.indexOf("a>")+2);

                                if (desc.contains("<img ")){
                                    String img  = desc.substring(desc.indexOf("<img "));
                                    String cleanUp = img.substring(0, img.indexOf(">") + 1);
                                    img = img.substring(img.indexOf("src=") + 5);
                                    //   int indexOf = img.indexOf("'");
                                    //  if (indexOf==-1){
                                    int indexOf = img.indexOf("\"");
                                    // }[
                                    if (indexOf!=-1){
                                        img = img.substring(0, indexOf);
                                    }
                                    //for haberturk matches ' char.
                                    else{
                                        indexOf = img.indexOf("\'");
                                        img = img.substring(0, indexOf);
                                    }
                                    String a=img.replace("100_100","200_200");
                                    image=a;
                                    System.out.println("***********  rs image "+a );

                                }

                            }else if (tagName.equalsIgnoreCase("imageUrl")) {

                            } else if (tagName.equalsIgnoreCase(PUBLISHEDDATE))
                            {
                                pubDate=tagValue;
                                // rssFeed.setPubDate(tagValue);
                            }
                            else if(tagName.equalsIgnoreCase(LINK))
                            {
                                link=tagValue;
                            }
                        }
                        if(tagName.equalsIgnoreCase(ITEM))
                        {
                            rssFeed = new RSSFeed(title, link, description, category, pubDate, guid, feedburner,image);
                            rssFeedList.add(rssFeed);
                            done=true;
                        }
                        break;
                }

                eventType=parser.next();
            }
            while(eventType !=XmlPullParser.END_DOCUMENT);

            /*while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                tagName = parser.getName();

                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if (tagName.equals(ITEM)) {
                            rssFeed = new RSSFeed();
                        }
                        if (tagName.equals(TITLE)) {
                            title = parser.nextText().toString();
                        }
                       *//* if (tagName.equals(LINK)) {
                            link = parser.nextText().toString();
                        }
                        if (tagName.equals(DESCRIPTION)) {
                           // description = parser.nextText().toString();
                            String decString=parser.nextText().toString();
                            description = decString;

                           String imgurl = decString.substring(decString.indexOf("src=") + 5, decString.indexOf(".jpg") + 3);
                           image=imgurl;
                        }
                        if (tagName.equals(CATEGORY)) {
                            category = parser.nextText().toString();
                        }
                        if (tagName.equals(PUBLISHEDDATE)) {
                            pubDate = parser.nextText().toString();
                        }
                        if (tagName.equals(GUID)) {
                            guid = parser.nextText().toString();
                        }
                        if (tagName.equals(FEEDBURNERORIGLINK)) {
                            feedburner = parser.nextText().toString();
                        }
                        if (tagName.equals(IMAGE)) {
                            //image = parser.nextText().toString();
                        }*//*
                        break;
                    case XmlPullParser.TEXT:
                        tagValue = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagName.equals(CHANNEL)) {
                            done = true;
                        }  else if (tagName.equalsIgnoreCase(TITLE)) {
                            rssFeed.setTitle(tagValue);
                        } else if (tagName.equalsIgnoreCase(LINK)) {
                            rssFeed.setLink(tagValue);
                        } else if (tagName.equalsIgnoreCase(DESCRIPTION)) {
                           // String dec = tagValue;
                           // rssFeed.setDescription(dec.substring(dec.indexOf("/>")));

                            //String imgurl = dec.substring(dec.indexOf("src") + 5, dec.indexOf(".jpg") + 3);
                          //  rssFeed.setImage(imgurl);
                        }else if (tagName.equalsIgnoreCase(ITEM)) {
                              rssFeed = new RSSFeed(title, link, description, category, pubDate, guid, feedburner,image);
                            // Log.i("RSSFeedItem", title + " " + link + " " + description + " " + category + " " + pubDate + guid + " " + feedburner);

                            rssFeedList.add(rssFeed);
                        }

                        break;
                }
                eventType = parser.next();
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("RSSCount", " : " + Integer.toString(rssFeedList.size()).toString());
        return rssFeedList;

    }

}
