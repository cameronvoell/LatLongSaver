package voell.latlongsaver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.BaseColumns;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class LocationListActivity extends Activity {
	private LocationManager locationManager;
	private Cursor c;
	Context context;
	private GeoUpdateHandler gh;
	private OnItemClickListener mMessageClickedHandler = new OnItemClickListener() 
	{
		@Override
		public void onItemClick(AdapterView arg0, View arg1, int listLocation, long orderAdded) 
		{
			
			Toast.makeText(getBaseContext(), "Arg0 "+ arg0.toString() + "Arg1 "+ arg1.toString() + "Arg2 "+ listLocation +"Arg3 "+ orderAdded, Toast.LENGTH_SHORT).show();

		}
		
		
	};
	private OnItemLongClickListener mMessageLongClickedHandler = new OnItemLongClickListener() 
	{
		
		public boolean onItemLongClick(final AdapterView arg0, View arg1, final int listLocation, long orderAdded) 
		{
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			CharSequence[] choices = {"choicea", "choiceb"};
			alertDialogBuilder.setTitle("Delete this location?")
			//.setMessage("uhhi")
			.setPositiveButton("yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int ids) {
					Toast.makeText(context, "chose 2", Toast.LENGTH_SHORT).show();
					SimpleCursorAdapter ca = (SimpleCursorAdapter) arg0.getAdapter();
					Cursor c = ca.getCursor();
					c.moveToPosition(listLocation);
					String id = c.getString(c.getColumnIndex(ContractCoordinates.CoordinatesEntry.COLUMN_NAME_ID));
					
					GameDBHelper mDbHelper = new GameDBHelper(getBaseContext());
					SQLiteDatabase db = mDbHelper.getWritableDatabase();
					String selection = ContractCoordinates.CoordinatesEntry.COLUMN_NAME_ID + " LIKE?";
					String[] selectionArgs = {id};
					db.delete(ContractCoordinates.CoordinatesEntry.TABLE_NAME, selection, selectionArgs);
					//***********************************************************************************
					String[] projection = {
							BaseColumns._ID,
							ContractCoordinates.CoordinatesEntry.COLUMN_NAME_ID,
							ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LOCATION_NAME,
							ContractCoordinates.CoordinatesEntry.COLUMN_NAME_DESCRIPTION,
							ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LATITUDE,
							ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LONGITUDE,
							ContractCoordinates.CoordinatesEntry.COLUMN_NAME_DATE
					};
					String sortOrder = BaseColumns._ID + " DESC";
					Cursor nc = db.query(
							ContractCoordinates.CoordinatesEntry.TABLE_NAME,
							projection,
							null,
							null,
							null,
							null,
							sortOrder
							);
					nc.moveToFirst();
					String[] fromColumns = {ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LOCATION_NAME,
							ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LATITUDE,
							ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LONGITUDE};
					int[] toViews = {R.id.location_name, R.id.location_lat,R.id.location_lng};
					SimpleCursorAdapter nca = new SimpleCursorAdapter(getBaseContext(), R.layout.coordinate_item_layout, nc, fromColumns, toViews,1);
					ListView listview = (ListView)findViewById(R.id.myCoordinateList);
					listview.setAdapter(nca);
					db.close();
					dialog.cancel();
					
				}
			});
			alertDialogBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					
				}
			});
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();/*
			SimpleCursorAdapter ca = (SimpleCursorAdapter) arg0.getAdapter();
			Cursor c = ca.getCursor();
			c.moveToPosition(listLocation);
			String id = c.getString(c.getColumnIndex(ContractCoordinates.CoordinatesEntry.COLUMN_NAME_ID));
			
			GameDBHelper mDbHelper = new GameDBHelper(getBaseContext());
			SQLiteDatabase db = mDbHelper.getWritableDatabase();
			String selection = ContractCoordinates.CoordinatesEntry.COLUMN_NAME_ID + " LIKE?";
			String[] selectionArgs = {id};
			db.delete(ContractCoordinates.CoordinatesEntry.TABLE_NAME, selection, selectionArgs);
			//***********************************************************************************
			String[] projection = {
					BaseColumns._ID,
					ContractCoordinates.CoordinatesEntry.COLUMN_NAME_ID,
					ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LOCATION_NAME,
					ContractCoordinates.CoordinatesEntry.COLUMN_NAME_DESCRIPTION,
					ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LATITUDE,
					ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LONGITUDE,
					ContractCoordinates.CoordinatesEntry.COLUMN_NAME_DATE
			};
			String sortOrder = BaseColumns._ID + " DESC";
			Cursor nc = db.query(
					ContractCoordinates.CoordinatesEntry.TABLE_NAME,
					projection,
					null,
					null,
					null,
					null,
					sortOrder
					);
			nc.moveToFirst();
			String[] fromColumns = {ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LOCATION_NAME,
					ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LATITUDE,
					ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LONGITUDE};
			int[] toViews = {R.id.location_name, R.id.location_lat,R.id.location_lng};
			SimpleCursorAdapter nca = new SimpleCursorAdapter(getBaseContext(), R.layout.coordinate_item_layout, nc, fromColumns, toViews,1);
			ListView listview = (ListView)findViewById(R.id.myCoordinateList);
			listview.setAdapter(nca);
			db.close();*/
			
			//Toast.makeText(getBaseContext(), "Delete ID " , Toast.LENGTH_SHORT).show();
			return true;

		}
		
		
	};
	
	
	public void onResume()
	{
		c.requery();
		super.onResume();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		context = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_list_layout);
		
		GameDBHelper mDBHelper = new GameDBHelper(getBaseContext());
		SQLiteDatabase db = mDBHelper.getWritableDatabase();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		//***********************************************************************************
				//These values will be used for a CursorAdapter technique for populating the ListView
				//***********************************************************************************
				String[] projection = {
						BaseColumns._ID,
						ContractCoordinates.CoordinatesEntry.COLUMN_NAME_ID,
						ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LOCATION_NAME,
						ContractCoordinates.CoordinatesEntry.COLUMN_NAME_DESCRIPTION,
						ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LATITUDE,
						ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LONGITUDE,
						ContractCoordinates.CoordinatesEntry.COLUMN_NAME_DATE
				};
				String sortOrder = BaseColumns._ID + " DESC";

				c = db.query(
						ContractCoordinates.CoordinatesEntry.TABLE_NAME,
						projection,
						null,
						null,
						null,
						null,
						sortOrder
						);
				c.moveToFirst();
				String[] fromColumns = {ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LOCATION_NAME,
										ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LATITUDE,
										ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LONGITUDE};
				int[] toViews = {R.id.location_name, R.id.location_lat,R.id.location_lng};
				SimpleCursorAdapter ca = new SimpleCursorAdapter(getBaseContext(), R.layout.coordinate_item_layout, c, fromColumns, toViews,1);
				db.close();

				//******************************
				//Add the adaptor to my ListView
				//******************************
				ListView listview = (ListView)findViewById(R.id.myCoordinateList);
				listview.setAdapter(ca);
				listview.setOnItemClickListener(mMessageClickedHandler);
				listview.setOnItemLongClickListener(mMessageLongClickedHandler);
	}
	

    public void addNew(View v)
    {
    	if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
    	{
    		Intent intent = new Intent(getBaseContext(),MapConfirmationActivity.class);
    		startActivity(intent);
    	}
    	else
    	{
    		Toast.makeText(getBaseContext(), "GPS disabled" , Toast.LENGTH_SHORT).show();
    	}
		
    }
    public void deleteAll(View v)
    {
    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle("Clear all locations?")
		.setPositiveButton("yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int ids) {
				Toast.makeText(context, "clear all", Toast.LENGTH_SHORT).show();
			}
		});
		alertDialogBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
    }
    //create a csv file of the different locations, and export it as an attachment to an email
    public void exportList(View v)
    {
    	//Construct the csv file from the database
    	GameDBHelper mDBHelper = new GameDBHelper(getBaseContext());
    	SQLiteDatabase db = mDBHelper.getReadableDatabase();

    	//***********************************************************************************
    	//These values will be used for a CursorAdapter technique for populating the ListView
    	//***********************************************************************************
    	String[] projection = {
    			BaseColumns._ID,
    			ContractCoordinates.CoordinatesEntry.COLUMN_NAME_ID,
    			ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LOCATION_NAME,
    			ContractCoordinates.CoordinatesEntry.COLUMN_NAME_DESCRIPTION,
    			ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LATITUDE,
    			ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LONGITUDE,
    			ContractCoordinates.CoordinatesEntry.COLUMN_NAME_DATE
    	};
    	String sortOrder = BaseColumns._ID + " DESC";

    	Cursor c2 = db.query(
    			ContractCoordinates.CoordinatesEntry.TABLE_NAME,
    			projection,
    			null,
    			null,
    			null,
    			null,
    			sortOrder
    			);
    	c2.moveToFirst();
    	String dataString = "";//   =   "\"" + "test location" +"\",\"" + "test lat" + "\",\"" + "test long" + "\"";
    	for (int i = 0; i < c2.getCount(); i++)
    	{
    		dataString =  dataString + "\"" + c2.getString(c.getColumnIndex(ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LOCATION_NAME)) +"\",\"" + c2.getString(c.getColumnIndex(ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LATITUDE)) + "\",\"" + c2.getString(c.getColumnIndex(ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LONGITUDE)) + "\"" + "\n";
    		c2.moveToNext();
    	}
    	db.close();
    	String columnString =   "\"Place\",\"Latitude\",\"Longitude\"";
    	String combinedString = columnString + "\n" + dataString;

    	File file   = null;
    	File root   = Environment.getExternalStorageDirectory();
    	if (root.canWrite()){
    	    File dir    =   new File (root.getAbsolutePath() + "/PersonData");
    	     dir.mkdirs();
    	     file   =   new File(dir, "Data.csv");
    	     FileOutputStream out   =   null;
    	    try {
    	        out = new FileOutputStream(file);
    	        } catch (FileNotFoundException e) {
    	            e.printStackTrace();
    	        }
    	        try {
    	            out.write(combinedString.getBytes());
    	        } catch (IOException e) {
    	            e.printStackTrace();
    	        }
    	        try {
    	            out.close();
    	        } catch (IOException e) {
    	            e.printStackTrace();
    	        }
    	    }
    	
    	Uri u1  =   null;
    	u1  =   Uri.fromFile(file);

    	Intent sendIntent = new Intent(Intent.ACTION_SEND);
    	sendIntent.putExtra(Intent.EXTRA_SUBJECT, "LatLong List");
    	sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
    	sendIntent.setType("text/html");
    	startActivity(sendIntent);
    }
    public void showHelp(View v)
    {
    	
    }

}
