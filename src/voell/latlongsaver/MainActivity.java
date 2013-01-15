package voell.latlongsaver;



import java.util.ArrayList;




import android.os.Bundle;
import android.provider.BaseColumns;
import android.app.TabActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TabHost;

public class MainActivity extends TabActivity {
	public TabHost tabHost;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tabHost = getTabHost();
        
       /* Intent mapIntent = new Intent().setClass(this, LocationList.class);
        TabHost.TabSpec mapTabSpec = tabHost.newTabSpec("spectrenav");
        mapTabSpec.setIndicator("Spectre");
        mapTabSpec.setContent(mapIntent);
        tabHost.addTab(mapTabSpec); //added map*/
        
        Intent locationListIntent = new Intent().setClass(this, LocationListActivity.class);
        TabHost.TabSpec locationListSpec = tabHost.newTabSpec("LocationList");
        locationListSpec.setIndicator("LocationList");
        locationListSpec.setContent(locationListIntent);
        tabHost.addTab(locationListSpec); //added location list
        
        Intent locationMapIntent = new Intent().setClass(this, LocationMapActivity.class);
        TabHost.TabSpec locationMapSpec = tabHost.newTabSpec("LocationMap");
        locationMapSpec.setIndicator("LocationMap");
        locationMapSpec.setContent(locationMapIntent);
        tabHost.addTab(locationMapSpec); //added location map
        
        tabHost.setCurrentTab(0);

		//Initiate database and add test item:
		//*****************************************************************************************************
		//Creating a DBHelper which will give me access to SQLiteOpenHelper methods such as getWritableDatabase
		//*****************************************************************************************************
		GameDBHelper mDBHelper = new GameDBHelper(getBaseContext());
		SQLiteDatabase db = mDBHelper.getWritableDatabase();

		//************************************************************
		//Test to see if the Database is empty, if it is initialize it
		//************************************************************
		if (numDbEntries(db) < 1)
		{
			//*******************************************
			//Initializing the values within the database
			//*******************************************
			ArrayList<ContentValues> coordinatesToAdd = getCoordinatesInitialize();

			for (ContentValues c: coordinatesToAdd)
			{
				db.insert(ContractCoordinates.CoordinatesEntry.TABLE_NAME, null, c);
			}

			db.close();
		}
		else{}
			db.close();

		

	
	}
	private ArrayList<ContentValues> getCoordinatesInitialize() 
	{
		ArrayList<ContentValues> values = new ArrayList<ContentValues>();
		//Creature 1
		ContentValues coordinates1 = new ContentValues();
		coordinates1.put(ContractCoordinates.CoordinatesEntry.COLUMN_NAME_ID, "1");
		coordinates1.put(ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LOCATION_NAME, "Home");
		coordinates1.put(ContractCoordinates.CoordinatesEntry.COLUMN_NAME_DESCRIPTION, "2");
		coordinates1.put(ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LATITUDE, "3");
		coordinates1.put(ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LONGITUDE, "4");
		coordinates1.put(ContractCoordinates.CoordinatesEntry.COLUMN_NAME_DATE, "5");
		values.add(coordinates1);
		return values;
	}

	
	 //*********************************************************
    //Calculate the number of entries currently in the Database
    //*********************************************************
    private int numDbEntries(SQLiteDatabase db)
    {
    	String[] projection = {BaseColumns._ID};
    	Cursor c = db.query(
    			ContractCoordinates.CoordinatesEntry.TABLE_NAME,
    			projection,
    			null,
    			null,
    			null,
    			null,
    			null
    			);
    	return c.getCount();
    	
    }
    

	/*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }*/
}
