package com.datamation.swdsfa.fragment.debtordetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.adapter.Last3InvoiceAdapter;
import com.datamation.swdsfa.controller.FInvhedL3Controller;
import com.datamation.swdsfa.helpers.NetworkFunctions;
import com.datamation.swdsfa.helpers.SharedPref;
import com.datamation.swdsfa.model.FInvhedL3;
import com.datamation.swdsfa.model.Last3Invoice;

import java.util.ArrayList;


public class HistoryDetailsFragment extends Fragment
{

    private Last3InvoiceAdapter l3Adapter;
    private RecyclerView rv;
    private TextView tvInvoice1,tvInvoice2,tvInvoice3;
    SharedPref pref;
    NetworkFunctions networkFunctions;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_last_three_invoices, container, false);

        rv = view.findViewById(R.id.rv_report);

        tvInvoice1 = view.findViewById(R.id.tvInvoice1);
        tvInvoice2 = view.findViewById(R.id.tvInvoice2);
        tvInvoice3 = view.findViewById(R.id.tvInvoice3);

        rv.setNestedScrollingEnabled(false);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());

        ArrayList<Last3Invoice> last3InvList =  new FInvhedL3Controller(getActivity()).getLast3InvoiceDetails(SharedPref.getInstance(getActivity()).getSelectedDebCode());
        ArrayList<FInvhedL3> FInvhedL3ArrayList = new FInvhedL3Controller(getActivity()).getLast3InvoiceHed(SharedPref.getInstance(getActivity()).getSelectedDebCode());

        for (int i = 0; i < FInvhedL3ArrayList.size(); i++) {
            if(i==0)
                tvInvoice1.setText(FInvhedL3ArrayList.get(i).getFINVHEDL3_REF_NO1()+" - "+FInvhedL3ArrayList.get(i).getFINVHEDL3_TXN_DATE());
                // tvInvoice1.setText(FInvhedL3ArrayList.get(i).getFINVHEDL3_REF_NO1()+" - "+new DateTimeFormatings().getDateFormat(FInvhedL3ArrayList.get(i).getFINVHEDL3_TXN_DATE()));
            else if(i==1)
                tvInvoice2.setText(FInvhedL3ArrayList.get(i).getFINVHEDL3_REF_NO1()+" - "+FInvhedL3ArrayList.get(i).getFINVHEDL3_TXN_DATE());
                // tvInvoice2.setText(FInvhedL3ArrayList.get(i).getFINVHEDL3_REF_NO1()+" - "+new DateTimeFormatings().getDateFormat(FInvhedL3ArrayList.get(i).getFINVHEDL3_TXN_DATE()));
            else if(i==2)
                tvInvoice3.setText(FInvhedL3ArrayList.get(i).getFINVHEDL3_REF_NO1()+" - "+FInvhedL3ArrayList.get(i).getFINVHEDL3_TXN_DATE());
            // tvInvoice3.setText(FInvhedL3ArrayList.get(i).getFINVHEDL3_REF_NO1()+" - "+new DateTimeFormatings().getDateFormat(FInvhedL3ArrayList.get(i).getFINVHEDL3_TXN_DATE()));
        }

        l3Adapter = new Last3InvoiceAdapter(last3InvList);
        rv.setAdapter(l3Adapter);

        return view;
    }

}
