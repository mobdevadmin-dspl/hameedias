package com.datamation.swdsfa.customer;



import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.datamation.swdsfa.adapter.NewCustomerAdapter;
import com.datamation.swdsfa.controller.NewCustomerController;
import com.datamation.swdsfa.utils.UtilityContainer;
import com.datamation.swdsfa.view.ActivityHome;
import com.datamation.swdsfa.model.NewCustomer;
import com.datamation.swdsfa.R;

import java.util.ArrayList;

/**
 * Created by Dhanushika on 4/4/2018.
 */

public class CustomerRegMain extends Fragment {
    private ListView listView_cusDet;
    private View view;
    private ArrayList<NewCustomer> customerArrayList;
    private FloatingActionButton fab;
    String btnType = "U";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cus_reg_main, container, false);

        listView_cusDet = (ListView) view.findViewById(R.id.lvCuslist);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().setTitle("Create New Customer");

                if (btnType.equals("N")) {
                    // fab.setImageResource(R.drawable.tick);
                    btnType = "U";
                    fatchData("N");
                } else {
                    // fab.setImageResource(R.drawable.cross);
                    UtilityContainer.mLoadFragment(new AddNewCusRegistration(), getActivity());
//                    Fragment newCustomer = new AddNewCusRegistration();
//                    getFragmentManager().beginTransaction().replace(
//                            R.id.fragmentContainer, newCustomer)
//                            .commit();

                    btnType = "N";
                }
            }
        });
        fatchData("N");
         /* connect context menu with listview for long click */
        registerForContextMenu(listView_cusDet);

        listView_cusDet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewCustomer newCustomer = customerArrayList.get(position);
                deleteDialog(getActivity(), newCustomer.getCUSTOMER_ID());
            }
        });


        //DISABLED BACK NAVIGATION
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("", "keyCode: " + keyCode);
                ActivityHome.navigation.setVisibility(View.VISIBLE);

                if( keyCode == KeyEvent.KEYCODE_BACK ) {
                    Toast.makeText(getActivity(), "Back button disabled!", Toast.LENGTH_SHORT).show();
                    return true;
                }else if ((keyCode == KeyEvent.KEYCODE_HOME)) {

                    getActivity().finish();

                    return true;

                } else {
                    return false;
                }
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        for (int i = 0; i < menu.size(); ++i) {
            menu.removeItem(menu.getItem(i).getItemId());
        }

//            inflater.inflate(R.menu.frag_nonprd_menu, menu);
//            SearchView searchView = (SearchView) menu.findItem(R.id.searchItems).getActionView();
//            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String newText) {
//                    listView_cusDet.clearTextFilter();
//                    customerArrayList=new NewCustomerDS(getActivity()).getAllNewCusDetails(newText,btnType.equals("N") ? "U" : "N");
//                  //  listView_cusDet.setAdapter(new NewCustomerAdapter(getActivity(),customerArrayList));
//
//                    return false;
//                }
//            });

        super.onCreateOptionsMenu(menu, inflater);
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
//
//            if (v.getId() == R.id.lvexpenselist) {
//                MenuInflater inflater = getActivity().getMenuInflater();
//                inflater.inflate(R.menu.menu_list, menu);
//            }
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//            if (item.getItemId() == R.id.enterNewItem) {
//                UtilityContainer.mLoadFragment(new AddNewCusRegistration(), getActivity());
//            } else if (item.getItemId() == R.id.exitExpence) {
//                UtilityContainer.mLoadFragment(new IconPallet(), getActivity());
//            }
        return super.onOptionsItemSelected(item);
    }

    public void fatchData(String param) {

        listView_cusDet.clearTextFilter();
        customerArrayList = new NewCustomerController(getActivity()).getAllNewCusDetails("", param);
        if(customerArrayList.size()==0){
            this.listView_cusDet.setEmptyView(view.findViewById(android.R.id.empty));
        }else{
            listView_cusDet.setAdapter(new NewCustomerAdapter(getActivity(), customerArrayList));
        }

    }


    private void deleteDialog(final Context context, final String refno) {

        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Are you sure you want to cancel this entry?");
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (new NewCustomerController(getActivity()).isEntrySynced(refno))
                    Toast.makeText(getActivity(), "Synced entry. Unable to delete.", Toast.LENGTH_LONG).show();
                else {
                    int count = new NewCustomerController(getActivity()).deleteRecord(refno);
                    if (count > 0) {
                        new NewCustomerController(getActivity()).deleteRecord(refno);
                        fatchData("");
                    }
                }
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        android.support.v7.app.AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }
}
