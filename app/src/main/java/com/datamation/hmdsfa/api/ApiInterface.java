package com.datamation.hmdsfa.api;

import com.datamation.hmdsfa.model.Order;
import com.datamation.hmdsfa.model.apimodel.ReadJsonList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Rashmi on 24/01/2019.
 */

public interface ApiInterface {

    @GET("GetdatabaseNames/mobile123")//01
    Call<ReadJsonList> getDatabaseResult();

    @GET("fSalRep/mobile123/{dbname}/{macid}")//01
    Call<ReadJsonList> getSalRepResult(@Path("dbname") String dbname,@Path("macid") String macid);

    @GET("Fdebtor/mobile123/{dbname}/{repcode}")//02
    Call<ReadJsonList> getDebtorResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("fControl/mobile123/{dbname}")//03
    Call<ReadJsonList> getControlResult(@Path("dbname") String dbname);

    @GET("fItemLoc/mobile123/{dbname}/{repcode}")//04
    Call<ReadJsonList> getItemLocResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("fItemPri/mobile123/{dbname}/{repcode}")//05
    Call<ReadJsonList> getItemPriResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("fItems/mobile123/{dbname}/{repcode}")//06
    Call<ReadJsonList> getItemsResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("fLocations/mobile123/{dbname}/{repcode}")//07
    Call<ReadJsonList> getLocationsResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("Ftax/mobile123/{dbname}")//08
    Call<ReadJsonList> getTaxResult(@Path("dbname") String dbname);

    @GET("Ftaxhed/mobile123/{dbname}")//09
    Call<ReadJsonList> getTaxHedResult(@Path("dbname") String dbname);

    @GET("Ftaxdet/mobile123/{dbname}")//10
    Call<ReadJsonList> getTaxDetResult(@Path("dbname") String dbname);

    @GET("FnearDebtor/mobile123/{dbname}")//11
    Call<ReadJsonList> getNearDebtorResult(@Path("dbname") String dbname);

    @GET("FCompanyBranch/mobile123/{dbname}/{repcode}")//12
    Call<ReadJsonList> getCompanyBranchResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("fCompanySetting/mobile123/{dbname}")//13
    Call<ReadJsonList> getCompanySettingResult(@Path("dbname") String dbname);

    @GET("freason/mobile123/{dbname}")//14
    Call<ReadJsonList> getReasonResult(@Path("dbname") String dbname);

    @GET("fexpense/mobile123/{dbname}")//15
    Call<ReadJsonList> getExpenseResult(@Path("dbname") String dbname);

    @GET("fFreehed/mobile123/{dbname}/{repcode}")//16
    Call<ReadJsonList> getFreehedResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("fFreeslab/mobile123/{dbname}")//17
    Call<ReadJsonList> getFreeSlabResult(@Path("dbname") String dbname);

    @GET("fFreedet/mobile123/{dbname}")//18
    Call<ReadJsonList> getFreeDetResult(@Path("dbname") String dbname);

    @GET("fFreedeb/mobile123/{dbname}")//19
    Call<ReadJsonList> getFreedebResult(@Path("dbname") String dbname);

    @GET("ffreeitem/mobile123/{dbname}")//20
    Call<ReadJsonList> getFreeitemResult(@Path("dbname") String dbname);

    @GET("ffreemslab/mobile123/{dbname}")//21
    Call<ReadJsonList> getFreeMSlabResult(@Path("dbname") String dbname);

    @GET("fbank/mobile123/{dbname}")//22
    Call<ReadJsonList> getBankResult(@Path("dbname") String dbname);

    @GET("fdisched/mobile123/{dbname}/{repcode}")//23
    Call<ReadJsonList> getDiscHedResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("fdiscdet/mobile123/{dbname}")//24
    Call<ReadJsonList> getDiscDetResult(@Path("dbname") String dbname);

    @GET("fdiscslab/mobile123/{dbname}")//25
    Call<ReadJsonList> getDiscSlabResult(@Path("dbname") String dbname);

    @GET("fdiscdeb/mobile123/{dbname}/{repcode}")//26
    Call<ReadJsonList> getDiscDebResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("fTown/mobile123/{dbname}")//27
    Call<ReadJsonList> getTownResult(@Path("dbname") String dbname);

    @GET("froute/mobile123/{dbname}/{repcode}")//28
    Call<ReadJsonList> getRouteResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("froutedet/mobile123/{dbname}/{repcode}")//29
    Call<ReadJsonList> getRouteDetResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("FItenrHed/mobile123/{dbname}/{repcode}/{year}/{month}")//30
    Call<ReadJsonList> getItenrHedResult(@Path("dbname") String dbname,@Path("repcode") String repcode,@Path("year") String year,@Path("month") String month);

    @GET("FItenrDet/mobile123/{dbname}/{repcode}/{year}/{month}")//31
    Call<ReadJsonList> getItenrDetResult(@Path("dbname") String dbname,@Path("repcode") String repcode,@Path("year") String year,@Path("month") String month);

    @GET("RepLastThreeInvDet/mobile123/{dbname}/{repcode}")//32
    Call<ReadJsonList> getLastThreeInvDetResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("RepLastThreeInvHed/mobile123/{dbname}/{repcode}")//33
    Call<ReadJsonList> getLastThreeInvHedResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("fDdbNoteWithCondition/mobile123/{dbname}/{repcode}")//34
    Call<ReadJsonList> getOutstandingResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("bundlebarcode/mobile123/{dbname}/{repcode}")//35
    Call<ReadJsonList> getItemBundle(@Path("dbname") String dbname,@Path("repcode") String repcode);

//    @FormUrlEncoded
//    @POST("/insertFordh")
//    Call<ReadJsonList> uploadOrder(@Body ArrayList<Order> orderlist);

    @GET("VATMaster/mobile123/{dbname}")//36
    Call<ReadJsonList> getVATResult(@Path("dbname") String dbname);

    @GET("fitedebdet/mobile123/{dbname}/{repcode}/{year}/{month}")//37
    Call<ReadJsonList> getIteaneryDeb(@Path("dbname") String dbname,@Path("repcode") String repcode,@Path("year") String year,@Path("month") String month);

    @GET("Salesprice/mobile123/{dbname}")//38
    Call<ReadJsonList> getSalesPriceResult(@Path("dbname") String dbname);

    @GET("CusProductDis/mobile123/{dbname}/{repcode}")//38
    Call<ReadJsonList> getDiscountResult(@Path("dbname") String dbname,@Path("repcode") String repcode);
    @POST("insertFOrdHedNew")
    Call<String> uploadOrder(@Body JsonArray orderlist, @Header("Content-Type") String cont_type);
    //Call<String> uploadOrder(@Body String orderlist, @Header("Content-Type") String cont_type);

    @POST("insertFInvHed")
    Call<String> uploadInvoice(@Body JsonArray orderlist, @Header("Content-Type") String cont_type);
    //******rashmi 29-07-2020********
    @POST("insertFDaynPrdHed")
    Call<String> uploadNonProd(@Body JsonArray nonpList, @Header("Content-Type") String cont_type);

    @POST("updateDebtorCordinates")
    Call<String> uploadDebtorCordinates(@Body JsonArray debtorCordinateList,@Header("Content_Type") String cont_type);

    @POST("updateDebtorImageURL")
    Call<String> uploadDebtorImg(@Body JsonArray debtorImgList, @Header("Content_Type")String cont_type);
    @POST("updateEditedDebtors")
    Call<String> uploadEditedDebtors(@Body JsonArray debtorList, @Header("Content_Type")String cont_type);
    @POST("updateEmailUpdatedSalRep")
    Call<String> uploadRepEmail(@Body JsonArray repList, @Header("Content_Type") String cont_type);

    @POST("insertCustomer")
    Call<String> uploadNCustomer(@Body JsonArray customerList, @Header("Content_Type") String cont_type);

    @POST("insertTourInfo")
    Call<String> uploadAttendence(@Body JsonArray attendenceList, @Header("Content_Type") String cont_type);

    @POST("insertDayExpense")
    Call<String> uploadExpense(@Body JsonArray expenseList, @Header("Content_Type") String cont_type);

    @POST("updateFirebaseTokenID")
    Call<String> uploadfTokenID(@Body JsonArray tokenIdList, @Header("Content_Type") String cont_type);
}
