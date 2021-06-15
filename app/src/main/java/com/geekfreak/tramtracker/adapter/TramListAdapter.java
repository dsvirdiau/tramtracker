package com.geekfreak.tramtracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geekfreak.tramtracker.R;
import com.geekfreak.tramtracker.model.TramModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TramListAdapter extends RecyclerView.Adapter< TramListAdapter.TramListViewHolder > {
   private Context context;
   private List< TramModel > tramModelList;
   public static final String TIMESTAMP_PATTERN = "HH:mm:ss a";
   public static final SimpleDateFormat FOMATTER = new SimpleDateFormat(TIMESTAMP_PATTERN);

   public TramListAdapter( Context context, List< TramModel > tramModelList ){
      this.context = context;
      this.tramModelList = tramModelList;
   }

   public void setTramListViewModelList( List< TramModel > tramListViewModelList ){
      this.tramModelList = tramListViewModelList;
      notifyDataSetChanged();
   }

   @NonNull
   @Override
   public TramListViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
      View view = LayoutInflater.from( context ).inflate( R.layout.tram_view_item, parent,
              false );
      return new TramListViewHolder( view );
   }

   @Override
   public void onBindViewHolder( @NonNull TramListAdapter.TramListViewHolder holder, int position ) {
      Date arrivalDateTime = dateFromDotNetDate( this.tramModelList.get( position )
                      .getPredictedArrivalDateTime() );
      Date currentDateTime = new Date(  System.currentTimeMillis( ) );

      //duration in milliseconds
      long duration = arrivalDateTime.getTime() - currentDateTime.getTime();
      long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);

      String formattedDate = FOMATTER.format( arrivalDateTime );
      if( minutes != 0 )
         holder.arrivalDateTimeText.setText( formattedDate + " ( in " + minutes + " mins )" );
      else
         holder.arrivalDateTimeText.setText( formattedDate + " ( Now )" );
   }

   @Override
   public int getItemCount( ) {
      if( this.tramModelList != null )
         return this.tramModelList.size();

      return 0;
   }

   public class TramListViewHolder extends RecyclerView.ViewHolder
   {
      TextView arrivalDateTimeText;

      public TramListViewHolder( @NonNull View itemView ) {
         super( itemView );

         arrivalDateTimeText = (TextView ) itemView.findViewById( R.id.arrivalDateTimeTextView );
      }
   }

   private Date dateFromDotNetDate( String dotNetDate ) {
      int startIndex = dotNetDate.indexOf( "(" ) + 1;
      int endIndex = dotNetDate.indexOf( "+" );
      String date = dotNetDate.substring( startIndex, endIndex );

      Long unixTime = Long.parseLong( date );
      return new Date( unixTime );
   }
}
