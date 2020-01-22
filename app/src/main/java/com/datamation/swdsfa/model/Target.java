package com.datamation.swdsfa.model;

public class Target {
    String date;
    double targetAmt;
    double achieveAmt;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTargetAmt() {
        return targetAmt;
    }

    public void setTargetAmt(double targetAmt) {
        this.targetAmt = targetAmt;
    }

    public double getAchieveAmt() {
        return achieveAmt;
    }

    public void setAchieveAmt(double achieveAmt) {
        this.achieveAmt = achieveAmt;
    }
}
