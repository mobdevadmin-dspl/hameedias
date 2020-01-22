package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by rashmi on 3/11/2019.
 */

public class User implements Serializable {
    String Name, UserName, Password, Mobile, Address, target, Status, Code, Prefix, macID, loccode, email;

    public User(){

    }

    public User(String name, String username, String password, String mobile, String address, String macid, String status, String code, String prefix,String target,String email){
        this.Name = name;
        this.UserName = username;
        this.Password = password;
        this.Mobile = mobile;
        this.Address = address;
        this.target = target;
        this.Status = status;
        this.Code = code;
        this.Prefix = prefix;
        this.macID = macid;
        this.email = email;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoccode() {
        return loccode;
    }

    public void setLoccode(String loccode) {
        this.loccode = loccode;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getMacID() {
        return macID;
    }

    public void setMacID(String macID) {
        this.macID = macID;
    }

    public String getPrefix() {
        return Prefix;
    }

    public void setPrefix(String prefix) {
        Prefix = prefix;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public static User parseUser(JSONObject instance) throws JSONException, NumberFormatException {

        if(instance != null) {
            User user = new User();
            user.setCode(instance.getString("RepCode"));
            user.setName(instance.getString("RepName"));
            user.setLoccode(instance.getString("LocCode"));
            user.setPassword(instance.getString("RepTCode"));
        //    user.setTarget(instance.getString("Target"));
            user.setMobile(instance.getString("RepMobil"));
            //  user.setAddress(instance.getString("Address"));
            user.setStatus(instance.getString("Status"));
            user.setPrefix(instance.getString("RepPrefix"));
            user.setMacID(instance.getString("macid"));
            return user;
        }

        return null;
    }

    @Override
    public String toString() {
        return "User{" +
                "Name='" + Name + '\'' +
                ", UserName='" + UserName + '\'' +
                ", Password='" + Password + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", Address='" + Address + '\'' +
                ", target='" + target + '\'' +
                ", Status='" + Status + '\'' +
                ", Code='" + Code + '\'' +
                ", Prefix='" + Prefix + '\'' +
                ", macID='" + macID + '\'' +
                ", loccode='" + loccode + '\'' +
                '}';
    }
}
