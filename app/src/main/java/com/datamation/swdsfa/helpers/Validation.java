package com.datamation.swdsfa.helpers;

import android.content.Context;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    Context context;

    public Validation (Context context){
        this.context = context;
    }

    public boolean isValid(String email,int emailLength , String mobile,int mobileLength ,String contactNumber,int contactNumberLength , String fax,int faxLength ) {

        boolean isValid = false;

        if (emailLength > 0 &&  mobileLength > 0 && faxLength > 0&& contactNumberLength > 0) {
            if (!isValidMobile(mobile) || !isValidFax(fax) || !isValidPhone(contactNumber) || !isEmailValid( email)) {
                isValid = false;
                Toast.makeText(context, "Invalid Data , Please check mobile , fax , contact number or email is valid or not ", Toast.LENGTH_SHORT).show();
            } else {
                isValid = true;
            }
        } else if (emailLength  > 0 && mobileLength > 0 && faxLength > 0){
            if (!isValidMobile(mobile) || !isValidFax(fax) || !isEmailValid(email)) {
                isValid = false;
                Toast.makeText(context, "Invalid Data , Please check mobile , fax or email is valid or not ", Toast.LENGTH_SHORT).show();
            } else {
                isValid = true;
            }
        } else if ( mobileLength > 0 && faxLength > 0 && contactNumberLength > 0) {
            if (!isValidMobile(mobile) || !isValidFax(fax) ||  !isValidPhone(contactNumber)) {
                isValid = false;
                Toast.makeText(context, "Invalid Data , Please check mobile , fax or contact number is valid or not ", Toast.LENGTH_SHORT).show();
            } else {
                isValid = true;
            }
        } else if(emailLength > 0 && mobileLength > 0 && contactNumberLength > 0){
            if (!isEmailValid(email) || !isValidFax(mobile) ||  !isValidPhone(contactNumber)) {
                isValid = false;
                Toast.makeText(context, "Invalid Data , Please check email , mobile or contact number is valid or not ", Toast.LENGTH_SHORT).show();
            } else {
                isValid = true;
            }
        } else if (emailLength > 0 && faxLength > 0 && contactNumberLength > 0) {
            if (!isEmailValid(email) || !isValidFax(fax) ||  !isValidPhone(contactNumber)) {
                isValid = false;
                Toast.makeText(context, "Invalid Data , Please check email , fax or contact number is valid or not ", Toast.LENGTH_SHORT).show();
            } else {
                isValid = true;
            }
        } else if (emailLength > 0 &&  mobileLength > 0){
            if (!isValidMobile(mobile) || !isEmailValid(email)) {
                isValid = false;
                Toast.makeText(context, "Invalid Data , Please check mobile  or email is valid or not ", Toast.LENGTH_SHORT).show();
            } else {
                isValid = true;
            }
        }else if(emailLength > 0 && contactNumberLength > 0 ){
            if (!isValidPhone(contactNumber) || !isEmailValid(email)) {
                isValid = false;
                Toast.makeText(context, "Invalid Data , Please check contact number  or email is valid or not ", Toast.LENGTH_SHORT).show();
            } else {
                isValid = true;
            }
        }else if(contactNumberLength > 0 && faxLength > 0){
            if (!isValidPhone(contactNumber) ||  !isValidFax(fax)) {
                isValid = false;
                Toast.makeText(context, "Invalid Data , Please check contact number  or fax is valid or not ", Toast.LENGTH_SHORT).show();
            } else {
                isValid = true;
            }
        } else if(emailLength > 0 && faxLength > 0){
            if ( !isValidFax(fax) || !isEmailValid(email)) {
                isValid = false;
                Toast.makeText(context, "Invalid Data , Please check fax  or email is valid or not ", Toast.LENGTH_SHORT).show();
            } else {
                isValid = true;
            }
        }else if(mobileLength  > 0 &&  faxLength > 0 ){
            if ( !isValidFax(fax) || !isValidMobile(mobile)) {
                isValid = false;
                Toast.makeText(context, "Invalid Data , Please check fax  or mobile is valid or not ", Toast.LENGTH_SHORT).show();
            } else {
                isValid = true;
            }
        } else if(mobileLength  > 0 && contactNumberLength > 0){
            if( !isValidMobile(mobile) || !isValidPhone(contactNumber) ) {
                isValid = false;
                Toast.makeText(context, "Invalid Data , Please check contact number  or mobile is valid or not ", Toast.LENGTH_SHORT).show();
            } else {
                isValid = true;
            }
        }
        else if (emailLength > 0) {
            if (!isEmailValid(email)) {
                isValid = false;
                Toast.makeText(context, "Invalid Data , Please check email is valid or not ", Toast.LENGTH_SHORT).show();
            } else {
                isValid = true;
            }
        }else if ( mobileLength  > 0) {
            if (!isValidMobile(mobile)) {
                isValid = false;
                Toast.makeText(context, "Invalid Data , Please check mobile is valid or not ", Toast.LENGTH_SHORT).show();
            } else {
                isValid = true;
            }
        } else if ( faxLength  > 0) {
            if (!isValidFax(fax)) {
                isValid = false;
                Toast.makeText(context, "Invalid Data , Please check fax is valid or not ", Toast.LENGTH_SHORT).show();
            } else {
                isValid = true;
            }
        } else if(contactNumberLength > 0)
        {
            if (!isValidPhone(contactNumber)) {
                isValid = false;
                Toast.makeText(context, "Invalid Data , Please check contact number is valid or not ", Toast.LENGTH_SHORT).show();
            } else {
                isValid = true;
            }
        }

        return isValid;
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

    private boolean isValidMobile(String phone) {
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() >=10 && phone.length() <= 13;
        }
        return false;
    }

    private boolean isValidPhone(String phone) {
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() >= 10 && phone.length() <= 13;
        }
        return false;
    }

    private boolean isValidFax(String phone) {
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() >=10 && phone.length() <= 13;
        }
        return false;
    }
}
