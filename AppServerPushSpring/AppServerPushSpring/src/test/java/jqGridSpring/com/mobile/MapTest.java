package jqGridSpring.com.mobile;

import org.junit.Test;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;
import com.mcnc.utils.MapAPI;

public class MapTest {

	@Test
	public void testMap() throws Exception{
		// Replace the API key below with a valid API key.
		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyB45WaHxqPNfFEsFcknIbyS1Sp3SHP5sfw");
//		GeocodingResult[] results =  GeocodingApi.geocode(context,
//		    "1600 Amphitheatre Parkway Mountain View, CA 94043").await();
//		System.out.println(results[0].formattedAddress);
		
		LatLng[] origins = new LatLng[]{ new LatLng(11.556026, 104.927914) };
		LatLng[] destinations = new LatLng[] { new LatLng(11.571372, 104.917786) };
		DistanceMatrixApi api;
		DistanceMatrix result2 = MapAPI.getDistanceMatrix(context, origins, destinations, Unit.METRIC, TravelMode.DRIVING).await();
		System.out.println(result2.rows[0].elements[0].distance);
		
	}
}
