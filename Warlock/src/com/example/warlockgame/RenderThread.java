package com.example.warlockgame;

/**
 * 
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Actors.Player;
import Game.Block;
import Game.GameObject;
import HUD.Button;
import HUD.PopupText;
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
    public static Player archie2;
	public static int r = 0, g = 0;
	public static Level l;
	public static int objects = 0;
	public List<Button> buttons = new ArrayList<Button>();
	public static Point size, trueSize;
	private final SurfaceHolder holder;
	public static GameThread gameThread;
	public static boolean loaded = false;
	public static Context c;
    public static int playerno = 1;
    Finger finger;
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
//				boolean retry = true;
//				GameThread.setRunning(false);
//				while (retry)
//					try {
//						RenderThread.gameThread.join();
//						retry = false;
//					} catch (InterruptedException e) {
//
//					}
			}

			public void surfaceCreated(SurfaceHolder holder) {
				GameThread.setRunning(true);
			//	RenderThread.gameThread.start();
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
			l = new Level();
			loaded = true;
		}
		if (gameObjects.size() == 0) {
            // load sprite sheet
            if(Global.Server)
            {
                archie = new Player(new SpriteSheet(BitmapFactory.decodeResource(
                        getResources(), R.drawable.charsheetedit),7,8), new Vector(Global.WORLD_BOUND_SIZE.x/3, Global.WORLD_BOUND_SIZE.y/3));
                archie2= new Player(new SpriteSheet(BitmapFactory.decodeResource(
                        getResources(), R.drawable.charsheetedit),7,8), new Vector(Global.WORLD_BOUND_SIZE.x*2/3, Global.WORLD_BOUND_SIZE.y*2/3));
            }
            else
            {
                archie2 = new Player(new SpriteSheet(BitmapFactory.decodeResource(
                        getResources(), R.drawable.charsheetedit),7,8), new Vector(Global.WORLD_BOUND_SIZE.x/3, Global.WORLD_BOUND_SIZE.y/3));
                archie= new Player(new SpriteSheet(BitmapFactory.decodeResource(
                        getResources(), R.drawable.charsheetedit),7,8), new Vector(Global.WORLD_BOUND_SIZE.x*2/3, Global.WORLD_BOUND_SIZE.y*2/3));
            }
            addObject(archie);
            addObject(archie2);
//           addObject(new EllipseMovingAI(new SpriteSheet(BitmapFactory.decodeResource(
//                    getResources(), R.drawable.charsheet),7,8),new Vector(2800,1050)));
//            addObject(new EllipseMovingAI(new SpriteSheet(BitmapFactory.decodeResource(
//                    getResources(), R.drawable.charsheet),7,8),new Vector(2800,1050)));

addObject(new Block(2700,750));
            try {

               // ServerThread.printRemoteAddress("127.0.0.1");
            }
            catch (Exception e   )
            {

            }

            try {
                playerno = 0;
                if(Global.Multiplayer==true)
                new ServerThread(ServerThread.ActionType.AcceptInfomation).start();
            } catch (IOException e) {
                Log.d("INET","BREAK!\n");
                e.printStackTrace();
            }

//            try {
//
//                ClientTask.Send("127.0.0.1");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
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
            {
                if( Global.LEFT_HAND_MODE)
                {
                    this.buttons.add(new Button(new RectF((9-x) * screenSize / 10,
                            size.y, (9-x) * screenSize / 10 + (screenSize / 10),
                            trueSize.y), x, archie.Spells[x]));
                }
                else
                {
				this.buttons.add(new Button(new RectF(x * screenSize / 10,
						size.y, x * screenSize / 10 + (screenSize / 10),
						trueSize.y), x, archie.Spells[x]));
                }
            }
		}
	}
public static List<PopupText> popupTexts = new ArrayList<PopupText>();
	@Override
	protected void onDraw(Canvas canvas) {

		canvas.drawColor(Color.BLACK);
		// buffer refresh color
		// canvas.drawBitmap(R.drawable.previewjpg,new
		// RectF(size.x/2,size.y/2,size.x/2+15,size.y/2+15),new
		// Paint(Color.MAGENTA));
SoundHandler s = new SoundHandler(c);

        float offsetX=(archie.position.x - size.x / 2),offsetY=(archie.position.y - size.y / 2);

;        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setTextSize(40);
//        canvas.drawRect(5600-(archie.position.x-size.x/2),-(archie.posi            if(Global.DEBUG_MODE)
            gameObjects.get(x).DrawHitBox(offsetX, offsetY,canvas);tion.y-size.y/2),5600,200,new Paint());
		l.Draw(canvas,offsetX, offsetY);

        //canvfor(int f = 0; f<popupTexts.size();f++)
        {
            popupTexts.get(f).Draw(offsetX,offsetY,canvas);
        }    //canvas.drawText("" +(int) (-(archie.position.x - size.x / 2)) + "," + (int)(-(archie.position.y - size.y / 2)), 100,100,p);

        int listsize = gameObjects.size() - 1;
		for (int x = 0; x <= listsize; x++)
        {
            gameObjects.get(x).Draw(canvas,offsetX, offsetY);

        }

		for (int y = 0; y < 10; y++)
			this.buttons.get(y).Draw(canvas);
        //canvas.drawText(""+GameThread.Time,50,50,new Paint());
	}

	public static
		Load();
< gameObjects.size(); x++)
			if (gameObjects.get(x).id == id)
				gameObjects.remove(x);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int         if(!gameThread.isAlive())
        gameThread.start();t width,
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
	gameThread.interrupt();
		Log.e
		Log.d(TAG, "Surface is being destroyed");
		// tell the thread to shut down and wait for it to finish
		//Fthis is a clean shutdown
		boolean retry = tru