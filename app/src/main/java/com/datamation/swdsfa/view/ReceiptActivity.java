package com.datamation.swdsfa.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.datamation.swdsfa.R;
import com.datamation.swdsfa.controller.ReceiptDetController;
import com.datamation.swdsfa.helpers.ReceiptResponseListener;
import com.datamation.swdsfa.helpers.VanSalesResponseListener;
import com.datamation.swdsfa.model.Customer;
import com.datamation.swdsfa.model.ReceiptHed;
import com.datamation.swdsfa.receipt.ReceiptDetails;
import com.datamation.swdsfa.receipt.ReceiptHeader;
import com.datamation.swdsfa.receipt.ReceiptSummary;

public class ReceiptActivity extends AppCompatActivity implements ReceiptResponseListener{
    private ReceiptHeader headerFragment;
    private ReceiptDetails detailFragment;
    private ReceiptSummary salesManagementFragment;

    private ViewPager viewPager;
    public Customer selectedDebtor = null;
    public ReceiptHed selectedRecHed = null;
    public Double ReceivedAmt = 0.0;
    Context context;
    boolean status = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_sales);
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("RECEIPT");

        PagerSlidingTabStrip slidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.presale_tab_strip);
        viewPager = (ViewPager) findViewById(R.id.presale_viewpager);

       // slidingTabStrip.setBackgroundColor(getResources().getColor(R.color.theme_color));
        slidingTabStrip.setTextColor(getResources().getColor(android.R.color.black));
        slidingTabStrip.setIndicatorColor(getResources().getColor(R.color.colorPrimaryDark));
        slidingTabStrip.setDividerColor(getResources().getColor(R.color.half_black));

        PreSalesPagerAdapter adapter = new PreSalesPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        viewPager.setPageMargin(pageMargin);
        slidingTabStrip.setViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                if (position == 2)
                    LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("TAG_SUMMARY"));
                else if (position == 0)
                    LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("TAG_HEADER"));
                else if (position == 1)
                    LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("TAG_DETAILS"));

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        status = new ReceiptDetController(getApplicationContext()).isAnyActiveReceipt();
    }

    private class PreSalesPagerAdapter extends FragmentPagerAdapter {

        private final String[] titles = {"HEADER", "RECEIPT DETAILS", "RECEIPT SUMMARY"};

        public PreSalesPagerAdapter(FragmentManager fm) {
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
                    if(headerFragment == null) headerFragment = new ReceiptHeader();
                    return headerFragment;
                case 1:
                    if(detailFragment == null) detailFragment = new ReceiptDetails();
                    return detailFragment;
                case 2:
                    if(salesManagementFragment == null) salesManagementFragment = new ReceiptSummary();
                    return salesManagementFragment;
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
    public void moveToDetailsRece(int index) {

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
    protected void onStart() {
        super.onStart();
        if (status)
            viewPager.setCurrentItem(1);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Toast.makeText(this,"Back button disabled until finish transaction",Toast.LENGTH_SHORT).show();
    }
}
