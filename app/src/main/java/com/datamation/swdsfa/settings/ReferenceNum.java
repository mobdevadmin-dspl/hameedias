package com.datamation.swdsfa.settings;

import android.content.Context;
import android.util.Log;

import com.datamation.swdsfa.controller.ReferenceController;
import com.datamation.swdsfa.controller.SalRepController;
import com.datamation.swdsfa.helpers.SharedPref;
import com.datamation.swdsfa.model.Reference;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ReferenceNum {
    Context context;
    SharedPref pref;

    public ReferenceNum(Context context) {
        this.context = context;
        this.pref = SharedPref.getInstance(context);
    }

    public String getCurrentRefNo(String cSettingsCode) {

        String preFix = "";
        ReferenceController referenceDS = new ReferenceController(context);
        DecimalFormat dFormat = new DecimalFormat("0000");

        Calendar c = Calendar.getInstance();
        /* Check if its new month. if so update fCompanyBranch */
        //referenceDS.isNewMonth(cSettingsCode);

        String sDate = String.valueOf(c.get(Calendar.YEAR)).substring(2) + String.format("%02d", c.get(Calendar.MONTH) + 1);



        String nextNumVal = referenceDS.getNextNumVal(cSettingsCode,new SalRepController(context).getCurrentRepCode().trim());
        //String nextNumVal = referenceDS.getNextNumVal(cSettingsCode,pref.getLoginUser().getCode());
        //ArrayList<Reference> list = referenceDS.getCurrentPreFix(cSettingsCode,"AA");
        ArrayList<Reference> list = referenceDS.getCurrentPreFix(cSettingsCode,pref.getLoginUser().getPrefix());

        if (!nextNumVal.equals("")) {

            for (Reference reference : list) {
                preFix = reference.getCharVal() + reference.getRepPrefix() + sDate;
            }
            Log.v("next num val", "NEXT :" + preFix + "/" + dFormat.format(Integer.valueOf(nextNumVal)));

        } else {
            for (Reference reference : list) {
                preFix = reference.getCharVal() + reference.getRepPrefix() + sDate;
            }

            Log.v("next num val", "NEXT :" + preFix);
        }

        return preFix + "/" + dFormat.format(Integer.valueOf(nextNumVal));
        //return preFix + "/" + dFormat.format(Integer.valueOf("00001"));
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

//    public int nNumValueInsertOrUpdate(String cSettingsCode) {
//
//        ReferenceController referenceDS = new ReferenceController(context);
//        int nextNumVal = 0;
//
////        if(referenceDS.getNextNumVal(cSettingsCode,new SalRepController(context).getCurrentRepCode().trim())!= null){
////            nextNumVal = Integer.parseInt(referenceDS.getNextNumVal(cSettingsCode,new SalRepController(context).getCurrentRepCode().trim())) + 1;
////
////        }else{
////            nextNumVal = 0;
////        }
//        Log.d("Check pref values",pref.getLoginUser().toString());
//        nextNumVal = Integer.parseInt(referenceDS.getNextNumVal(cSettingsCode,pref.getLoginUser().getCode())) + 1;
//
//        int count = referenceDS.InsetOrUpdate(cSettingsCode, nextNumVal);
//
//        if (count > 0) {
//            Log.v("InsertOrUpdate", "success");
//        } else {
//            Log.v("InsertOrUpdate", "Failed");
//        }
//
//        return count;
//
//    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-Item or value based ref no update-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public int NumValueUpdate(String cSettingsCode) {

        ReferenceController referenceDS = new ReferenceController(context);
        int nextNumVal = 0;

        nextNumVal = Integer.parseInt(referenceDS.getNextNumVal(cSettingsCode,new SalRepController(context).getCurrentRepCode().trim())) + 1;

        int count = referenceDS.InsetOrUpdate(cSettingsCode, nextNumVal);

        if (count > 0) {
            Log.v("InsertOrUpdate", "success");
        } else {
            Log.v("InsertOrUpdate", "Failed");
        }

        return 0;

    }

}
