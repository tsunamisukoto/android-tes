package com.example.androidproject;
import Game.Player;
import Shapes.Rectangle;
import Shapes.Text;
import World.Level;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Screen extends View implements OnTouchListener  {
    private static final String TAG = "Screen";
    public static Vector size;
    
    Text text = new Text();
    Player player = new Player();
    public Screen(Context context) //this refreshes on view changed.
    {
    	//everything draws from the top left.
        super(context);

        setFocusable(true);
        setFocusableInTouchMode(true);
        setOnTouchListener(this);
        text.text = "Finger";
    }
    

    @Override
    public void onDraw(Canvas c) {
    	new Rectangle(new Vector(50,50), new Vector(50,50)).Draw(c);
    	//new Text().DrawAt(canvas, Finger.position.get());
    	//text.position = Finger.position.get();
    	text.Draw(c, "Finger", Finger.position.get());
    	new Level().Draw(c);
    	player.Draw(c);
    	this.invalidate();
    }

    public boolean onTouch(View view, MotionEvent event) {
    	super.onTouchEvent(event);
    	Finger.update(event);
    	this.invalidate();
        return true;
    }
}
