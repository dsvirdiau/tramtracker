package com.geekfreak.tramtracker.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.geekfreak.tramtracker.R;
import com.geekfreak.tramtracker.model.ApiServiceModel;
import com.geekfreak.tramtracker.model.TokenModel;
import com.geekfreak.tramtracker.viewmodel.TokenListViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
   private TokenListViewModel tokenListViewModel;

   public static final String Preferences = "MyPreferences" ;
   public static final String Token = "tokenKey";
   public static final String NorthFragmentTag = "north_bound_fragment";
   public static final String SouthFragmentTag = "south_bound_fragment";

   public static int NORTH_BOUND_STOP = 4055;
   public static int SOUTH_BOUND_STOP = 4155;

   private SharedPreferences sharedPreferences;

   @Override
   protected void onCreate( Bundle savedInstanceState ) {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.activity_main );

      sharedPreferences = getSharedPreferences( Preferences, Context.MODE_PRIVATE );

      tokenListViewModel = new ViewModelProvider( this ).get( TokenListViewModel.class );
      tokenListViewModel.getTokenListObserver().observe( this, tokenModelApiServiceModel -> {
          if( tokenModelApiServiceModel != null )
          {
             if( tokenModelApiServiceModel.getHasError() )
             {
                Toast.makeText( MainActivity.this, tokenModelApiServiceModel.getErrorMessage(), Toast.LENGTH_LONG ).show();
                return;
             }

             List< TokenModel > deviceTokenModelList =
                     tokenModelApiServiceModel.getResponseObject();
             String token = deviceTokenModelList.get( 0 ).getDeviceToken();

             SharedPreferences.Editor editor = sharedPreferences.edit();
             editor.putString( Token, token );
             editor.commit();
          }
      } );
      tokenListViewModel.makeApiCall();

      Fragment northFragment = TramListFragment.newInstance( NORTH_BOUND_STOP );
      Fragment southFragment = TramListFragment.newInstance( SOUTH_BOUND_STOP );

      getSupportFragmentManager().beginTransaction()
              .add( R.id.north_bound_fragment_container, northFragment, NorthFragmentTag )
              .add( R.id.south_bound_fragment_container, southFragment, SouthFragmentTag )
              .commit();
   }//onCreate
}