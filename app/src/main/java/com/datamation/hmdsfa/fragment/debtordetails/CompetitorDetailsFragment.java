package com.datamation.hmdsfa.fragment.debtordetails;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datamation.hmdsfa.R;


public class CompetitorDetailsFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_competitor_details, container, false);
        //view = inflater.inflate(R.layout.test_constraint, container, false);

        return view;
    }

}
