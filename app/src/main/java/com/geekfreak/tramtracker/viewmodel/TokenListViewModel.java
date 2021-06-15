package com.geekfreak.tramtracker.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.geekfreak.tramtracker.model.ApiServiceModel;
import com.geekfreak.tramtracker.model.TokenModel;
import com.geekfreak.tramtracker.network.ApiService;
import com.geekfreak.tramtracker.network.RetroInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenListViewModel extends ViewModel {

   public static String A_ID = "TTIOSJSON";
   public static String DEV_INFO = "HomeTime";

   private MutableLiveData< ApiServiceModel< TokenModel > > tokenListObserver;

   public TokenListViewModel( ) {
      tokenListObserver = new MutableLiveData<>();
   }

   public MutableLiveData< ApiServiceModel< TokenModel > > getTokenListObserver(){
      return tokenListObserver;
   }

   public void makeApiCall(){
      ApiService apiService = RetroInstance.getRetrofitClient().create( ApiService.class );
      Call< ApiServiceModel< TokenModel > > call = apiService.getDeviceToken( A_ID, DEV_INFO );
      call.enqueue( new Callback< ApiServiceModel< TokenModel > >( ) {
         @Override
         public void onResponse( Call< ApiServiceModel< TokenModel > > call, Response< ApiServiceModel< TokenModel > > response ) {
            tokenListObserver.postValue( response.body() );
         }

         @Override
         public void onFailure( Call< ApiServiceModel< TokenModel > > call, Throwable t ) {
            tokenListObserver.postValue( null );
         }
      } );
   }
}
