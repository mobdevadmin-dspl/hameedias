package com.datamation.hmdsfa.settings;


public enum TaskTypeDownload {

    ItenrDeb(1),
    Controllist(2),
    Customers(3),
    Settings(4),
    Reference(5),
    ItemBundle(6),
    VAT(7),
    Items(8),
    Reason(9),
    Bank(10),
    Expense(11),
    Route(12),
    RouteDet(13),
    Freeslab(14),
    Freemslab(15),
    Freehed(16),
    Freedet(17),
    Freedeb(18),
    Freeitem(19),
    Iteneryhed(20),
    Itenerydet(21),
    Stock(22),
    Salesprice(23),
    Discount(24),
    fddbnote(25),
    VanStock(26),
    Barcodevarient(27),
    MonTarget(28),
    MonSales(29);

    int value;

    private TaskTypeDownload(int value) {
        this.value = value;
    }

}
