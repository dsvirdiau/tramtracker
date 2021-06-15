package com.geekfreak.tramtracker.network;

import com.geekfreak.tramtracker.model.TokenModel;
import com.geekfreak.tramtracker.model.TramModel;
import com.geekfreak.tramtracker.model.ApiServiceModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
   //?aid=TTIOSJSON&devInfo=HomeTime
   @GET("TramTracker/RestService/GetDeviceToken/")
   Call< ApiServiceModel< TokenModel > > getDeviceToken(
           @Query( "aid" ) String aid,
           @Query( "devInfo" ) String devInfo );

   //{STOP_ID}/78/false/?aid=TTIOSJSON&cid=2&tkn={TOKEN}
   @GET("TramTracker/RestService/GetNextPredictedRoutesCollection/{STOP_ID}/{TRAM_NUMBER}/false/")
   Call< ApiServiceModel< TramModel > > getNextPredictedRoute(
           @Path( "STOP_ID" ) int stopId,
           @Path( "TRAM_NUMBER" ) int tramNumber,
           @Query( "aid" ) String aid,
           @Query( "cid" ) String cid,
           @Query( "tkn" ) String token );
}
