package voell.latlongsaver;



//import com.google.android.maps.GeoPoint;
//import com.google.android.maps.MapController;
//import com.google.android.maps.MapView;
//import com.google.android.maps.OverlayItem;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class GeoUpdateHandler implements LocationListener {
	
	private Context context;
	private MapController mapController;
	private List mapOverlays;
	private MapItemizedOverlay userOverlay;
	private MapView mapView;
	
	public GeoUpdateHandler(Context c, MapView mv, MapController mc, List mos, MapItemizedOverlay uo)//, MapView mv, MapController mc, List mos, SpectreMapItemizedOverlay so)
	{
		context = c;
		mapController = mc;
		mapOverlays = mos;
		userOverlay = uo;
		mapView = mv;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		int lat = (int)(location.getLatitude() * 1E6);
		int lng = (int)(location.getLongitude() * 1E6);
		GeoPoint newLocation = new GeoPoint(lat,lng);
		userOverlay.removeOverlay(0);
		userOverlay.addOverlay(new OverlayItem(newLocation, "", "new message"));
		mapOverlays.set(0,userOverlay);
		mapController.animateTo(newLocation);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
