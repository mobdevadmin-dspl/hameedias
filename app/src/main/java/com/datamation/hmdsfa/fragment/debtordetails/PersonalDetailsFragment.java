package com.datamation.hmdsfa.fragment.debtordetails;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.controller.CustomerController;
import com.datamation.hmdsfa.helpers.DatabaseHelper;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.model.Customer;

import java.text.NumberFormat;


public class PersonalDetailsFragment extends Fragment
{

    private TextView tvOutletName,
            tvOutletAddress,
            tvOwnerName,
            tvContact,
            tvOutletId,
            tvTypeAndClass,
            tvRemarks,
            tvLocation;
    private ImageView shopFront;
//    private ImageView showcase;
//    private ImageView promotionOne;
//    private ImageView promotionTwo;
    private RelativeLayout frontNA;
//    private RelativeLayout showcaseNA;
//    private RelativeLayout promotionOneNA;
//    private RelativeLayout promotionTwoNA;

    private Customer outlet;
    private String debCode;
    private DatabaseHelper dbHandler;

    private NumberFormat numberFormat;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_details, container, false);
        SharedPref pref = SharedPref.getInstance(getActivity());

        tvOutletName = (TextView) view.findViewById(R.id.fragment_out_det_personal_tv_outlet);
        tvOutletId = (TextView) view.findViewById(R.id.fragment_out_det_personal_tv_outlet_id);
        tvOutletAddress = (TextView) view.findViewById(R.id.fragment_out_det_personal_tv_address);
        tvOwnerName = (TextView) view.findViewById(R.id.fragment_out_det_personal_tv_owner);
        tvContact = (TextView) view.findViewById(R.id.fragment_out_det_personal_tv_contact);
        tvTypeAndClass = (TextView) view.findViewById(R.id.fragment_out_det_personal_tv_type_and_class);
        tvRemarks = (TextView) view.findViewById(R.id.fragment_out_det_personal_tv_opbl);
        tvLocation = (TextView) view.findViewById(R.id.fragment_out_det_personal_tv_location);

        numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(false);
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);

        debCode = pref.getSelectedDebCode();
        outlet = new CustomerController(getActivity()).getSelectedCustomerByCode(debCode);
        Log.d("###", "debcode: "+debCode);
        Log.d("###", "onCreateView: "+outlet.getCusImage());


        if (outlet == null) {
            getActivity().onBackPressed();
            Toast.makeText(getActivity(), "Invalid outlet", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadDebtor(outlet);
        }



        shopFront = (ImageView) view.findViewById(R.id.fragment_out_det_personal_iv_front_image_view);
        //showcase = (ImageView) view.findViewById(R.id.fragment_out_det_personal_iv_show_image_view);
        //promotionOne = (ImageView) view.findViewById(R.id.fragment_out_det_personal_iv_promotion_one_image_view);
        //promotionTwo = (ImageView) view.findViewById(R.id.fragment_out_det_personal_iv_promotion_two_image_view);


        frontNA = (RelativeLayout) view.findViewById(R.id.fragment_out_det_personal_rel_front_image_view_na);
//        showcaseNA = (RelativeLayout) view.findViewById(R.id.fragment_out_det_personal_rel_show_image_view_na);
//        promotionOneNA = (RelativeLayout) view.findViewById(R.id.fragment_out_det_personal_rel_promotion_one_image_view_na);
//        promotionTwoNA = (RelativeLayout) view.findViewById(R.id.fragment_out_det_personal_rel_promotion_two_image_view_na);

        loadWithGlide();

        tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double latitude;
                    double longitude;
//                    if ((latitude = outlet.getLatitude()) != 0 && (longitude = outlet.getLongitude()) != 0) {
//
//                        String strUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude;
//                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(strUri));
//                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
//                        startActivity(intent);
//                    } else {
//                        Toast.makeText(PersonalDetailsFragment.this.getActivity(), "Location haven't set", Toast.LENGTH_LONG).show();
//                    }
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "Google maps not installed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        decorateView();

        return view;
    }

    public void loadWithGlide() {
        Glide.with(this)
//                .load(SharedPref.getInstance(getActivity()).getGlobalVal("image"))
                .load(outlet.getCusImage())
                .into(shopFront);
        Log.d("*img", "loadWithGlide: "+outlet.getCusImage());
    }

    private void loadDebtor(Customer customer)
    {
        tvOutletName.setText(customer.getCusName());
        tvOutletId.setText(customer.getCusCode());
        tvOutletAddress.setText(customer.getCusAdd1() + ", " + customer.getCusAdd2());
        tvOwnerName.setText(customer.getCusName());
        tvContact.setText(customer.getCusMob());
        tvTypeAndClass.setText(customer.getCusStatus());
        tvLocation.setText(customer.getCusRoute());
    }

    private void decorateView() {
//        tvOutletName.setText(outlet.getCusName() + "(" + outlet.getCusCode() + ")");
//        tvOutletId.setText(String.valueOf(outlet.getCusCode()));
//        tvOutletAddress.setText(outlet.getCusAdd1());
//        tvContact.setText("Land : " + outlet.getCusMob());
//        List<OutletType> outletTypes = dbHandler.getOutletTypes();
//        String typeAndClassText = "";
//        for (OutletType outletType : outletTypes) {
//            if (outletType.getTypeId() == outlet.getOutletType()) {
//                typeAndClassText = typeAndClassText.concat(outletType.getTypeName()).concat(", ");
//                break;
//            }
//        }
//        List<OutletClass> outletClasses = dbHandler.getOutletClasses();
//        for (OutletClass outletClass : outletClasses) {
//            if (outletClass.getClassId() == outlet.getOutletClass()) {
//                typeAndClassText = typeAndClassText.concat(outletClass.getClassName());
//                typeAndClassText = typeAndClassText.concat(" (" + outletClass.getClassRange() + ")");
//                break;
//            }
//        }
//        tvTypeAndClass.setText(typeAndClassText);
//        tvRemarks.setText(outlet.getComments());
//
//        if (outlet.getLatitude() != 0 && outlet.getLongitude() != 0) {
//
//            String type;
//            if (outlet.getLocationType() == LocationProvider.LOCATION_TYPE_GPS) {
//                type = "GPS";
//            } else if (outlet.getLocationType() == LocationProvider.LOCATION_TYPE_NETWORK) {
//                type = "Network";
//            } else {
//                type = "Undefined";
//            }
//
//            tvLocation.setText(outlet.getLatitude() + ", " + outlet.getLongitude()
//                    + " (" + type + " : " + numberFormat.format(outlet.getLocationAccuracy()) + "m)");
//        } else {
//            tvLocation.setText("Not set!");
//        }
//
//        shopFront.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                displayLargeImage();
//
//                Intent intent = new Intent(getActivity(), ImageViewActivity.class);
//
//                ActivityTransitionLauncher.with(getActivity()).from(v).launch(intent);
//
//            }
//        });

        //DealerImageHolder imageHolder = outlet.getImageHolder();
        //if (imageHolder != null) {
//            Log.d(LOG_TAG, "Front : " + imageHolder.getFrontImage());
//            if (imageHolder.getFrontImage() != null) {
//                final File image = new File(Environment.getExternalStorageDirectory(), "/SIDDHALEPA/Outlet/" + imageHolder.getFrontImage().getImageUri());
//                if (image.exists()) {
//                    frontNA.setVisibility(View.INVISIBLE);
//                    frontNA.setEnabled(false);
//                    shopFront.setVisibility(View.VISIBLE);
//                    Uri uri = Uri.fromFile(image);
//                    Picasso.with(getActivity()).load(uri).resize(512, 512).centerInside().into(shopFront);
//
//                    shopFront.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(getActivity(), ImageViewActivity.class);
//                            intent.putExtra("image_file", image);
//                            intent.putExtra("image_header", "Front Image");
//                            ActivityTransitionLauncher.with(getActivity()).from(v).launch(intent);
//                        }
//                    });
//
//                }
//            }

//            Log.d(LOG_TAG, "Showcase : " + imageHolder.getShowcaseImage());
//            if (imageHolder.getShowcaseImage() != null) {
//                final File image = new File(Environment.getExternalStorageDirectory(), "/SIDDHALEPA/Outlet/" + imageHolder.getShowcaseImage().getImageUri());
//                if (image.exists()) {
//                    showcaseNA.setVisibility(View.INVISIBLE);
//                    showcaseNA.setEnabled(false);
//                    showcase.setVisibility(View.VISIBLE);
//                    Uri uri = Uri.fromFile(image);
//                    Picasso.with(getActivity()).load(uri).resize(512, 512).centerInside().into(showcase);
//
//                    showcase.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(getActivity(), ImageViewActivity.class);
//                            intent.putExtra("image_file", image);
//                            intent.putExtra("image_header", "Showcase Image");
//                            ActivityTransitionLauncher.with(getActivity()).from(v).launch(intent);
//                        }
//                    });
//
//                }
//            }

//            Log.d(LOG_TAG, "Promo1 : " + imageHolder.getPromoImage1());
//            if (imageHolder.getPromoImage1() != null) {
//                final File image = new File(Environment.getExternalStorageDirectory(), "/SIDDHALEPA/Outlet/" + imageHolder.getPromoImage1().getImageUri());
//                if (image.exists()) {
//                    promotionOneNA.setVisibility(View.INVISIBLE);
//                    promotionOneNA.setEnabled(false);
//                    promotionOne.setVisibility(View.VISIBLE);
//                    Uri uri = Uri.fromFile(image);
//                    Picasso.with(getActivity()).load(uri).resize(512, 512).centerInside().into(promotionOne);
//
//                    promotionOne.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(getActivity(), ImageViewActivity.class);
//                            intent.putExtra("image_file", image);
//                            intent.putExtra("image_header", "Promotion Image 1");
//                            ActivityTransitionLauncher.with(getActivity()).from(v).launch(intent);
//                        }
//                    });
//
//                }
//            }

//            Log.d(LOG_TAG, "Promo2 : " + imageHolder.getPromoImage2());
//            if (imageHolder.getPromoImage2() != null) {
//                final File image = new File(Environment.getExternalStorageDirectory(), "/SIDDHALEPA/Outlet/" + imageHolder.getPromoImage2().getImageUri());
//                if (image.exists()) {
//                    promotionTwoNA.setVisibility(View.INVISIBLE);
//                    promotionTwoNA.setEnabled(false);
//                    promotionTwo.setVisibility(View.VISIBLE);
//                    Uri uri = Uri.fromFile(image);
//                    Picasso.with(getActivity()).load(uri).resize(512, 512).centerInside().into(promotionTwo);
//
//                    promotionTwo.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(getActivity(), ImageViewActivity.class);
//                            intent.putExtra("image_file", image);
//                            intent.putExtra("image_header", "Promotion Image 2");
//                            ActivityTransitionLauncher.with(getActivity()).from(v).launch(intent);
//                        }
//                    });
//                }
//            }
        //}
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == ValueHolder.ENTER_DEALER_DETAIL && resultCode == Activity.RESULT_OK) {
//            outlet = (Outlet) data.getExtras().get("updated_outlet");
//            decorateView();
//        }
    }

}
