package voell.latlongsaver;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NewEntryActivity extends Activity {@Override
	protected void onCreate(Bundle bundle) 
	{
		//*****************************
		//This is the Encounter View
		//*****************************
		super.onCreate(bundle);
		setContentView(R.layout.new_entry_layout);
		
		Intent intent = getIntent();
		//int idOfCreature = intent.getIntExtra("creatureID", -1);
		TextView tv = (TextView)findViewById(R.id.coordinates_input);
		//tv.setText("The id is: " + idOfCreature);
		
	}
	
	public void backToList(View v)
	{
		finish();
	}
}

