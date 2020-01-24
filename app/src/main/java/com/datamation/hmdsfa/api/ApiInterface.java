package com.datamation.hmdsfa.api;

import com.datamation.hmdsfa.model.apimodel.ReadJsonList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Rashmi on 7/15/2019.
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
}
