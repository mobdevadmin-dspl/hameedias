package com.datamation.swdsfa.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.model.Import;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class SQLiteBackUp {

    String PACKAGE_NAME;
    String DATABASE_NAME;
    String APP_NAME;
    Context context;

    public SQLiteBackUp (Context context){
        this.context = context;

        PACKAGE_NAME = context.getPackageName();
        DATABASE_NAME = context.getResources().getString(R.string.DATABASE_NAME);
        APP_NAME = context.getResources().getString(R.string.app_name_new);

    }

    @SuppressWarnings("resource")
    public void importDB(String name) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            if (sd.canWrite()) {
                String currentDBPath = "//data//" + PACKAGE_NAME+ "//databases//" + DATABASE_NAME+"";
                String backupDBPath ="//SwadeshiSFA//"+name; // From SD directory.
                File backupDB = new File(data, currentDBPath);
                File currentDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(context, "Import Successful!",  Toast.LENGTH_SHORT).show();

            }

        } catch (Exception e) {
            Log.v("Error ", e.toString());
            Toast.makeText(context, "Import Failed!", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("resource")
    public void exportDB() {
        try {

            File folder = new File(Environment.getExternalStorageDirectory() + "/" + APP_NAME);
            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdir();
            }
            if (success) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                Date date = new Date();
                System.out.println(dateFormat.format(date));

                File sd = Environment.getExternalStorageDirectory();
                File data = Environment.getDataDirectory();

                if (sd.canWrite()) {
                    String currentDBPath = "//data//" + PACKAGE_NAME
                            + "//databases//" + DATABASE_NAME;
                    String backupDBPath = "//SwadeshiSFA//backupname_"+dateFormat.format(date).toString()+".db"; // From SD directory.
                    File currentDB = new File(data, currentDBPath);
                    File backupDB = new File(sd, backupDBPath);

                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    Toast.makeText(context, "Backup Successful!",Toast.LENGTH_SHORT).show();

                }
            }else {
                // Do something else on failure
                Toast.makeText(context, "Backup Failed!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

            Log.e("error", e.toString());
            Toast.makeText(context, "Backup Failed!", Toast.LENGTH_SHORT).show();

        }
    }


    @SuppressLint("NewApi") public List<Import> getListOfFiles() {

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy_HH:mm:ss");
        String path = Environment.getExternalStorageDirectory() + "/SwadeshiSFA";
        File files = new File(path);

        FileFilter filter = new FileFilter() {

            private final List<String> exts = Arrays.asList("db");

            @Override
            public boolean accept(File pathname) {
                String ext;
                String path = pathname.getPath();
                ext = path.substring(path.lastIndexOf(".") + 1);
                return exts.contains(ext);
            }
        };

        final File [] filesFound = files.listFiles(filter);
        List<Import> list = new ArrayList<Import>();
        if (filesFound != null && filesFound.length > 0) {
            for (File file : filesFound) {

                Import imprt=new Import();

                imprt.setDate(sdf.format(file.lastModified()).toString());
                imprt.setFileName(file.getName().toString());
                imprt.setFilePath(file.getPath());
                imprt.setSize(file.getFreeSpace()+"");

                list.add(imprt);

            }
        }

        return list;
    }

}
