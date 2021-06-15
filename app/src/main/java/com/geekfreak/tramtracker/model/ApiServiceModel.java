package com.geekfreak.tramtracker.model;

import java.util.List;

public class ApiServiceModel<T> {
   private String errorMessage;
   private Boolean hasError;
   private Boolean hasResponse;
   private List<T> responseObject;

   public ApiServiceModel( String errorMessage, Boolean hasError, Boolean hasResponse, List< T > responseObject ) {
      this.errorMessage = errorMessage;
      this.hasError = hasError;
      this.hasResponse = hasResponse;
      this.responseObject = responseObject;
   }

   public String getErrorMessage( ) {
      return errorMessage;
   }

   public Boolean getHasError( ) {
      return hasError;
   }

   public Boolean getHasResponse( ) {
      return hasResponse;
   }

   public List< T > getResponseObject( ) {
      return responseObject;
   }
}
