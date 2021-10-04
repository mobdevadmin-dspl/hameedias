package com.datamation.hmdsfa.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.datamation.hmdsfa.OtherUploads.UploadAttendance;
import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.adapter.downloadListAdapter;
import com.datamation.hmdsfa.api.ApiCllient;
import com.datamation.hmdsfa.api.ApiInterface;
import com.datamation.hmdsfa.api.TaskTypeUpload;
import com.datamation.hmdsfa.barcode.upload.UploadDeletedInvoices;
import com.datamation.hmdsfa.barcode.upload.UploadExpenses;
import com.datamation.hmdsfa.barcode.upload.UploadNonProd;
import com.datamation.hmdsfa.barcode.upload.UploadReceipt;
import com.datamation.hmdsfa.controller.AttendanceController;
import com.datamation.hmdsfa.controller.BankController;
import com.datamation.hmdsfa.controller.BarcodeVarientController;
import com.datamation.hmdsfa.controller.CompanyDetailsController;
import com.datamation.hmdsfa.controller.DiscountController;
import com.datamation.hmdsfa.controller.ExpenseController;
import com.datamation.hmdsfa.controller.FItenrDetController;
import com.datamation.hmdsfa.controller.FreeDebController;
import com.datamation.hmdsfa.controller.FreeDetController;
import com.datamation.hmdsfa.controller.FreeHedController;
import com.datamation.hmdsfa.controller.FreeItemController;
import com.datamation.hmdsfa.controller.FreeMslabController;
import com.datamation.hmdsfa.controller.FreeSlabController;
import com.datamation.hmdsfa.controller.IteaneryDebController;
import com.datamation.hmdsfa.controller.ItemLocController;
import com.datamation.hmdsfa.controller.NewCustomerController;
import com.datamation.hmdsfa.controller.OutstandingController;
import com.datamation.hmdsfa.controller.ReasonController;
import com.datamation.hmdsfa.controller.ReceiptController;
import com.datamation.hmdsfa.controller.ReferenceDetailDownloader;
import com.datamation.hmdsfa.controller.ReferenceSettingController;
import com.datamation.hmdsfa.controller.RouteController;
import com.datamation.hmdsfa.controller.RouteDetController;
import com.datamation.hmdsfa.controller.SalesPriceController;
import com.datamation.hmdsfa.controller.VATController;
import com.datamation.hmdsfa.controller.VanStockController;
import com.datamation.hmdsfa.model.Attendance;
import com.datamation.hmdsfa.model.Control;
import com.datamation.hmdsfa.model.NewCustomer;
import com.datamation.hmdsfa.model.ReceiptHed;
import com.datamation.hmdsfa.settings.TaskTypeDownload;
import com.datamation.hmdsfa.barcode.upload.UploadPreSales;
import com.datamation.hmdsfa.controller.CustomerController;
import com.datamation.hmdsfa.controller.DayExpHedController;
import com.datamation.hmdsfa.controller.DayNPrdHedController;
import com.datamation.hmdsfa.controller.FItenrHedController;
import com.datamation.hmdsfa.controller.FirebaseMediaController;
import com.datamation.hmdsfa.controller.InvHedController;
import com.datamation.hmdsfa.controller.ItemBundleController;
import com.datamation.hmdsfa.controller.ItemController;
import com.datamation.hmdsfa.controller.OrderController;
import com.datamation.hmdsfa.controller.SalRepController;
import com.datamation.hmdsfa.controller.SalesReturnController;
import com.datamation.hmdsfa.dialog.CustomProgressDialog;
import com.datamation.hmdsfa.dialog.StockInquiryDialog;
import com.datamation.hmdsfa.helpers.NetworkFunctions;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.helpers.UploadTaskListener;
import com.datamation.hmdsfa.model.DayExpHed;
import com.datamation.hmdsfa.model.DayNPrdHed;
import com.datamation.hmdsfa.model.Debtor;
import com.datamation.hmdsfa.model.FInvRHed;
import com.datamation.hmdsfa.model.FirebaseData;
import com.datamation.hmdsfa.model.InvHed;
import com.datamation.hmdsfa.model.Order;
import com.datamation.hmdsfa.model.SalRep;
import com.datamation.hmdsfa.model.User;
import com.datamation.hmdsfa.model.apimodel.ReadJsonList;
import com.datamation.hmdsfa.barcode.upload.UploadSalesReturn;
import com.datamation.hmdsfa.utils.NetworkUtil;
import com.datamation.hmdsfa.utils.UtilityContainer;
import com.datamation.hmdsfa.barcode.upload.UploadVanSales;
import com.datamation.hmdsfa.view.DayExpenseActivity;
import com.datamation.hmdsfa.view.ReportActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/***@Auther - rashmi**/

public class FragmentTools extends Fragment implements View.OnClickListener, UploadTaskListener {

    private Context context = getActivity();
    User loggedUser;
    View view;
    int count = 0;
    Animation animScale;
    ImageView imgSync, imgUpload, imgPrinter, imgDatabase, imgStockDown, imgStockInq, imgSalesRep, imgTour, imgDayExp, imgImage, imgVideo,imgReport;
    NetworkFunctions networkFunctions;
    SharedPref pref;
    List<String> resultList;
    LinearLayout layoutTool;
    private long timeInMillis;
    private Handler mHandler;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    ArrayList<FirebaseData> imgList, vdoList;
    ApiInterface apiInterface;
    ArrayList<FirebaseData> imgUrlList;
    ArrayList<FirebaseData> vdoUrlList;
    FirebaseData fd;
    ArrayList<Control> downloadList = new ArrayList<>();
    DatabaseReference rootRef;
    FirebaseMediaController fmc;

    boolean isAnyActiveImages = false;
    boolean isAnyActiveVideos = false;
    boolean isImageFitToScreen;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_management_tools, container, false);
        pref = SharedPref.getInstance(getActivity());

        animScale = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_scale);
        layoutTool = (LinearLayout) view.findViewById(R.id.layoutTool);
        imgTour = (ImageView) view.findViewById(R.id.imgTourInfo);
        imgStockInq = (ImageView) view.findViewById(R.id.imgStockInquiry);
        imgSync = (ImageView) view.findViewById(R.id.imgSync);
        imgUpload = (ImageView) view.findViewById(R.id.imgUpload);
        imgStockDown = (ImageView) view.findViewById(R.id.imgDownload);
        imgReport = (ImageView) view.findViewById(R.id.imgReport);
        imgDatabase = (ImageView) view.findViewById(R.id.imgSqlite);
        imgSalesRep = (ImageView) view.findViewById(R.id.imgSalrep);
        imgDayExp = (ImageView) view.findViewById(R.id.imgDayExp);
        imgImage = (ImageView) view.findViewById(R.id.imgImage);
        imgVideo = (ImageView) view.findViewById(R.id.imgVideo);

        fmc = new FirebaseMediaController(getActivity());
        mHandler = new Handler(Looper.getMainLooper());
        imgList = new ArrayList<FirebaseData>();
        vdoList = new ArrayList<FirebaseData>();

        rootRef = FirebaseDatabase.getInstance().getReference();

       // getImgDataFromFirebase(rootRef);
       // getVdoDataFromFirebase(rootRef);

//        isAnyActiveImages = new InvDetController(getActivity()).isAnyActiveOrders();
//        isAnyActiveVideos = new ReceiptDetController(getActivity()).isAnyActiveReceipt();
//
//        if (isAnyActiveImages) {
//            imgImage.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_image));
//        } else {
//            imgImage.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_image));
//        }
//
//        if (isAnyActiveVideos) {
//            imgVideo.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_video));
//        } else {
//            imgVideo.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_video));
//        }
        networkFunctions = new NetworkFunctions(getActivity());
        imgTour.setOnClickListener(this);
        imgStockInq.setOnClickListener(this);
        imgSync.setOnClickListener(this);
        imgUpload.setOnClickListener(this);
        imgStockDown.setOnClickListener(this);
        imgReport.setOnClickListener(this);
        imgDatabase.setOnClickListener(this);
        imgSalesRep.setOnClickListener(this);
        imgDayExp.setOnClickListener(this);
        imgImage.setOnClickListener(this);
        imgVideo.setOnClickListener(this);
        resultList = new ArrayList<>();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        timeInMillis = System.currentTimeMillis();

        Log.d("FRAGMENT_TOOL", "IMAGE_FLAG: " + pref.getImageFlag());


//        if (fmc.getAllMediaforCheckIfIsExist("IMG") > 0) {
//            imgImage.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_img_notification));
//        } else {
//            imgImage.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_image));
 //       }

//        if (fmc.getAllMediaforCheckIfIsExist("VDO") > 0) {
//            imgVideo.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_video_notification));
//        } else {
//            imgVideo.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_video));
//        }

        return view;
    }

//    private void getVdoDataFromFirebase(DatabaseReference rootRef) {
//        DatabaseReference chatSpaceRef = rootRef.child("Videos");
//        ValueEventListener eventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    try
//                    {
//                        int flag = ds.child("FLAG").getValue(Integer.class);
//                        String mType = ds.child("M_TYPE").getValue(String.class);
//                        List<Object> repCodeList = (List<Object>) ds.child("REPCODE").getValue();
//                        String url = ds.child("URL").getValue(String.class);
//                        if(repCodeList.size()>0)
//                            if (repCodeList.contains(pref.getLoginUser().getRepCode()) && (flag == 0)) {
//                                FirebaseData fd = new FirebaseData();
//                                fd.setMEDIA_FLAG(flag + "");
//                                fd.setMEDIA_URL(url);
//                                fd.setMEDIA_TYPE(mType);
//                                vdoList.add(fd);
//                                Log.d("*TAG", url + "," + flag + "," + repCodeList + "" + pref.getLoginUser().getRepCode() + ", " + mType);
//                            }
//                    }
//                    catch (Exception ex)
//                    {
//                        Toast.makeText(getActivity(),"Video Media Problem....",Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d("*ERR", databaseError + "");
//            }
//        };
//        chatSpaceRef.addListenerForSingleValueEvent(eventListener);
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgTourInfo:
                imgTour.startAnimation(animScale);
                UtilityContainer.mLoadFragment(new FragmentMarkAttendance(), getActivity());
                break;

            case R.id.imgStockInquiry:
                imgStockInq.startAnimation(animScale);//
                new StockInquiryDialog(getActivity());
                break;

            case R.id.imgSync:
                imgSync.startAnimation(animScale);
                Log.d("Validate Secondary Sync", ">>Mac>> " + pref.getMacAddress().trim() + " >>URL>> " + pref.getBaseURL() + " >>DB>> " + pref.getDistDB());
                try {
                    if(NetworkUtil.isNetworkAvailable(getActivity())) {
                        new Validate(pref.getMacAddress().trim(), pref.getBaseURL(), pref.getDistDB()).execute();
                    }else{
                        Toast.makeText(getActivity(),"No internet connection",Toast.LENGTH_LONG).show();
                    }
                }catch(Exception e){
                    Log.e(">>>> Secondary Sync",e.toString());
                }
                break;

            case R.id.imgUpload:
                imgUpload.startAnimation(animScale);
                syncDialog(getActivity());
                break;

            case R.id.imgDownload:
                imgStockDown.startAnimation(animScale);
                UtilityContainer.mLoadFragment(new FragmentCategoryWiseDownload(), getActivity());
                break;

            case R.id.imgReport:
                imgReport.startAnimation(animScale);
                new ReportActivity(getActivity()).reportDialog();
                break;

            case R.id.imgSqlite:
                imgDatabase.startAnimation(animScale);
                UtilityContainer.mSQLiteDatabase(getActivity());
                break;

            case R.id.imgSalrep:
                imgSalesRep.startAnimation(animScale);
                ViewRepProfile();
                break;

            case R.id.imgDayExp:
                imgDayExp.startAnimation(animScale);
                Intent intent = new Intent(getActivity(), DayExpenseActivity.class);
                startActivity(intent);
                break;

            case R.id.imgImage:
                imgImage.startAnimation(animScale);
                //2020-09-09-by rashmi
                UtilityContainer.mBarcodeDialogbox(getActivity());
               // imgUrlList = fmc.getAllMediafromDb("IMG");
               // ViewImageList();
                break;

            case R.id.imgVideo:
               // imgVideo.startAnimation(animScale);
               // vdoUrlList = fmc.getAllMediafromDb("VDO");
                UtilityContainer.mPrinterDialogbox(getActivity());
                //ViewVideoList();
                break;

        }

    }


//    public void ViewImageList() {
//        final Dialog imageDialog = new Dialog(getActivity());
//        imageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        imageDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        imageDialog.setCancelable(false);
//        imageDialog.setCanceledOnTouchOutside(false);
//        imageDialog.setContentView(R.layout.whatsapp_image_layout);
//
//        LinearLayout parentLayout = (LinearLayout) imageDialog.findViewById(R.id.image_layout);
//
//
//        if (imgUrlList != null) {
//            for (FirebaseData fd : imgUrlList) {
////            for (int i = 0; i < imgUrlList.size(); i++) {
//                try {
//                    final ImageView imageButton = new ImageView(getActivity());
//                    final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(400, 400);
//                    lp.setMargins(20, 20, 20, 20);
//                    imageButton.setLayoutParams(lp);
//
//                    Glide.with(this)
//                            .load(fd.getMEDIA_URL())
//                            .into(imageButton);
//
//                    imageButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            if (isImageFitToScreen) {
//                                isImageFitToScreen = false;
//                                imageButton.setLayoutParams(lp);
//                                imageButton.setAdjustViewBounds(true);
//                            } else {
//                                isImageFitToScreen = true;
//                                imageButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//                                imageButton.setScaleType(ImageView.ScaleType.FIT_XY);
//                            }
//                        }
//                    });
//
//                    parentLayout.addView(imageButton);
//                } catch (Exception exception) {
//                    exception.printStackTrace();
//                }
//            }
//        } else {
//            Toast.makeText(getActivity(), "No Images to show!", Toast.LENGTH_SHORT).show();
//        }
//
//        imageDialog.findViewById(R.id.got_it).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                int result = fmc.createOrUpdateFirebaseData(imgList, 1);
//                if (result > 0) {
//                    Toast.makeText(getActivity(), "Image flag updated", Toast.LENGTH_SHORT).show();
//                }
//
//                if (fmc.getAllMediaforCheckIfIsExist("IMG") > 0) {
//                    imgImage.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_img_notification));
//                } else {
//                    imgImage.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_image));
//                }
//                imageDialog.dismiss();
//            }
//        });
//
//        imageDialog.show();
//    }


//    public void ViewVideoList() {
//        final Dialog videoDialog = new Dialog(getActivity());
//        videoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        videoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        videoDialog.setCancelable(false);
//        videoDialog.setCanceledOnTouchOutside(false);
//        //videoDialog.setContentView(R.layout.whatsapp_video_responsive_layout);
//        videoDialog.setContentView(R.layout.whatsapp_video_layout);
//        LinearLayout parentLayout = (LinearLayout) videoDialog.findViewById(R.id.video_layout);
//
//        for (FirebaseData fd : vdoUrlList) {
//            try {
//                final fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard videoView = new fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard(getActivity());
//                final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(475, 250);
//                lp.setMargins(20, 20, 20, 20);
//                videoView.setLayoutParams(lp);
//                String pathName = "" + fd.getMEDIA_URL();
//                videoView.setUp(pathName, "SWADESHI");
//                videoView.ivThumb.setImageDrawable(getResources().getDrawable(R.drawable.video));
//                parentLayout.addView(videoView);
//            } catch (Exception exception) {
//                exception.printStackTrace();
//            }
//        }
//
//        videoDialog.findViewById(R.id.got_it).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int vdoresult = fmc.createOrUpdateFirebaseData(vdoList, 1);
//                if (vdoresult > 0) {
//                    Toast.makeText(getActivity(), "Video flag updated", Toast.LENGTH_SHORT).show();
//                }
//                if (fmc.getAllMediaforCheckIfIsExist("VDO") > 0) {
//                    imgVideo.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_video_notification));
//                } else {
//                    imgVideo.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_video));
//                }
//                videoDialog.dismiss();
//            }
//        });
//        videoDialog.show();
//    }

    public void ViewRepProfile() {
        final Dialog repDialog = new Dialog(getActivity());
        repDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        repDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        repDialog.setCancelable(false);
        repDialog.setCanceledOnTouchOutside(false);
        repDialog.setContentView(R.layout.rep_profile);

        //initializations
        TextView repname = (TextView) repDialog.findViewById(R.id.repname);
        final TextView repcode = (TextView) repDialog.findViewById(R.id.repcode);
        final TextView repPrefix = (TextView) repDialog.findViewById(R.id.repPrefix);
        // final TextView locCode = (TextView) repDialog.findViewById(R.id.target);
        final EditText repemail = (EditText) repDialog.findViewById(R.id.email);
        final TextView areaCode = (TextView) repDialog.findViewById(R.id.areaCode);
        final TextView dealCode = (TextView) repDialog.findViewById(R.id.dealclode);
        //  areaCode.setText(loggedUser.getRoute());
        final SalRep rep = new SalRepController(getActivity()).getSaleRepDet(new SalRepController(getActivity()).getCurrentRepCode());
        repname.setText(rep.getNAME());
        repcode.setText(rep.getRepCode());
        repPrefix.setText(rep.getPREFIX());
        repemail.setText(rep.getEMAIL());

        //close
        repDialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (repemail.length() > 0) {
                    if (isEmailValid(repemail.getText().toString())) {
                        ArrayList<SalRep> salRepslist = new ArrayList<>();
                        rep.setEMAIL(repemail.getText().toString().trim());
                        salRepslist.add(rep);
                        new SalRepController(getActivity()).createOrUpdateSalRep(salRepslist);
                        repDialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Invalid email address, Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    repDialog.dismiss();
                }
            }
        });
        repDialog.show();
    }

    public boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches())
            return true;
        else
            return false;
    }

    private void syncDialog(final Context context) {
        MaterialDialog materialDialog = new MaterialDialog.Builder(getActivity())
                .content("Are you sure, Do you want to Upload Data?")
                .positiveColor(ContextCompat.getColor(getActivity(), R.color.material_alert_positive_button))
                .positiveText("Yes")
                .negativeColor(ContextCompat.getColor(getActivity(), R.color.material_alert_negative_button))
                .negativeText("No, Exit")
                .callback(new MaterialDialog.ButtonCallback() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        boolean connectionStatus = NetworkUtil.isNetworkAvailable(context);
                        if (connectionStatus == true) {
                            OrderController orderHed = new OrderController(getActivity());
                            final ArrayList<Order> ordHedList = orderHed.getAllUnSyncOrdHed();//1
                            DayNPrdHedController npHed = new DayNPrdHedController(getActivity());
                            final ArrayList<DayNPrdHed> npHedList = npHed.getUnSyncedData();//2
                            AttendanceController attendanceController = new AttendanceController(getActivity());//4
                            ArrayList<Attendance> attendList = attendanceController.getUnsyncedTourData();
                            CustomerController customerDS = new CustomerController(getActivity());
                            ArrayList<Debtor> debtorlist = customerDS.getAllDebtorsToCordinatesUpdate();//5
                            ArrayList<Debtor> updExistingDebtors = customerDS.getAllUpdatedDebtors();//6
                            ArrayList<Debtor> imgDebtorList = customerDS.getAllImagUpdatedDebtors();//7
                            DayExpHedController exHed = new DayExpHedController(getActivity());
                            final ArrayList<DayExpHed> exHedList = exHed.getUnSyncedData();//8
                            NewCustomerController customerNwDS = new NewCustomerController(getActivity());
                            ArrayList<NewCustomer> newCustomersList = customerNwDS.getAllNewCustomersForSync();//9
                            SalesReturnController retHed = new SalesReturnController(getActivity());
                            ArrayList<FInvRHed> retHedList = retHed.getAllUnsyncedWithInvoice();
                            ArrayList<ReceiptHed> receiptlist = new ReceiptController(getActivity()).getAllUnsyncedRecHed();
                            InvHedController hedDS = new InvHedController(getActivity());
                            ArrayList<InvHed> invHedList = hedDS.getAllUnsynced();
                                   // firebasetokenid - 10
//                    /* If records available for upload then */
                            if (invHedList.size() <= 0 && receiptlist.size() <= 0 && retHedList.size() <= 0 && ordHedList.size() <= 0 && npHedList.size() <= 0  && attendList.size()<= 0 && debtorlist.size()<=0 && updExistingDebtors.size() <= 0 && imgDebtorList.size()<= 0 && exHedList.size()<=0)
                            {
                                Toast.makeText(getActivity(), "No Records to upload !", Toast.LENGTH_LONG).show();
                            }else {
//                            try { // new customer upload 2019-10-17MMS
//                                ArrayList<SalRep> fblist = new ArrayList<>();
//                                SalRep salRep = new SalRep();
//                                salRep.setCONSOLE_DB(SharedPref.getInstance(context).getConsoleDB().trim());
//                                salRep.setDIST_DB(SharedPref.getInstance(context).getDistDB().trim());
//                                salRep.setRepCode(SharedPref.getInstance(context).getLoginUser().getRepCode());
//                                salRep.setFirebaseTokenID(SharedPref.getInstance(context).getFirebaseTokenKey());
//                                fblist.add(salRep);
//                                if (fblist.size() <= 0)
//                                    Toast.makeText(getActivity(), "No firebase records to upload !", Toast.LENGTH_LONG).show();
//                                else {
//                                    new UploadFirebaseTokenKey(getActivity(), FragmentTools.this,fblist).execute(fblist);
////                                    new UploadAttendance(getActivity(), FragmentTools.this, attendList).execute(fblist);
//                                    Log.v(">>8>>", "Upload new firebase records finish" + fblist);
//                                }
//                            } catch (Exception e) {
//                                Log.v("Excp in sync attendance", e.toString());
//                            }
//                            try { // new customer upload 2019-10-17MMS
//                                AttendanceController attendanceController = new AttendanceController(getActivity());
//                                ArrayList<Attendance> attendList = attendanceController.getUnsyncedTourData();
//                                if (attendList.size() <= 0)
//                                    Toast.makeText(getActivity(), "No Attendance Records to upload !", Toast.LENGTH_LONG).show();
//                                else {
//                                    new UploadAttendance(getActivity(), FragmentTools.this, attendList).execute(attendList);
//                                    Log.v(">>8>>", "Upload new Attendance execute finish");
//                                }
//                            } catch (Exception e) {
//                                Log.v("Excp in sync attendance", e.toString());
//                            }
//                            try { // new customer upload 2019-10-17MMS
//                                NewCustomerController customerDS = new NewCustomerController(getActivity());
//                                ArrayList<NewCustomer> newCustomers = customerDS.getAllNewCustomersForSync();
//                                if (newCustomers.size() <= 0)
//                                    Toast.makeText(getActivity(), "No Customer Records to upload !", Toast.LENGTH_LONG).show();
//                                else {
//                                    new UploadNewCustomer(getActivity(), FragmentTools.this, newCustomers).execute(newCustomers);
//                                    Log.v(">>8>>", "Upload new customer execute finish");
//                                }
//                            } catch (Exception e) {
//                                Log.v("Exception in sync order", e.toString());
//                            }

//                            try {//existing update debtors upload - 2019-12-16
//                                CustomerController customerDS = new CustomerController(getActivity());
//                                ArrayList<Debtor> updExistingDebtors = customerDS.getAllUpdatedDebtors();
//                                if (updExistingDebtors.size() <= 0)
//                                    Toast.makeText(getActivity(), "No updated debtors to upload !", Toast.LENGTH_LONG).show();
//                                else {
//                                    new UploadEditedDebtors(getActivity(), FragmentTools.this, updExistingDebtors).execute(updExistingDebtors);
//                                    Log.v(">>>>", "Updated debtors are uploaded");
//                                }
//                            } catch (Exception e) {
//                                Log.v("Excptn upld edted dbtrs", e.toString());
//                            }
//                            try {// debtor image uploads 2019-11-01MMS
//                                CustomerController customerDS = new CustomerController(getActivity());
//                                ArrayList<Debtor> imgUpdDebtors = customerDS.getAllImagUpdatedDebtors();
//                                if (imgUpdDebtors.size() <= 0)
//                                    Toast.makeText(getActivity(), "No Debtors business images to upload !", Toast.LENGTH_LONG).show();
//                                else {
//                                    new UploadDebtorImges(getActivity(), FragmentTools.this, imgUpdDebtors).execute(imgUpdDebtors);
//                                    Log.v(">>8>>", "Debtor business images uploded");
//                                }
//                            } catch (Exception e) {
//                                Log.v("Exception business img", e.toString());
//                            }
//                            try {// debtor uploads 2019-10-21MMS
//                                CustomerController customerDS = new CustomerController(getActivity());
//                                ArrayList<Debtor> debtors = customerDS.getAllDebtorsToCordinatesUpdate();
//                                if (debtors.size() <= 0)
//                                    Toast.makeText(getActivity(), "No Debtor cordinates to upload !", Toast.LENGTH_LONG).show();
//                                else {
//                                    new UploadDebtorCordinates(getActivity(), FragmentTools.this, debtors).execute(debtors);
//                                    Log.v(">>8>>", "Debtor cordinates uploded");
//                                }
//                            } catch (Exception e) {
//                                Log.v("Exception in sync De", e.toString());
//                            }
                                try { // upload pre sale order

//                    /* If records available for upload then */
                                    if (ordHedList.size() <= 0)
                                        Toast.makeText(getActivity(), "No Pre Sale Records to upload !", Toast.LENGTH_LONG).show();
                                        new UploadPreSales(getActivity(), FragmentTools.this, TaskTypeUpload.UPLOAD_ORDER).execute(ordHedList);
                                        Log.v(">>8>>", "UploadPreSales execute finish");
                                        // new ReferenceNum(getActivity()).NumValueUpdate(getResources().getString(R.string.NumVal));
                                } catch (Exception e) {
                                    Log.v("Exception in sync order", e.toString());
                                }

                            }



//                            try { // upload Non productive 2019-10-23MMS
//                                DayNPrdHedController npHed = new DayNPrdHedController(getActivity());
//                                ArrayList<DayNPrdHed> npHedList = npHed.getUnSyncedData();
//                                if (npHedList.size() <= 0)
//                                    Toast.makeText(getActivity(), "No Non Productive Records to upload !", Toast.LENGTH_LONG).show();
//                                else {
//                                    new UploadNonProd(getActivity(), FragmentTools.this).execute(npHedList);
//                                    Log.v(">>8>>", "Upload non productive execute finish");//
//                                }
//                            } catch (Exception e) {
//                                Log.v("Exception in sync order", e.toString());
//                            }
//                            try { // upload dAY eXPENSE
//                                DayExpHedController exHed = new DayExpHedController(getActivity());
//                                ArrayList<DayExpHed> exHedList = exHed.getUnSyncedData();
////                    /* If records available for upload then */
//                                if (exHedList.size() <= 0)
//                                    Toast.makeText(getActivity(), "No Expense Records to upload !", Toast.LENGTH_LONG).show();
//                                else {
//                                    new UploadExpenses(getActivity(), FragmentTools.this).execute(exHedList);
//                                    Log.v(">>8>>", "Upload expense execute finish");
//                                }
//                            } catch (Exception e) {
//                                Log.v("Exception in sync order", e.toString());
//                            }
                        } else
                            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        dialog.dismiss();
                    }
                })
                .build();
        materialDialog.setCanceledOnTouchOutside(false);
        materialDialog.show();
    }

    public void mDevelopingMessage(String message, String title) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setIcon(R.drawable.info);
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
        alertD.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    private void syncMasterDataDialog(final Context context) {
        MaterialDialog materialDialog = new MaterialDialog.Builder(getActivity())
                .content("Are you sure, Do you want to Sync Master Data?")
                .positiveColor(ContextCompat.getColor(getActivity(), R.color.material_alert_positive_button))
                .positiveText("Yes")
                .negativeColor(ContextCompat.getColor(getActivity(), R.color.material_alert_negative_button))
                .negativeText("No, Exit")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        boolean connectionStatus = NetworkUtil.isNetworkAvailable(getActivity());
                        if (connectionStatus == true) {
                            if (isAllUploaded()) {
                                dialog.dismiss();
                                try {
                                    new secondarySync(SharedPref.getInstance(getActivity()).getLoginUser().getRepCode()).execute();
                                    SharedPref.getInstance(getActivity()).setGlobalVal("SyncDate", dateFormat.format(new Date(timeInMillis)));
                                } catch (Exception e) {
                                    Log.e("## ErrorIn2ndSync ##", e.toString());
                                }
                            } else {
                                Toast.makeText(context, "Please Upload All Transactions", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        dialog.dismiss();
                    }
                })
                .build();
        materialDialog.setCanceledOnTouchOutside(false);
        materialDialog.show();
    }

    private boolean isAllUploaded() {
        Boolean allUpload = false;

        OrderController orderHed = new OrderController(getActivity());
        ArrayList<Order> ordHedList = orderHed.getAllUnSyncOrdHed();

        DayNPrdHedController npHed = new DayNPrdHedController(getActivity());
        ArrayList<DayNPrdHed> npHedList = npHed.getUnSyncedData();

        DayExpHedController exHed = new DayExpHedController(getActivity());
        ArrayList<DayExpHed> exHedList = exHed.getUnSyncedData();

        InvHedController hedDS = new InvHedController(getActivity());
        ArrayList<InvHed> invHedList = hedDS.getAllUnsynced();

        SalesReturnController retHed = new SalesReturnController(getActivity());
        ArrayList<FInvRHed> retHedList = retHed.getAllUnsyncedWithInvoice();

        ArrayList<InvHed> invHedDltList = hedDS.getAllUnsyncedDeleteInvoices();
        ArrayList<ReceiptHed> receiplist = new ReceiptController(getActivity()).getAllUnsyncedRecHed();
//&& invHedDltList.isEmpty()
        if (ordHedList.isEmpty() && npHedList.isEmpty() && receiplist.isEmpty() && exHedList.isEmpty() && invHedList.isEmpty() && retHedList.isEmpty() ) {
            allUpload = true;
        } else {
            allUpload = false;
        }

        return allUpload;
    }

//    private void getImgDataFromFirebase(DatabaseReference rootRef) {
//        DatabaseReference chatSpaceRef = rootRef.child("Images");
//        ValueEventListener eventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//
//                    try
//                    {
//                        int flag = ds.child("FLAG").getValue(Integer.class);
//                        String mType = ds.child("M_TYPE").getValue(String.class);
//                        List<String> repCodeList = (List<String>) ds.child("REPCODE").getValue();
//                        String url = ds.child("URL").getValue(String.class);
//                        if(repCodeList.size()>0)
//                            if (repCodeList.contains(pref.getLoginUser().getRepCode()) && (flag == 0)) {
//                                fd = new FirebaseData();
//                                fd.setMEDIA_FLAG(flag + "");
//                                fd.setMEDIA_URL(url);
//                                fd.setMEDIA_TYPE(mType);
//                                imgList.add(fd);
//
//                                Log.d("*TAG", url + "," + flag + "," + repCodeList + "" + pref.getLoginUser().getRepCode() + " " + mType);
//                            }
//                    }
//                    catch (Exception ex)
//                    {
//                        Toast.makeText(getActivity(),"Image Media Problem....",Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d("*ERR", databaseError + "");
//            }
//        };
//        chatSpaceRef.addListenerForSingleValueEvent(eventListener);
//    }

    @Override
    public void onTaskCompleted(TaskTypeUpload taskType, List<String> list) {
        if(list != null) {
            resultList.addAll(list);
        }
        switch (taskType) {
            case UPLOAD_ORDER: {
                try {//Van sale upload - 2020-03-24-rashmi
                    InvHedController hedDS = new InvHedController(getActivity());
                    // InvoiceBarcodeController hedDS = new InvoiceBarcodeController(getActivity());
                    ArrayList<InvHed> invHedList = hedDS.getAllUnsynced();
//                    /* If records available for upload then */
                    if (invHedList.size() <= 0) {
                        Toast.makeText(getActivity(), "No Van Sale Records to upload !", Toast.LENGTH_LONG).show();
                    }
                    new UploadVanSales(getActivity(), FragmentTools.this, TaskTypeUpload.UPLOAD_INVOICE).execute(invHedList);
                    Log.v(">>8>>","Uploadinvoices execute finish");

                }catch(Exception e){
                    Log.v("Exception in sync order",e.toString());
                }

            }
            break;
            case UPLOAD_INVOICE: {
                try//Sales return upload -
                {
                    SalesReturnController retHed = new SalesReturnController(getActivity());
                    ArrayList<FInvRHed> retHedList = retHed.getAllUnsyncedWithInvoice();
                    if(retHedList.size() <= 0) {
                        Toast.makeText(getActivity(), "No Non Productive Records to upload !", Toast.LENGTH_LONG).show();
                    }
                    new UploadSalesReturn(getActivity(), FragmentTools.this, TaskTypeUpload.UPLOAD_RETURNS).execute(retHedList);
                    Log.v(">>8>>", "Upload sales return execute finish");
                }
                catch (Exception e)
                {
                    Log.v("Exception in syncretrn" , e.toString());
                }

            }
            break;
            case UPLOAD_RETURNS: {
                try {//Van sale upload - 2020-03-24-rashmi
                    InvHedController hedDS = new InvHedController(getActivity());
                    ArrayList<InvHed> invHedList = hedDS.getAllUnsyncedDeleteInvoices();
//                    /* If records available for upload then */
                    if (invHedList.size() <= 0){
                        Toast.makeText(getActivity(), "No deleted invoices to upload !", Toast.LENGTH_LONG).show();
                    }
                    new UploadDeletedInvoices(getActivity(), FragmentTools.this,TaskTypeUpload.UPLOAD_DELETED_INVOICE).execute(invHedList);
                    Log.v(">>8>>","Uploaddeleteinvoices execute finish");
                }catch(Exception e){
                    Log.v("Exception in sync order",e.toString());
                }
            }
            break;
            case UPLOAD_DELETED_INVOICE: {
                DayExpHedController exHed = new DayExpHedController(getActivity());
                final ArrayList<DayExpHed> exHedList = exHed.getUnSyncedData();//8
                if (exHedList.size() <= 0){
                    Toast.makeText(getActivity(), "No expenses to upload !", Toast.LENGTH_LONG).show();
                }
                new UploadExpenses(getActivity(), FragmentTools.this, TaskTypeUpload.UPLOAD_EXPENSE).execute(exHedList);
                Log.v(">>upload>>", "Upload expense execute finish");

            }
            break;
            case UPLOAD_EXPENSE: {
                DayNPrdHedController npHed = new DayNPrdHedController(getActivity());
                final ArrayList<DayNPrdHed> npHedList = npHed.getUnSyncedData();
                if (npHedList.size() <= 0){
                    Toast.makeText(getActivity(), "No nonproductives to upload !", Toast.LENGTH_LONG).show();
                }
                new UploadNonProd(getActivity(), FragmentTools.this, TaskTypeUpload.UPLOAD_NONPROD).execute(npHedList);
                Log.v(">>upload>>", "Upload non productive execute finish");
            }
            break;
            case UPLOAD_NONPROD:{

                ArrayList<ReceiptHed> receiplist = new ReceiptController(getActivity()).getAllUnsyncedRecHed();
                if (receiplist.size() <= 0){
                    Toast.makeText(getActivity(), "No receipts to upload !", Toast.LENGTH_LONG).show();
                }
                new UploadReceipt(getActivity(), FragmentTools.this, TaskTypeUpload.UPLOAD_RECEIPT).execute(receiplist);
            }
            break;
            case UPLOAD_RECEIPT: {
                AttendanceController attendanceController = new AttendanceController(getActivity());//4
                ArrayList<Attendance> attendList = attendanceController.getUnsyncedTourData();
                if (attendList.size() <= 0){
                    Toast.makeText(getActivity(), "No tour info to upload !", Toast.LENGTH_LONG).show();
                }
                new UploadAttendance(getActivity(), FragmentTools.this,attendList, TaskTypeUpload.UPLOAD_ATTENDANCE).execute(attendList);
                Log.v(">>upload>>", "Upload attendance execute finish");
            }
            break;
            case UPLOAD_ATTENDANCE:{

                Log.v(">>upload>>", "all upload finish");

                String msg = "";
                for (String s : resultList) {
                    msg += s;
                }
                resultList.clear();
                mUploadResult(msg);

            }
            break;
            default:
                break;
        }
    }

    @Override
    public void onTaskCompleted(List<String> list) {

    }

    //**********************secondary sysnc start***********************************************/
     private class secondarySync extends AsyncTask<String, Integer, Boolean> {
        int totalRecords = 0;
        CustomProgressDialog pdialog;
        private String repcode;
        private List<String> errors = new ArrayList<>();
        private Handler mHandler;

        public secondarySync(String repCode) {
            this.repcode = repCode;
            this.pdialog = new CustomProgressDialog(getActivity());
            mHandler = new Handler(Looper.getMainLooper());
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new CustomProgressDialog(getActivity());
            mHandler = new Handler(Looper.getMainLooper());
            pdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pdialog.setMessage("Authenticating...");
            pdialog.show();
        }
        @Override
        protected Boolean doInBackground(String... arg0) {
            apiInterface = ApiCllient.getClient(getActivity()).create(ApiInterface.class);
            mHandler = new Handler(Looper.getMainLooper());
            try {
                if (SharedPref.getInstance(getActivity()).getLoginUser() != null && SharedPref.getInstance(getActivity()).isLoggedIn()) {
                    new CompanyDetailsController(getActivity()).deleteAll();

                    /*****************itenary detdeb**********************************************************************/

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading iteanery customer details...");
                        }
                    });
                    try{
                            IteaneryDebController itenaryDebController = new IteaneryDebController(getActivity());
                            itenaryDebController.deleteAll();
                            UtilityContainer.download(getActivity(), TaskTypeDownload.ItenrDeb, networkFunctions.getItenaryDebDet(repcode));
                    } catch (Exception e) {
                        errors.add(e.toString());
                        throw e;
                    }
/*****************company details**********************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading data(Company details)...");
                        }
                    });
                    // Processing controls
                    try{
                        UtilityContainer.download(getActivity(),TaskTypeDownload.Controllist, networkFunctions.getCompanyDetails(repcode));
                    } catch (Exception e) {
                        errors.add(e.toString());
                        throw e;
                    }
/*****************outlets**********************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading Customers...");
                        }
                    });

                    try{
                            CustomerController customerController = new CustomerController(getActivity());
                            customerController.deleteAll();
                            UtilityContainer.download(getActivity(), TaskTypeDownload.Customers, networkFunctions.getCustomer(repcode));
                    } catch (Exception e) {
                        errors.add(e.toString());
                        throw e;
                    }
                    /*****************Settings*****************************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading Settings...");
                        }
                    });
                    // Processing company settings
                    try{
                            ReferenceSettingController settingController = new ReferenceSettingController(getActivity());
                            settingController.deleteAll();
                            UtilityContainer.download(getActivity(), TaskTypeDownload.Settings, networkFunctions.getReferenceSettings());
                    } catch (Exception e) {
                        errors.add(e.toString());
                        throw e;
                    }

                    // Processing company settings


/*****************end Settings**********************************************************************/
/*****************Branches*****************************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading data (reference details)...");
                        }
                    });
                    // Processing Branches

                    // Processing Branches

                    try{
                        ReferenceDetailDownloader branchController = new ReferenceDetailDownloader(getActivity());
                        branchController.deleteAll();
                        UtilityContainer.download(getActivity(),TaskTypeDownload.Reference, networkFunctions.getReferences(repcode));
                    } catch (Exception e) {
                        errors.add(e.toString());
                        throw e;
                    }

                    /*****************end Branches**********************************************************************/
                    /*****************ItemBundle*****************************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading data (ItemBundle details)...");
                        }
                    });
                    // Processing ItemBundle

                    ItemBundleController itemBundleController = new ItemBundleController(getActivity());
                    itemBundleController.deleteAll();
                    try{
                        UtilityContainer.download(getActivity(),TaskTypeDownload.ItemBundle, networkFunctions.getItemBundles(repcode));
                    } catch (Exception e) {
                        errors.add(e.toString());
                        throw e;
                    }

                    /*****************end ItemBundle**********************************************************************/

/*****************VAT*****************************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading data (VAT details)...");
                        }
                    });
                    // Processing Branches

                    try{
                        VATController vatController = new VATController(getActivity());
                        vatController.deleteAll();
                        UtilityContainer.download(getActivity(),TaskTypeDownload.VAT, networkFunctions.getVAT());
                    } catch (Exception e) {
                        errors.add(e.toString());
                        throw e;
                    }

                    /*****************end VAT**********************************************************************/
                    /*****************Items*****************************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Processing downloaded data (item details)...");
                        }
                    });

//                    // Processing Items
                    ItemController itemController = new ItemController(getActivity());
                    itemController.deleteAll();
                    // Processing item price
                    try{
                        UtilityContainer.download(getActivity(),TaskTypeDownload.Items, networkFunctions.getItems(repcode,new SalRepController(getActivity()).getRepType().trim()));
                    } catch (Exception e) {
                        errors.add(e.toString());
                        throw e;
                    }

                    /*****************end Items **********************************************************************/
                    //                    /*****************reasons**********************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Items downloaded\nDownloading reasons...");
                        }
                    });
                    // Processing reasons

                    try {
                        ReasonController reasonController = new ReasonController(getActivity());
                        reasonController.deleteAll();
                        UtilityContainer.download(getActivity(),TaskTypeDownload.Reason, networkFunctions.getReasons());
                    } catch (IOException e) {
                        e.printStackTrace();
                        errors.add(e.toString());
                        throw e;
                    }

                    /*****************end reasons **********************************************************************/

//                    // Processing fddbnote
//                    try {
//                        Call<ReadJsonList> resultCall = apiInterface.getOutstandingResult(pref.getDistDB(),repcode);
//                        resultCall.enqueue(new Callback<ReadJsonList>() {
//                            @Override
//                            public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
//                                if(response.body() != null) {
//                                    OutstandingController outstandingController = new OutstandingController(getActivity());
//                                    outstandingController.deleteAll();
//                                    ArrayList<FddbNote> fddbnoteList = new ArrayList<FddbNote>();
//                                    for (int i = 0; i < response.body().getOutstandingResult().size(); i++) {
//                                        fddbnoteList.add(response.body().getOutstandingResult().get(i));
//                                    }
//                                    outstandingController.createOrUpdateFDDbNote(fddbnoteList);
//                                }else{
//                                    errors.add("Outstanding response is null");
//                                }
//                            }
//                            @Override
//                            public void onFailure(Call<ReadJsonList> call, Throwable t) {
//                                errors.add(t.toString());
//                            }
//                        });
//                    } catch (Exception e) {
//                        errors.add(e.toString());
//                        throw e;
//                    }



                    /*****************end fddbnote**********************************************************************/

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading banks...");
                        }
                    });
//                    /*****************banks**********************************************************************/

                    try {
                        BankController bankController = new BankController(getActivity());
                        bankController.deleteAll();
                        UtilityContainer.download(getActivity(),TaskTypeDownload.Bank, networkFunctions.getBanks());
                    } catch (IOException e) {
                        e.printStackTrace();
                        errors.add(e.toString());
                        throw e;
                    }
                    /*****************end banks**********************************************************************/
                    /*****************expenses**********************************************************************/

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading data (expenses)...");
                        }
                    });
                    // Processing expense
                    try {
                        ExpenseController expenseController = new ExpenseController(getActivity());
                        expenseController.deleteAll();
                        UtilityContainer.download(getActivity(),TaskTypeDownload.Expense, networkFunctions.getExpenses());
                    } catch (IOException e) {
                        errors.add(e.toString());
                        e.printStackTrace();
                        throw e;
                    }

                    /*****************end expenses**********************************************************************/
                    /*****************Route**********************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Expenses downloaded\nDownloading route details...");
                        }
                    });
                    // Processing route

                    try {
                        RouteController routeController = new RouteController(getActivity());
                        routeController.deleteAll();
                        UtilityContainer.download(getActivity(),TaskTypeDownload.Route, networkFunctions.getRoutes(repcode));
                    } catch (IOException e) {
                        e.printStackTrace();
                        errors.add(e.toString());
                        throw e;
                    }


                    // Processing route


                    /*****************end ItemBundle**********************************************************************/
                    /*****************last 3 invoice heds**********************************************************************/

//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            pdialog.setMessage("Processing downloaded data (last invoices)...");
//                        }
//                    });
//                    // Processing lastinvoiceheds
//                    try {
//                        Call<ReadJsonList> resultCall = apiInterface.getLastThreeInvHedResult(pref.getDistDB(),repcode);
//                        resultCall.enqueue(new Callback<ReadJsonList>() {
//                            @Override
//                            public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
//                                FInvhedL3Controller invoiceHedController = new FInvhedL3Controller(getActivity());
//                                invoiceHedController.deleteAll();
//                                ArrayList<FInvhedL3> invoiceHedList = new ArrayList<FInvhedL3>();
//                                if(response.body() != null) {
//
//                                    for (int i = 0; i < response.body().getLastThreeInvHedResult().size(); i++) {
//                                        invoiceHedList.add(response.body().getLastThreeInvHedResult().get(i));
//                                    }
//                                    invoiceHedController.createOrUpdateFinvHedL3(invoiceHedList);
//                                }else{
//                                    errors.add("LastThreeInvHed response is null");
//                                }
//                            }
//                            @Override
//                            public void onFailure(Call<ReadJsonList> call, Throwable t) {
//                                errors.add(t.toString());
//                            }
//                        });
//                    } catch (Exception e) {
//                        errors.add(e.toString());
//                        throw e;
//                    }
                    /*****************end lastinvoiceheds**********************************************************************/
                    /*****************last 3 invoice dets**********************************************************************/
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            pdialog.setMessage("Processing downloaded data (invoices)...");
//                        }
//                    });
//                    // Processing lastinvoiceheds
//                    try {
//                        Call<ReadJsonList> resultCall = apiInterface.getLastThreeInvDetResult(pref.getDistDB(),repcode);
//                        resultCall.enqueue(new Callback<ReadJsonList>() {
//                            @Override
//                            public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
//                                FinvDetL3Controller invoiceDetController = new FinvDetL3Controller(getActivity());
//                                invoiceDetController.deleteAll();
//                                ArrayList<FinvDetL3> invoiceDetList = new ArrayList<FinvDetL3>();
//                                if(response.body() != null) {
//                                    for (int i = 0; i < response.body().getLastThreeInvDetResult().size(); i++) {
//                                        invoiceDetList.add(response.body().getLastThreeInvDetResult().get(i));
//                                    }
//                                    invoiceDetController.createOrUpdateFinvDetL3(invoiceDetList);
//                                }else{
//                                    errors.add("LastThreeInvDet response is null");
//                                }
//                            }
//                            @Override
//                            public void onFailure(Call<ReadJsonList> call, Throwable t) {
//                                errors.add(t.toString());
//                            }
//                        });
//                    } catch (Exception e) {
//                        errors.add(e.toString());
//
//                        throw e;
//                    }
                    /*****************end lastinvoicedets**********************************************************************/
                    /*****************Route det**********************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading route details...");
                        }
                    });

                    // Processing route
                    try {
                        RouteDetController routeDetController = new RouteDetController(getActivity());
                        routeDetController.deleteAll();
                        UtilityContainer.download(getActivity(),TaskTypeDownload.RouteDet, networkFunctions.getRouteDets(repcode));
                    } catch (IOException e) {
                        e.printStackTrace();
                        errors.add(e.toString());
                        throw e;
                    }
                    // Processing route

//                    try {
//                        Call<ReadJsonList> resultCall = apiInterface.getRouteDetResult(pref.getDistDB(),repcode);
//                        UtilityContainer.download(getActivity(),resultCall, TaskType.Route);
//                    } catch (Exception e) {
//                        errors.add(e.toString());
//
//                        throw e;
//                    }
                    /*****************end route det**********************************************************************/
                    /*****************towns**********************************************************************/
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            pdialog.setMessage("Expenses downloaded\nDownloading town details...");
//                        }
//                    });
//                    TownController townController = new TownController(getActivity());
//                    townController.deleteAll();
//                    // Processing towns
//                    try {
//                        Call<ReadJsonList> resultCall = apiInterface.getTownResult(pref.getDistDB());
//                        resultCall.enqueue(new Callback<ReadJsonList>() {
//                            @Override
//                            public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
//                                if(response.body() != null) {
//                                    ArrayList<Town> townList = new ArrayList<Town>();
//                                    for (int i = 0; i < response.body().getTownResult().size(); i++) {
//                                        townList.add(response.body().getTownResult().get(i));
//                                    }
//                                    TownController townController = new TownController(getActivity());
//                                    townController.createOrUpdateFTown(townList);
//                                }else{
//                                    errors.add("Town response is null");
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<ReadJsonList> call, Throwable t) {
//                                errors.add(t.toString());
//                            }
//                        });
//                    } catch (Exception e) {
//                        errors.add(e.toString());
//                        throw e;
//                    }
                    /*****************end towns**********************************************************************/
                    /*****************Freeslab**********************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Processing downloaded data (free)...");
                        }
                    });
                    // Processing freeslab
                    try {

                        FreeSlabController freeSlabController = new FreeSlabController(getActivity());
                        freeSlabController.deleteAll();
                        UtilityContainer.download(getActivity(),TaskTypeDownload.Freeslab, networkFunctions.getFreeSlab());
                    } catch (Exception e) {
                        errors.add(e.toString());
                        throw e;
                    }
                    /*****************end freeSlab**********************************************************************/
                    /*****************freeMslab**********************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Processing downloaded data (free)...");
                        }
                    });
                    // Processing freeMslab
                    try {

                        FreeMslabController freemSlabController = new FreeMslabController(getActivity());
                        freemSlabController.deleteAll();
                        UtilityContainer.download(getActivity(),TaskTypeDownload.Freemslab, networkFunctions.getFreeMslab());
                    } catch (Exception e) {
                        errors.add(e.toString());

                        throw e;
                    }
                    /*****************end freeMSlab**********************************************************************/
                    /*****************FreeHed**********************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Processing downloaded data (free)...");
                        }
                    });
                    // Processing freehed
                    try {

                        FreeHedController freeHedController = new FreeHedController(getActivity());
                        freeHedController.deleteAll();
                        UtilityContainer.download(getActivity(),TaskTypeDownload.Freehed, networkFunctions.getFreeHed(repcode));
                    } catch (Exception e) {
                        errors.add(e.toString());

                        throw e;
                    }
                    /*****************end freeHed**********************************************************************/
                    /*****************Freedet**********************************************************************/

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Processing downloaded data (free)...");
                        }
                    });

                    // Processing freedet
                    try {

                        FreeDetController freeDetController = new FreeDetController(getActivity());
                        freeDetController.deleteAll();
                        UtilityContainer.download(getActivity(),TaskTypeDownload.Freedet, networkFunctions.getFreeDet());

                    } catch (Exception e) {
                        errors.add(e.toString());

                        throw e;
                    }
                    /*****************end freedet**********************************************************************/
                    /*****************freedeb**********************************************************************/

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Processing downloaded data (free)...");
                        }
                    });

                    // Processing freedeb
                    try {

                        FreeDebController freeDebController = new FreeDebController(getActivity());
                        freeDebController.deleteAll();
                        UtilityContainer.download(getActivity(),TaskTypeDownload.Freedeb, networkFunctions.getFreeDebs());
                    } catch (Exception e) {
                        errors.add(e.toString());
                        throw e;
                    }
                    /*****************end freedeb**********************************************************************/
                    /*****************freeItem**********************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Processing downloaded data (free)...");
                        }
                    });


                    // Processing freeItem
                    try {

                        FreeItemController freeItemController = new FreeItemController(getActivity());
                        freeItemController.deleteAll();
                        UtilityContainer.download(getActivity(),TaskTypeDownload.Freeitem, networkFunctions.getFreeItems());
                    } catch (Exception e) {
                        errors.add(e.toString());
                        throw e;
                    }
                    /*****************end freeItem**********************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading iteanery details...");
                        }
                    });
//**************************************************itenaryhed**********************************************************
                    final FItenrHedController itenaryHedController = new FItenrHedController(getActivity());
                    itenaryHedController.deleteAll();
                    // Processing itenaryhed
                    try {
                        Calendar c = Calendar.getInstance();
                        int cyear = c.get(Calendar.YEAR);
                        int cmonth = c.get(Calendar.MONTH) + 1;
                        DecimalFormat df_month = new DecimalFormat("00");
                        UtilityContainer.download(getActivity(),TaskTypeDownload.Iteneryhed, networkFunctions.getItenaryHed(repcode));
                    } catch (Exception e) {
                        errors.add(e.toString());
                        throw e;
                    }

                    /*****************itenary det**********************************************************************/

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading iteanery details...");
                        }
                    });
                    // Processing itenarydet
                    try {
                        FItenrDetController fItenrDetController = new FItenrDetController(getActivity());
                        fItenrDetController.deleteAll();
                        Calendar c = Calendar.getInstance();
                        int cyear = c.get(Calendar.YEAR);
                        int cmonth = c.get(Calendar.MONTH) + 1;
                        DecimalFormat df_month = new DecimalFormat("00");
                        UtilityContainer.download(getActivity(),TaskTypeDownload.Itenerydet, networkFunctions.getItenaryDet(repcode));

                    } catch (Exception e) {
                        errors.add(e.toString());

                        throw e;
                    }
                    /*****************stock - kaveesha - 10-06-2020**********************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading ItemLoc....");
                        }
                    });
                    // Processing discount
                    try {

                        ItemLocController itemLocController = new ItemLocController(getActivity());
                        itemLocController.deleteAll();
                        UtilityContainer.download(getActivity(),TaskTypeDownload.Stock, networkFunctions.getStock(repcode));
                    } catch (Exception e) {
                        errors.add(e.toString());
                        throw e;
                    }
                    /*****************SalesPrice**********************************************************************/


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading SalesPrice....");
                        }
                    });
                    // Processing SalesPrice
                    try {
                        SalesPriceController salesPriceController = new SalesPriceController(getActivity());
                        salesPriceController.deleteAll();
                        UtilityContainer.download(getActivity(),TaskTypeDownload.Salesprice, networkFunctions.getSalesPrice(repcode,new SalRepController(getActivity()).getRepType().trim()));

                    } catch (Exception e) {
                        errors.add(e.toString());
                        throw e;
                    }
                    /*****************discount**********************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading discount....");
                        }
                    });
                    // Processing discount
                    try {

                        DiscountController discountController = new DiscountController(getActivity());
                        discountController.deleteAll();
                        UtilityContainer.download(getActivity(),TaskTypeDownload.Discount, networkFunctions.getDiscounts(repcode));
                    } catch (Exception e) {
                        errors.add(e.toString());
                        throw e;
                    }

                    // *****************fddbnote**********************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading Outsatnding...");
                        }
                    });
                    // Processing fddbnote


                    try {
                        OutstandingController outstandingController = new OutstandingController(getActivity());
                        outstandingController.deleteAll();
                        UtilityContainer.download(getActivity(),TaskTypeDownload.fddbnote, networkFunctions.getFddbNotes(repcode));
                    } catch (IOException e) {
                        e.printStackTrace();
                        errors.add(e.toString());
                        throw e;
                    }



                    /*****************Van Stock  - kaveesha - 12-06-2020**********************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading Van Stock....");
                        }
                    });
                    // Processing van stock


                    try {
                        VanStockController vanStockController = new VanStockController(getActivity());
                        vanStockController.deleteAll();
                        UtilityContainer.download(getActivity(),TaskTypeDownload.VanStock, networkFunctions.getVanStock(repcode));
                    } catch (Exception e) {
                        errors.add(e.toString());
                        throw e;
                    }

                    /*****************Barcode Variant  - kaveesha - 12-06-2020**********************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading Barcode Variant....");
                        }
                    });

                    // Processing Barcode Varient

                    BarcodeVarientController barcodeVarientController = new BarcodeVarientController(getActivity());
                    barcodeVarientController.deleteAll_BarcodeVariant();
                    try {
                        UtilityContainer.download(getActivity(),TaskTypeDownload.Barcodevarient, networkFunctions.getBarcodeVariant(repcode,new SalRepController(getActivity()).getRepType().trim()));
                    } catch (Exception e) {
                        errors.add(e.toString());
                        throw e;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Completed...");
                        }
                    });

                    /*****************end sync**********************************************************************/

                    return true;
                } else {
                    errors.add("SharedPref.getInstance(getActivity()).getLoginUser() = null OR !SharedPref.getInstance(getActivity()).isLoggedIn()");
                    Log.d("ERROR>>>>>", "Login USer" + SharedPref.getInstance(getActivity()).getLoginUser().toString() + " IS LoggedIn --> " + SharedPref.getInstance(getActivity()).isLoggedIn());
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                errors.add(e.toString());

                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean result) {
            super.onPostExecute(result);

            pdialog.setMessage("Finalizing data");
            pdialog.setMessage("Download Completed..");
            if (result) {
                if (pdialog.isShowing()) {
                    pdialog.dismiss();

                    int i = 1;
                    for (Control c : new CompanyDetailsController(getActivity()).getAllDownload()) {
                        downloadList.add(c);
                        i++;
                    }

                    if(downloadList.size()>0) {
                        mDownloadResult(downloadList);
                    }
                }
                showErrorText("Successfully Synchronized");
            } else {
                if (pdialog.isShowing()) {
                    pdialog.dismiss();

                    int i = 1;
                    for (Control c : new CompanyDetailsController(getActivity()).getAllDownload()) {
                        downloadList.add(c);
                        i++;
                    }

                    if(downloadList.size()>0) {
                        mDownloadResult(downloadList);
                    }
                }
                StringBuilder sb = new StringBuilder();
                if (errors.size() == 1) {
                    sb.append(errors.get(0));
                    showErrorText(sb.toString());
                } else if(errors.size() == 0) {
                    sb.append("Following errors occurred");
                    for (String error : errors) {
                        sb.append("\n - ").append(error);
                        showErrorText(sb.toString());
                    }
                }
                //showErrorText(sb.toString());
            }
            if (fmc.getAllMediaforCheckIfIsExist("IMG") > 0) {
                imgImage.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_img_notification));
            } else {
                imgImage.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_image));
            }

            if (fmc.getAllMediaforCheckIfIsExist("VDO") > 0) {
                imgVideo.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_video_notification));
            } else {
                imgVideo.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_video));
            }
        }
    }

    private void showErrorText(String s) {
        Toast.makeText(getActivity(), "" + s, Toast.LENGTH_LONG).show();

    }

    /////////////***********************secondory sync finish***********************************/
    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public void mUploadResult(String message) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setTitle("Upload Summary");

        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
        alertD.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

    }

    private class Validate extends AsyncTask<String, Integer, Boolean> {
        int totalRecords = 0;
        CustomProgressDialog pdialog;
        private String macId, url, db;

        public Validate(String macId, String url, String db) {
            this.macId = macId;
            this.url = url;
            this.db = db;
            this.pdialog = new CustomProgressDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pdialog.setMessage("Validating...");
            pdialog.show();
        }

        @Override
        protected Boolean doInBackground(String... arg0) {

            try {

                try {
                    ApiInterface apiInterface = ApiCllient.getClient(getActivity()).create(ApiInterface.class);
                    Call<ReadJsonList> resultCall = apiInterface.getSalRepResult(pref.getDistDB(),macId);
                    resultCall.enqueue(new Callback<ReadJsonList>() {
                        @Override
                        public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                            ArrayList<SalRep> repList = new ArrayList<SalRep>();
                            for (int i = 0; i < response.body().getSalRepResult().size(); i++) {
                                repList.add(response.body().getSalRepResult().get(i));
                            }
                            new SalRepController(getActivity()).createOrUpdateSalRep(repList);

                            if(repList.size()>0){
                                networkFunctions.setUser(repList.get(0));
                                pref.storeLoginUser(repList.get(0));
                            }

                        }

                        @Override
                        public void onFailure(Call<ReadJsonList> call, Throwable t) {
                            Log.d(">>>Error in failure",t.toString());
                        }
                    });

                        pref = SharedPref.getInstance(getActivity());

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pdialog.setMessage("Authenticated...");
                            }
                        });

                        return true;

                } catch (Exception e) {
                    Log.e("networkFunctions ->", "IOException -> " + e.toString());
                    throw e;
                }



            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }


        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (pdialog.isShowing())
                pdialog.cancel();
            // pdialog.cancel();
            if (result) {
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                syncMasterDataDialog(getActivity());
            } else {
                Toast.makeText(getActivity(), "Invalid Mac Id", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void mDownloadResult(ArrayList<Control> downlodaList)
    {
        final Dialog sDialog = new Dialog(getActivity());
        sDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sDialog.setCancelable(false);
        sDialog.setCanceledOnTouchOutside(false);
        sDialog.setContentView(R.layout.download_dialog);

        ListView download_list = (ListView) sDialog.findViewById(R.id.download_listview);
        downlodaList = new CompanyDetailsController(getActivity()).getAllDownload();
        download_list.setAdapter(new downloadListAdapter(getActivity(),downlodaList));

        sDialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDialog.dismiss();
            }
        });

        sDialog.show();
    }


}
