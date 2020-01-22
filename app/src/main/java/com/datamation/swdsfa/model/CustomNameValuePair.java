package com.datamation.swdsfa.model;

/**
 * Created by Rashmi on 1/23/2019.
 * Model class to hold a name value pair.
 * Created as a substitution to the deprecated {@link org.apache.http.NameValuePair}.
 */
public class CustomNameValuePair {

    private String name, value;

    public CustomNameValuePair(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name + " : " + value;
    }
}
