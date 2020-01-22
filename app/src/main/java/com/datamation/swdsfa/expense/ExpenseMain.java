package com.datamation.swdsfa.expense;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;


import com.datamation.swdsfa.adapter.SalesExpenseHeader;
import com.datamation.swdsfa.R;
import com.datamation.swdsfa.settings.ReferenceNum;
import com.datamation.swdsfa.controller.DayExpDetController;
import com.datamation.swdsfa.controller.DayExpHedController;
import com.datamation.swdsfa.model.DayExpHed;
import com.datamation.swdsfa.view.ActivityHome;

import java.util.ArrayList;

public class ExpenseMain extends Fragment {
    View view;
    ListView lv_expnes_load;
    ArrayList<DayExpHed> loadexplist;
    ReferenceNum referenceNum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.expense_main, container, false);
        lv_expnes_load = (ListView) view.findViewById(R.id.lvexpenselist);
        referenceNum = new ReferenceNum(getActivity());

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //((MainActivity) getActivity()).setSupportActionBar(toolbar);
        getActivity().setTitle("Expenses");
       // toolbar.setLogo(R.drawable.dm_logo_64);
        setHasOptionsMenu(true);
        fatchData();
        /* connect context menu with listview for long click */
        registerForContextMenu(lv_expnes_load);
        return view;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        for (int i = 0; i < menu.size(); ++i) {
            menu.removeItem(menu.getItem(i).getItemId());
        }

        inflater.inflate(R.menu.frag_nonprd_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchItems).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                lv_expnes_load.clearTextFilter();
                loadexplist = new DayExpHedController(getActivity()).getAllExpHedDetails(newText);
                lv_expnes_load.setAdapter(new SalesExpenseHeader(getActivity(), loadexplist));
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.lvexpenselist) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_discard, menu);
        }
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.enterNewItem) {
            //UtilityContainer.mLoadFragment(new ExpenseDetail(), getActivity());
        } else if (item.getItemId() == R.id.exitExpence) {
            //UtilityContainer.mLoadFragment(new IconPallet_mega(), getActivity());
            Intent intent = new Intent(getActivity(), ActivityHome.class);
            startActivity(intent);
            getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    // Long Press on Selected item
   @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        DayExpHed dayHed = loadexplist.get(info.position);
        switch (item.getItemId()) {
            case R.id.mnuDiscard:
                deleteDialog(getActivity(), dayHed.getEXP_REFNO());
                return true;


            default:
                return super.onContextItemSelected(item);
        }
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*/

    public void fatchData() {
        try {
            lv_expnes_load.setAdapter(null);
            loadexplist = new DayExpHedController(getActivity()).getAllExpHedDetails("");
            if (loadexplist.size() > 0)
                lv_expnes_load.setAdapter(new SalesExpenseHeader(getActivity(), loadexplist));
        } catch (NullPointerException e) {
            Log.v("Expense Error", e.toString());
        }
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*/

    private void deleteDialog(final Context context, final String refno) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Are you sure you want to cancel this entry?");
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (new DayExpHedController(getActivity()).isEntrySynced(refno))
                    Toast.makeText(getActivity(), "Synced entry. Unable to delete.", Toast.LENGTH_LONG).show();
                else {
                    int count = new DayExpHedController(getActivity()).restDataExp(refno);
                    if (count > 0) {
                        new DayExpDetController(getActivity()).restDataExp(refno);
                        fatchData();
                    }
                }
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }

}
