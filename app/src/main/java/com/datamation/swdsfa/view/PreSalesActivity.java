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
import android.util.TypedValue;

import com.astuetz.PagerSlidingTabStrip;
import com.datamation.swdsfa.R;
import com.datamation.swdsfa.controller.OrderDetailController;
import com.datamation.swdsfa.helpers.PreSalesResponseListener;
import com.datamation.swdsfa.model.Customer;
import com.datamation.swdsfa.model.FInvRDet;
import com.datamation.swdsfa.model.FInvRHed;
import com.datamation.swdsfa.model.OrderDetail;
import com.datamation.swdsfa.model.Order;
import com.datamation.swdsfa.presale.OrderDetailFragment;
import com.datamation.swdsfa.presale.OrderHeaderFragment;
import com.datamation.swdsfa.presale.OrderReturnFragment;
import com.datamation.swdsfa.presale.OrderSummaryFragment;

public class PreSalesActivity extends AppCompatActivity implements PreSalesResponseListener{

    private OrderHeaderFragment orderHeaderFragment;
    private OrderDetailFragment orderDetailFragment;
    private OrderSummaryFragment orderSummaryFragment;
    private OrderReturnFragment orderReturnFragment;
    private ViewPager viewPager;
    public Customer selectedDebtor = null;
    public Customer selectedRetDebtor = null;
    public Order selectedPreHed = null;
    public FInvRHed selectedReturnHed = null;
    public FInvRDet selectedReturnDet = null;
    public OrderDetail selectedOrderDet = null;
    Context context;
    boolean status = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_sales);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("SALES ORDER");
        context = this;

        PagerSlidingTabStrip slidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.presale_tab_strip);
        viewPager = (ViewPager) findViewById(R.id.presale_viewpager);

     //   slidingTabStrip.setBackgroundColor(getResources().getColor(R.color.theme_color));
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
                    LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("TAG_PRE_SUMMARY"));
                else if (position == 0)
                    LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("TAG_PRE_HEADER"));
                else if (position == 1)
                    LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("TAG_PRE_DETAILS"));
//                else if (position == 3)
//                    LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("TAG_PRE_SUMMARY"));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        status = new OrderDetailController(getApplicationContext()).isAnyActiveOrders();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (status)
            viewPager.setCurrentItem(1);
    }

    private class PreSalesPagerAdapter extends FragmentPagerAdapter {

        private final String[] titles = {"HEADER", "ORDER DETAILS", "ORDER SUMMARY"};
       // private final String[] titles = {"HEADER", "ORDER DETAILS", "ORDER RETURN", "ORDER SUMMARY"};

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
                    if(orderHeaderFragment == null) orderHeaderFragment = new OrderHeaderFragment();
                    return orderHeaderFragment;
                case 1:
                    if(orderDetailFragment == null) orderDetailFragment = new OrderDetailFragment();
                    return orderDetailFragment;
//                case 2:
//                    if(orderReturnFragment == null) orderReturnFragment = new OrderReturnFragment();
//                    return orderReturnFragment;
                case 2:
                    if(orderSummaryFragment == null) orderSummaryFragment = new OrderSummaryFragment();
                    return orderSummaryFragment;
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
    public void moveBackToCustomer_pre(int index) {

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

//        if (index == 3)
//        {
//            viewPager.setCurrentItem(3);
//        }
    }

    @Override
    public void moveNextToCustomer_pre(int index) {

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

//        if (index == 3)
//        {
//            viewPager.setCurrentItem(3);
//        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
