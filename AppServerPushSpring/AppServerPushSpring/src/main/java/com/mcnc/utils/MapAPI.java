package com.mcnc.utils;

import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.internal.ApiConfig;
import com.google.maps.internal.ApiResponse;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;

public class MapAPI {
	  static final ApiConfig API_CONFIG = new ApiConfig("/maps/api/distancematrix/json");

	  private MapAPI() {
	  }

	  public static DistanceMatrixApiRequest newRequest(GeoApiContext context) {
	    return new DistanceMatrixApiRequest(context);
	  }

	  public static DistanceMatrixApiRequest getDistanceMatrix(GeoApiContext context, LatLng[] origins,
			  													LatLng[] destinations,
	                                                           Unit unit,
	                                                           TravelMode mode) {
	    return newRequest(context).origins(origins).destinations(destinations).units(unit).mode(mode);
	  }

	  static class Response implements ApiResponse<DistanceMatrix> {
	    public String status;
	    public String errorMessage;
	    public String[] originAddresses;
	    public String[] destinationAddresses;
	    public DistanceMatrixRow[] rows;

	    @Override
	    public boolean successful() {
	      return "OK".equals(status);
	    }

	    @Override
	    public ApiException getError() {
	      if (successful()) {
	        return null;
	      }
	      return ApiException.from(status, errorMessage);
	    }

	    @Override
	    public DistanceMatrix getResult() {
	      return new DistanceMatrix(originAddresses, destinationAddresses, rows);
	    }
	  }


	}