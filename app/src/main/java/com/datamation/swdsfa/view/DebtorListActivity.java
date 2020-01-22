package com.datamation.swdsfa.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.datamation.swdsfa.R;
import com.datamation.swdsfa.customer.AddNewCusRegistration;
import com.datamation.swdsfa.customer.NewCustomerActivity;
import com.datamation.swdsfa.fragment.debtordetails.CompetitorDetailsFragment;
import com.datamation.swdsfa.fragment.debtordetails.HistoryDetailsFragment;
import com.datamation.swdsfa.fragment.debtordetails.OutstandingDetailsFragment;
import com.datamation.swdsfa.fragment.debtordetails.PersonalDetailsFragment;
import com.datamation.swdsfa.fragment.debtorlist.AllCustomerFragment;
import com.datamation.swdsfa.fragment.debtorlist.RouteCustomerFragment;
import com.datamation.swdsfa.helpers.SharedPref;
import com.datamation.swdsfa.presale.OrderHeaderFragment;
import com.datamation.swdsfa.utils.GPSTracker;
import com.datamation.swdsfa.utils.UtilityContainer;

import at.markushi.ui.CircleButton;

public class DebtorListActivity extends AppCompatActivity {

    private CircleButton fabNewCust;

    private RouteCustomerFragment routeCustomerFragment;
    private AllCustomerFragment allCustomerFragment;

    double latitude = 0.0;
    double longitude = 0.0;
    private Context context;
    private Location myLocation;
    private Location debLocation;

    //GPSTracker gpsTracker;
    SharedPref mSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debtor_list);

        context = this;
        mSharedPref = SharedPref.getInstance(context);

        fabNewCust = (CircleButton) findViewById(R.id.outlet_details_fab_add_new_cus);
        fabNewCust.setColor(ContextCompat.getColor(this, R.color.theme_color));
        fabNewCust.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.addusr));

        Toolbar toolbar = (Toolbar) findViewById(R.id.debtor_list_toolbar);
        TextView title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        ImageView backIc = (ImageView) toolbar.findViewById(R.id.back);
        //Switch gpsSw = (Switch)toolbar.findViewById(R.id.gps_switch);
        title.setText("DEALER LIST");
        backIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DebtorListActivity.this, ActivityHome.class);
                startActivity(intent);
                finish();
            }
        });
        PagerSlidingTabStrip slidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.debtor_list_tab_strip);
        ViewPager viewPager = (ViewPager) findViewById(R.id.debtor_list_viewpager);

        //  slidingTabStrip.setBackgroundColor(getResources().getColor(R.color.theme_color));
        slidingTabStrip.setTextColor(getResources().getColor(android.R.color.black));
        slidingTabStrip.setIndicatorColor(getResources().getColor(R.color.colorPrimaryDark));
        slidingTabStrip.setDividerColor(getResources().getColor(R.color.half_black));

        DebtorListActivity.DebtorListPagerAdapter adapter = new DebtorListActivity.DebtorListPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        viewPager.setPageMargin(pageMargin);
        slidingTabStrip.setViewPager(viewPager);

        fabNewCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewCustomerActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        gpsSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b)
//                {
//                    gpsTracker = new GPSTracker(context);
//
//                    if (gpsTracker.canGetLocation())
//                    {
//                        mSharedPref.setGPSSwitched(true);
//                        Log.d("DEBTOR_LIST", "IS_SWITCHED" + mSharedPref.isGPSSwitched());
//                    }
//                }
//                else
//                {
//                    Log.d("Checked", "Switched OFF");
//                }
//            }
//        });


    }

    private class DebtorListPagerAdapter extends FragmentPagerAdapter {

        private final String[] titles = {"ROUTE DEALERS", "ALL DEALERS"};

        public DebtorListPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (routeCustomerFragment == null)
                        routeCustomerFragment = new RouteCustomerFragment();
                    return routeCustomerFragment;
                case 1:
                    if (allCustomerFragment == null)
                        allCustomerFragment = new AllCustomerFragment();
                    return allCustomerFragment;
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
        Toast.makeText(context, "Back options disabled from here, Please try to back using back icon", Toast.LENGTH_LONG).show();
        //super.onBackPressed();

    }
}
