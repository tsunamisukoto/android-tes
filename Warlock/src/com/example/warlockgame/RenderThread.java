package com.example.warlockgame;
/**
 * 
 */

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
	private static final String TAG = RenderThread.class.getSimpleName();
	
	private GameThread thread;
	private Droid droid;

	public RenderThread(Context context) {
		super(context);
		getHolder().addCallback(this);
		// load sprite sheet
		sprites = new SpriteSheet(BitmapFactory.decodeResource(getResources(), R.drawable.tiles));
		// create droid and load bitmap
		droid = new Droid(sprites.tiles.get(5), 150, 150);
		// create the game loop thread
		thread = new GameThread(getHolder(), this);
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
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
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// delegating event handling to the droid
			droid.handleActionDown((int)event.getX(), (int)event.getY());
			
			// check if in the lower part of the screen we exit
			if (event.getY() > getHeight() - 50) {
				thread.setRunning(false);
				((Activity)getContext()).finish();
			} else {
				Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
			}
		} if (event.getAction() == MotionEvent.ACTION_MOVE) {
			// the gestures
			if (droid.isTouched()) {
				// the droid was picked up and is being dragged
				droid.setX((int)event.getX());
				droid.setY((int)event.getY());
			}
		} if (event.getAction() == MotionEvent.ACTION_UP) {
			// touch was released
			if (droid.isTouched()) {
				droid.setTouched(false);
			}
		}
		return true;
	}

	protected void onDraw(Canvas canvas) {
		// fills the canvas with black
		try
		{
			canvas.drawColor(Color.BLACK);
			int tmpx=0;
			int y =0;
			for(int x=0;x < sprites.tiles.size(); x++)
			{
				if(tmpx > 20)
				{
					tmpx=0;
					y++;
				}
				else
				{
					tmpx++;
				}
				canvas.drawBitmap(sprites.tiles.get(x), null, new Rect(tmpx * 32 , y * 32, (tmpx * 32) + 32, (y * 32) + 32), new Paint());
			}
			//droid.draw(canvas);
		}
		catch(Exception ex)
		{
			//System.out.println("asd");
		}
	}
}
