package com.datamation.swdsfa.fragment.debtordetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datamation.swdsfa.R;


public class CompetitorDetailsFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_competitor_details, container, false);
        //view = inflater.inflate(R.layout.test_constraint, container, false);

        return view;
    }

}
