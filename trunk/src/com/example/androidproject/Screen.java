package com.example.androidproject;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Screen extends View implements OnTouchListener  {
    private static final String TAG = "Screen";
    public static Vector size;
    
    
    public Screen(Context context) //this refreshes on view changed.
    {
    	//everything draws from the top left.
        super(context);

        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        this.setOnTouchListener(this);

    }
    
    @Override
    public void onDraw(Canvas canvas) {
    	
    	invalidate();
    }

    public boolean onTouch(View view, MotionEvent event) {
    	super.onTouchEvent(event);
    	Finger.update(event);
        return false;
    }
	
}
