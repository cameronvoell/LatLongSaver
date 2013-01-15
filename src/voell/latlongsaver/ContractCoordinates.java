package voell.latlongsaver;

import android.provider.BaseColumns;

public class ContractCoordinates 
{
	private ContractCoordinates(){}
	
	public static abstract class CoordinatesEntry implements BaseColumns
	{
		public static final String TABLE_NAME ="coordinates";
		public static final String COLUMN_NAME_ID ="id";
		public static final String COLUMN_NAME_LOCATION_NAME ="name";
		public static final String COLUMN_NAME_DESCRIPTION ="description";
		public static final String COLUMN_NAME_LATITUDE ="latitude";
		public static final String COLUMN_NAME_LONGITUDE ="longitude";
		public static final String COLUMN_NAME_DATE ="DATE";
		
	}
}
