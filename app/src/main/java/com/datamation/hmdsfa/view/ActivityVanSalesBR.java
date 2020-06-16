package com.datamation.hmdsfa.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.TypedValue;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.barcode.BRInvoiceDetailFragment;
import com.datamation.hmdsfa.barcode.BRInvoiceHeaderFragment;
import com.datamation.hmdsfa.barcode.BRInvoiceSummaryFragment;
import com.datamation.hmdsfa.controller.InvDetController;
import com.datamation.hmdsfa.controller.InvoiceDetBarcodeController;
import com.datamation.hmdsfa.helpers.VanSalesResponseListener;
import com.datamation.hmdsfa.model.Customer;
import com.datamation.hmdsfa.model.FInvRDet;
import com.datamation.hmdsfa.model.FInvRHed;
import com.datamation.hmdsfa.model.Order;
import com.datamation.hmdsfa.model.OrderDetail;
import com.datamation.hmdsfa.presale.OrderReturnFragment;

public class ActivityVanSalesBR extends AppCompatActivity implements VanSalesResponseListener {

    private BRInvoiceHeaderFragment orderHeaderFragment;
    private BRInvoiceDetailFragment orderDetailFragment;
    private BRInvoiceSummaryFragment orderSummaryFragment;
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
        setTitle("INVOICE BARCODE");
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

        status = new InvDetController(getApplicationContext()).isAnyActiveOrders();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (status)
            viewPager.setCurrentItem(1);
    }


    private class PreSalesPagerAdapter extends FragmentPagerAdapter {

        //private final String[] titles = {"HEADER", "ORDER DETAILS", "ORDER SUMMARY"};
        private final String[] titles = {"INVOICE HEADER", "INVOICE DETAILS", "INVOICE SUMMARY"};

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
                    if(orderHeaderFragment == null) orderHeaderFragment = new BRInvoiceHeaderFragment();
                    return orderHeaderFragment;
                case 1:
                    if(orderDetailFragment == null) orderDetailFragment = new BRInvoiceDetailFragment();
                    return orderDetailFragment;
//                case 2:
//                    if(orderReturnFragment == null) orderReturnFragment = new OrderReturnFragment();
//                    return orderReturnFragment;
                case 2:
                    if(orderSummaryFragment == null) orderSummaryFragment = new BRInvoiceSummaryFragment();
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
    public void moveBackToCustomer(int index) {

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

        if (index == 3)
        {
            viewPager.setCurrentItem(3);
        }
    }

    @Override
    public void moveNextToCustomer(int index) {

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

        if (index == 3)
        {
            viewPager.setCurrentItem(3);
        }
    }


    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Toast.makeText(this,"Back button disabled until finish transaction",Toast.LENGTH_SHORT).show();
    }
}
