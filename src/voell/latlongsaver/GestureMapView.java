package voell.latlongsaver;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;

import com.google.android.maps.MapView;

public class GestureMapView extends MapView
{

	private Context context;
	private GestureDetector gestureDetector;
	public GestureMapView(Context aContext, AttributeSet attrs)
	{
		super(aContext,attrs);
		context = aContext;

		gestureDetector = new GestureDetector((OnGestureListener)context);
		gestureDetector.setOnDoubleTapListener((OnDoubleTapListener) context);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		if (this.gestureDetector.onTouchEvent(ev))
			return true;
		else
			return super.onTouchEvent(ev);
	}

}
