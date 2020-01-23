package com.datamation.hmdsfa.customer;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.adapter.Customer_Adapter;
import com.datamation.hmdsfa.adapter.DistrictAdapter;
import com.datamation.hmdsfa.adapter.RouteAdapter;
import com.datamation.hmdsfa.adapter.TownAdapter;
import com.datamation.hmdsfa.controller.CustomerController;
import com.datamation.hmdsfa.controller.DistrictController;
import com.datamation.hmdsfa.controller.NewCustomerController;
import com.datamation.hmdsfa.controller.RouteController;
import com.datamation.hmdsfa.controller.RouteDetController;
import com.datamation.hmdsfa.controller.SalRepController;
import com.datamation.hmdsfa.controller.TownController;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.helpers.Validation;
import com.datamation.hmdsfa.model.Customer;
import com.datamation.hmdsfa.model.District;
import com.datamation.hmdsfa.model.NewCustomer;
import com.datamation.hmdsfa.model.Route;
import com.datamation.hmdsfa.model.Town;
import com.datamation.hmdsfa.settings.GPSTracker;
import com.datamation.hmdsfa.settings.ReferenceNum;
import com.datamation.hmdsfa.utils.NetworkUtil;
import com.datamation.hmdsfa.view.ActivityHome;
import com.datamation.hmdsfa.view.DebtorDetailsActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import at.markushi.ui.CircleButton;

public class NewCustomerActivity extends AppCompatActivity {

    public EditText OtherCode;
    public TextInputEditText customerCode, customerName, editTextCNic, businessRegno, Reg_date, town, route, addressline1, addressline2, district,
            city, contactPerson, mobile,contactNumber, fax, emailaddress;


    public ImageButton btn_Route, btn_District, btn_Town, CustomerbtnSearch;
    private ArrayList<Route> routeArrayList;
    private ArrayList<Town> townArrayList;
    private ArrayList<District> districtArrayList;
    private ArrayList<NewCustomer> newCustomerArrayList;
    ArrayList<Uri> uris = new ArrayList<>();
    SharedPref mSharedPref;
    Switch mySwitch;
    private ReferenceNum referenceNum;
    private ArrayList<String> pictureDownloadurl;
    private String jsonstr;
    private ProgressDialog progressDialog;
    public static SharedPreferences localSP;
    public static final String SETTINGS = "SETTINGS";
    ActivityHome home;
    CircleButton fabSave, fabDiscard;
    int CUSFLG = 1;
    private String nCustomerNo;
    Context context;
    private Uri filePath;
    private byte[] byteArray;
    private static int IMAGE1 = 0;
    private int allCusFragInt;
    Bitmap bitimage = null;
    private static String pictureName = null;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView customerImg;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    private StorageReference mStorageRef;


    private Customer outlet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.new_cus_toolbar);
        TextView title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        title.setText("Customer Registration");
        context = this;
        mSharedPref = SharedPref.getInstance(this);
        referenceNum = new ReferenceNum(this);
        localSP = this.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
        fabSave = (CircleButton) findViewById(R.id.new_cus_fab_save);
        fabDiscard = (CircleButton) findViewById(R.id.new_cus_fab_discard);
        customerCode = (TextInputEditText) findViewById(R.id.etRefNo);
        customerName = (TextInputEditText) findViewById(R.id.etCustomer_Name);
        editTextCNic = (TextInputEditText) findViewById(R.id.etCusNIC);
        Reg_date = (TextInputEditText) findViewById(R.id.etDate);
        businessRegno = (TextInputEditText) findViewById(R.id.etRegNo);
        addressline1 = (TextInputEditText) findViewById(R.id.etAddLine_1);
        addressline2 = (TextInputEditText) findViewById(R.id.etAddLine_2);
        city = (TextInputEditText) findViewById(R.id.etCity);
        contactPerson = (TextInputEditText) findViewById(R.id.et_contact_person);
        mobile = (TextInputEditText) findViewById(R.id.etCusMobile_No);
        contactNumber = (TextInputEditText) findViewById(R.id.etCusPhone_No);
        fax = (TextInputEditText) findViewById(R.id.etFax);
        emailaddress = (TextInputEditText) findViewById(R.id.etEmail);
        route = (TextInputEditText) findViewById(R.id.etRoute);
        district = (TextInputEditText) findViewById(R.id.etDistrict);
        town = (TextInputEditText) findViewById(R.id.etTown);


        customerImg = (ImageView) findViewById(R.id.ivCus_Photo);

        fabSave.setColor(ContextCompat.getColor(this, R.color.colorAccent));
        fabSave.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_save_icon));

        fabDiscard.setColor(ContextCompat.getColor(this, R.color.colorAccent));
        fabDiscard.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_undo_icon));

        btn_Town = (ImageButton) findViewById(R.id.btnTown);
        btn_Route = (ImageButton) findViewById(R.id.btnRoute);
        btn_District = (ImageButton) findViewById(R.id.btnDistrict);
        CustomerbtnSearch = (ImageButton) findViewById(R.id.btnSearch);

        mySwitch = (Switch) findViewById(R.id.switch1);
        pictureDownloadurl = new ArrayList<>();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        //-----------------------------------------------------------------------------------------------------------------

        customerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        //show new customer ref no
        if (mySwitch.isChecked()) {
            CustomerbtnSearch.setEnabled(false);
            fabSave.setEnabled(true);
            fabDiscard.setEnabled(true);
            try {
                nCustomerNo = referenceNum.getCurrentRefNo(getResources().getString(R.string.newCusVal));
                customerCode.setText(nCustomerNo);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            CustomerbtnSearch.setEnabled(true);
            fabSave.setEnabled(false);
            fabDiscard.setEnabled(false);
        }


        Intent dataIntent = getIntent();
        allCusFragInt = dataIntent.getIntExtra("allCusFrag", 0);
        if (dataIntent.hasExtra("outlet")) {
            outlet = (Customer) dataIntent.getExtras().get("outlet");
            setDebtorDetToFields(outlet);
            if (outlet == null) {
                Toast.makeText(NewCustomerActivity.this, "Error receiving the outlet. Please try again.", Toast.LENGTH_SHORT).show();
            } else {
                mySwitch.setChecked(false);
                setFiealdsAvaliability(false);
            }
        }


        btn_District.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupDialog(1);

            }
        });
        btn_Town.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupDialog(2);
            }
        });
        btn_Route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupDialog(3);
            }
        });

        CustomerbtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // CUSFLG = 0;
                customerListDialogBox();
            }
        });
        //get old customers
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    referenceNum = new ReferenceNum(getApplicationContext());
                    try {
                        nCustomerNo = referenceNum.getCurrentRefNo(getResources().getString(R.string.newCusVal));
                        customerCode.setText(nCustomerNo);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    CUSFLG = 1;
                    customerName.setFocusable(true);
                    customerName.setEnabled(true);
                    customerName.setClickable(true);
                    customerName.setFocusableInTouchMode(true);

                    CustomerController TW = new CustomerController(getApplicationContext());

                    customerName.setEnabled(true);
                    editTextCNic.setEnabled(true);
                   // OtherCode.setEnabled(true);
                    Reg_date.setEnabled(true);
                    businessRegno.setEnabled(true);
                    addressline1.setEnabled(true);
                    addressline2.setEnabled(true);
                    mobile.setEnabled(true);
                    contactNumber.setEnabled(true);
                    fax.setEnabled(true);
                    emailaddress.setEnabled(true);
                    route.setEnabled(true);
                    district.setEnabled(true);
                    town.setEnabled(true);

                    city.setEnabled(true);
                    contactPerson.setEnabled(true);
                    btn_Town.setEnabled(true);
                    btn_Route.setEnabled(true);
                    btn_District.setEnabled(true);

                    customerName.setText("");
                    editTextCNic.setText("");
                    //OtherCode.setText("");
                    Reg_date.setText("");
                    businessRegno.setText("");
                    addressline1.setText("");
                    addressline2.setText("");
                    city.setText("");
                    contactPerson.setText("");
                    mobile.setText("");
                    contactNumber.setText("");
                    fax.setText("");
                    emailaddress.setText("");
                    route.setText("");
                    district.setText("");
                    town.setText("");


                } else {
                    //WHEN NEW CUSTOMER MODE OFF, existing customer edit mode
                    customerName.setFocusable(false);
                    customerName.setEnabled(false);
                    customerName.setClickable(false);
                    customerName.setFocusableInTouchMode(false);


                    customerName.setEnabled(false);
                    editTextCNic.setEnabled(false);
                  //  OtherCode.setEnabled(false);
                    Reg_date.setEnabled(false);
                    businessRegno.setEnabled(false);
                    addressline1.setEnabled(false);
                    addressline2.setEnabled(false);
                    mobile.setEnabled(false);
                    contactNumber.setEnabled(false);
                    fax.setEnabled(false);
                    emailaddress.setEnabled(false);
                    route.setEnabled(false);
                    district.setEnabled(false);
                    town.setEnabled(false);
                    city.setEnabled(false);
                    contactPerson.setEnabled(false);
                    btn_Town.setEnabled(false);
                    btn_Route.setEnabled(false);
                    btn_District.setEnabled(false);
                   // OtherCode.setText("");
                    CUSFLG = 0;
                    customerCode.setText("");
                }
                Log.d("CUSFLAG >>>>",""+CUSFLG);
            }
        });

        //save new customer
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  if (allCusFragInt == 88) {
                    //if (pictureDownloadurl.size() == 0) {
                    if (SharedPref.getInstance(NewCustomerActivity.this).getGlobalVal("image").equals("")) {
                        Toast.makeText(getApplicationContext(), "Please add business image", Toast.LENGTH_SHORT).show();
                    } else {
                        Customer customer = new Customer();
                        customer.setCusCode(outlet.getCusCode());
                        customer.setCusImage(SharedPref.getInstance(NewCustomerActivity.this).getGlobalVal("image"));
                        int result = new CustomerController(context).updateforCusImageUrl(customer);

                        if(result > 0) {
                            Toast.makeText(getApplicationContext(), "Business image updated successfully", Toast.LENGTH_SHORT).show();
                            ClearFiled();
                            Intent intent = new Intent(getApplicationContext(),DebtorDetailsActivity.class);
                            intent.putExtra("outlet", outlet);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "Business image updated Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    if (CUSFLG == 1) {

                        // if (customerName.getText().length() != 0 && addressline1.getText().length() != 0 && addressline2.getText().length() != 0 && mobile.getText().length() != 0 && town.getText().length() != 0 && route.getText().length() != 0 && city.getText().length() != 0) {
                        if (customerName.getText().length() != 0 && addressline1.getText().length() != 0 && addressline2.getText().length() != 0) {


//                                else if (SharedPref.getInstance(NewCustomerActivity.this).getGlobalVal("image").equals("")) {
//                                Toast.makeText(getApplicationContext(), "Please add business image", Toast.LENGTH_SHORT).show();
//                            }
//                            else {
                            if (SharedPref.getInstance(NewCustomerActivity.this).getGlobalVal("image").equals("")) {
                                Toast.makeText(getApplicationContext(), "Customer Save without Image", Toast.LENGTH_SHORT).show();
                            }

                            //RouteController RO = new RouteController(getApplicationContext());

                            DateFormat Dformat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = new Date();

                            SalRepController fSalRep = new SalRepController(getApplicationContext());
                            //SalRepDS fSalRepDS = new SalRepDS(getActivity());
                            //ReferenceDetailDownloader branchDS = new ReferenceDetailDownloader(getApplicationContext());

                            GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
                            referenceNum = new ReferenceNum(getApplicationContext());

                            NewCustomer customer = new NewCustomer();
                            customer.setC_IMAGE(SharedPref.getInstance(NewCustomerActivity.this).getGlobalVal("image"));
                            customer.setC_REGNUM(businessRegno.getText().toString());
                            customer.setNAME(customerName.getText().toString());
                            customer.setCUSTOMER_NIC(editTextCNic.getText().toString());
                            customer.setCUSTOMER_ID(customerCode.getText().toString());
                            customer.setC_DATE(Reg_date.getText().toString());
                            customer.setROUTE_ID(route.getText().toString());
                            customer.setC_TOWN(town.getText().toString());
                            customer.setDISTRICT(district.getText().toString());
                            customer.setADDRESS1(addressline1.getText().toString());
                            customer.setADDRESS2(addressline2.getText().toString());
                            customer.setCITY(city.getText().toString());
                            customer.setCONTACTPERSON(contactPerson.getText().toString());
                            customer.setMOBILE(mobile.getText().toString());
                            customer.setPHONE(contactNumber.getText().toString());
                            customer.setFAX(fax.getText().toString());
                            customer.setE_MAIL(emailaddress.getText().toString());
                            customer.setC_SYNCSTATE("0");
                            customer.setAddMac(fSalRep.getMacId());
                            customer.setC_ADDDATE(Dformat.format(date));
                            customer.setC_LATITUDE("" + gpsTracker.getLatitude());
                            customer.setC_LONGITUDE("" + gpsTracker.getLongitude());
                            customer.setnNumVal(referenceNum.getCurrentRefNo(getResources().getString(R.string.newCusVal)));
                           // customer.setnNumVal("1");
                            customer.setTxnDate(Dformat.format(date));
                            customer.setCONSOLE_DB(localSP.getString("Console_DB", "").toString());

                            ArrayList<NewCustomer> cusList = new ArrayList<>();
                            cusList.add(customer);

                            NewCustomerController customerDS = new NewCustomerController(getApplicationContext());
                            int result = 0;

                            //validation -:
                            if(new Validation(context).isValid(emailaddress.getText().toString() ,emailaddress.getText().toString().length(),
                                    mobile.getText().toString() ,mobile.getText().toString().length(),
                                    contactNumber.getText().toString() ,contactNumber.getText().toString().length(),
                                    fax.getText().toString() ,fax.getText().toString().length())){

                                result = customerDS.createOrUpdateCustomer(cusList);

                            }


                            if (result > 0) {

                                Toast.makeText(getApplicationContext(), "New Customer saved", Toast.LENGTH_SHORT).show();
                                //insert current NC number for next num generation
                                referenceNum = new ReferenceNum(getApplicationContext());
                                referenceNum.NumValueUpdate(getResources().getString(R.string.newCusVal));
                                ClearFiled();
                                Intent intent = new Intent(getApplicationContext(),ActivityHome.class);
                                //intent.putExtra("outlet", outlet);
                                startActivity(intent);
                                finish();
                                try {
                                    if (NetworkUtil.isNetworkAvailable(getApplicationContext())) {
                                        //upload to master
                                        // new UploadNewCustomer(getActivity(), AddNewCusRegistration.this, UPLOAD_NEW_CUSTOMER, cusList).execute();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                                    }
                                } catch (Exception e) {
                                    Log.v("CUSTOMER REG>>>>", "ERROR.....");
                                    e.printStackTrace();
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), "New Customer not saved , Please Check fields", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            ArrayList<TextInputEditText> fieldsList = new ArrayList<TextInputEditText>(Arrays.asList(customerName, Reg_date, town, route, addressline1, addressline2, city, contactPerson, contactNumber));
                            String[] fieldsNmList = {"Customer Name", "Date", "Town", "Route", "Address line 1", "Address line 2", "City", "Contact person", "Contact Number"};
                            int i = 0;
                            while (fieldsList.size() > i && fieldsNmList.length > i) {
                                validateCusName(fieldsList.get(i), fieldsNmList[i]);
                                i++;
                            }

                            Toast.makeText(getApplicationContext(), "Please fill all compulsory fields", Toast.LENGTH_SHORT).show();
                        }
                        //finish new customer registration part
                    }else{
                        //existing customer edit option - 2019-12-12 rashmi

                        if (customerName.getText().length() != 0 && addressline1.getText().length() != 0 && addressline2.getText().length() != 0) {

                            if (SharedPref.getInstance(NewCustomerActivity.this).getGlobalVal("image").equals("")) {
                                Toast.makeText(getApplicationContext(), "Customer Save without Image", Toast.LENGTH_SHORT).show();
                            }

                            DateFormat Dformat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = new Date();

                            referenceNum = new ReferenceNum(getApplicationContext());

                            Customer customer = new Customer();
                            customer.setCusName(customerName.getText().toString());
                            customer.setCusAdd1(addressline1.getText().toString());
                            customer.setCusAdd2(addressline2.getText().toString());
                            customer.setCusMob(contactNumber.getText().toString());
                            customer.setCusCode(customerCode.getText().toString().trim());
                            int result = new CustomerController(context).updateforCus(customer);

                            if(result > 0) {
                                Toast.makeText(getApplicationContext(), "Existing customer updated successfully", Toast.LENGTH_SHORT).show();
                                ClearFiled();
                                Intent intent = new Intent(getApplicationContext(),ActivityHome.class);
                                //intent.putExtra("outlet", outlet);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "Existing customer updated Failed", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            ArrayList<TextInputEditText> fieldsList = new ArrayList<TextInputEditText>(Arrays.asList(customerName, Reg_date, town, route, addressline1, addressline2, city, contactPerson, contactNumber));
                            String[] fieldsNmList = {"Customer Name", "Date", "Town", "Route", "Address line 1", "Address line 2", "City", "Contact person", "Contact Number"};
                            int i = 0;
                            while (fieldsList.size() > i && fieldsNmList.length > i) {
                                validateCusName(fieldsList.get(i), fieldsNmList[i]);
                                i++;
                            }

                            Toast.makeText(getApplicationContext(), "Please fill all compulsory fields", Toast.LENGTH_SHORT).show();
                        }

                    }//close existing customer edit part

                    }//close image url update else part
                }//close fab onclick
        });//close fab onclick

        fabDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearFiled();
            }
        });

        //get old customer for update record
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    customerName.requestFocus();
                    customerCode.setText(referenceNum.getCurrentRefNo(getResources().getString(R.string.newCusVal)));
                    CustomerbtnSearch.setEnabled(false);

                    editTextCNic.setEnabled(true);
                    customerCode.setEnabled(true);
                    customerName.setEnabled(true);
//                    OtherCode.setEnabled(true);
                    businessRegno.setEnabled(true);
                    Reg_date.setEnabled(true);
                    btn_District.setEnabled(true);
                    btn_Town.setEnabled(true);
                    btn_Route.setEnabled(true);
                    addressline1.setEnabled(true);
                    addressline2.setEnabled(true);
                    city.setEnabled(true);
                    contactPerson.setEnabled(true);
                    mobile.setEnabled(true);
                    contactNumber.setEnabled(true);
                    fax.setEnabled(true);
                    emailaddress.setEnabled(true);
                } else {
                    CustomerbtnSearch.setEnabled(true);
                    customerCode.setText("");
                    customerCode.setEnabled(false);
                    customerName.setEnabled(false);
                    editTextCNic.setEnabled(false);
                    Reg_date.setEnabled(false);
                    businessRegno.setEnabled(false);
                    btn_District.setEnabled(false);
                    btn_Town.setEnabled(false);
                    btn_Route.setEnabled(false);
                    addressline1.setEnabled(false);
                    addressline2.setEnabled(false);
                    city.setEnabled(false);
                    contactPerson.setEnabled(false);
                    mobile.setEnabled(false);
                    contactNumber.setEnabled(false);
                    fax.setEnabled(false);
                    emailaddress.setEnabled(false);
                }
            }
        });

        currentDate();
    }

    private void setFiealdsAvaliability(boolean bVal) {
        mySwitch.setEnabled(bVal);
        CustomerbtnSearch.setEnabled(bVal);
        customerCode.setEnabled(bVal);
        customerName.setEnabled(bVal);
        editTextCNic.setEnabled(bVal);
        Reg_date.setEnabled(bVal);
        businessRegno.setEnabled(bVal);
        btn_District.setEnabled(bVal);
        btn_Town.setEnabled(bVal);
        btn_Route.setEnabled(bVal);
        addressline1.setEnabled(bVal);
        addressline2.setEnabled(bVal);
        city.setEnabled(bVal);
        contactPerson.setEnabled(bVal);
        mobile.setEnabled(bVal);
        contactNumber.setEnabled(bVal);
        fax.setEnabled(bVal);
        emailaddress.setEnabled(bVal);
    }

    private void setDebtorDetToFields(Customer cus) {
        customerCode.setText(cus.getCusCode());
        customerName.setText(cus.getCusName());
        editTextCNic.setText("");
        businessRegno.setText("");
        Reg_date.setText("");
        district.setText("");
        town.setText("");
        route.setText(cus.getCusRoute());
        addressline1.setText(cus.getCusAdd1());
        addressline2.setText(cus.getCusAdd2());
        city.setText("");
        contactPerson.setText("");
        mobile.setText(cus.getCusMob());
        contactNumber.setText("");
        fax.setText("");
        emailaddress.setText(cus.getCusEmail());
    }

    private void currentDate() {
        Reg_date.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

    private void dispatchTakePictureIntent() {
        IMAGE1 = 1;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");

                if (photo != null) {
                    fabSave.setEnabled(true);
                    pictureName = "img";
                    filePath = data.getData();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byteArray = stream.toByteArray();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    bitimage = bitmap;
                    customerImg.setImageBitmap(bitmap);
//                    Drawable drawable = new BitmapDrawable(photo);
//                    customerImg.setImageDrawable(drawable);
                } else {
                    fabSave.setEnabled(false);
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setMessage("Your device or OS not supported for this function. Please try to get one from Gallery or " +
                            "contact service provider.")
                            .setTitle("Unsupported camera function");
                    alert.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = alert.create();
                    dialog.show();
                }

//            Bitmap bmp = (Bitmap) data.getExtras().get("data");

                try {
                    if (IMAGE1 == 1) {

                        pictureName = "img";
                        filePath = data.getData();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byteArray = stream.toByteArray();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                        bitimage = bitmap;
                        customerImg.setImageBitmap(bitmap);
                        uris.add(filePath);
                        Log.d("<>1>>>>", "" + filePath);
                    }
                    System.out.println("uris ; " + uris.size() + "-" + pictureName);
                    Log.v("ACTICITY RESULT: ", "REQUESTCODE " + requestCode + " REQUEST CODE " + resultCode + " INTENT " + data.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (NetworkUtil.isNetworkAvailable(NewCustomerActivity.this)) {
                    uploadFile(pictureName);
                } else {
                    Toast.makeText(NewCustomerActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Take a customer photo first.!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateCusName(TextInputEditText fieldNm, String fieldValueNm) {
        String cusFieldName = fieldNm.getText().toString().trim();
        if (cusFieldName.isEmpty()) {
            fieldNm.setError(fieldValueNm + " is compulsury!");
            return false;
        } else {
            fieldNm.setError(null);
            return true;
        }
    }

    private void uploadFile(String imgName) {
        //if there is a file to upload

        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String imageDate = sdf.format(new Date());
        String imagepathName = imgName + imageDate;

        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            try {

                FirebaseApp.initializeApp(this);

                FirebaseStorage storage = FirebaseStorage.getInstance();
                // gs://swdnew-d8d42.appspot.com
                //FirebaseStorage storage = mAuth.getCurrentUser().
                StorageReference mStorageReference = storage.getReferenceFromUrl("gs://swdlaravel-cfa17.appspot.com/").child("new_cus_images/" + imagepathName);
                mStorageReference.putFile(filePath)
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                //calculating progress percentage
                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                                //displaying percentage in progress dialog
                                progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                            }
                        })
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                //if the upload is successfull
                                //hiding the progress dialog

                                try{

                                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                                    Log.d("***", "downloadUriLIST: " + downloadUri);
                                   // pictureDownloadurl = new ArrayList<>();
                                   // pictureDownloadurl.add(downloadUri.toString()); // add uploaded image url to list
                                    SharedPref.getInstance(NewCustomerActivity.this).setGlobalVal("image", downloadUri.toString());
                                    Log.d("***", "downloadUriSF: " + SharedPref.getInstance(NewCustomerActivity.this).getGlobalVal("image"));
                                    //and displaying a success toast
                                    Toast.makeText(getApplicationContext(), "Picture Uploaded ", Toast.LENGTH_SHORT).show();
                                    fabSave.setEnabled(true);
                                    progressDialog.dismiss();
                                }catch (Exception e){
                                   // e.printStackTrace();
                                    fabSave.setEnabled(false);
                                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                    alert.setMessage("Your device or OS not supported for this function. Please try to get one from Gallery or " +
                                            "contact service provider.")
                                            .setTitle("Unsupported camera function");
                                    alert.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();

                                        }
                                    });
                                    AlertDialog dialog = alert.create();
                                    dialog.show();
                                }

                            }

                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                //if the upload is not successfull
                                //hiding the progress dialog
                                progressDialog.dismiss();
                                //and displaying error message
                                Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //if there is not any file
        else {
            Toast.makeText(this, "Can not find fill path", Toast.LENGTH_SHORT).show();
        }
    }

    public void customerListDialogBox() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.details_search_item);
        dialog.setCancelable(true);

        final SearchView search = (SearchView) dialog.findViewById(R.id.et_search);
        final ListView Detlist = (ListView) dialog.findViewById(R.id.lv_product_list);

        final NewCustomerController newCustomerDS = new NewCustomerController(this);

        Detlist.clearTextFilter();

            newCustomerArrayList = newCustomerDS.getAllCusDetailsForEdit("");
            Detlist.setAdapter(new Customer_Adapter(this, newCustomerArrayList));

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                        newCustomerArrayList = newCustomerDS.getAllCusDetailsForEdit(newText);
                        Detlist.setAdapter(new Customer_Adapter(getApplicationContext(), newCustomerArrayList));

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return false;
            }
        });
        //-=========================================
        Detlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                customerName.setEnabled(true);
                addressline1.setEnabled(true);
                addressline2.setEnabled(true);
                contactNumber.setEnabled(true);
                CUSFLG = 0;
//                if(newCustomerArrayList.get(position).getCUSTOMER_NIC().trim().equals(null))
//                    editTextCNic.setText("");
//                else
//                    editTextCNic.setText(""+newCustomerArrayList.get(position).getCUSTOMER_NIC());
                if(newCustomerArrayList.get(position).getCUSTOMER_ID().trim().equals(null)) {
                    customerCode.setText("");
                    businessRegno.setText("");
                    route.setText("");
                }else {
                    customerCode.setText("" + newCustomerArrayList.get(position).getCUSTOMER_ID());
                    businessRegno.setText(""+newCustomerArrayList.get(position).getCUSTOMER_ID());
                    route.setText(""+new RouteDetController(context).getRouteCodeByDebCode(newCustomerArrayList.get(position).getCUSTOMER_ID().trim()));
                }

                if(newCustomerArrayList.get(position).getC_TOWN().trim().equals(null)) {
                    district.setText("");
                    town.setText("");
                }else {
                    district.setText("" + newCustomerArrayList.get(position).getC_TOWN());
                    town.setText(""+newCustomerArrayList.get(position).getC_TOWN());
                }

                customerName.setText(""+newCustomerArrayList.get(position).getNAME());
                addressline1.setText(""+newCustomerArrayList.get(position).getADDRESS1());
                addressline2.setText(""+newCustomerArrayList.get(position).getADDRESS2());
                contactNumber.setText(""+newCustomerArrayList.get(position).getPHONE());

                if(newCustomerArrayList.get(position).getCITY().isEmpty() || newCustomerArrayList.get(position).getCITY().trim().equals(null))
                    city.setText("");
                else
                    city.setText(""+newCustomerArrayList.get(position).getCITY());
//
                if(newCustomerArrayList.get(position).getCONTACTPERSON().isEmpty() || newCustomerArrayList.get(position).getCONTACTPERSON().trim().equals(null))
                    contactPerson.setText("");
                else
                    contactPerson.setText(""+newCustomerArrayList.get(position).getCONTACTPERSON());

                if(newCustomerArrayList.get(position).getMOBILE().isEmpty() || newCustomerArrayList.get(position).getMOBILE().trim().equals(null))
                    mobile.setText("");
                else
                    mobile.setText(""+newCustomerArrayList.get(position).getMOBILE());

                if(newCustomerArrayList.get(position).getFAX().isEmpty() || newCustomerArrayList.get(position).getFAX().trim().equals(null))
                    fax.setText("");
                else
                    fax.setText(""+newCustomerArrayList.get(position).getFAX());

                if(newCustomerArrayList.get(position).getE_MAIL().trim().equals(null))
                    emailaddress.setText("");
                else
                    emailaddress.setText(""+newCustomerArrayList.get(position).getE_MAIL());



                customerImg.setBackground(ContextCompat.getDrawable(context, R.drawable.shadow_image_man));


                dialog.dismiss();
            }
        });


        dialog.show();
    }

    public void ClearFiled() {

        editTextCNic.setText("");
//        OtherCode.setText("");
        businessRegno.setText("");
        district.setText("");
        town.setText("");
        route.setText("");
        customerName.setText("");
        addressline1.setText("");
        addressline2.setText("");
        contactNumber.setText("");
        city.setText("");
        contactPerson.setText("");
        mobile.setText("");

        fax.setText("");
        emailaddress.setText("");
        customerImg.setBackground(ContextCompat.getDrawable(context, R.drawable.shadow_image_man));

    }


    private void exitData() {
        // UtilityContainer.mLoadFragment(new CustomerRegMain(), getActivity());
        android.widget.Toast.makeText(getApplicationContext(), "Success", android.widget.Toast.LENGTH_LONG).show();
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("OnResume", "oo");

    }






    public void popupDialog(final int Flag) {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.details_search_item);
        dialog.setCancelable(true);

        final SearchView search = (SearchView) dialog.findViewById(R.id.et_search);
        final ListView Detlist = (ListView) dialog.findViewById(R.id.lv_product_list);

        final RouteController routeDS = new RouteController(this);
        final DistrictController distDS = new DistrictController(this);
        final TownController townDS = new TownController(this);

        Detlist.clearTextFilter();
        if (Flag == 1) {
            districtArrayList = distDS.getAllDistrict();
            Detlist.setAdapter(new DistrictAdapter(this, districtArrayList));
        } else if (Flag == 2) {
            townArrayList = townDS.getAllTowns();
            Detlist.setAdapter(new TownAdapter(this, townArrayList));
        } else if (Flag == 3) {
            routeArrayList = routeDS.getRoute();
            Detlist.setAdapter(new RouteAdapter(this, routeArrayList));
        }

        Detlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (Flag == 3) {
                    route.setText(routeArrayList.get(position).getFROUTE_ROUTECODE());
                } else if (Flag == 2) {
                    town.setText(townArrayList.get(position).getTownCode());
                } else if (Flag == 1) {
                    district.setText(districtArrayList.get(position).getDistrictCode());
                }

                dialog.dismiss();
            }
        });


        dialog.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NewCustomerActivity.this, ActivityHome.class);
        startActivity(intent);
        finish();
    }
}
