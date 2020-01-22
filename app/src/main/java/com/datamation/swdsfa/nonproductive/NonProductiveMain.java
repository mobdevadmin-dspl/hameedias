package com.datamation.swdsfa.nonproductive;

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


import com.datamation.swdsfa.R;
import com.datamation.swdsfa.adapter.NonPrdHeader;
import com.datamation.swdsfa.controller.DayNPrdDetController;
import com.datamation.swdsfa.controller.DayNPrdHedController;
import com.datamation.swdsfa.model.DayNPrdHed;
import com.datamation.swdsfa.settings.ReferenceNum;
import com.datamation.swdsfa.utils.UtilityContainer;
import com.datamation.swdsfa.fragment.FragmentHome;

import java.util.ArrayList;

public class NonProductiveMain extends Fragment {
    View view;
    ListView lv_invent_load;
    ArrayList<DayNPrdHed> loadlist;
    ReferenceNum referenceNum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.non_productive_main, container, false);
        setHasOptionsMenu(true);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        getActivity().setTitle("Non productive");
        referenceNum = new ReferenceNum(getActivity());
        lv_invent_load = (ListView) view.findViewById(R.id.lvPhonenonphedlist);
        registerForContextMenu(lv_invent_load);
        fatchData();
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
                lv_invent_load.clearTextFilter();
                loadlist = new DayNPrdHedController(getActivity()).getAllnonprdHedDetails(newText);
                lv_invent_load.setAdapter(new NonPrdHeader(getActivity(), loadlist));
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.enterNewItem) {
            //UtilityContainer.mLoadFragment(new NonProductiveDetail(), getActivity());
            UtilityContainer.mLoadFragment(new NonProductiveManage(), getActivity());
        } else if (item.getItemId() == R.id.exitExpence) {
            UtilityContainer.mLoadFragment(new FragmentHome(), getActivity());
        }
        return super.onOptionsItemSelected(item);
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.lvPhonenonphedlist) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_list, menu);
        }
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        DayNPrdHed daynPrdHed = loadlist.get(info.position);
        switch (item.getItemId()) {
            case R.id.cancel:

                if (new DayNPrdHedController(getActivity()).isEntrySynced(daynPrdHed.getNONPRDHED_REFNO())) {
                    Toast.makeText(getActivity(), "Synced entry. Unable to delete.", Toast.LENGTH_LONG).show();
                } else {
                    int count = new DayNPrdHedController(getActivity()).undoOrdHedByID(daynPrdHed.getNONPRDHED_REFNO());
                    if (count > 0) {
                        new DayNPrdDetController(getActivity()).OrdDetByRefno(daynPrdHed.getNONPRDHED_REFNO());
                        fatchData();
                        Toast.makeText(getActivity(), "Deleted successfully.", Toast.LENGTH_LONG).show();
                    }

                }
                return true;

            case R.id.print:

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void fatchData() {
        try {
            lv_invent_load.setAdapter(null);
            loadlist = new DayNPrdHedController(getActivity()).getAllnonprdHedDetails("");
            if (loadlist.size() > 0)
                lv_invent_load.setAdapter(new NonPrdHeader(getActivity(), loadlist));

        } catch (NullPointerException e) {
            Log.v("Loading Error", e.toString());
        }
    }

}
