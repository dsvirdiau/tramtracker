package com.geekfreak.tramtracker.model;

public class TramModel {
   private Boolean AirConditioned;
   private String Destination;
   private Boolean DisplayAC;
   private DisruptionMessage DisruptionMessage;
   private Boolean HasDisruption;
   private Boolean HasSpecialEvent;
   private String HeadBoardRouteNo;
   private int InternalRouteNo;
   private Boolean IsLowFloorTram;
   private Boolean IsTTAvailable;
   private String PredictedArrivalDateTime;
   private String RouteNo;
   private String SpecialEventMessage;
   private int TripID;
   private int VehicleNo;

   public TramModel( Boolean airConditioned, String destination,
                     Boolean displayAC, TramModel.DisruptionMessage disruptionMessage,
                     Boolean hasDisruption, Boolean hasSpecialEvent, String headBoardRouteNo,
                     int internalRouteNo, Boolean isLowFloorTram, Boolean isTTAvailable,
                     String predictedArrivalDateTime, String routeNo,
                     String specialEventMessage, int tripID, int vehicleNo ) {
      AirConditioned = airConditioned;
      Destination = destination;
      DisplayAC = displayAC;
      DisruptionMessage = disruptionMessage;
      HasDisruption = hasDisruption;
      HasSpecialEvent = hasSpecialEvent;
      HeadBoardRouteNo = headBoardRouteNo;
      InternalRouteNo = internalRouteNo;
      IsLowFloorTram = isLowFloorTram;
      IsTTAvailable = isTTAvailable;
      PredictedArrivalDateTime = predictedArrivalDateTime;
      RouteNo = routeNo;
      SpecialEventMessage = specialEventMessage;
      TripID = tripID;
      VehicleNo = vehicleNo;
   }

   public Boolean getAirConditioned( ) {
      return AirConditioned;
   }

   public String getDestination( ) {
      return Destination;
   }

   public Boolean getDisplayAC( ) {
      return DisplayAC;
   }

   public TramModel.DisruptionMessage getDisruptionMessage( ) {
      return DisruptionMessage;
   }

   public Boolean getHasDisruption( ) {
      return HasDisruption;
   }

   public Boolean getHasSpecialEvent( ) {
      return HasSpecialEvent;
   }

   public String getHeadBoardRouteNo( ) {
      return HeadBoardRouteNo;
   }

   public int getInternalRouteNo( ) {
      return InternalRouteNo;
   }

   public Boolean getLowFloorTram( ) {
      return IsLowFloorTram;
   }

   public Boolean getTTAvailable( ) {
      return IsTTAvailable;
   }

   public String getPredictedArrivalDateTime( ) {
      return PredictedArrivalDateTime;
   }

   public String getRouteNo( ) {
      return RouteNo;
   }

   public String getSpecialEventMessage( ) {
      return SpecialEventMessage;
   }

   public int getTripID( ) {
      return TripID;
   }

   public int getVehicleNo( ) {
      return VehicleNo;
   }

   class DisruptionMessage{
      private String DisplayType;
      private int MessageCount;
      String[] Messages;

      public String getDisplayType( ) {
         return DisplayType;
      }

      public int getMessageCount( ) {
         return MessageCount;
      }

      public String[] getMessages( ) {
         return Messages;
      }
   }
}


