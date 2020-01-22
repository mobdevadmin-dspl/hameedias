package com.datamation.swdsfa.model;

public class FirebaseData {
    public String MEDIA_URL;
    public String MEDIA_FLAG;
    public String MEDIA_TYPE;

    public String getMEDIA_URL() {
        return MEDIA_URL;
    }

    public void setMEDIA_URL(String MEDIA_URL) {
        this.MEDIA_URL = MEDIA_URL;
    }

    public String getMEDIA_FLAG() {
        return MEDIA_FLAG;
    }

    public void setMEDIA_FLAG(String MEDIA_FLAG) {
        this.MEDIA_FLAG = MEDIA_FLAG;
    }

    public String getMEDIA_TYPE() {
        return MEDIA_TYPE;
    }

    public void setMEDIA_TYPE(String MEDIA_TYPE) {
        this.MEDIA_TYPE = MEDIA_TYPE;
    }

    @Override
    public String toString() {
        return "FirebaseData{" +
                "MEDIA_URL='" + MEDIA_URL + '\'' +
                ", MEDIA_FLAG='" + MEDIA_FLAG + '\'' +
                ", MEDIA_TYPE='" + MEDIA_TYPE + '\'' +
                '}';
    }
}
