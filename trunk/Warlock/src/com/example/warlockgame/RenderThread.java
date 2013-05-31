package com.example.warlockgame;

/**
 * 
 */

import java.util.ArrayList;
import java.util.List;

import Actors.EllipseMovingAI;
import Actors.Player;
import Game.GameObject;
import HUD.Button;
import Input.Finger;
import Tools.SpriteSheet;
import Tools.Vector;
import World.Level;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author impaler This is the main surface that handles the ontouch events and
 *         draws the image to the screen.
 */
public class RenderThread extends SurfaceView implements SurfaceHolder.Callback {
	private static final String TAG = RenderThread.class.getSimpleName();
	public static List<GameObject> gameObjects = new ArrayList<GameObject>();
	public static Player archie;
	public static int r = 0, g = 0;
	public static Level l;
	public static int objects = 0;
	public List<Button> buttons = new ArrayList<Button>();
	public static Point size, trueSize;
	private final SurfaceHolder holder;
	public static GameThread gameThread;
	public static boolean loaded = false;
	public static Context c;

	public RenderThread(Context context, Point _size) {
		super(context);
		c = context;
		getHolder().addCallback(this);
		size = _size;
		trueSize = new Point(_size.x, _size.y);
		size.y -= size.y / 5;
		Global.paint = new Paint();
		Global.paint.setAntiAlias(true);
		Global.paint.setColor(Color.RED);


		Load();

		// create the game loop thread
		gameThread = new GameThread(getHolder(), this);
		this.holder = getHolder();
		this.holder.addCallback(new SurfaceHolder.Callback() {

			public void surfaceDestroyed(SurfaceHolder holder) {
				boolean retry = true;
				GameThread.setRunning(false);
				while (retry)
					try {
						RenderThread.gameThread.join();
						retry = false;
					} catch (InterruptedException e) {
					}
			}

			public void surfaceCreated(SurfaceHolder holder) {
				GameThread.setRunning(true);
				RenderThread.gameThread.start();
			}

			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
			}
		});

		// make the GamePanel focusable so it can handle events
		setFocusable(true);
		// getHolder().
	}

	public void Load() {
		if (Global.PlatformSkins.size() == 0) {
			// tiles.add(Bitmap.createScaledBitmap(Bitmap.createBitmap(bmp, x,
			// y, (int)size.x ,(int)size.y),(int)size.x, (int)size.y, false));
			Bitmap tmpbmp = BitmapFactory.decodeResource(getResources(),
					R.drawable.ground);

			Global.PlatformSkins.add(tmpbmp);
		}
		if (loaded == false) {
			l = new Level(new SpriteSheet(BitmapFactory.decodeResource(
					getResources(), R.drawable.isotiles), new Vector(32, 32)),
					new Vector(100, 100), BitmapFactory.decodeResource(
							getResources(), R.drawable.mousepos));
			loaded = true;
		}
		if (gameObjects.size() == 0) {
			// load sprite sheet
			archie = new Player(new SpriteSheet(BitmapFactory.decodeResource(
					getResources(), R.drawable.charsheetedit), new Vector(32,
					32)), new Vector(2800, 750));

			addObject(archie);

			EllipseMovingAI e = new EllipseMovingAI();
			e.position = new Vector(2800, 1050);
			addObject(e);
//            addObject(new EllipseMovingAI());
//            addObject(new EllipseMovingAI());
//            addObject(new EllipseMovingAI());
//            addObject(new EllipseMovingAI());
//            addObject(new EllipseMovingAI());
//            addObject(new EllipseMovingAI());
//            addObject(new EllipseMovingAI());
//            addObject(new EllipseMovingAI());
//            addObject(new EllipseMovingAI());
//            addObject(new EllipseMovingAI());
//            addObject(new EllipseMovingAI());
//            addObject(new EllipseMovingAI());
//            addObject(new EllipseMovingAI());
//            addObject(new EllipseMovingAI());
//            addObject(new EllipseMovingAI());
//            addObject(new EllipseMovingAI());
//			// Game.Block b = new Game.Block();
			// b.position=new Vector(2800,900);
			// addObject(b);

		}

		UserInterface();
	}

	public void UserInterface() {
		if (this.buttons.size() == 0) {
			int screenSize = size.x;
			for (int x = 0; x < 10; x += 1)
				this.buttons.add(new Button(new RectF(x * screenSize / 10,
						size.y, x * screenSize / 10 + (screenSize / 10),
						trueSize.y), x, archie.Spells[x]));
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {

		canvas.drawColor(Color.BLACK);// buffer refresh color
		// canvas.drawBitmap(R.drawable.previewjpg,new
		// RectF(size.x/2,size.y/2,size.x/2+15,size.y/2+15),new
		// Paint(Color.MAGENTA));

		canvas.save();
		canvas.translate(-archie.position.x - archie.size.x / 2,
				-archie.position.y);

        canvas.drawRect(0,0,5600,3000,Global.paint);
		l.Draw(canvas, 0, 0);

		canvas.translate(size.x / 2, size.y / 2);

		int size = gameObjects.size() - 1;
		for (int x = 0; x <= size; x++)
        {
            gameObjects.get(x).Draw(canvas);
            GameThread.q.insert(gameObjects.get(x).rect);
        }
        GameThread.q.Draw(canvas);
		canvas.restore();

		for (int y = 0; y < 10; y++)
			this.buttons.get(y).Draw(canvas);
        //canvas.drawText(""+GameThread.Time,50,50,new Paint());
	}

	public static void addObject(GameObject obj) {
		gameObjects.add(obj);
		gameObjects.get(gameObjects.size() - 1).id = objects++;
	}

	public static void delObject(int id) {
		for (int x = 0; x < gameObjects.size(); x++)
			if (gameObjects.get(x).id == id)
				gameObjects.remove(x);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		if (!RenderThread.gameThread.isAlive()) {
			GameThread.setRunning(true);
			RenderThread.gameThread.start();
		}
		System.out.println("surface Changed");
		Load();
	}

	public void surfaceCreated(SurfaceHolder holder) {
		/*
		 * if(!gameThread.isAlive()) { gameThread.setRunning(true);
		 * gameThread.start(); }
		 */
		System.out.println("surface Created");
		// Load();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface is being destroyed");
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
		boolean retry = true;
		while (retry)
			try {
				RenderThread.gameThread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
				GameThread.setRunning(false);
			}
		Log.d(TAG, "Thread was shut down cleanly");
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Finger.Update(event);
		return true;
	}

}
