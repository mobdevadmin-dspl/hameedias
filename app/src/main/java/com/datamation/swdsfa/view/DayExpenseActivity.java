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
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.datamation.swdsfa.R;
import com.datamation.swdsfa.expense.ExpenseDetail;
import com.datamation.swdsfa.salesreturn.SalesReturnDetails;
import com.datamation.swdsfa.salesreturn.SalesReturnHeader;
import com.datamation.swdsfa.salesreturn.SalesReturnSummary;

public class DayExpenseActivity extends AppCompatActivity {

    ViewPager viewPager;
    private ExpenseDetail expenseDetail;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_expense);

        Toolbar toolbar = (Toolbar) findViewById(R.id.de_toolbar);
        TextView title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        title.setText("EXPENSE DETAILS");
        context = this;

        PagerSlidingTabStrip slidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.de_tab_strip);
        viewPager = (ViewPager) findViewById(R.id.de_viewpager);

     //   slidingTabStrip.setBackgroundColor(getResources().getColor(R.color.theme_color));
//        slidingTabStrip.setTextColor(getResources().getColor(android.R.color.black));
//        slidingTabStrip.setIndicatorColor(getResources().getColor(R.color.red_error));
//        slidingTabStrip.setDividerColor(getResources().getColor(R.color.half_black));

        DEPagerAdapter adapter = new DEPagerAdapter(getSupportFragmentManager());
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

                if (position == 0)
                    LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("TAG_DE_DETAILS"));

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private class DEPagerAdapter extends FragmentPagerAdapter {

        private final String[] titles = {"EXPENSE DETAILS"};

        public DEPagerAdapter(FragmentManager fm) {
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
                    if(expenseDetail == null) expenseDetail = new ExpenseDetail();
                    return expenseDetail;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }
}
