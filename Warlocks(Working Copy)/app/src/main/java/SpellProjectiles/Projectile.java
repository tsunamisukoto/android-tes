package SpellProjectiles;

import com.developmental.warlocks.R;

import javax.microedition.khronos.opengles.GL10;

import Game.DamageType;
import Tools.Vector;
import developmental.warlocks.GL.Grid;
import developmental.warlocks.GL.NewHeirarchy.Collideable;
import developmental.warlocks.GL.SimpleGLRenderer;
import developmental.warlocks.Global;

public abstract class Projectile extends Collideable {

   protected float height = 0;


    public Projectile(int resource, Vector _from, Vector _to, Collideable shooter, int rank) {
        super(resource, _from, new Vector(100, 100), 50, 0);
        this.owner = shooter;
        Vector from = _from.get();
        Vector to = new Vector(_to.x - size.x / 2, _to.y - size.y / 2);

        this.bounds.Center = position;

        this.maxVelocity = this.maxVelocity;
        Stats(rank);
        this.bounds.Radius = this.size.x / 2;
        this.shadowed = true;
        this.shadowGrid = Grid.shadowGridGenerateProjectile(size);
        setFrames();
        this.objectObjectType = ObjectType.Projectile;

        this.velocity = GetVel(from, to);

        SetVelocity(this.maxVelocity);
        DiesOnImpact = true;
        this.KillsOnImpact = false;
        this.AppliesVelocity = true;
        this.CanBeSwapped = true;
        this.CanBeExploded = true;
        this.CanBeAbsorbed = true;
        height = 40;
        //   this.bounds.Radius=size.x;
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

    @Override
    public void Animate() {
        super.Animate();
        if (lifePhase % this.frameRate == 1)
            frame++;
        if (frame >= mGrid.size()) {
            frame = 0;
        }
    }

    protected void Stats(int rank)
    {
        this.maxVelocity = 4;

        switch (rank)
        {
            case 1:
                this.health = 100;
                this.knockback =7;
                this.size = new Vector(50,50);
                this.damagevalue = 6;

                break;
            case 2:
                this.health = 110;
                this.knockback =8.5;
                this.size = new Vector(50,50);
                this.damagevalue = 7;
                break;
            case 3:
                this.health = 120;
                this.knockback =10;
                this.size = new Vector(50,50);
                this.damagevalue = 8;
                break;
            case 4:
                this.health = 130;
                this.knockback =11.5;
                this.size = new Vector(50,50);
                this.damagevalue = 9;
                break;
            case 5:
                this.health = 140;
                this.knockback =13;
                this.size = new Vector(60,60);
                this.damagevalue = 10;
                break;
            case 6:
                this.health = 150;
                this.knockback =14.5;
                this.size = new Vector(60,60);
                this.damagevalue = 11;
                break;
            case 7:
                this.health = 160;
                this.knockback =16;
                this.size = new Vector(60,60);
                this.damagevalue = 12;
                break;
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
