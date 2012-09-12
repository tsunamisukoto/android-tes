package Input;
import java.util.ArrayList;
import java.util.List;

import Tools.Vector;
import android.graphics.RectF;
import android.view.MotionEvent;

public class Finger {
	public static boolean down = false;
	public static Vector position = new Vector(150, 150);
	public static List<Vector> pointers = new ArrayList<Vector>();
	public static boolean fired = false;
	
	public static void Update(MotionEvent event)
	{
		int action = event.getAction() & MotionEvent.ACTION_MASK;
		int tmp = event.getPointerCount() - pointers.size();
		/*for(int x=0;x<tmp;x++)
		{
			pointers.add(new Pointer());
		}
		for( int x=0; x<event.getPointerCount();x++)
		{
			pointers.get(x).position.x = event.getX(x);
			pointers.get(x).position.y = event.getY(x);//(event.geta);
		}*/
		
		switch(action)
    	{
	    	case MotionEvent.ACTION_DOWN:
	    		pointers.clear();
	    		for(int x=0;x < event.getPointerCount();x++)
	    		{
	    			pointers.add(new Vector(event.getX(x),event.getY(x)));
	    		}
	    		position.x = event.getX();
	    		position.y = event.getY();
	    		down = true;
	    		break;
	    	case MotionEvent.ACTION_UP:
	    		if(fired)
	    		{
	    			fired = false;
	    		}
	    		pointers.clear();
	    		for(int x=0;x < event.getPointerCount();x++)
	    		{
	    			pointers.add(new Vector(event.getX(x),event.getY(x)));
	    		}
	    		position.x = event.getX();
	    		position.y = event.getY();
	    		down = false;
	    		break;
	    	case MotionEvent.ACTION_MOVE:
	    		pointers.clear();
	    		for(int x=0;x < event.getPointerCount();x++)
	    		{
	    			pointers.add(new Vector(event.getX(x),event.getY(x)));
	    		}
	    		position.x = event.getX();
	    		position.y = event.getY();
	    		break;
	    	default:
	    		break;
	    	
    	}
	}
	
}