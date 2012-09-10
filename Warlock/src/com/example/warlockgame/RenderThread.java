package com.example.warlockgame;
/**
 * 
 */

import Input.Finger;
import NPC.Droid;
import Tools.SpriteSheet;
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
	SpriteSheet sprites;
	Paint paint;
	private static final String TAG = RenderThread.class.getSimpleName();
	
	private GameThread thread;

	public RenderThread(Context context) {
		super(context);
		paint = new Paint();
		paint.setColor(Color.RED);
		getHolder().addCallback(this);
		// load sprite sheet
		sprites = new SpriteSheet(BitmapFactory.decodeResource(getResources(), R.drawable.tiles),64);
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
		// fills the canvas with black
		try
		{
			canvas.drawColor(Color.BLACK);
			canvas.drawRect(new Rect(0,0,100,100), paint);

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