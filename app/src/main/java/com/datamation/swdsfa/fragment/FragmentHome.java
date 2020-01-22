package com.datamation.swdsfa.fragment;




import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.datamation.swdsfa.R;
import com.datamation.swdsfa.view.dashboard.DaySummaryFragment;
import com.datamation.swdsfa.view.dashboard.InvoiceDetailsFragmentNew;
import com.datamation.swdsfa.view.dashboard.MainDashboardFragment;
import com.datamation.swdsfa.view.dashboard.OrderDetailsFragment;
import com.datamation.swdsfa.view.dashboard.ReportFragment;
import com.datamation.swdsfa.view.dashboard.PaymentDetailsFragment;
import com.datamation.swdsfa.view.dashboard.PromotionDetailsFragment;
import com.datamation.swdsfa.view.dashboard.TransactionDetailsFragment;


public class FragmentHome extends Fragment {
    public Fragment currentFragment;

    private MainDashboardFragment mainDashboardFragment;
    private DaySummaryFragment daySummaryFragment;
    private InvoiceDetailsFragmentNew invoiceDetailsFragment;
    private PaymentDetailsFragment paymentDetailsFragment;
    private OrderDetailsFragment orderDetailsFragment;
    private TransactionDetailsFragment transactionDetailsFragment;
    private PromotionDetailsFragment promoDetailsFragment;
    private ReportFragment otherDetailsFragment;


    private ViewPager viewPager;
    private DashboardPagerAdapter pagerAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, container, false);
       // ImageButton imgbtnCalendar = (ImageButton) view.findViewById(R.id.dashboard_toolbar_icon_calendar);

//        calendarDatePickerDialog = new CalendarDatePickerDialog();
//
//        calendarDatePickerDialog.setOnDateSetListener(new CalendarDatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(CalendarDatePickerDialog calendarDatePickerDialog, int year, int month, int day) {
//                Calendar calendar = Calendar.getInstance();
//                calendar.set(year, month, day);
//
//                thisDay = day;
//
//                long millis = calendar.getTimeInMillis();
//
////                new GetDashboardData(millis).execute();
//                try {
//                    setDashboardDetails(millis);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getActivity(), "Error loading data. Please try again", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });

//        imgbtnCalendar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (currentFragment != null) {
//                    if (currentFragment instanceof MainDashboardFragment) {
//                        ((MainDashboardFragment) currentFragment).showCalendar();
//                    } else if (currentFragment instanceof DaySummaryFragment) {
//                        ((DaySummaryFragment) currentFragment).showCalendar();
//                    } else if (currentFragment instanceof InvoiceDetailsFragment) {
//                        ((InvoiceDetailsFragment) currentFragment).showCalendar();
//                    } else if (currentFragment instanceof PaymentDetailsFragment) {
//                        ((PaymentDetailsFragment) currentFragment).showCalendar();
//                    }
//                }
//            }
//        });

       // ImageButton imgbtnSync = (ImageButton) view.findViewById(R.id.dashboard_toolbar_icon_sync);
//        imgbtnSync.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (currentFragment != null) {
//                    if (currentFragment instanceof MainDashboardFragment) {
//                        ((MainDashboardFragment) currentFragment).sync();
//                    } else if (currentFragment instanceof DaySummaryFragment) {
//                        ((DaySummaryFragment) currentFragment).refresh();
//                    } else if (currentFragment instanceof InvoiceDetailsFragment) {
//                        ((InvoiceDetailsFragment) currentFragment).refresh();
//                    } else if (currentFragment instanceof PaymentDetailsFragment) {
//                        ((PaymentDetailsFragment) currentFragment).refresh();
//                    }
//                }
//            }
//        });

        viewPager = (ViewPager) view.findViewById(R.id.dashboard_vp);
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.dashboard_tab_strip);

        pagerAdapter = new DashboardPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        Resources resources = getResources();

      //  tabStrip.setBackgroundColor(resources.getColor(R.color.theme_color));
        tabStrip.setTextColor(resources.getColor(android.R.color.black));
        tabStrip.setIndicatorColor(resources.getColor(R.color.blue_c));
        tabStrip.setDividerColor(resources.getColor(R.color.half_black));
        tabStrip.setViewPager(viewPager);
        tabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                             @Override
                                             public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                             }

                                             @Override
                                             public void onPageSelected(int position) {

                                             }

                                             @Override
                                             public void onPageScrollStateChanged(int state) {

                                             }
                                         });

//        new ViewPager.SimpleOnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
////                IOnDashboardFragmentVisibleListener listener = (IOnDashboardFragmentVisibleListener) pagerAdapter.instantiateItem(viewPager, position);
////                if (listener != null) {
////                    listener.onFragmentVisible(DashboardContainerFragment.this);
////                }
//            }
//        });


//        Pie pie = AnyChart.pie();
//        Pie bar = AnyChart.pie();
//
//        List<DataEntry> data = new ArrayList<>();
//        data.add(new ValueDataEntry("John", 10000));
//        data.add(new ValueDataEntry("Jake", 12000));
//        data.add(new ValueDataEntry("Peter", 18000));
//        bar.setData(data);
//        List<DataEntry> data1 = new ArrayList<>();
//        data1.add(new ValueDataEntry("John", 10000));
//        data1.add(new ValueDataEntry("Jake", 12000));
//        data1.add(new ValueDataEntry("Peter", 18000));
//        pie.setData(data1);
//        AnyChartView anyChartView = (AnyChartView) view.findViewById(R.id.any_chart_view);
//        AnyChartView anyChartView2 = (AnyChartView) view.findViewById(R.id.any_chart_view2);
                //anyChartView.setChart(bar);
                //anyChartView2.setChart(pie);


        return view;
    }
    private class DashboardPagerAdapter extends FragmentPagerAdapter {

      //  private String[] titles = {"Main", "Summary","Promotion","Order", "Invoice", "Payment"};
      //  private String[] titles = {"Main", "Summary", "Transactions", "Promotion", "Payment", "Reports"};
        private String[] titles = {"Main", "Summary", "Today Sale", "Promotion", "Reports"};

        public DashboardPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (mainDashboardFragment == null)
                        mainDashboardFragment = MainDashboardFragment.newInstance();
                    currentFragment = mainDashboardFragment;
                    return mainDashboardFragment;
                case 1:
                    if (daySummaryFragment == null) daySummaryFragment = new DaySummaryFragment();
                    return daySummaryFragment;
                case 2:
                    if(transactionDetailsFragment == null) transactionDetailsFragment = new TransactionDetailsFragment();
                    return transactionDetailsFragment;
//                case 3:
//                    if(invoiceDetailsFragment == null) invoiceDetailsFragment = new InvoiceDetailsFragmentNew();
//                    return invoiceDetailsFragment;
                case 3:
                    if(promoDetailsFragment == null) promoDetailsFragment = new PromotionDetailsFragment();
                    return promoDetailsFragment;
//                case 4:
//                    if(paymentDetailsFragment == null) paymentDetailsFragment = new PaymentDetailsFragment();
//                    return paymentDetailsFragment;
                case 4:
                    if(otherDetailsFragment == null) otherDetailsFragment = new ReportFragment();
                    return otherDetailsFragment;
//
//                case 4:
//
//if(promoDetailsFragment == null) promoDetailsFragment = new PromotionDetailsFragment();
////                    return promoDetailsFragment;
//                case 5:
//
//                    if(orderDetailsFragment == null) orderDetailsFragment = new OrderDetailsFragment();
//                    return invoiceDetailsFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
