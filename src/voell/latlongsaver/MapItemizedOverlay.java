package voell.latlongsaver;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MapItemizedOverlay extends ItemizedOverlay {
	private ArrayList mOverlays = new ArrayList();
	private Context context;
	
	public MapItemizedOverlay(Drawable defaultMarker, Context context) {
		super(boundCenter(defaultMarker));
		this.context = context;
	}
	
	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}
	
	public void removeOverlay(int i)
	{
		mOverlays.remove(i);
		populate();
	}
	
	@Override
	protected boolean onTap(int i) {
		OverlayItem overlayItem = (OverlayItem) mOverlays.get(i);
		Toast.makeText(context, overlayItem.getSnippet(), Toast.LENGTH_SHORT).show();
		return true;
	}

	@Override
	protected OverlayItem createItem(int i) {
		return (OverlayItem) mOverlays.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return mOverlays.size();
	}

	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
}
