package com.developmental.myapplication.GL.NewHeirachy;

import android.graphics.Color;
import android.graphics.RectF;

import com.developmental.myapplication.GL.Grid;
import com.developmental.myapplication.Global;
import com.developmental.myapplication.RenderThread;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11Ext;

import Input.Pointer;

/**
 * Created by Scott on 24/01/14.
 */
public class glButton extends Renderable {

    public RectF rect;
    Grid spellGrid;
  public int spellResource;
    public glButton(int _mResourceID,int _sResourceID, int x, int y, int w, int h,Grid grid) {
        super(_mResourceID);
        rect = new RectF(x,y,x+w,y+h);
        spellResource = _sResourceID;
        spellGrid= grid;
    }
public boolean down =false;

    @Override
    public void draw(GL10 gl, float offsetX, float offsetY, boolean b) {
        super.draw(gl, offsetX, offsetY, b);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, spellResource);

        if (spellGrid == null) {
            // Draw using the DrawTexture extension.
            ((GL11Ext) gl).glDrawTexfOES(position.x, position.y, z, size.x, size.y);
        } else {
            // Draw using verts or VBO verts.
            gl.glPushMatrix();
            gl.glLoadIdentity();
            if(b)
                gl.glTranslatef(position.x,position.y,0);
            else
                gl.glTranslatef(
                        position.x-offsetX,
                        Global.WORLD_BOUND_SIZE.y-position.y-offsetY,
                        z);
            spellGrid.draw(gl, true, false);
//            if(!boundsz)
//            OpenGLTestActivity.boundingCircle.draw(gl,0,0);
            gl.glPopMatrix();

            //
        }
//        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, vertices);
//        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP,0,4);
//        new MyGLBall().draw(gl);
    }

    public void Update(int i) {
        super.Update();

        if (!Global.LOCKSPELLMODE) {
            for (int x = 0; x < RenderThread.finger.pointers.length; x++) {
                Pointer f = RenderThread.finger.pointers[x];

                if (!f.down)
                    continue;

                if (this.rect.contains(f.position.x, Global.size.y*5/4-f.position.y)) {
                    this.down = true;

                    if(RenderThread.archie.Spells[i].Current>Global.GlobalCooldown)
                    {       frame = 2;
                        return;
                    }
                  frame=1;

                    return;
                }
            }
            this.down = false;
            if(RenderThread.archie.Spells[i].Current>Global.GlobalCooldown)
            {       frame = 2;
                return;
            }
           frame= 0;


        }
    }
}
