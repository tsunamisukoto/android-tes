package com.example.warlockgame;
/**
 * 
 */

import Input.Finger;
import NPC.Archie;
import NPC.Droid;
import Tools.SpriteSheet;
import World.Level;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author impaler
 * This is the main surface that handles the ontouch events and draws
 * the image to the screen.
 */
public class RenderThread extends SurfaceView implements
		SurfaceHolder.Callback 
{
	Archie archie;
	Paint paint;
	private static final String TAG = RenderThread.class.getSimpleName();
	Level l;
	private GameThread thread;
SpriteSheet sprites;
	public RenderThread(Context context) {
		super(context);
		
		sprites = new SpriteSheet(BitmapFactory.decodeResource(getResources(), R.drawable.tiles),64);
		paint = new Paint();
		paint.setAntiAlias(false);
		paint.setColor(Color.RED);
		l = new Level(sprites);
		getHolder().addCallback(this);
		// load sprite sheet
		archie = new Archie(BitmapFactory.decodeResource(getResources(), R.drawable.characteridle),BitmapFactory.decodeResource(getResources(), R.drawable.characteridle2));
		// create the game loop thread
		thread = new GameThread(getHolder(), this);
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface is being destroyed");
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
		Log.d(TAG, "Thread was shut down cleanly");
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		Finger.update(event);
		return true;
	}

	protected void onDraw(Canvas canvas) {
		try
		{
			l.Draw(canvas,paint);
			archie.Draw(canvas);
		}
		catch(Exception ex)
		{
			//System.out.println("asd");
		}
	}
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		if(!thread.isAlive())
		{
			thread.setRunning(true);
			thread.start();
		}
	}

	public void surfaceCreated(SurfaceHolder holder) {
		if(!thread.isAlive())
		{
			thread.setRunning(true);
			thread.start();
		}
	}
}
