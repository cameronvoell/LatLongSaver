package voell.latlongsaver;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GameDBHelper extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 40;
	public static final String DATABASE_NAME = "coordinates.db";
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_COORDINATES = 
			"CREATE TABLE " + ContractCoordinates.CoordinatesEntry.TABLE_NAME + " (" +
					ContractCoordinates.CoordinatesEntry._ID + " INTEGER PRIMARY KEY," +
					ContractCoordinates.CoordinatesEntry.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
					ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LOCATION_NAME + TEXT_TYPE + COMMA_SEP +
					ContractCoordinates.CoordinatesEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
					ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LATITUDE + TEXT_TYPE + COMMA_SEP +
					ContractCoordinates.CoordinatesEntry.COLUMN_NAME_LONGITUDE + TEXT_TYPE + COMMA_SEP +
					ContractCoordinates.CoordinatesEntry.COLUMN_NAME_DATE + TEXT_TYPE + 
					" )";
	
	private static final String SQL_DELETE_C_ENTRIES = "DROP TABLE IF EXISTS " + ContractCoordinates.CoordinatesEntry.TABLE_NAME;
	
	public GameDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_DELETE_C_ENTRIES);

		db.execSQL(SQL_CREATE_COORDINATES);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_C_ENTRIES);
		onCreate(db);
	}

}
