package com.example.androidproject;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Finger{
	public Finger()
	{
	}
	public static boolean down = false;
	public static Vector position = new Vector(1000,1000);
	public static void update(MotionEvent event)
	{
		int action = event.getAction() & MotionEvent.ACTION_MASK;
		switch(action)
    	{
	    	case MotionEvent.ACTION_DOWN:
	    		Finger.down = true;
	    		break;
	    	case MotionEvent.ACTION_UP:
	    		Finger.down = false;
	    		break;
	    	case MotionEvent.ACTION_MOVE:
	    		position.x = event.getX();
	    		position.y = event.getX();
	    		break;
	    	
    	}
		System.out.println("asdasdsa");

	}
}