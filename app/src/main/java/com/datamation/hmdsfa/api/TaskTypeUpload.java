package com.datamation.hmdsfa.api;

public enum TaskTypeUpload

{
    UPLOAD_ORDER(1),
    UPLOAD_INVOICE(2),
    UPLOAD_DELETED_INVOICE(3),
    UPLOAD_RETURNS(4),
    UPLOAD_RECEIPT(5),
    UPLOAD_NONPROD(6),
    UPLOAD_EXPENSE(7),
    UPLOAD_ATTENDANCE(8);
//
//    UPLOAD_COORDINATES(6),
//    UPLOAD_IMAGES(7),
//    UPLOAD_NEWCUS(8),
//    UPLOAD_EDTCUS(9),
//    UPLOAD_TKN(10);
    int value;

    private TaskTypeUpload(int value) {
        this.value = value;
    }
}
