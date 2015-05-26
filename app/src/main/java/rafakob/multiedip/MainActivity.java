package rafakob.multiedip;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.astuetz.PagerSlidingTabStrip;

import rafakob.multiedip.drawer.DrawerAdapter;
import rafakob.multiedip.drawer.DrawerHeader;
import rafakob.multiedip.drawer.DrawerItem;
import rafakob.multiedip.drawer.DrawerObject;


public class MainActivity extends ActionBarActivity  {

    private LinearLayout mMainWindowLayout; // layout with tabs Project and Result
    private FrameLayout mFragmentContainerLayout; // layout where fragments are injected from drawer
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mTitle;

    // Drawer items:
    private static final int NAV_DASHBOARD = 1;
    private static final int NAV_STATISTICS = 2;
    private static final int NAV_PLOTS = 3;
    private static final int NAV_SETTINGS = 5;
    private static final int NAV_ABOUT = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** Setup drawer. **/
        mDrawerList = (ListView) findViewById(R.id.drawer_list);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mTitle = getTitle().toString();
        addDrawerItems();
        setupDrawer();
        /**  Drawer actionbar button. **/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        /**  Get the ViewPager and set it's PagerAdapter so that it can display items. **/
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_main);
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(),getApplicationContext()));
        /** Give the PagerSlidingTabStrip the ViewPager. **/
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabstrip_main);
        /** Attach the view pager to the tab strip. **/
        tabsStrip.setViewPager(viewPager);

        /** References to layouts. **/
        mMainWindowLayout = (LinearLayout) this.findViewById(R.id.main_view);
        mFragmentContainerLayout = (FrameLayout) this.findViewById(R.id.fragment_container);

        getSupportActionBar().setElevation(0);




    }


    private void addDrawerItems() {
        DrawerObject[] menu = new DrawerObject[]{
                DrawerHeader.create(100, getString(R.string.title_header1)),
                DrawerItem.create(101, getString(R.string.title_item1), "ic_view_dashboard", true, this),
                DrawerItem.create(102, getString(R.string.title_item2), "ic_trending_up", true, this),
                DrawerItem.create(103, getString(R.string.title_item3), "ic_poll", true, this),
                DrawerHeader.create(200, getString(R.string.title_header2)),
                DrawerItem.create(201, getString(R.string.title_item4), "ic_settings", false, this),
                DrawerItem.create(202, getString(R.string.title_item5), "ic_information_outline", false, this),
        };

        DrawerAdapter mAdapter = new DrawerAdapter(this, R.layout.drawer_list_item, menu);
        mDrawerList.setAdapter(mAdapter);

        /** On drawer item click. **/
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getApplicationContext(), "test"+position, Toast.LENGTH_SHORT).show();
                showFragment(position);
                mDrawerLayout.closeDrawers();
            }
        });

    }

    private void showFragment(int position) {
        Fragment f = null;
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        switch (position) {
            case NAV_DASHBOARD:
                /** Show Project and Result tabs. **/
                mMainWindowLayout.setVisibility(LinearLayout.VISIBLE);
                /** Clear fragment container. **/
                ft.replace(R.id.fragment_container, new Fragment()).commit();

                mTitle = getString(R.string.title_item1);
                setTitle(mTitle);
                break;

            case NAV_STATISTICS:
                f = new StatisticsFragment();
                mTitle = getString(R.string.title_item2);
                break;
            case NAV_PLOTS:
//                f = new PlotsFragment();
//                mTitle = getString(R.string.title_item3);
                Intent i;
                i = new Intent(this, PlotsActivity.class);
                startActivity(i);
                break;

            case NAV_SETTINGS:
                f = new AboutFragment();
                mTitle = getString(R.string.title_item4);
                break;

            case NAV_ABOUT:
                f = new AboutFragment();
                mTitle = getString(R.string.title_item5);
                break;
        }

        if (f != null && position != NAV_DASHBOARD && position != NAV_PLOTS) {
            /** Hide main window. **/
            mMainWindowLayout.setVisibility(LinearLayout.GONE);
            /** "Insert" new fragment in the container. **/
            ft.replace(R.id.fragment_container, f).commit();
        }
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. **/
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(getString(R.string.title_drawer_opened));
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. **/
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public void onBackPressed() {
        /** If the drawer is opened, Back button closes it. **/
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState(); // sync the toggle state after onRestoreInstanceState has occurred
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /** Activate the navigation drawer toggle. **/
        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    public void onFloatingActionButtonClick(View v) {
        showFragment(NAV_DASHBOARD);
    }


}
