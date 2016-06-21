package com.ishnira.news.newsfeeds.Activity;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ishnira.news.newsfeeds.Fragment.AboutUs;
import com.ishnira.news.newsfeeds.Fragment.MyFavorite;
import com.ishnira.news.newsfeeds.Fragment.News;
import com.ishnira.news.newsfeeds.Fragment.NewsFeedTabs;
import com.ishnira.news.newsfeeds.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    Fragment fragment = null;
    private ExpandableListView mExpandableListView;
    private ExpandableListAdapter mExpandableListAdapter;
    private List<String> mExpandableListTitle;
    private HashMap<String, List<String>> mExpandableListData;
    private TextView mSelectedItemView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        mExpandableListView = (ExpandableListView) findViewById(R.id.navList);
        mSelectedItemView = (TextView) findViewById(R.id.selected_item);

        LayoutInflater inflater = getLayoutInflater();
        View listHeaderView = inflater.inflate(R.layout.nav_header, null, false);
        mExpandableListView.addHeaderView(listHeaderView);

       /* mExpandableListData = ExpandableListDataSource.getData(this);
        mExpandableListTitle = new ArrayList(mExpandableListData.keySet());*/
        preparelistItems();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void addDrawerItems() {
        mExpandableListAdapter = new CustomExpandableListAdapter(this, mExpandableListTitle, mExpandableListData);
        mExpandableListView.setAdapter(mExpandableListAdapter);
        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                getSupportActionBar().setTitle(mExpandableListTitle.get(groupPosition).toString());
                mSelectedItemView.setText(mExpandableListTitle.get(groupPosition).toString());
            }
        });

        mExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                getSupportActionBar().setTitle(R.string.film_genres);
                mSelectedItemView.setText(R.string.selected_item);
            }
        });

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String selectedItem = ((List) (mExpandableListData.get(mExpandableListTitle.get(groupPosition)))).get(childPosition).toString();
                getSupportActionBar().setTitle(selectedItem);
                mSelectedItemView.setText(mExpandableListTitle.get(groupPosition).toString() + " -> " + selectedItem);
                switch (groupPosition) {
                    case 0:
                        switch (childPosition) {
                            case 0:
                                fragment = new NewsFeedTabs();
                                getSupportFragmentManager().beginTransaction().replace(R.id.selected_content, fragment).commit();
                                System.out.println(" group no..111a");
                                break;
                            case 1:
                                fragment = new NewsFeedTabs();
                                getSupportFragmentManager().beginTransaction().replace(R.id.selected_content, fragment).commit();
                                System.out.println(" group no..111b");
                                break;
                            case 2:
                                fragment = new NewsFeedTabs();
                                getSupportFragmentManager().beginTransaction().replace(R.id.selected_content, fragment).commit();
                                System.out.println(" group no..111c");
                                break;
                            default:
                                break;
                        }

                        break;
                    case 1:
                        switch (childPosition) {
                            case 0:
                                fragment = new News();
                                getSupportFragmentManager().beginTransaction().replace(R.id.selected_content, fragment).commit();
                                System.out.println(" group no..111dda");
                                break;
                            default:
                                break;

                        }
                        break;
                    case 2:

                        fragment = new AboutUs();
                        getSupportFragmentManager().beginTransaction().replace(R.id.selected_content, fragment).commit();
                        System.out.println(" group no..11ff1a");

                        break;

                    default:
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.selected_content, fragment).commit();
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void preparelistItems() {
        mExpandableListData = new HashMap<String, List<String>>();
        mExpandableListTitle = new ArrayList<String>();
        mExpandableListTitle.add("News");
        //  mExpandableListTitle.add("Weather");
        mExpandableListTitle.add("My Favorite");
        mExpandableListTitle.add("About Us");
        mExpandableListTitle.add("Exit");
        List<String> a_news = Arrays.asList(this.getResources().getStringArray(R.array.news));
        //   List<String> b_weather = Arrays.asList(this.getResources().getStringArray(R.array.weather));
        List<String> c_myfevorite = Arrays.asList(this.getResources().getString(R.string.myfevorite));
        List<String> d_aboutus = Arrays.asList(this.getResources().getStringArray(R.array.aboutus));
        List<String> e_exit = Arrays.asList(this.getResources().getStringArray(R.array.exit));

        mExpandableListData.put(mExpandableListTitle.get(0), a_news);
        //   mExpandableListData.put(mExpandableListTitle.get(1), b_weather);
        mExpandableListData.put(mExpandableListTitle.get(1), c_myfevorite);
        mExpandableListData.put(mExpandableListTitle.get(2), d_aboutus);
        mExpandableListData.put(mExpandableListTitle.get(3), e_exit);
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.film_genres);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        // spinner.setBackgroundColor(C);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.select_location, R.layout.spinner_row);
        adapter.setDropDownViewResource(R.layout.spinner_row);

        spinner.setAdapter(adapter);

        return true;
    }

    public void setupSpinner(Spinner spin1){
        String[] items={"Homwe","Searwwch","w"};
        //wrap the items in the Adapter
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,items);
        //assign adapter to the Spinner
        spin1.setAdapter(adapter);

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

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

        private Context mContext;
        private List<String> mExpandableListTitle;
        private HashMap<String, List<String>> mExpandableListDetail;
        private LayoutInflater mLayoutInflater;
        int[] pics = {R.drawable.ic_library_books_black_18dp,
                //    R.drawable.ic_cloud_black_18dp,
                R.drawable.ic_star_rate_black_18dp,
                R.drawable.ic_info_black_18dp,
                R.drawable.ic_exit_to_app_black_18dp};

        public CustomExpandableListAdapter(Context context, List<String> expandableListTitle,
                                           HashMap<String, List<String>> expandableListDetail) {
            mContext = context;
            mExpandableListTitle = expandableListTitle;
            mExpandableListDetail = expandableListDetail;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public Object getChild(int listPosition, int expandedListPosition) {
            return mExpandableListDetail.get(mExpandableListTitle.get(listPosition))
                    .get(expandedListPosition);
        }

        @Override
        public long getChildId(int listPosition, int expandedListPosition) {
            return expandedListPosition;
        }

        @Override
        public View getChildView(int listPosition, final int expandedListPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            final String expandedListText = (String) getChild(listPosition, expandedListPosition);
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.list_item, null);
            }
            TextView expandedListTextView = (TextView) convertView
                    .findViewById(R.id.expandedListItem);
            expandedListTextView.setText(expandedListText);
            return convertView;
        }

        @Override
        public int getChildrenCount(int listPosition) {
            return mExpandableListDetail.get(mExpandableListTitle.get(listPosition))
                    .size();
        }

        @Override
        public Object getGroup(int listPosition) {
            return mExpandableListTitle.get(listPosition);
        }

        @Override
        public int getGroupCount() {
            return mExpandableListTitle.size();
        }

        @Override
        public long getGroupId(int listPosition) {
            return listPosition;
        }

        @Override
        public View getGroupView(final int listPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String listTitle = (String) getGroup(listPosition);
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.list_group, null);
            }
            TextView listTitleTextView = (TextView) convertView
                    .findViewById(R.id.listTitle);
            listTitleTextView.setTypeface(null, Typeface.BOLD);
            listTitleTextView.setText(listTitle);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
            imageView.setImageResource(pics[listPosition]);
            /*convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Fragment fragment22=null;
                switch (listPosition)
                {

                    case 1:
                       // fragment=new Weather();
                        Toast.makeText(mContext, "clicked "+listPosition, Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        android.support.v4.app.Fragment fragment=new AboutUs();
                        android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.selected_content,fragment);
                        fragmentTransaction.commit();
                        Toast.makeText(mContext, "clicked "+listPosition, Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        android.support.v4.app.Fragment fragment1=new News();
                        android.support.v4.app.FragmentTransaction fragmentTransaction1=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction1.replace(R.id.selected_content,fragment1);
                        fragmentTransaction1.commit();
                        Toast.makeText(mContext, "clicked "+listPosition, Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        break;
                    default:
                        break;

                }
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            });*/
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int listPosition, int expandedListPosition) {
            return true;
        }
    }


}

