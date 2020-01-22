package com.datamation.swdsfa.view;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.datamation.swdsfa.R;
import com.datamation.swdsfa.controller.SalesReturnDetController;
import com.datamation.swdsfa.helpers.SalesReturnResponseListener;
import com.datamation.swdsfa.model.FInvRHed;
import com.datamation.swdsfa.salesreturn.SalesReturnHeader;
import com.datamation.swdsfa.salesreturn.SalesReturnDetails;
import com.datamation.swdsfa.salesreturn.SalesReturnSummary;
import com.datamation.swdsfa.settings.ReferenceNum;

public class SalesReturnActivity extends AppCompatActivity implements SalesReturnResponseListener{

    ViewPager viewPager;
    private SalesReturnHeader salesRetrunHeader;
    private SalesReturnDetails salesReturnDetails;
    private SalesReturnSummary salesReturnSummary;
    Context context;
    public FInvRHed selectedReturnHed = null;
    boolean status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_return);

        Toolbar toolbar = (Toolbar) findViewById(R.id.saleretrun_toolbar);
        TextView title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        title.setText("SALES RETURN");
        context = this;

        PagerSlidingTabStrip slidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.saleretrun_tab_strip);
        viewPager = (ViewPager) findViewById(R.id.saleretrun_viewpager);

       // slidingTabStrip.setBackgroundColor(getResources().getColor(R.color.theme_color));
        slidingTabStrip.setTextColor(getResources().getColor(android.R.color.black));
        slidingTabStrip.setIndicatorColor(getResources().getColor(R.color.colorPrimaryDark));
        slidingTabStrip.setDividerColor(getResources().getColor(R.color.half_black));

        SalesReturnPagerAdapter adapter = new SalesReturnPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        viewPager.setPageMargin(pageMargin);
        slidingTabStrip.setViewPager(viewPager);
        ReferenceNum referenceNum = new ReferenceNum(getApplicationContext());

        status = new SalesReturnDetController(getApplicationContext()).isAnyActiveReturnHedDet(referenceNum.getCurrentRefNo(getResources().getString(R.string.salRet)));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                if (position == 2)
                    LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("TAG_RET_SUMMARY"));
                else if (position == 0)
                    LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("TAG_RET_HEADER"));
                else if (position == 1)
                    LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("TAG_RET_DETAILS"));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (status)
            viewPager.setCurrentItem(1);
    }

    @Override
    public void moveBackTo_ret(int index) {
        if (index == 0)
        {
            viewPager.setCurrentItem(0);
        }

        if (index == 1)
        {
            viewPager.setCurrentItem(1);
        }

        if (index == 2)
        {
            viewPager.setCurrentItem(2);
        }
    }

    @Override
    public void moveNextTo_ret(int index) {

        if (index == 0)
        {
            viewPager.setCurrentItem(0);
        }

        if (index == 1)
        {
            viewPager.setCurrentItem(1);
        }

        if (index == 2)
        {
            viewPager.setCurrentItem(2);
        }

    }

    private class SalesReturnPagerAdapter extends FragmentPagerAdapter {

        private final String[] titles = {"RETURN HEADER", "RETURN DETAILS", "RETURN SUMMARY"};

        public SalesReturnPagerAdapter(FragmentManager fm) {
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
                    if(salesRetrunHeader == null) salesRetrunHeader = new SalesReturnHeader();
                    return salesRetrunHeader;
                case 1:
                    if(salesReturnDetails == null) salesReturnDetails = new SalesReturnDetails();
                    return salesReturnDetails;
                case 2:
                    if(salesReturnSummary == null) salesReturnSummary = new SalesReturnSummary();
                    return salesReturnSummary;
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
