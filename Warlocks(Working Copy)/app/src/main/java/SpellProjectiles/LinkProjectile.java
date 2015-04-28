package SpellProjectiles;


import android.graphics.RectF;

import com.developmental.warlocks.R;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11Ext;

import Tools.Vector;
import developmental.warlocks.GL.Grid;
import developmental.warlocks.GL.NewHeirarchy.Collideable;
import developmental.warlocks.GL.NewHeirarchy.GameObject;
import developmental.warlocks.GL.SimpleGLRenderer;
import developmental.warlocks.Global;


/**
 * Created by Scott on 27/07/13.
 */
public class LinkProjectile extends Projectile {
    public Vector Start, Dest;
    float Range = 10;
    Grid lineGrid;
    int lineTex = 0;
    int lineShadowTex = 0;
    float LineRadius;
    private int drawPhase = 0;

    public LinkProjectile(Vector _start, Vector _dest, GameObject _parent, int Rank) {
        super(R.drawable.spell_link_head2, _start, _dest, _parent, Rank);
        lineTex = Global.resources.get(R.drawable.spell_link2);
        lineShadowTex = Global.resources.get(R.drawable.spell_link2_shadow);
        LineRadius = 50f;

        Start = _start.get();
        this.Dest = _dest;
        this.objectObjectType = ObjectType.LinkSpell;
        this.Dest = Start;
        this.DiesOnImpact = false;
        this.KillsOnImpact = false;
        this.LinksToThings = true;
        this.AppliesVelocity = false;
        this.CanBeLinked = false;
        CanBeAbsorbed = false;
    }

    @Override
    public boolean Within(RectF Bounds) {
        return true;
    }

    @Override
    public void draw(GL10 gl, float offsetX, float offsetY, boolean dontDrawInRelationToWorld) {



        if (mGrid == null) {
            // Draw using the DrawTexture extension.
            ((GL11Ext) gl).glDrawTexfOES(position.x, position.y, z, size.x, size.y);
        } else {
            // Draw using verts or VBO verts.
            gl.glPushMatrix();
            gl.glLoadIdentity();

            gl.glTranslatef(
                    bounds.Center.x - offsetX,
                    -bounds.Center.y - offsetY + 8,
                    z);
            float angle = 0;


//                Range= Vector.DistanceBetween(Start,Dest);
//                mGrid=  Grid.LightningLineGrid(Range);


            angle = (float) Math.toDegrees((float) Math.atan2((this.Start.y - this.Dest.y), -(this.Start.x - this.Dest.x)) - Math.atan2(0, 0));
            gl.glPushMatrix();
            gl.glRotatef((angle), 0, 0, 1.0f);
            if (lineGrid != null) {
                gl.glBindTexture(GL10.GL_TEXTURE_2D, lineShadowTex);
                lineGrid.draw(gl, true, false);
                gl.glPopMatrix();
                gl.glPushMatrix();
                gl.glBindTexture(GL10.GL_TEXTURE_2D, lineTex);
                gl.glTranslatef(0, this.height, 0);

                gl.glRotatef((angle), 0, 0, 1.0f);
                lineGrid.draw(gl, true, false);

                gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureName);
                mGrid.get(this.frame).draw(gl, true, false);
                gl.glPopMatrix();
            }



//            if(!boundsz)
//            OpenGLTestActivity.boundingCircle.draw(gl,0,0);
            gl.glPopMatrix();

            //
        }
    }

    protected void Stats(int rank) {
        this.maxVelocity = 10f;

        switch (rank) {
            case 1:
                this.health = 400;
                this.knockback = 0.5;
                this.size = new Vector(50, 50);
                this.damagevalue = 0.05f;

                break;
            case 2:
                this.health = 400;
                this.knockback = 0.5;
                this.size = new Vector(50, 50);
                this.damagevalue = 0.1f;
                break;
            case 3:
                this.health = 400;
                this.knockback = 0.5;
                this.size = new Vector(50, 50);
                this.damagevalue = 0.15f;
                break;
            case 4:
                this.health = 400;
                this.knockback = 0.5;
                this.size = new Vector(50, 50);
                this.damagevalue = 0.20f;
                break;
            case 5:
                this.health = 400;
                this.knockback = 0.5;
                this.size = new Vector(60, 60);
                this.damagevalue = 0.25f;
                break;
            case 6:
                this.health = 400;
                this.knockback = 0.5;
                this.size = new Vector(60, 60);
                this.damagevalue = 0.3f;
                break;
            case 7:
                this.health = 400;
                this.knockback = 0.5;
                this.size = new Vector(60, 60);
                this.damagevalue = 0.35f;
                break;
        }

    }

    @Override
    public void Update() {

        if (linked != null) {

            if (linked.health <= 0 || linked.position == null) {
                this.position = bounds.Center;
                this.velocity = linked.velocity;
                linked = null;

            }

        }
        if (linked != null)
            if (linked.health <= 0) {
                this.position = bounds.Center;
                this.velocity = linked.velocity;
                linked = null;

            }
        if (this.health > 0) {
            if (linked == null || linked.health <= 0) {
                super.Update();
                linked = null;
                Dest = owner.bounds.Center;
                Start = this.bounds.Center;
                drawPhase = lifePhase;

            } else {
                lifePhase++;
                owner.velocity = owner.velocity.add(linked
                        .DirectionalPull(owner.bounds.Center, this.knockback / 2));
                linked.velocity = linked.velocity.add(owner
                        .DirectionalPull(linked.bounds.Center, this.knockback / 2));
                Dest = owner.bounds.Center;
                this.bounds = linked.bounds;
                Start = this.bounds.Center;

                switch (linked.objectObjectType) {
                    case Player:
                    case Enemy:
                    case GameObject:

                        DealDamageTo(linked);

                        break;
                }
                health -= 1;
                drawPhase--;
            }
            Range = Vector.DistanceBetween(Start, Dest);
            lineGrid = Grid.LinkLineGrid(Range, drawPhase, LineRadius);
        } else {

            SimpleGLRenderer.delObject(this.id);
        }

    }

    @Override
    protected void setFrames() {
        FramesNoTail();
    }

    public void Link(Collideable g) {
        if (linked == null) {
            this.linked = g;
            this.bounds = linked.bounds;
            Start = linked.bounds.Center;
            Dest = this.owner.bounds.Center;
        }
//        paint.setColor(Color.WHITE);
//        paint.setStrokeWidth(4);
    }

}
