package com.geekfreak.tramtracker.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.geekfreak.tramtracker.R;
import com.geekfreak.tramtracker.adapter.TramListAdapter;
import com.geekfreak.tramtracker.model.TramModel;
import com.geekfreak.tramtracker.viewmodel.TramListViewModel;

import java.util.List;

import static com.geekfreak.tramtracker.ui.MainActivity.NORTH_BOUND_STOP;
import static com.geekfreak.tramtracker.ui.MainActivity.Preferences;
import static com.geekfreak.tramtracker.ui.MainActivity.Token;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TramListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TramListFragment extends Fragment {

   private static final String STOP_ID = "stopId";
   private List< TramModel > tramModelList;
   private int stopId;
   private TramListAdapter tramListAdapter;
   private TramListViewModel tramListViewModel;
   private TextView titleTextView, subtitleTextView;
   private SwipeRefreshLayout swipeRefreshLayout;
   private ImageView refreshImageView;
   private String token;

   public TramListFragment( ) {
      // Required empty public constructor
   }

   public static TramListFragment newInstance( int stopId ) {
      TramListFragment fragment = new TramListFragment( );
      Bundle args = new Bundle( );
      args.putInt( STOP_ID, stopId );
      fragment.setArguments( args );
      return fragment;
   }

   @Override
   public void onCreate( Bundle savedInstanceState ) {
      super.onCreate( savedInstanceState );
      if ( getArguments( ) != null ) {
         stopId = getArguments( ).getInt( STOP_ID );
      }

      SharedPreferences sharedPreferences = getActivity( ).getSharedPreferences( Preferences,
              Context.MODE_PRIVATE );
      token = sharedPreferences.getString( Token, "" );
   }

   @Override
   public View onCreateView( LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState ) {
      // Inflate the layout for this fragment
      View view = inflater.inflate( R.layout.fragment_tram_list, container, false );

      initRecyclerView( view );
      setupObserver();

      //failsafe for title
      titleTextView = ( TextView ) view.findViewById( R.id.titleTextView );
      titleTextView.setText( stopId == NORTH_BOUND_STOP ? "North" : "South" );

      subtitleTextView = ( TextView ) view.findViewById( R.id.subtitleTextView );
      subtitleTextView.setText( "" );

      refreshImageView = ( ImageView ) view.findViewById( R.id.refreshImageView );
      refreshImageView.setOnClickListener( v -> {
         refreshData();
         refreshImageView.clearAnimation( );
         RotateAnimation anim = new RotateAnimation(0, 360,
                 refreshImageView.getWidth()/2, refreshImageView.getHeight()/2);
         anim.setFillAfter( true );
         anim.setRepeatCount( 0 );
         anim.setDuration( 1000 );
         refreshImageView.startAnimation(anim);
      } );


      swipeRefreshLayout = ( SwipeRefreshLayout ) view.findViewById( R.id.swipeRefreshRecyclerView );
      swipeRefreshLayout.setOnRefreshListener( () -> refreshData() );

      return view;
   }

   private void initRecyclerView( View view ){
      RecyclerView recyclerView = view.findViewById( R.id.recyclerView );
      LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getActivity() );

      tramListAdapter = new TramListAdapter( getActivity(), tramModelList );

      recyclerView.setLayoutManager( linearLayoutManager );
      recyclerView.setAdapter( tramListAdapter );
   }

   private void setupObserver( )
   {
      tramListViewModel = new ViewModelProvider( this ).get( TramListViewModel.class );
      tramListViewModel.getTramListObserver().observe( getViewLifecycleOwner(), tramModelApiServiceModel -> {
         if( tramModelApiServiceModel != null )
         {
            if( tramModelApiServiceModel.getHasError() )
            {
               Toast.makeText( getActivity(), tramModelApiServiceModel.getErrorMessage(),
                       Toast.LENGTH_LONG ).show();
            }

            tramModelList = tramModelApiServiceModel.getResponseObject();
            tramListAdapter.setTramListViewModelList( tramModelApiServiceModel.getResponseObject() );

            updateTitle( );
            updateSubTitle( );

            swipeRefreshLayout.setRefreshing( false );
         }
      } );

      refreshData();
   }

   private void updateTitle( )
   {
      if( titleTextView != null )
         titleTextView.setText( tramModelList.get( 0 ).getDestination() + " ( "
                 + tramModelList.get( 0 ).getRouteNo() + " ) ");

   }

   private void updateSubTitle( )
   {
      if( subtitleTextView != null )
      {
         subtitleTextView.setText( tramModelList.get( 0 ).getSpecialEventMessage() );
         int v = (subtitleTextView.getText() == null
                 || subtitleTextView.getText().toString().trim().isEmpty())
                 ? View.GONE
                 : View.VISIBLE;
         subtitleTextView.setVisibility( v );
      }
   }

   private void refreshData( )
   {
      if( tramListViewModel != null )
         tramListViewModel.makeApiCall( stopId, token );
   }
}