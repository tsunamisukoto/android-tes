package com.example.androidproject;

import android.view.MotionEvent;

public class Finger {
	public static boolean down = false;
	public static Vector position = new Vector(150, 150);
	public static void update(MotionEvent event)
	{
		int action = event.getAction() & MotionEvent.ACTION_MASK;
		switch(action)
    	{
	    	case MotionEvent.ACTION_DOWN:
	    		Finger.down = true;
	    	case MotionEvent.ACTION_UP:
	    		Finger.down = false;
	    	case MotionEvent.ACTION_MOVE:
	    		position.x = event.getX();
	    		position.y = event.getY();
	    	default:
	    		break;
	    	
    	}
	}
}