package com.datamation.swdsfa.settings;


public enum TaskType {

    FITEMPRI(8),
    FITEMS(9),
    FCOMPANYSETTING(10),

    UPLOAD_NEW_CUSTOMER(41),
    UPLOAD_PROMO_ORDER(42),
    UPLOAD_ISSUE_NOTE(43);

    int value;

    private TaskType(int value) {
        this.value = value;
    }

}
