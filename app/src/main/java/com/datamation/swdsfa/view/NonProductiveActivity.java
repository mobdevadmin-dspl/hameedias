package com.datamation.swdsfa.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.datamation.swdsfa.R;
import com.datamation.swdsfa.controller.DayNPrdDetController;
import com.datamation.swdsfa.controller.SalesReturnDetController;
import com.datamation.swdsfa.helpers.SharedPref;
import com.datamation.swdsfa.model.Customer;
import com.datamation.swdsfa.model.FInvRHed;
import com.datamation.swdsfa.nonproductive.NonProductiveDetail;
import com.datamation.swdsfa.salesreturn.SalesReturnDetails;
import com.datamation.swdsfa.salesreturn.SalesReturnHeader;
import com.datamation.swdsfa.salesreturn.SalesReturnSummary;
import com.datamation.swdsfa.utils.GPSTracker;

public class NonProductiveActivity extends AppCompatActivity {

    ViewPager viewPager;
    private NonProductiveDetail nonProductiveDetail;
    Context context;
    public Customer selectedNonDebtor = null;
    boolean status = true;
    GPSTracker gpsTracker;
    public SharedPref mSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_productive);

        Toolbar toolbar = (Toolbar) findViewById(R.id.np_toolbar);
        TextView title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        title.setText("NON PRODUCTIVE");
        context = this;
        gpsTracker = new GPSTracker(this);
        mSharedPref = SharedPref.getInstance(this);

        Log.v("Lati in Nonprdctv>>>>>",mSharedPref.getGlobalVal("Latitude"));
        Log.v("Longi in Nonprdctv>>>>",mSharedPref.getGlobalVal("Longitude"));

        PagerSlidingTabStrip slidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.np_tab_strip);
        viewPager = (ViewPager) findViewById(R.id.np_viewpager);

    //    slidingTabStrip.setBackgroundColor(getResources().getColor(R.color.theme_color));
        slidingTabStrip.setTextColor(getResources().getColor(android.R.color.black));
        slidingTabStrip.setIndicatorColor(getResources().getColor(R.color.colorPrimaryDark));
        slidingTabStrip.setDividerColor(getResources().getColor(R.color.half_black));

        NPPagerAdapter adapter = new NPPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        viewPager.setPageMargin(pageMargin);
        slidingTabStrip.setViewPager(viewPager);

        status = new DayNPrdDetController(getApplicationContext()).isAnyActiveNPs();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0)
                    LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("TAG_NP_DETAILS"));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private class NPPagerAdapter extends FragmentPagerAdapter {

        private final String[] titles = {"NON PRODUCTIVE DETAILS"};

        public NPPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    if(nonProductiveDetail == null) nonProductiveDetail = new NonProductiveDetail();
                    return nonProductiveDetail;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
