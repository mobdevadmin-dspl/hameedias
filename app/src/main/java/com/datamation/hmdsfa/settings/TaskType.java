package com.datamation.hmdsfa.settings;


public enum TaskType {

    ItenrDeb(1),
    Controllist(2),
    Customers(3),
    Settings(4),
    Reference(4);

    int value;

    private TaskType(int value) {
        this.value = value;
    }

}
