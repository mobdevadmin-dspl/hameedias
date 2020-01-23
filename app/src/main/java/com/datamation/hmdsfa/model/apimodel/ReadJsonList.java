package com.datamation.hmdsfa.model.apimodel;

import com.datamation.hmdsfa.helpers.ListExpandHelper;
import com.datamation.hmdsfa.model.Control;
import com.datamation.hmdsfa.model.Debtor;
import com.datamation.hmdsfa.model.ItemLoc;
import com.datamation.hmdsfa.model.SalRep;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReadJsonList {

    @SerializedName("fSalRepResult")
    private List<SalRep> salRepResult = null;

    public List<SalRep> getSalRepResult() {
        return salRepResult;
    }

    @SerializedName("FdebtorResult")
    private List<Debtor> debtorResult = null;

    public List<Debtor> getDebtorResult() {
        return debtorResult;
    }

    @SerializedName("fControlResult")
    private List<Control> controlResult = null;

    public List<Control> getControlResult() {
        return controlResult;
    }

    @SerializedName("fItemLocResult")
    private List<ItemLoc> itemLocResult = null;

    public List<ItemLoc> getItemLocResult() {
        return itemLocResult;
    }

    @SerializedName("fItemPriResult")
    private List<ItemLoc> itemPriResult = null ;

    public List<ItemLoc> getItemPriResult() {
        return itemPriResult;
    }


}

