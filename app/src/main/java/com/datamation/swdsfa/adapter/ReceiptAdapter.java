package com.datamation.swdsfa.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.controller.ReceiptController;
import com.datamation.swdsfa.model.FddbNote;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReceiptAdapter extends ArrayAdapter<FddbNote> {
	Context context;
	ArrayList<FddbNote> list;
	boolean isSummery;
	String refno;
	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	public ReceiptAdapter(Context context, ArrayList<FddbNote> list, boolean isSummery, String RefNo) {

		super(context, R.layout.row_receipt_details, list);
		this.context = context;
		this.list = list;
		this.isSummery = isSummery;
		this.refno = RefNo;
		
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {

		LayoutInflater inflater = null;
		View row = null;
		long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
		
	
		ReceiptController reched = new ReceiptController(context);
		
		Date date,cDate;
		long txn = 0;
		long current =0;
		try {
			date = (Date)formatter.parse(list.get(position).getFDDBNOTE_TXN_DATE());
			System.out.println("receipt date is " +date.getTime());
			 txn = date.getTime();
			
		} catch (ParseException e) {
			e.printStackTrace();
		} 

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date currentDate = new Date();

			//String curDate =	dateFormat.format(reched.getChequeDate(refno));

		try {
			cDate =(Date)formatter.parse(reched.getChequeDate(refno));
			  current = cDate.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		row = inflater.inflate(R.layout.row_receipt_details, parent, false);


	
		int numOfDays =   (int) ((System.currentTimeMillis()  - txn) / DAY_IN_MILLIS);
		
		int datediff = 0;
		
		if (reched.getChequeDate(refno).length()>0){
			datediff = (int) ((current  - txn) / DAY_IN_MILLIS);
		}else{
			datediff = numOfDays;
		}
		

		
		TextView lblRefNo = (TextView) row.findViewById(R.id.row_refno);
		TextView lblTxnDate = (TextView) row.findViewById(R.id.row_txndate);
		TextView lblDueAmt = (TextView) row.findViewById(R.id.row_dueAmt);
		TextView lblAmt = (TextView) row.findViewById(R.id.row_Amt);
		TextView lblRepName = (TextView) row.findViewById(R.id.repName);
		TextView lblDays = (TextView) row.findViewById(R.id.days);
		TextView lblDatediff = (TextView) row.findViewById(R.id.dateDiff);

		lblRefNo.setText(list.get(position).getFDDBNOTE_REFNO());

		lblTxnDate.setText(list.get(position).getFDDBNOTE_TXN_DATE().toString());

		lblDays.setText(""+numOfDays);
		lblDatediff.setText(""+datediff);
		
//		if(rep.getSaleRep(list.get(position).getFDDBNOTE_REP_CODE()).equals(null)){
//			lblRepName.setText("Not Set");
//		}else{
//		lblRepName.setText(""+rep.getSaleRep(list.get(position).getFDDBNOTE_REP_CODE()));
//		}
		// if (list.get(position).getFDDBNOTE_ENTER_AMT() != null)
		// lblDueAmt.setText(String.valueOf(Double.parseDouble(list.get(position).getFDDBNOTE_TOT_BAL())));
		// else

		lblRepName.setText(""+list.get(position).getFDDBNOTE_REPNAME());
		
		if (isSummery) {
			
			if (list.get(position).getFDDBNOTE_ENTER_AMT() != null) {
				lblDueAmt.setText(String.format("%,.2f", Double.parseDouble(list.get(position).getFDDBNOTE_TOT_BAL())
						- Double.parseDouble(list.get(position).getFDDBNOTE_ENTER_AMT())));
				lblAmt.setText(String.format("%,.2f", Double.parseDouble(list.get(position).getFDDBNOTE_ENTER_AMT())));
			}

		} else {
			
			lblDueAmt.setText(String.format("%,.2f", Double.parseDouble(list.get(position).getFDDBNOTE_TOT_BAL())));
			if (list.get(position).getFDDBNOTE_ENTER_AMT() != null) {
				if (list.get(position).getFDDBNOTE_ENTER_AMT().length() > 0)
					lblAmt.setText(String.format("%,.2f", Double.parseDouble(list.get(position).getFDDBNOTE_ENTER_AMT())));
				else
					lblAmt.setText("0.00");
			}

		}

		return row;
	}
}
