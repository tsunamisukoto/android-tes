package SpellProjectiles;

import com.developmental.warlocks.R;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import Game.DamageType;
import Tools.Vector;
import developmental.warlocks.GL.Grid;
import developmental.warlocks.GL.NewHeirarchy.Collideable;
import developmental.warlocks.GL.SimpleGLRenderer;
import developmental.warlocks.Global;

public abstract class Projectile extends Collideable {

   protected float height = 0;
    public void SetVelocity(float vel) {

        float totalVel = Math.abs(this.velocity.x) + Math.abs(this.velocity.y);
        this.velocity = new Vector(vel * this.velocity.x / totalVel, vel
                * this.velocity.y / totalVel);
    }

    public Vector GetVel(Vector from, Vector to) {
        this.position = from;
        float distanceX = to.x - from.x;
        float distanceY = to.y - from.y;
        float totalDist = Vector.DistanceBetween(to, from);

        return new Vector(this.maxVelocity * (distanceX / totalDist),
                this.maxVelocity * distanceY / totalDist);
    }

    @Override
    public void draw(GL10 gl, float offsetX, float offsetY, boolean dontDrawInRelationToWorld) {

            // Draw using verts or VBO verts.
            gl.glPushMatrix();
            gl.glLoadIdentity();
            if(dontDrawInRelationToWorld)
                gl.glTranslatef(bounds.Center.x,bounds.Center.y,0);
            else
                gl.glTranslatef(
                        position.x-offsetX,
                     -position.y-offsetY+height,
                        z);


        gl.glPushMatrix();
        gl.glTranslatef(0f,-5f-height,0f);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, Global.resources.get(R.drawable.shadow));
        if(shadowGrid!=null)
            shadowGrid.draw(gl,true,false);
        gl.glPopMatrix();
            if(rotation!=0)
                gl.glRotatef(rotation,0,0,1.0f);

        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureName);
        mGrid.get(this.frame).draw(gl, true, false);
//            if(!boundsz)
//            OpenGLTestActivity.boundingCircle.draw(gl,0,0);
            gl.glPopMatrix();

            //

    }
protected int framecount = 4;
    public Projectile(int resource,Vector _from, Vector _to, Collideable shooter, float _health, float _maxvelocity, Vector _size, float _damagevalue) {
        super(resource,_from, _size,_health,_damagevalue);

        this.owner = shooter;
        shadowed=true;
        this.health = _health;
        this.maxVelocity = _maxvelocity;
        this.size = _size;

        this.shadowed=true;
        this.shadowGrid=Grid.shadowGridGenerateProjectile(new Vector (100,100));
        this.damagevalue = _damagevalue;
        this.objectObjectType = ObjectType.Projectile;
        Vector from = _from.get();
        Vector to = new Vector(_to.x-size.x/2,_to.y-size.y/2);
setFrames();
        this.velocity =GetVel(from,to);

        SetVelocity(this.maxVelocity);

        this.bounds.Center = position;
        this.bounds.Radius = this.size.x / 2;

        //   this.bounds.Radius=size.x;
    }
    protected void setFrames()
    {
    FramesTail();

    }
    protected void FramesTail()
    {
        this.mGrid= new ArrayList<Grid>();
        for(int i = 0; i< framecount; i++) {
            Grid backgroundGrid = new Grid(2, 2, false);
            backgroundGrid.set(0, 0, -1.5f * size.x , -size.y / 2, 0.0f,  0.25f * i, 1.0f, null);
            backgroundGrid.set(1, 0, size.x / 2, -size.y / 2, 0.0f, 0.25f * (i+1), 1.0f, null);
            backgroundGrid.set(0, 1, -1.5f * size.x , size.y / 2, 0.0f,  0.25f * i, 0.0f, null);
            backgroundGrid.set(1, 1, size.x / 2, size.y / 2, 0.0f,  0.25f * (i+1), 0.0f, null);
            mGrid.add(backgroundGrid);
        }
    }
    protected void FramesNoTail()
    {
        this.mGrid= new ArrayList<Grid>();
        for(int i = 0; i< framecount; i++) {
            Grid backgroundGrid = new Grid(2, 2, false);
            backgroundGrid.set(0, 0, -size.x / 2, -size.y / 2, 0.0f,  0.25f * i, 1.0f, null);
            backgroundGrid.set(1, 0, size.x / 2, -size.y / 2, 0.0f, 0.25f * (i+1), 1.0f, null);
            backgroundGrid.set(0, 1,- size.x / 2, size.y / 2, 0.0f,  0.25f * i, 0.0f, null);
            backgroundGrid.set(1, 1, size.x / 2, size.y / 2, 0.0f,  0.25f * (i+1), 0.0f, null);
            mGrid.add(backgroundGrid);
        }
    }


    public void DealDamageTo(Collideable g) {
        g.Damage(this.damagevalue, DamageType.Spell);
        owner.damageDealtThisRound += damagevalue;
    }

    public void Damage(float dmgDealt, DamageType d) {
        switch (d) {
            case Lava:
                break;
            case Spell:
            default:
                if (dmgDealt > this.health)
                    this.health = 0;
                else
                    this.health -= dmgDealt;
                break;
        }

    }




    @Override
    public void Update() {
        if (this.health > 0) {
            super.Update();
            this.health--;
        } else
            SimpleGLRenderer.delObject(this.id);
    }
}