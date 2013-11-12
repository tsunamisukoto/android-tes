package com.developmental.myapplication;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceHolder;

import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.multiplayer.realtime.RealTimeReliableMessageSentListener;
import com.google.android.gms.games.multiplayer.realtime.Room;

import java.util.ArrayList;
import java.util.Collections;

import HUD.Button;
import HUD.PopupText;
import Input.NetworkFinger;
import Tools.Serializer;

/**
 * @author impaler
 *         <p/>
 *         The Main thread which contains the game loop. The thread must have
 *         access to the surface view and holder to trigger events every game
 *         tick.
 */
public class GameThread extends Thread implements RealTimeReliableMessageSentListener {
    public static int Gamestep = 0;
    public static ArrayList<NetworkFinger> fingers = new ArrayList<NetworkFinger>();

    // Surface holder that can access the physical surface
    private final SurfaceHolder surfaceHolder;
    // The actual view that handles inputs
    // and draws to the surface
    private final RenderThread renderThread;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.d("INET", "SENDING MESSAGE" + GameThread.k.Step + " AT" + Gamestep);

            for (String p : RenderThread.c.mRoom.getParticipantIds()) {
                try {
                    if (p.equals(RenderThread.c.mMyId))
                        continue;
                    RenderThread.c.getGamesClient().sendReliableRealTimeMessage(GameThread.this, Serializer.SerializetoBytes(GameThread.k),
                            RenderThread.c.mRoom.getRoomId(), p);

                } catch (Exception e) {

                }
            }
            Log.d("INET", "MESSAGE LEFT AT" + Gamestep);
        }
    };
    // flag to hold game state
    public static boolean running;

    public static void setRunning(boolean _running) {
        running = _running;
    }

    public GameThread(SurfaceHolder surfaceHolder, RenderThread gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.renderThread = gamePanel;
        RenderThread.popupTexts = new ArrayList<PopupText>();
    }

    int j = 0;

    public void Update() {
        Gamestep += 1;

        // boolean f = false;
        int selectedSpell = -1;
        // Chekcs Which Buttons are Down, the last down one in order of left to
        // right becomes the selected spell
        for (Button b : this.renderThread.buttons) {
            b.Update();
            if (b.down) {
                selectedSpell = this.renderThread.buttons.indexOf(b);

                // Log.d("INET","DOWN");
            }
        }
        for (int f = 0; f < RenderThread.popupTexts.size(); f++) {
            RenderThread.popupTexts.get(f).Update();
        }
        for (int f = 0; f < RenderThread.Particles.size(); f++) {
            RenderThread.Particles.get(f).Update();
        }

        int i = 0;
        if (i < fingers.size())

            while (i < fingers.size()) {
                // Log.d("INET",fingers.get(i).Step+" " + Gamestep);
                if (fingers.get(i).Step <= Gamestep) {
                    RenderThread.players.get(fingers.get(i).id).FingerUpdate(fingers.get(i).finger, fingers.get(i).SelectedSpell);
                    fingers.remove(i);
                } else
                    i++;
            }
        Collision();

        RenderThread.l.platform.Shrink();
        Collections.sort(RenderThread.gameObjects);


        if (RenderThread.finger != null)
            if (RenderThread.finger.down) {

                k = new NetworkFinger(Gamestep + 4, RenderThread.finger.WorldPositions(), Global.playerno, selectedSpell);
                fingers.add(k);

                if (Global.Multiplayer)
                    new Thread(this.runnable).start();
            }

    }

    public static int s = 0;
    public static NetworkFinger k;

    public void Collision() {
        q.clear();
        for (int x = 0; x < RenderThread.gameObjects.size(); x++) {
            RenderThread.gameObjects.get(x).Update();
            for (int y = x + 1; y < RenderThread.gameObjects.size(); y++) {
                if (RenderThread.gameObjects.size() > y
                        && RenderThread.gameObjects.size() > x)
                    if (RenderThread.gameObjects.get(x).owner == null
                            || RenderThread.gameObjects.get(y).owner == null) {
                        if (RenderThread.gameObjects.get(x).CollidesWith(RenderThread.gameObjects.get(y))) {

                            RenderThread.gameObjects.get(y).Collision(
                                    RenderThread.gameObjects.get(x));
                            continue;
                        }

                    } else if (RenderThread.gameObjects.get(x).owner.id != RenderThread.gameObjects
                            .get(y).id
                            && RenderThread.gameObjects.get(y).owner.id != RenderThread.gameObjects
                            .get(x).id)
                        if (RenderThread.gameObjects.get(x).CollidesWith(RenderThread.gameObjects.get(y))) {
                            RenderThread.gameObjects.get(y).Collision(
                                    RenderThread.gameObjects.get(x));
                            continue;
                        }
            }


        }
    }


    public static GamesClient gamesClient;
    public static Room room;
    public static Quadtree q = new Quadtree(0, new RectF(0, 0, Global.WORLD_BOUND_SIZE.x, Global.WORLD_BOUND_SIZE.y));
    int i = 0;


    private boolean paused = false;
    private int fps = 30;
    private int frameCount = 0;

    @Override
    public void run() {
        Canvas canvas = null;
        //This value would probably be stored elsewhere.
        final double GAME_HERTZ = 25.0;
        //Calculate how many ns each frame should take for our target game hertz.
        final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
        //At the very most we will update the game this many times before a new render.
        //If you're worried about visual hitches more than perfect timing, set this to 1.
        final int MAX_UPDATES_BEFORE_RENDER = 5;
        //We will need the last update time.
        double lastUpdateTime = System.nanoTime();
        //Store the last time we rendered.
        double lastRenderTime = System.nanoTime();


        //If we are able to get as high as this FPS, don't render again.
        final double TARGET_FPS = 60;

        final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

        //Simple way of finding FPS.
        int lastSecondTime = (int) (lastUpdateTime / 1000000000);

        while (running) {
            double now = System.nanoTime();
            int updateCount = 0;

            if (!paused) {
                try {
                    canvas = this.surfaceHolder.lockCanvas();

                    //Do as many game updates as we need to, potentially playing catchup.
                    while (now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
                        Update();
                        lastUpdateTime += TIME_BETWEEN_UPDATES;
                        updateCount++;
                    }

                    //If for some reason an update takes forever, we don't want to do an insane number of catchups.
                    //If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
                    if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
                        lastUpdateTime = now - TIME_BETWEEN_UPDATES;
                    }

                    //Render. To do so, we need to calculate interpolation for a smooth render.
                    float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES));
                    if (canvas != null)
                        renderThread.onDraw(canvas);
                    else
                        break;
                    lastRenderTime = now;
                } finally {
                    if (canvas != null)
                        this.surfaceHolder.unlockCanvasAndPost(canvas);
                }
                //Update the frames we got.
                int thisSecond = (int) (lastUpdateTime / 1000000000);
                if (thisSecond > lastSecondTime) {
                    System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
                    fps = frameCount;
                    frameCount = 0;
                    lastSecondTime = thisSecond;
                }

                //Yield until it has been at least the target time between renders. This saves the CPU from hogging.
                while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
                    Thread.yield();

                    //This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
                    //You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
                    //FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different peoples' solutions to this.
                    try {
                        Thread.sleep(1);
                    } catch (Exception e) {
                    }

                    now = System.nanoTime();
                }
            }
        }
    }

    @Override
    public void onRealTimeMessageSent(int statusCode, int tokenId, String s) {

    }
}