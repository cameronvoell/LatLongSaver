package voell.latlongsaver;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MapConfirmationActivity extends MapActivity
{
	private GeoUpdateHandler gh;
	private LocationManager locationManager;
@Override
protected void onCreate(Bundle bundle) 
{
		
		//************************************************************************************
		//Pass bundle to superclass constructor and set the correct content view upon creation
		//************************************************************************************
		super.onCreate(bundle);
		setContentView(R.layout.map_confirmation_layout);
		
		MapView mapView = (MapView) findViewById(R.id.conf_mapview);
		MapController mapController = mapView.getController();
		List mapOverlays = mapView.getOverlays();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mapView.setBuiltInZoomControls(false);
		mapView.setClickable(false);
		
		Drawable userPic =  this.getResources().getDrawable(R.drawable.compass);
		MapItemizedOverlay userOverlay = new MapItemizedOverlay(userPic, this);
		Location locToSave = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		int lat = 0;
		int lng = 0;
		if (locToSave == null)
		{
			lat = (int)(34.7 * 1E6);
			lng = (int)(106.3 * 1E6);
		}
		else
		{
			lat = (int)(locToSave.getLatitude() * 1E6);
			lng = (int)(locToSave.getLongitude() * 1E6);
		}
		userOverlay.addOverlay(new OverlayItem(new GeoPoint(lat,lng), "", ""));
		mapOverlays.add(0,userOverlay);
		int zoom = 18;
		mapController.setZoom(zoom);
		mapController.animateTo(new GeoPoint(lat,lng));

		
		
		//Toast.makeText(getBaseContext(), "Following Activated", Toast.LENGTH_SHORT).show();
		gh = new GeoUpdateHandler(getBaseContext(), mapView, mapController, mapOverlays, userOverlay);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, gh);
		
		
		
		
}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	public void confirm(View v)
	{
		Intent intent = new Intent(getBaseContext(), NewLocationFormActivity.class);
		Location locToSave = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		intent.putExtra("lat", String.valueOf(locToSave.getLatitude()));
		intent.putExtra("lng", String.valueOf(locToSave.getLongitude()));
		startActivity(intent);
		locationManager.removeUpdates(gh);
		
	}

}
