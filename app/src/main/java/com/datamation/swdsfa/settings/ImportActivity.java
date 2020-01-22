package com.datamation.swdsfa.settings;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.datamation.swdsfa.adapter.ListViewDataAdapterRestore;
import com.datamation.swdsfa.helpers.SQLiteBackUp;
import com.datamation.swdsfa.model.ContentItemBackups;
import com.datamation.swdsfa.model.Import;
import com.datamation.swdsfa.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ImportActivity extends AppCompatActivity {

    ArrayList<ContentItemBackups> objects = null;

    @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);

        objects = new ArrayList<ContentItemBackups>();

        SQLiteBackUp backUp = new SQLiteBackUp(getApplicationContext());

        List<Import> backfiles = backUp.getListOfFiles();

        //sort the list values by date
        Collections.sort(backfiles, new Comparator<Import>() {
            @Override
            public int compare(Import fruite1, Import fruite2) {

                return fruite2.getDate().compareTo(fruite1.getDate());
            }
        });

        try {

            String latestDb= backfiles.get(0).getDate();

            for (Import import1 : backfiles) {

                if(latestDb==import1.getDate()){
                    objects.add(new ContentItemBackups(import1.getDate(), import1.getFileName(), 1, "*NEW*"));
                }else{
                    objects.add(new ContentItemBackups(import1.getDate(), import1.getFileName(), 1, ""));
                }

            }
        }catch (Exception e){

        }

        ListViewDataAdapterRestore adapter = new ListViewDataAdapterRestore(this, objects);

        ListView lv = (ListView) findViewById(R.id.listView1);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view2,
                                    final int position, long id) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ImportActivity.this);

                alertDialogBuilder.setTitle("Do you want to restore backup?");
                alertDialogBuilder.setMessage("DB Name:"+objects.get(position).getFileName().toString()+"\n"+"Date :"+objects.get(position).getDate().toString());
                // set positive button: Yes message
                alertDialogBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {


                        Toast.makeText(ImportActivity.this,objects.get(position).getFileName().toString(),
                                Toast.LENGTH_SHORT).show();

                        SQLiteBackUp backUp =new SQLiteBackUp(getApplicationContext());
                        backUp.importDB(objects.get(position).getFileName().toString());

                    }
                });
                // set negative button: No message
                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // cancel the alert box and put a Toast to the user
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "You chose a negative answer",
                                Toast.LENGTH_LONG).show();
                    }
                });


                AlertDialog alertDialog = alertDialogBuilder.create();
                // show alert
                alertDialog.show();



            }
        });

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();

    }



}
