package voell.latlongsaver;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class LocationMapActivity extends MapActivity implements OnGestureListener, OnDoubleTapListener
{
	private List mapOverlays;
	private GestureMapView mapView;

	protected void onResume()
	{
		mapOverlays.clear();

		overlayLocations();

		Toast.makeText(getBaseContext(), "hola", Toast.LENGTH_SHORT).show();
		mapView.postInvalidate();
		super.onResume();
	}
	protected void onCreate(Bundle bundle) 
	{

		//************************************************************************************
		//Pass bundle to superclass constructor and set the correct content view upon creation
		//************************************************************************************
		super.onCreate(bundle);
		setContentView(R.layout.location_map_layout);

		mapView = (GestureMapView) findViewById(R.id.mapview);
		MapController mapController = mapView.getController();
		mapOverlays = mapView.getOverlays();
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mapView.setBuiltInZoomControls(false);
		mapView.setClickable(true);
		overlayLocations();


	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	private void overlayLocations()
	{
		GameDBHelper mDbHelper = new GameDBHelper(getBaseContext());
		SQLiteDatabase db = mDbHelper.getReadableDatabase();

		String[] projection = {
				BaseColumns._ID,
				ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LOCATION_NAME,
				ContractCoordinates.CoordinatesEntry.COLUMN_NAME_DESCRIPTION,
				ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LATITUDE,
				ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LONGITUDE
		};
		String sortOrder = BaseColumns._ID + " DESC";
		//String selection = ContractCreatures.CreatureEntry.COLUMN_NAME_HOME_ZONE + " LIKE ?";
		//String[] selectionArgs = { String.valueOf(zone) };

		Cursor c = db.query(
				ContractCoordinates.CoordinatesEntry.TABLE_NAME,
				projection,
				null,//selection, //selection
				null,//selectionArgs, //selectionArgs
				null,
				null,
				sortOrder
				);
		c.moveToFirst();

		for (int i = 0; i < c.getCount(); i++)
		{
			String locName = c.getString(c.getColumnIndex(ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LOCATION_NAME));
			Double locLat = Double.valueOf(c.getString(c.getColumnIndex(ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LATITUDE)));
			Double locLng = Double.valueOf(c.getString(c.getColumnIndex(ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LONGITUDE)));
			locLat *= 1E6;
			locLng *= 1E6;
			Drawable flagPic =  this.getResources().getDrawable(R.drawable.blue_flag);
			MapItemizedOverlay locationOverlay = new MapItemizedOverlay(flagPic, this);
			GeoPoint pointLocation = new GeoPoint(locLat.intValue(), locLng.intValue());
			OverlayItem locOverlayItem = new OverlayItem(pointLocation, "", locName);
			locationOverlay.addOverlay(locOverlayItem);
			mapOverlays.add(locationOverlay);

			c.moveToNext();
		}
		db.close();
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {

		Toast.makeText(getBaseContext(), "hi", Toast.LENGTH_SHORT).show();
		return true;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		GeoPoint p = mapView.getProjection().fromPixels((int)e.getX(), (int)e.getY());
		String lat = String.valueOf(p.getLatitudeE6()/1E6);
		String lng= String.valueOf(p.getLongitudeE6()/1E6);
		Toast.makeText(getBaseContext(), "Lat:" + lat + ",Lng:"+ lng, Toast.LENGTH_SHORT).show();

		//Location added
		Intent intent = new Intent(getBaseContext(), NewLocationFormActivity.class);
		intent.putExtra("lat", lat);
		intent.putExtra("lng", lng);
		startActivity(intent);


	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

}
