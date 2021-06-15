package com.geekfreak.tramtracker.model;

public class TokenModel {

   private String DeviceToken;

   public TokenModel( String deviceToken ) {
      DeviceToken = deviceToken;
   }

   public String getDeviceToken( ) {
      return DeviceToken;
   }
}
