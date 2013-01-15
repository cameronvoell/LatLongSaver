package voell.latlongsaver;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewLocationFormActivity extends Activity{
	private String lat;
	private String lng;
	
	@Override
	protected void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.new_location_form_layout);
		
		Intent intent = getIntent();
		lat = intent.getStringExtra("lat");
		lng = intent.getStringExtra("lng");
	}
	
	public void save(View v)
	{
		EditText text = (EditText)findViewById(R.id.edit_name);
		Editable name = text.getText();
		String s = name.toString();
		Toast.makeText(getBaseContext(),s, Toast.LENGTH_SHORT).show();

		//Location added
		GameDBHelper mDbHelper = new GameDBHelper(getBaseContext());
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		ContentValues location1 = new ContentValues();
		location1.put(ContractCoordinates.CoordinatesEntry.COLUMN_NAME_ID, getNextIdToUse(db));
		location1.put(ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LOCATION_NAME, s);
		location1.put(ContractCoordinates.CoordinatesEntry.COLUMN_NAME_DESCRIPTION, "Newly added");
		location1.put(ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LATITUDE, lat);
		location1.put(ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LONGITUDE, lng);
		db.insert(ContractCoordinates.CoordinatesEntry.TABLE_NAME, null, location1);
		db.close();
		Intent intent = new Intent(getBaseContext(), MainActivity.class);
		startActivity(intent);
	}
    

	
	public String getNextIdToUse(SQLiteDatabase db)
	{
		GameDBHelper mDbHelper = new GameDBHelper(getBaseContext());
		

		String[] projection = {
					BaseColumns._ID,
					ContractCoordinates.CoordinatesEntry.COLUMN_NAME_ID
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
			
			int highestID = Integer.valueOf(c.getString(c.getColumnIndex(ContractCoordinates.CoordinatesEntry.COLUMN_NAME_ID)));
			return String.valueOf(highestID + 1);
	}

}
