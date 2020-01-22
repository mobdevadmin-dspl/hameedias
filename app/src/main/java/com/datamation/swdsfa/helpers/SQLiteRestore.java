package com.datamation.swdsfa.helpers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.adapter.ListViewDataAdapterRestore;
import com.datamation.swdsfa.model.ContentItemBackups;
import com.datamation.swdsfa.model.Import;
import com.datamation.swdsfa.utils.UtilityContainer;
import com.datamation.swdsfa.fragment.FragmentTools;


public class SQLiteRestore extends Fragment {

    ArrayList<ContentItemBackups> objects = null;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sqlite_restore, container, false);

        //Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //getActivity().setSupportActionBar(toolbar);
        getActivity().setTitle("SQLite Restore");
        //toolbar.setLogo(R.drawable.dm_logo_64);
        setHasOptionsMenu(true);

        objects = new ArrayList<ContentItemBackups>();
        SQLiteBackUp backUp = new SQLiteBackUp(getActivity());
        List<Import> backfiles = backUp.getListOfFiles();
        Collections.sort(backfiles, new Comparator<Import>() {

            @Override
            public int compare(Import fruite1, Import fruite2) {
                return fruite2.getDate().compareTo(fruite1.getDate());
            }
        });

        try {

            String latestDb = backfiles.get(0).getDate();

            for (Import import1 : backfiles) {

                if (latestDb == import1.getDate()) {
                    objects.add(new ContentItemBackups(import1.getDate(), import1.getFileName(), 1, "*NEW*"));
                } else {
                    objects.add(new ContentItemBackups(import1.getDate(), import1.getFileName(), 1, ""));
                }
            }
        } catch (Exception e) {

        }

        ListViewDataAdapterRestore adapter = new ListViewDataAdapterRestore(getActivity(), objects);
        ListView lv = (ListView) view.findViewById(R.id.listView1);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view2, final int position, long id) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("Do you want to restore backup?");
                alertDialogBuilder.setMessage("DB Name:" + objects.get(position).getFileName().toString() + "\n" + "Date :" + objects.get(position).getDate().toString());

                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        Toast.makeText(getActivity(), objects.get(position).getFileName().toString(),
                                Toast.LENGTH_SHORT).show();

                        SQLiteBackUp backUp = new SQLiteBackUp(getActivity());
                        backUp.importDB(objects.get(position).getFileName().toString());

                    }
                });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.close:
                UtilityContainer.mLoadFragment(new FragmentTools(),getActivity());
//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.replace(R.id.main_container, new FragmentTools());
//                ft.addToBackStack(null);
//                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                ft.commit();
                return true;
        }
        return false;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        for (int i = 0; i < menu.size(); ++i) {
            menu.removeItem(menu.getItem(i).getItemId());
        }

        inflater.inflate(R.menu.mnu_close, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


}
