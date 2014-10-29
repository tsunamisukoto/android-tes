package developmental.warlocks.GL.NewHeirarchy;

import com.developmental.warlocks.R;

import javax.microedition.khronos.opengles.GL10;

import Game.DamageType;
import Game.Destination;
import HUD.glHealthBar;
import Spells.SpellEffect;
import HUD.PopupText;
import Spells.Spell;
import Spells.SpellInfo;
import Tools.Vector;
import Tools.iVector;
import developmental.warlocks.GL.Grid;
import developmental.warlocks.GL.SimpleGLRenderer;
import developmental.warlocks.Global;

public class GameObject extends Collideable {



   glHealthBar healthbar;

    public Spell[] Spells;
    public boolean dead = false;





    public GameObject(int resourceId,Vector _pos, Vector _feet, Vector _size,SpellInfo[] spellList) {
        this(resourceId,_pos,_feet,_size);
        this.Spells = new Spell[7];
        shadowed = true;
        healthbar = new glHealthBar(R.drawable.healthbar2,new Vector(100,20), new Vector(0,-120),this, glHealthBar.type.Health);
        this.Spells = Spell.GenerateSpellList(this,spellList);
    }

@Override
    public void draw(GL10 gl, float offsetX, float offsetY, boolean dontDrawInRelationToWorld) {
       super.draw(gl,offsetX,offsetY, dontDrawInRelationToWorld);
        for(SpellEffect e : Debuffs) {
          e.draw(gl,offsetX-position.x,offsetY+position.y,false);
        }
        if(this.Marker!=null)
        {
            this.Marker.draw(gl,offsetX,offsetY,dontDrawInRelationToWorld);
        }
        if(displayhealth>0)
            this.healthbar.draw(gl,offsetX-position.x,offsetY+position.y,dontDrawInRelationToWorld);
    }
    public void Animate(Vector dest) {
        if (dest != null) {
            float deltaY = Math.abs(dest.y) - Math.abs(this.feet.y);
            float deltaX = Math.abs(dest.x) - Math.abs(this.feet.x);
            float angleInDegrees =(float) (Math.atan2(deltaY, deltaX) * 180 / Math.PI
                    + 180);



            if (angleInDegrees >= 157.5 && angleInDegrees < 202.5) {
                mGrid= Global.SpritesRight;
            } else if (angleInDegrees >= 112.5
                    && angleInDegrees < 157.5) {
                mGrid=Global.SpritesRightUp;
            } else if (angleInDegrees >= 202.5
                    && angleInDegrees < 247.5) {
                mGrid= Global.SpritesRightDown;
            } else if (angleInDegrees >= 247.5
                    && angleInDegrees < 292.5) {
                mGrid=Global.SpritesDown;
            } else if (angleInDegrees >= 292.5
                    && angleInDegrees < 337.5) {
                mGrid=Global.SpritesLeftDown;
            } else if (angleInDegrees < 22.5
                    || angleInDegrees >= 337.5) {
                mGrid=Global.SpritesLeft;
            } else if (angleInDegrees >= 22.5
                    && angleInDegrees < 67.5) {
                mGrid=Global.SpritesLeftUp;
            } else if (angleInDegrees >= 67.5
                    && angleInDegrees < 112.5)
                mGrid=Global.SpritesUp;

            if(lifePhase%this.frameRate==1)
            frame++;
            if(frame>=mGrid.size())
            {
                frame = 0;
            }
        }

    }

    public GameObject(int resourceId,Vector _pos, Vector _feet, Vector _size) {
        super(resourceId,_pos, _size,500,0);

        this.objectObjectType = ObjectType.GameObject;


        this.velocity = new Vector(0, 0);
        //this.Spells = new Spell[10];

        this.shadowed=true;
    this.shadowGrid=Grid.shadowGridGenerateObject(new Vector(100, 100));

    }


@Override
    protected void Heal(float HealAmount)
    {
        if (HealAmount+health > this.maxhealth) {

            HealAmount=maxhealth-health;
            this.health = maxhealth;
        } else {
            this.health += HealAmount;
        }
        if(HealAmount>0)
                    SimpleGLRenderer.popupTexts.add(new PopupText(PopupText.TextType.Poison,HealAmount+"",this.bounds.Center.get(),12));



    }
    @Override
    public void Damage(float dmgDealt, DamageType d) {
        if (dmgDealt > this.health) {
            this.health = 0;
            dmgDealt=0;
            if (!Global.DEBUG_MODE) {
                this.dead = true;
                SimpleGLRenderer.delObject(this.id);
            }
        } else {
          this.health -= dmgDealt;
        }
        this.mana += dmgDealt;
        this.displayhealth = 20;
        if(dmgDealt>0)
        switch (d) {
            case Spell:

                SimpleGLRenderer.popupTexts.add(new PopupText(PopupText.TextType.Spell,dmgDealt+"",this.bounds.Center.get(),12));
                break;
            case Lava:

                SimpleGLRenderer.popupTexts.add(new PopupText(PopupText.TextType.Lava,dmgDealt+"",this.bounds.Center.get(),12));
                this.AddtoBurnCounter((int)dmgDealt);
                break;
        }
    }

    protected int displayhealth = 0;





    public boolean casting = false, frozen = false, stunned = false;
    @Override
    public void Update() {
        super.Update();
        if(Marker!=null)
            Marker.Update();
        if(lifePhase%150 == 149)
            Heal(this.HealthRegenPer150Updates);
        if (displayhealth > 0)
            this.displayhealth -= 1;

        switch (objectObjectType)
        {
            case GameObject:
                case Player:
                    case Enemy:
                        if ((!SimpleGLRenderer.l.platform.Within(this.bounds.Center))&&!SimpleGLRenderer.l.iceplatform.Within(this.bounds.Center)) {
                        //    Log.e("LAVA","I AM ON ZEE LAVA!!!");
                           Damage(3, DamageType.Lava);
                        } else {
//                            if(displayhealth==0)
//                            velocity = Vector.multiply(velocity, 0.99f);
                        }
                break;

        }
        if(this.burnTicker>0)
        {
            burnTicker--;

        }
        else
        {
            burnCounter = 0;
        }
        if(burnCounter>=100)
        {
            burnCounter-=100;
            this.Debuffs.add(new SpellEffect(150, SpellEffect.EffectType.Burn,this, R.drawable.effect_burn));
        }
        casting = false;
        frozen = false;
        stunned = false;
        int slowcounter = 0;
        for (int i = 0; i < Debuffs.size(); i++) {

            SpellEffect e = Debuffs.get(i);
            e.Update();
            if (e.Duration > 0) {


                if (e.effectType == SpellEffect.EffectType.Cast)
                    casting = true;
                if (e.effectType == SpellEffect.EffectType.Explode)
                    casting = true;
                if (e.effectType == SpellEffect.EffectType.Freeze)
                    frozen = true;
                if (e.effectType == SpellEffect.EffectType.Stun)
                    stunned = true;
               if(e.effectType== SpellEffect.EffectType.Slow)
                   slowcounter++;
            } else {

                e.FinalUpdate();
                Debuffs.remove(i);
            }


        }
        if (!casting && !frozen)
            if (this.destination != null)
                MoveTowards(this.destination, maxVelocity * (float) Math.pow(0.5, slowcounter), acceleration * (float) Math.pow(0.5, slowcounter));

        this.feet = new Vector(this.position.x + this.size.x / 2,
                this.position.y -bounds.Radius );
        bounds.Center = feet;


        CollideMap();

        if (Spells != null)
            for (int j = 0; j < Spells.length; j++) {

                Spells[j].Update();
            }
       switch (objectObjectType)
       {

           case GameObject:
           case Player:
           case Enemy:
               if(destination!=null)
                   Animate(destination);
               break;

       }

    }


    void AddtoBurnCounter(int burrns)
    {
       this.burnCounter+=burrns;
        this.burnTicker = 200;
    }

    public void FingerUpdate(iVector[] f, int SelectedSpell) {

        if (SelectedSpell == -1) {
            if (f.length > 0)
            {

                StartTo(new Vector(f[0].x, f[0].y));
            }
        } else {

            if (Spells[SelectedSpell].Current == 0)
            {

                if(Spells[SelectedSpell].Cast(f))
                    for(Spell spell:Spells)
                    {
                        if(spell.Current<Global.GlobalCooldown)
                            spell.Current=Global.GlobalCooldown;
                    }
            }
        }
    }

    protected Destination Marker;

    public void StartTo(Vector Dest) {
        this.destination = new Vector(Dest.x, Dest.y);
        this.Marker = new Destination(destination);
    }

    public void CollideMap() {
        if (this.bounds.Center.x -bounds.Radius< 0)
            this.velocity.x = Math.abs(this.velocity.x);
        if (this.bounds.Center.x + bounds.Radius > Global.WORLD_BOUND_SIZE.x)
            this.velocity.x = -Math.abs(this.velocity.x);
        if (this.bounds.Center.y + bounds.Radius> Global.WORLD_BOUND_SIZE.y)
            this.velocity.y = -Math.abs(this.velocity.y);
        if (this.bounds.Center.y-bounds.Radius < 0)
            this.velocity.y = Math.abs(this.velocity.y);
    }


    //Applies a Vector to the velocity, based on accelleration and max speed, in the direction of the destination
    protected void MoveTowards(Vector d, float _maxVelocity, float _acceleration) {

        float distanceX = d.x - this.bounds.Center.x;
        float distanceY = d.y - this.bounds.Center.y;

        float totalDist = (float) Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));

        if (totalDist > Vector.CurrentVelocity(velocity) + _acceleration) {
            Vector newvelocity = new Vector(_maxVelocity
                    * (distanceX / totalDist), _maxVelocity * distanceY
                    / totalDist);
            if (Math.abs(newvelocity.x - this.velocity.x) > _acceleration)
                if (newvelocity.x > this.velocity.x)
                    newvelocity.x = this.velocity.x + _acceleration;
                else
                    newvelocity.x = this.velocity.x - _acceleration;
            if (Math.abs(newvelocity.y - this.velocity.y) > _acceleration)
                if (newvelocity.y > this.velocity.y)
                    newvelocity.y = this.velocity.y + _acceleration;
                else
                    newvelocity.y = this.velocity.y - _acceleration;
            this.velocity = newvelocity;
        } else {

            this.feet = this.destination;
             //bounds.Center=feet;
            this.destination = null;
            this.Marker=null;
            this.velocity = new Vector(0, 0);
        }
    }



    public static Vector PositiononEllipse(float _angle) {
        float _x = (SimpleGLRenderer.l.platform.size.x / 2 - (SimpleGLRenderer.l.platform.size.x / 10))
                * (float) Math.cos(Math.toRadians(_angle))
                + SimpleGLRenderer.l.platform.position.x;
        float _y = (SimpleGLRenderer.l.platform.size.y / 2 - (SimpleGLRenderer.l.platform.size.y / 10))
                * (float) Math.sin(Math.toRadians(_angle))
                + SimpleGLRenderer.l.platform.position.y;
        return new Vector(_x, _y);
    }


    public void ProjectileHit(Vector v) {


        this.velocity = v;
        this.position.add(Vector.multiply(this.velocity, 2));

    }



}