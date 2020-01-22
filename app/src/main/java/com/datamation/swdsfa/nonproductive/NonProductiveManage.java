package com.datamation.swdsfa.nonproductive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.adapter.NonProductivePagerAdapter;
import com.datamation.swdsfa.model.DayNPrdHed;


/**
 * Created by Rashmi
 */

public class NonProductiveManage extends Fragment {
    View view;
    TabLayout tabLayout;
    ViewPager viewPager;
    NonProductivePagerAdapter adapter;
    Activity activity;
    boolean status;
    private   String sp_url;
    DayNPrdHed tempNonProdHed = null;
    public static SharedPreferences localSP;
    public static final String SETTINGS = "SETTINGS";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.nonproductive_management, container, false);

        activity = getActivity();
        localSP = getActivity().getSharedPreferences(SETTINGS, 0);
        sp_url= localSP.getString("URL", "").toString();

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Customer"));  //0
        tabLayout.addTab(tabLayout.newTab().setText("Detail"));  //1
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                               @Override
                                               public void onTabSelected(TabLayout.Tab tab) {
                                                   viewPager.setCurrentItem(tab.getPosition());
                                               }

                                               @Override
                                               public void onTabUnselected(TabLayout.Tab tab) {
                                               }

                                               @Override
                                               public void onTabReselected(TabLayout.Tab tab) {
                                               }
                                           }

        );

        viewPager = (ViewPager) view.findViewById(R.id.pager);
        Bundle mBundle = getArguments();

        if (mBundle != null) {
            status = mBundle.getBoolean("Active");
            tempNonProdHed= (DayNPrdHed) mBundle.getSerializable("nonPrdHed");

            if(tempNonProdHed!=null)
                Log.d("<>********hh********","" +tempNonProdHed.getNONPRDHED_REFNO());

        }
        adapter = new NonProductivePagerAdapter(getChildFragmentManager(), 2,tempNonProdHed);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                tabLayout.getTabAt(position).select();
                  Log.d("<======>","click position "+ position);

                  if(position == 1){
                      LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent("TAG_HEADER"));
                  }else{
                      Toast.makeText(getActivity(),"SELECT CUSTOMER",Toast.LENGTH_LONG).show();
                      Log.d(NonProductiveManage.class.getSimpleName(),"I'm here");
                  }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        return view;
    }
        /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void mMoveToHeader() {
        viewPager.setCurrentItem(1);
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
}
