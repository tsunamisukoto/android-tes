package SpellProjectiles;


import com.developmental.warlocks.R;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11Ext;

import Tools.Vector;
import developmental.warlocks.GL.Grid;
import developmental.warlocks.GL.NewHeirarchy.GameObject;

public class LightningProjectile extends Projectile {
    public Vector Start, Dest;
    public float Range;
    public ArrayList<Integer> collisions = new ArrayList<Integer>();
    @Override
    public void draw(GL10 gl, float offsetX, float offsetY, boolean dontDrawInRelationToWorld) {

        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureName);

        if (mGrid == null) {
            // Draw using the DrawTexture extension.
            ((GL11Ext) gl).glDrawTexfOES(position.x, position.y, z, size.x, size.y);
        } else {
            // Draw using verts or VBO verts.
            gl.glPushMatrix();
            gl.glLoadIdentity();

                gl.glTranslatef(
                        bounds.Center.x-offsetX,
                       -bounds.Center.y-offsetY+15,
                        z);
            float angle = (float) Math.toDegrees((float) Math.atan2(this.Start.y-this.Dest.y, -(this.Start.x-this.Dest.x)) - Math.atan2(0, 0));
            gl.glRotatef(angle,0,0,1.0f);
            mGrid.get(this.frame).draw(gl, true, false);
//            if(!boundsz)
//            OpenGLTestActivity.boundingCircle.draw(gl,0,0);
            gl.glPopMatrix();

            //
        }
    }

    public LightningProjectile(Vector _start, Vector _dest, GameObject _parent) {
        super(R.drawable.spell_lightning,_start, _dest, _parent, 1, 4, new Vector(50, 50), 15);


        Range = 600;
        this.mGrid= new ArrayList<Grid>();
        Grid backgroundGrid = new Grid(2, 2, false);
        backgroundGrid.set(0, 0,  0,0, 0.0f, 0.0f, 1.0f, null);
        backgroundGrid.set(1, 0,Range, 0, 0.0f, 1.0f, 1.0f, null);
        backgroundGrid.set(0, 1,0,30, 0.0f, 0.0f, 0.0f, null);
        backgroundGrid.set(1, 1, Range,30, 0.0f,1.0f, 0.0f, null );
        mGrid.add(backgroundGrid);


    Start = _start.get();
        this.Dest = _dest;
        float dx = this.Start.x - this.Dest.x;
        float dy = this.Start.y - this.Dest.y;
        float ToteDist = Math.abs(dx) + Math.abs(dy);
        this.objectObjectType = ObjectType.LineSpell;
        this.Dest = new Vector(Start.x - ((dx / ToteDist) * Range), Start.y - ((dy / ToteDist) * Range));
        // Dest=new Vector(dx/ToteDist*maxVelocity,dy/ToteDist*maxVelocity);
        //this.health = 3;
        // shadowPaint = new Paint();
        // this.damagevalue=15;

        this.knockback =30;

       // SimpleGLRenderer.addParticle(new glParticle(Start, Dest, this.velocity, 7,  R.drawable.fireball));
        //this.paint.setAlpha(125);
    }

    @Override
    public void Update() {
        super.Update();
    }


}
