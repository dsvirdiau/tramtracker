package com.geekfreak.tramtracker.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.geekfreak.tramtracker.model.ApiServiceModel;
import com.geekfreak.tramtracker.model.TramModel;
import com.geekfreak.tramtracker.network.ApiService;
import com.geekfreak.tramtracker.network.RetroInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TramListViewModel extends ViewModel {
   public static String A_ID = "TTIOSJSON";
   public static String C_ID = "2";
   public static int TRAM_NUMBER = 78;

   private MutableLiveData< ApiServiceModel< TramModel > > tramListObserver;

   public TramListViewModel(  ) {
      tramListObserver = new MutableLiveData<>();
   }

   public MutableLiveData< ApiServiceModel< TramModel > > getTramListObserver(){
      return tramListObserver;
   }

   public void makeApiCall( int stopId, String token ) {
      ApiService apiService = RetroInstance.getRetrofitClient().create( ApiService.class );
      Call< ApiServiceModel< TramModel > > call = apiService.getNextPredictedRoute(
              stopId,
              TRAM_NUMBER,
              A_ID,
              C_ID,
              token
      );
      call.enqueue( new Callback< ApiServiceModel< TramModel > >( ) {
         @Override
         public void onResponse( Call< ApiServiceModel< TramModel > > call, Response< ApiServiceModel< TramModel > > response ) {
            tramListObserver.postValue( response.body() );
         }

         @Override
         public void onFailure( Call< ApiServiceModel< TramModel > > call, Throwable t ) {
            tramListObserver.postValue( null );
         }
      } );
   }
}
