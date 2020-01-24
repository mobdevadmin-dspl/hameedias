package com.datamation.hmdsfa.api;

import com.datamation.hmdsfa.model.apimodel.ReadJsonList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Rashmi on 24/01/2019.
 */

public interface ApiInterface {

    @GET("fSalRep/mobile123/{dbname}/{macid}")
    Call<ReadJsonList> getSalRepResult(@Path("dbname") String dbname,@Path("macid") String macid);

    @GET("Fdebtor/mobile123/{dbname}/{repcode}")
    Call<ReadJsonList> getDebtorResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("fControl/mobile123/{dbname}")
    Call<ReadJsonList> getControlResult(@Path("dbname") String dbname);

    @GET("fItemLoc/mobile123/{dbname}/{repcode}")
    Call<ReadJsonList> getItemLocResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("fItemPri/mobile123/{dbname}/{repcode}")
    Call<ReadJsonList> getItemPriResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("fItems/mobile123/{dbname}/{repcode}")
    Call<ReadJsonList> getItemsResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("fLocations/mobile123/{dbname}/{repcode}")
    Call<ReadJsonList> getLocationsResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("Ftax/mobile123/{dbname}")
    Call<ReadJsonList> getTaxResult(@Path("dbname") String dbname);

    @GET("Ftaxhed/mobile123/{dbname}")
    Call<ReadJsonList> getTaxHedResult(@Path("dbname") String dbname);

    @GET("Ftaxdet/mobile123/{dbname}")
    Call<ReadJsonList> getTaxDetResult(@Path("dbname") String dbname);

    @GET("FnearDebtor/mobile123/{dbname}/{repcode}")
    Call<ReadJsonList> getNearDebtorResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("FCompanyBranch/mobile123/{dbname}/{repcode}")
    Call<ReadJsonList> getCompanyBranchResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("fCompanySetting/mobile123/{dbname}")
    Call<ReadJsonList> getCompanySettingResult(@Path("dbname") String dbname);

    @GET("freason/mobile123/{dbname}")
    Call<ReadJsonList> getReasonResult(@Path("dbname") String dbname);

    @GET("fexpense/mobile123/{dbname}")
    Call<ReadJsonList> getExpenseResult(@Path("dbname") String dbname);

    @GET("fFreehed/mobile123/{dbname}/{repcode}")
    Call<ReadJsonList> getFreehedResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("fFreeslab/mobile123/{dbname}")
    Call<ReadJsonList> getFreeSlabResult(@Path("dbname") String dbname);

    @GET("fFreedet/mobile123/{dbname}")
    Call<ReadJsonList> getFreeDetResult(@Path("dbname") String dbname);

    @GET("fFreedeb/mobile123/{dbname}")
    Call<ReadJsonList> getFreedebResult(@Path("dbname") String dbname);

    @GET("ffreeitem/mobile123/{dbname}")
    Call<ReadJsonList> getFreeitemResult(@Path("dbname") String dbname);

    @GET("ffreemslab/mobile123/{dbname}")
    Call<ReadJsonList> getFreeMSlabResult(@Path("dbname") String dbname);

    @GET("fbank/mobile123/{dbname}")
    Call<ReadJsonList> getBankResult(@Path("dbname") String dbname);

    @GET("fdisched/mobile123/{dbname}/{repcode}")
    Call<ReadJsonList> getDiscHedResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("fdiscdet/mobile123/{dbname}")
    Call<ReadJsonList> getDiscDetResult(@Path("dbname") String dbname);

    @GET("fdiscslab/mobile123/{dbname}")
    Call<ReadJsonList> getDiscSlabResult(@Path("dbname") String dbname);

    @GET("fdiscdeb/mobile123/{dbname}/{repcode}")
    Call<ReadJsonList> getDiscDebResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("fTown/mobile123/{dbname}")
    Call<ReadJsonList> getTownResult(@Path("dbname") String dbname);

    @GET("froute/mobile123/{dbname}/{repcode}")
    Call<ReadJsonList> getRouteResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("froutedet/mobile123/{dbname}/{repcode}")
    Call<ReadJsonList> getRouteDetResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("FItenrHed/mobile123/{dbname}/{repcode}")
    Call<ReadJsonList> getItenrHedResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("FItenrDet/mobile123/{dbname}/{repcode}")
    Call<ReadJsonList> getItenrDetResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("RepLastThreeInvDet/mobile123/{dbname}/{repcode}")
    Call<ReadJsonList> getLastThreeInvDetResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("RepLastThreeInvHed/mobile123/{dbname}/{repcode}")
    Call<ReadJsonList> getLastThreeInvHedResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

    @GET("fDdbNoteWithCondition/mobile123/{dbname}/{repcode}")
    Call<ReadJsonList> getOutstandingResult(@Path("dbname") String dbname,@Path("repcode") String repcode);

}
