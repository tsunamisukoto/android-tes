package com.developmental.myapplication.GL.NewHeirachy;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import Game.DamageType;
import Game.Destination;
import Game.ObjectType;
import Game.SpellEffect;
import HUD.PopupText;
import SpellProjectiles.AbsorptionProjectile;
import SpellProjectiles.BounceProjectile;
import SpellProjectiles.ExplosionProjectile;
import SpellProjectiles.HealProjectile;
import SpellProjectiles.IcesplosionProjectile;
import SpellProjectiles.LightningProjectile;
import SpellProjectiles.LinkProjectile;
import SpellProjectiles.MeteorProjectile;
import SpellProjectiles.SwapProjectile;
import Spells.Spell;
import Spells.SpellInfo;
import Tools.BoundingCircle;
import Tools.Vector;
import Tools.iVector;

import com.developmental.myapplication.GL.SimpleGLRenderer;
import com.developmental.myapplication.Global;

import javax.microedition.khronos.opengles.GL10;

public class GameObject extends Collideable implements Comparable<GameObject> {

    public GameObject(int charsheet, SpellInfo[] spellList) {
        this(charsheet);
        this.Spells = new Spell[10];


        for (int x = 0; x < 10; x++) {


            this.Spells[x] = new Spell(this,spellList[x]);
        }
    }
    // If drawing with verts or VBO verts, the grid object defining those verts.

@Override
    public void draw(GL10 gl, float offsetX, float offsetY, boolean b) {
       super.draw(gl,offsetX,offsetY,b);

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
                mGrid=Global.SpritesRightDown;
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
    public Bitmap curr = null;
    public RectF rect;
    public Paint paint, shadowPaint;
    public float damageDealtThisRound = 0;
    public boolean dead = false;
    public List<SpellEffect> Debuffs = new ArrayList<SpellEffect>();
    public float health = 500;
    public int burnCounter = 0;
    public int burnTicker = 0;
    public int burnHit = 0;
    public int HealthRegenPer150Updates = 5;
    public float maxhealth = this.health;
    public float mana = 0;
    public float pull = 0.2f;
    public Vector  destination, feet;
    public Spell[] Spells;
    public GameObject(int resourceId) {
        super(resourceId);
        this.objectObjectType = ObjectType.GameObject;
        this.position = new Vector(0, 0);
        this.size = new Vector(50, 50);
        this.velocity = new Vector(0, 0);
        //this.Spells = new Spell[10];
        this.paint = new Paint();
        this.paint.setColor(Color.RED);
        this.shadowPaint = new Paint();
        this.shadowPaint.setColor(Color.BLACK);
        this.shadowPaint.setMaskFilter(new BlurMaskFilter(30,
                BlurMaskFilter.Blur.INNER));

        this.rect = new RectF(this.position.x, this.position.y, this.position.x
                + this.size.x, this.position.y + this.size.y);
        this.dRect = new RectF(this.position.x, this.position.y, this.position.x
                + this.size.x, this.position.y + this.size.y);

        this.feet = new Vector(this.position.x + this.size.x / 2,
                this.position.y -33);
        bounds = new BoundingCircle(feet, 33);
    }

    public int compareTo(GameObject o) {
        return (int) (this.rect.bottom - o.rect.bottom);
        //	return (int) (this.position.y - o.position.y);
    }

    protected void GetSprites(ArrayList<Bitmap> spriteSheet) {

    }

    public void DrawHitBox(float offsetx, float offsety, Canvas c) {
        Paint s = new Paint();
        s.setColor(Color.GREEN);
        s.setStyle(Paint.Style.STROKE);
//        c.drawRect(dRect,s);
        bounds.Draw(c, offsetx, offsety, s);
    }


    public void DrawHealthBar(Canvas c, float playerx, float playery) {
        Paint s = new Paint();
        s.setColor(Color.BLACK);
        c.drawRect(this.dRect.left - 2 - playerx, this.dRect.top - 2 - playery, this.dRect.right + 2 - playerx,
                this.dRect.top + 10 + 2 - playery, s);
        s.setColor(Color.GRAY);
        c.drawRect(this.dRect.left - playerx, this.dRect.top - playery, this.dRect.right - playerx,
                this.dRect.top + 10 - playery, s);
        if (this.health / this.maxhealth < 0.2)
            s.setColor(Color.RED);
        else if (this.health / this.maxhealth < 0.5)
            s.setColor(Color.YELLOW);
        else
            s.setColor(Color.GREEN);
        c.drawRect(
                this.dRect.left - playerx,
                this.dRect.top - playery,
                this.dRect.right
                        - ((1 - (this.health / this.maxhealth)) * this.dRect
                        .width()) - playerx, this.dRect.top - playery + 10, s);
    }
    protected void DrawManaBar(Canvas c,Vector Pos,iVector dimensions) {
        Paint s1=new Paint();
        Paint s2 = new Paint();
        c.drawRect(Pos.x, Pos.y,Pos.x+dimensions.x, Pos.y+dimensions.y, Global.PaintBlack);

        switch (((int)this.mana/100)%5)
        {
            case 0:
                s1= this.mana/100<4?Global.PaintGray:Global.PaintBlue;
                s2 = Global.PaintYellow;
                break;
            case 1:
                s1 = Global.PaintYellow;
                s2 = Global.PaintOrange;
                break;
            case 2:
                s1 = Global.PaintOrange;
                s2 = Global.PaintRed;
                break;
            case 3:
                s1 = Global.PaintRed;
                s2 = Global.PaintMagenta;
                break;
            case 4:
                s1 = Global.PaintMagenta;
                s2 = Global.PaintBlue;
                break;
        }
        c.drawRect(2+Pos.x, Pos.y+2,Pos.x+dimensions.x-2, Pos.y+dimensions.y-2, s1);
        c.drawRect(2+Pos.x,
                Pos.y+2,Pos.x+
                dimensions.x-2
                        - ((1 - ((float) this.mana%100 / 100)) * size.x), Pos.y+dimensions.y-2, s2);
    }
    public float damagevalue = 0;
    protected void Heal(float HealAmount)
    {
        if (HealAmount+health > this.maxhealth) {

            HealAmount=maxhealth-health;
            this.health = maxhealth;
        } else {
            // SimpleGLRenderer.addParticle(new HealthDisplay(position.get(), velocity.get(), 20, paint, this));
            this.health += HealAmount;
        }
        if(HealAmount>0)
                    SimpleGLRenderer.popupTexts.add(new PopupText(PopupText.TextType.Poison,HealAmount+"",this.bounds.Center.get(),12));



    }
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

    public int displayhealth = 0;

    public boolean Intersect(RectF PassedObj) {

        return RectF.intersects(this.rect, PassedObj);
    }

    protected RectF dRect;

    public void Draw(Canvas canvas, float playerx, float playery) {
        this.dRect = new RectF(rect.left - SimpleGLRenderer.archie.position.x + SimpleGLRenderer.size.x / 2, rect.top - SimpleGLRenderer.archie.position.y + SimpleGLRenderer.size.y / 2, rect.right - SimpleGLRenderer.archie.position.x + SimpleGLRenderer.size.x / 2, rect.bottom - SimpleGLRenderer.archie.position.y + SimpleGLRenderer.size.y / 2);

        canvas.save();
        canvas.translate(this.dRect.left + this.dRect.width() / 2, +this.dRect.top
                - this.dRect.height() / 2);
        canvas.rotate(45);
        if (this.curr != null)
            canvas.drawBitmap(this.curr.extractAlpha(), null, new RectF(
                    this.size.x / 2, 0, this.dRect.width() + this.size.x / 3,
                    this.dRect.height()), this.shadowPaint);
        else
            canvas.drawRect(new RectF(this.size.x / 2, 0, this.dRect.width()
                    + this.size.x / 3, this.dRect.height()), this.shadowPaint);
        canvas.restore();
        if (this.curr == null)
            canvas.drawRect(this.dRect, this.paint);
        if (Global.DEBUG_MODE) {
            if (destination != null)
                canvas.drawLine(this.rect.centerX() - playerx, this.rect.centerY() - playery, destination.x - playerx, destination.y - playery, Global.PaintBlack);
        }

        // super.Draw(canvas,dRect);

    }



    public boolean casting = false, frozen = false, stunned = false;
    @Override
    public void Update() {
        super.Update();
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
            this.Debuffs.add(new SpellEffect(150, SpellEffect.EffectType.Burn,this));
        }
        casting = false;
        frozen = false;
        stunned = false;
        int slowcounter = 0;
        for (int i = 0; i < Debuffs.size(); i++) {

            SpellEffect e = Debuffs.get(i);
            e.Update();
            if (e.Duration > 0) {
                Log.d("INET", e.effectType + " " + e.Duration);

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
                Log.d("INET", "GET RID OF SPELL");
                e.FinalUpdate();
                Debuffs.remove(i);
            }
            Log.d("INET", "Casting");

        }
        if (!casting && !frozen)
            if (this.destination != null)
                GoTo(this.destination,maxVelocity*(float)Math.pow(0.5,slowcounter),acceleration*(float)Math.pow(0.5,slowcounter));

        this.feet = new Vector(this.position.x + this.size.x / 2,
                this.position.y -bounds.Radius );
        bounds.Center = feet;


        CollideMap();

        this.rect = new RectF(this.position.x, this.position.y, this.position.x
                + this.size.x, this.position.y + this.size.y);
        if (Spells != null)
            for (int j = 0; j < Spells.length; j++) {

                Spells[j].Update();
            }
        if(destination!=null)
        Animate(destination);
    }
    boolean lightningCollidesWith(GameObject obj1, GameObject obj2)
    {   if(obj2.objectObjectType == ObjectType.GravityField)
        return false;
        if(obj2.owner!=null)
        if(obj1.id==obj2.owner.id)
            return false;
        if(obj1.owner!=null)
        if(obj2.id==obj1.owner.id)
            return false;
        LightningProjectile l = (LightningProjectile) obj2;
        Vector ClosestPoint = obj1.bounds.closestpointonline(l.Dest, l.Start);
        double distance = Math.sqrt((ClosestPoint.x - obj1.bounds.Center.x) * (ClosestPoint.x - obj1.bounds.Center.x) + (ClosestPoint.y - obj1.bounds.Center.y) * (ClosestPoint.y - obj1.bounds.Center.y));
        double distance2 = Math.sqrt((ClosestPoint.x - l.Start.x) * (ClosestPoint.x - l.Start.x) + (ClosestPoint.y - l.Start.y) * (ClosestPoint.y - l.Start.y));
        if (distance < obj1.bounds.Radius && distance2 < l.Range && l.Start.x > ClosestPoint.x == l.Start.x > l.Dest.x && l.Start.y > ClosestPoint.y == l.Start.y > l.Dest.y) {
            l.Dest.x = ClosestPoint.x;
            l.Dest.y = ClosestPoint.y;
            return true;
        }
        return false;
    }
    public boolean CollidesWith(GameObject objj) {
        if ((this.objectObjectType == ObjectType.LineSpell) && (objj.objectObjectType != ObjectType.LineSpell)) {
            return this.lightningCollidesWith(objj,this);
        } else if ((objj.objectObjectType == ObjectType.LineSpell) && (objectObjectType != ObjectType.LineSpell)) {
          return lightningCollidesWith(this,objj);
        } else if (objj.objectObjectType != ObjectType.LineSpell && objectObjectType != ObjectType.LineSpell) {
            return objj.bounds.CollidesWith(this.bounds);

        }
        return false;
    }

    protected GameObject FindClosestPlayer(float maxDistance) {
        GameObject player = null;
        for (GameObject p : SimpleGLRenderer.players) {
            if (p.id != owner.id) {
                float totalDist = Vector.DistanceBetween(this.bounds.Center, p.bounds.Center);
                if (totalDist < maxDistance) {
                    maxDistance = totalDist;
                    player = p;
                    Log.d("INET", "TARGET SET");
                }
            }
        }
        return player;
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
                Log.e("TEST IF FINGERS ARE WORKING",f[0].x+ " , " + f[0].y);
//                SimpleGLRenderer.addObject(new GameObject(R.drawable.characteridle2));
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

    public float CurrentVelocity(Vector vel) {
        float distanceX = vel.x;
        float distanceY = vel.y;
        return (float) Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));

    }

    //Applies a Vector to the velocity, based on accelleration and max speed, in the direction of the destination
    protected void GoTo(Vector d,float _maxVelocity, float _acceleration) {

        float distanceX = d.x - this.bounds.Center.x;
        float distanceY = d.y - this.bounds.Center.y;

        float totalDist = (float) Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));

        if (totalDist > this.CurrentVelocity(velocity) + _acceleration) {
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
            this.velocity = new Vector(0, 0);
        }
    }

public void Collision2(GameObject obj)
{
    Vector ImpulseYou = new Vector(0,0);
    Vector ImpulseObj = new Vector(0,0);
    float damageYou = 0;
    float damageObj = 0;
    switch (objectObjectType)
    {
        /***********************************************************************************************************************
         * GameObjcets/Players/Enemies
         ***********************************************************************************************************************/
        case GameObject:
        case Player:
        case Enemy:

            switch (obj.objectObjectType) {

                case GameObject:
                case Player:
                case Enemy:
                    ImpulseYou =(obj.GetVel2(obj.bounds.Center, this.bounds.Center, this.knockback));
                    ImpulseObj =  this.GetVel2(bounds.Center, obj.bounds.Center, obj.knockback);


                    break;
                case Boomerang:
                    ImpulseYou =(obj.GetVel2(obj.bounds.Center, this.bounds.Center, obj.knockback));
                    ImpulseObj =  this.GetVel2(bounds.Center, obj.bounds.Center, obj.knockback);
                    damageYou = obj.damagevalue;

                    break;
                case Projectile:
                    case Absorb:
                    if (obj.owner.id != this.id) {
                        ImpulseYou = obj.velocity;
                        SimpleGLRenderer.delObject(obj.id);
                        damageYou = obj.damagevalue;
                    }
                    break;

                case LineSpell:
                    if ((obj.owner != null) && (this.id != obj.owner.id)) {

                        ImpulseYou= this.GetVel2(((LightningProjectile)obj).Start,this.bounds.Center, ((LightningProjectile) obj).knockback);
                        damageYou= obj.damagevalue;
                    }
                    break;

                case Meteor:
                    if (this.owner != null)
                        if (obj.id != this.owner.id)
                            if (obj.health == 10) {
                                ImpulseYou = (this.GetVel2( obj.bounds.Center,bounds.Center, obj.knockback));
                                damageYou=obj.damagevalue;
                            }
                    break;
                case GravityField:
                    if(obj.owner.id!=this.id)
                    {
                    ImpulseYou=obj
                            .DirectionalPull(this.position, obj.pull);
                    damageYou= obj.damagevalue;
                    }
                    break;
                case LinkSpell:
                    ((LinkProjectile) obj).linked = this;
                    obj.paint.setColor(Color.WHITE);
                    break;
                case IceSpell:
                    if (this.id != obj.owner.id) {
                        this.Debuffs.add(new SpellEffect(100, SpellEffect.EffectType.Freeze,  this));
                        SimpleGLRenderer.delObject(obj.id);
                    }
                    break;
                case Explosion:
                    if (this.owner != null)
                        if (obj.id != this.owner.id) {


                            ImpulseYou = (this.GetVel2( obj.bounds.Center,bounds.Center, obj.knockback));
                            damageYou = obj.damagevalue;
                        }
                    break;
                case Bounce:
                    if(obj.owner.id!=this.id)
                    {
                        ((BounceProjectile) obj).findNewTarget();
                        damageYou= obj.damagevalue;

                    }
                    break;
                case SwapProjectile:
                    ((SwapProjectile)obj).Swap(this);
                    break;

                case Drain:
                    this.Debuffs.add(new SpellEffect(500, SpellEffect.EffectType.Slow,this));
                    SimpleGLRenderer.addObject(new HealProjectile(this.position,obj.owner.bounds.Center.get(),obj.owner));
                    SimpleGLRenderer.delObject(obj.id);
                    break;
                case HealHoming:

                    if(this.id==obj.owner.id)
                    {
                        Heal(obj.damagevalue);
                        SimpleGLRenderer.delObject(obj.id);
                    }
                    break;
            }
            break;
        /***********************************************************************************************************************
         * Projectiles
         ***********************************************************************************************************************/
        case Projectile:
            switch (obj.objectObjectType) {
                case GameObject:
                case Player:
                case Enemy:
                    if (owner.id != obj.id) {
                        ImpulseObj = velocity;

                        SimpleGLRenderer.delObject(id);
                        damageObj = this.damagevalue;
                    }
                    break;
                case Projectile:
                case Bounce:
                case IceSpell:
                case Drain:
                case Boomerang:
                    if ((obj.owner.id != this.owner.id)) {
                        SimpleGLRenderer.delObject(obj.id);
                        SimpleGLRenderer.delObject(this.id);


                    }
                    break;
                case Absorb:

                    ((AbsorptionProjectile)obj).Absorb(this);
                    break;
                case LineSpell:
                    SimpleGLRenderer.delObject(this.id);
                    SimpleGLRenderer.addObject(new ExplosionProjectile(this.bounds.Center.get(), new Vector(200, 200), obj.owner));
                    break;

                case Meteor:
                    if (obj.health == ((MeteorProjectile) obj).landing)
                        SimpleGLRenderer.delObject(this.id);

                    break;
                case GravityField:
                    ImpulseYou = obj.DirectionalPull(this.position, obj.pull);
                    break;
                case LinkSpell:

                    ((LinkProjectile) obj).Link(this);
                    break;

                case Explosion:
                    if ((this.owner != null) && (obj.id != this.owner.id))
                        SimpleGLRenderer.delObject(this.id);
                    break;

                case SwapProjectile:

                    ((SwapProjectile) obj).Swap(this);
                    break;
            }
            break;
        /***********************************************************************************************************************
         * LineSpells
         ***********************************************************************************************************************/
        case LineSpell:
            switch (obj.objectObjectType) {
                case GameObject:
                case Player:
                case Enemy:
                    if ((this.owner != null) && (obj.id != this.owner.id)) {
                        // obj.ProjectileHit(this.velocity);
                       ImpulseObj= (obj.GetVel2( ((LightningProjectile) this).Start,obj.bounds.Center, 30));
                        damageObj= this.damagevalue;
                    }
                    break;
                case Absorb:

                    ((AbsorptionProjectile)obj).Absorb(this);
                    break;
                case IceSpell:
                    SimpleGLRenderer.delObject(obj.id);
                    SimpleGLRenderer.addObject(new IcesplosionProjectile(obj.bounds.Center.get(), new Vector(500, 500), this.owner));
                    break;
                case Projectile:
                case Bounce:
                case Boomerang:
                case Drain:
                    SimpleGLRenderer.delObject(obj.id);
                    SimpleGLRenderer.addObject(new ExplosionProjectile(obj.bounds.Center.get(), new Vector(200, 200), this.owner));
                    break;
                case LineSpell:
                case GravityField:
                case LinkSpell:
                case SwapProjectile:
                case Explosion:
                case Meteor:
                    break;
            }
            break;
        /***********************************************************************************************************************
         *Meteors
         ***********************************************************************************************************************/
        case Meteor:
            switch (obj.objectObjectType) {
                case GameObject:
                case Player:
                case Enemy:
                    if (this.health ==((MeteorProjectile)this).landing)
                        if (obj.id != this.owner.id) {
                            ImpulseObj = (obj.GetVel2( bounds.Center,obj.bounds.Center,this.knockback));
                            damageObj = this.damagevalue;
                        }
                    break;
                case Projectile:
                case Bounce:
                case IceSpell:
                case Boomerang:
                case Drain:
                    if (this.health ==((MeteorProjectile)this).landing)
                        SimpleGLRenderer.delObject(obj.id);
                    break;
                case LineSpell:
                case LinkSpell:
                case SwapProjectile:
                case Explosion:
                case Meteor:
                    break;
                case GravityField:
                    ImpulseYou = obj.DirectionalPull(this.position, obj.pull);
                    break;
            }
            break;
        /***********************************************************************************************************************
         * Boomerang
         ***********************************************************************************************************************/
        case Boomerang:
            switch (obj.objectObjectType) {
                case GameObject:
                case Player:
                case Enemy:
                    ImpulseYou = (this.GetVel2( obj.bounds.Center,bounds.Center, this.knockback));
                    ImpulseObj = (obj.GetVel2( this.bounds.Center,obj.bounds.Center, this.knockback));
                    damageObj = this.damagevalue;
                    break;
                case Projectile:
                case Bounce:
                case IceSpell:
                case Boomerang:
                case Drain:
                    if ((obj.owner.id != this.owner.id)) {
                        SimpleGLRenderer.delObject(obj.id);
                        SimpleGLRenderer.delObject(this.id);


                    }
                    break;
                case Absorb:
                    ((AbsorptionProjectile)obj).Absorb(this);
                    break;
                case LineSpell:
                    SimpleGLRenderer.delObject(this.id);
                    SimpleGLRenderer.addObject(new ExplosionProjectile(this.bounds.Center.get(), new Vector(200, 200), obj.owner));
                    break;

                case Meteor:
                    if (obj.health == ((MeteorProjectile) obj).landing)
                        SimpleGLRenderer.delObject(this.id);

                    break;
                case GravityField:
                    ImpulseYou = obj.DirectionalPull(this.position, obj.pull);
                    break;
                case LinkSpell:

                    ((LinkProjectile) obj).Link(this);
                    break;

                case Explosion:
                    if ((this.owner != null) && (obj.id != this.owner.id))
                        SimpleGLRenderer.delObject(this.id);
                    break;

                case SwapProjectile:

                    ((SwapProjectile) obj).Swap(this);
                    break;
            }
            break;
        /***********************************************************************************************************************
         * ABSORB
         ***********************************************************************************************************************/

        case Absorb:
            switch (obj.objectObjectType) {

                case GameObject:
                case Player:
                case Enemy:
                    if (owner.id != obj.id) {
                    ImpulseObj = velocity;

                    SimpleGLRenderer.delObject(id);
                    damageObj = this.damagevalue;
                }
                    break;
                case Projectile:
                case Bounce:
                case IceSpell:
                case Boomerang:
                case Drain:
                    ((AbsorptionProjectile)this).Absorb(obj);
                    break;
                case LineSpell:
                case HealHoming:
                case Absorb:
                    break;
                case GravityField:
                    break;
                case LinkSpell:
                    break;
                case SwapProjectile:
                    ((SwapProjectile)obj).Swap(this);
                    break;
                case Explosion:
                    break;
                case Meteor:
                    break;
            }
            break;
        /***********************************************************************************************************************
         * Gravity
         ***********************************************************************************************************************/

        case GravityField:
            switch (obj.objectObjectType) {
                case GameObject:
                case Player:
                case Enemy:
                    if(obj.id!=this.owner.id)
                    {
                    ImpulseObj=   this.DirectionalPull(obj.position, pull);
                    damageObj  =this.damagevalue;
                    }
                    break;
                case Meteor:
                case GravityField:
                case LinkSpell:
                case IceSpell:
                case Bounce:
                case SwapProjectile:
                case Boomerang:
                case Projectile:
                case Drain:
                    ImpulseObj=   this.DirectionalPull(obj.position, pull);

                    break;
                case LineSpell:
                    break;

                case Explosion:
                    break;

            }
            break;
        /***********************************************************************************************************************
         * Link
         ***********************************************************************************************************************/
        case LinkSpell:
            switch (obj.objectObjectType) {
                case Player:
                case Enemy:
                case GameObject:
                case Projectile:
                case Bounce:
                case Boomerang:
                case Drain:
                case IceSpell:
                    if(obj.id!=owner.id)
                        ((LinkProjectile)this).Link(obj);
                    break;
                case GravityField:
                    ImpulseYou= velocity.add(obj
                            .DirectionalPull(this.position, obj.pull));
                    break;
                case Meteor:
                case LinkSpell:
                case Explosion:
                case SwapProjectile:
                case LineSpell:
                    break;
            }
            break;
        /***********************************************************************************************************************
         * Ice
         ***********************************************************************************************************************/
        case IceSpell:
            switch (obj.objectObjectType) {
                case IceSpell:
                case Projectile:
                case Bounce:
                 case Boomerang:
                     case Drain:
                    if (obj.owner.id != this.owner.id) {
                        SimpleGLRenderer.delObject(obj.id);
                        SimpleGLRenderer.delObject(this.id);
                    }
                    break;
                case GravityField:
                   ImpulseYou=obj.DirectionalPull(this.position, obj.pull);
                    break;
                case Absorb:
                    ((AbsorptionProjectile)obj).Absorb(this);
                    break;
                case GameObject:
                case Enemy:
                case Player:
                    if(obj.id!=owner.id)
                    {
                        obj.Debuffs.add(new SpellEffect(100, SpellEffect.EffectType.Freeze,  obj));
                        SimpleGLRenderer.delObject(this.id);

                        damageObj = this.damagevalue;
                    }
                    break;


                case LineSpell:
                    SimpleGLRenderer.delObject(this.id);
                    SimpleGLRenderer.addObject(new IcesplosionProjectile(this.bounds.Center, new Vector(500, 500), obj.owner));
                    break;
                case Meteor:
                    if (obj.health == ((MeteorProjectile) obj).landing)
                        SimpleGLRenderer.delObject(this.id);
                    break;
                case Explosion:
                    if ((this.owner != null) && (obj.id != this.owner.id))
                        SimpleGLRenderer.delObject(this.id);
                    break;
                case LinkSpell:
                    ((LinkProjectile) obj).Link(this);
                    break;


                case SwapProjectile:
                    ((SwapProjectile) obj).Swap(this);
                    break;
            }
            break;
        /***********************************************************************************************************************
         * Explosion
         ***********************************************************************************************************************/
        case Explosion:
            switch (obj.objectObjectType) {
                case GameObject:
               case Player:
               case Enemy:
                    if (this.owner != null)
                        if (obj.id != this.owner.id) {

                            ImpulseObj = (obj.GetVel2( bounds.Center,obj.bounds.Center, this.knockback));
                           damageObj = this.damagevalue;
                        }
                    break;
                case Projectile:
                case Bounce:
                case IceSpell:
                case Boomerang:
                    case Drain:
                    if (obj.owner.id != this.id) {
                        SimpleGLRenderer.delObject(obj.id);

                    }
                    break;
                case LineSpell:
                case GravityField:
                case LinkSpell:
                case SwapProjectile:
                case Explosion:
                case Meteor:
                    break;
            }
            break;
        /***********************************************************************************************************************
         * Bounce
         ***********************************************************************************************************************/
        case Bounce:
            switch (obj.objectObjectType) {
                case GameObject:
                case Player:
                case Enemy:
                    BounceProjectile b=(BounceProjectile)this;
                    if ((this.owner != null) && (obj.id != this.owner.id))
                        if (b.lastTarget == null || obj.id != b.lastTarget.id) {
                            ImpulseObj = this.velocity;
                           damageObj = b.damagevalue;
                            if (b.bounces > 0) {
                                b.lastTarget = obj;
                                b.findNewTarget();
                                b.bounces -= 1;
                            } else {
                                SimpleGLRenderer.delObject(this.id);
                            }
                        }
                    break;
                case Absorb:
                    ((AbsorptionProjectile)obj).Absorb(this);
                    break;
                case SwapProjectile:
                ((SwapProjectile) obj).Swap(this);
                    break;
                case Projectile:
                case Bounce:
                case IceSpell:
                    case Drain:
                case Boomerang:

                    if ((obj.owner.id != this.owner.id)) {
                        SimpleGLRenderer.delObject(obj.id);
                        SimpleGLRenderer.delObject(this.id);
                    }
                    break;
                case LineSpell:
                    SimpleGLRenderer.delObject(this.id);
                    SimpleGLRenderer.addObject(new ExplosionProjectile(this.bounds.Center, new Vector(200, 200), obj.owner));
                    break;
                case GravityField:
                    ImpulseYou = obj
                            .DirectionalPull(this.position, obj.pull);
                    break;
                case LinkSpell:

                    ((LinkProjectile) obj).Link(this);
                    break;

                case Explosion:
                    if ((this.owner != null) && (obj.id != this.owner.id))
                        SimpleGLRenderer.delObject(this.id);
                    break;
                case Meteor:
                    if (obj.health == ((MeteorProjectile) obj).landing)
                        SimpleGLRenderer.delObject(this.id);
                    break;
            }
            break;
        /***********************************************************************************************************************
         * Swap
         ***********************************************************************************************************************/
        case SwapProjectile:
            switch (obj.objectObjectType) {
                case GameObject:
                case Projectile:
                case Player:
                case Enemy:
                case IceSpell:
                case Bounce:
                    case Drain:
                    case Boomerang:
                        case Absorb:
                    ((SwapProjectile)this).Swap(obj);
                    break;

                case LineSpell:
                case Meteor:
                case GravityField:
                case LinkSpell:
                case Explosion:
                case SwapProjectile:
                    break;
            }
            break;
        case Drain:
            switch (obj.objectObjectType) {
                case GameObject:
                case Player:
                case Enemy:
                    obj.Debuffs.add(new SpellEffect(500, SpellEffect.EffectType.Slow, obj));
                    SimpleGLRenderer.addObject(new HealProjectile(obj.position, owner.bounds.Center.get(), owner));

                    SimpleGLRenderer.delObject(this.id);
                    break;
                case Absorb:
                    ((AbsorptionProjectile)obj).Absorb(this);
                    break;
                case Projectile:
                case Bounce:
                case IceSpell:
                case Drain:
                case Boomerang:
                   if ((obj.owner.id != this.owner.id)) {
                        SimpleGLRenderer.delObject(obj.id);
                        SimpleGLRenderer.delObject(this.id);
                   }
                    break;
                case LineSpell:

                    SimpleGLRenderer.delObject(this.id);
                    SimpleGLRenderer.addObject(new ExplosionProjectile(this.bounds.Center.get(), new Vector(200, 200), obj.owner));
                    break;
                case GravityField:

                    ImpulseYou=   obj.DirectionalPull(this.position, obj.pull);

                    break;
                case LinkSpell:
                    if(obj.id!=owner.id)
                        ((LinkProjectile)obj).Link(this);
                    break;
                case SwapProjectile:

                    ((SwapProjectile)obj).Swap(this);
                    break;
                case Explosion:
                    if (obj.owner.id != this.id) {
                        SimpleGLRenderer.delObject(this.id);

                    }
                    break;
                case Meteor:
                    if (obj.health ==((MeteorProjectile)obj).landing)
                        SimpleGLRenderer.delObject(this.id);
                    break;
            }
            break;
        case HealHoming:
            if(this.owner.id==obj.id)
            {
                obj.Heal(this.damagevalue);
                SimpleGLRenderer.delObject(this.id);
            }
            break;

    }

   if(damageYou>0)
   {
        this.Damage(damageYou,DamageType.Spell);
       obj.owner.damageDealtThisRound+=damageYou;
   }
   if(damageObj>0)
   {
       obj.Damage(damageObj,DamageType.Spell);
       this.owner.damageDealtThisRound+=damageObj;
   }
      if(this.CurrentVelocity(ImpulseYou)>0)
    {
        int counter = 0;
        for(SpellEffect s : Debuffs)
        {
            if(s.effectType== SpellEffect.EffectType.Burn)
                counter++;
        }
        float Multiplier = (this.mana+400)/400*(float)Math.pow(1.2,counter);
        ImpulseYou = Vector.multiply(ImpulseYou,Multiplier);
        if(Global.DEBUG_MODE)
        SimpleGLRenderer.popupTexts.add(new PopupText(PopupText.TextType.Poison, "" + (this.mana+400)/400, this.position.get(), 100));
        this.velocity= this.velocity.add(ImpulseYou);

    }

    if(this.CurrentVelocity(ImpulseObj)>0)
    {
        int counter = 0;
        for(SpellEffect s : obj.Debuffs)
        {
            if(s.effectType== SpellEffect.EffectType.Burn)
                counter++;

        }
        float Multiplier = (obj.mana+400)/400*(float)Math.pow(1.2,counter);
        ImpulseObj = Vector.multiply(ImpulseObj, Multiplier);
        if(Global.DEBUG_MODE)
        SimpleGLRenderer.popupTexts.add(new PopupText(PopupText.TextType.Poison,""+(obj.mana + 400) / 400,obj.position.get(),100));
        obj.velocity = obj.velocity.add(ImpulseObj);


    }

}
    public Vector GetVel2(Vector from, Vector to,int pull) {
      //  this.position = from;
        float distanceX = to.x - from.x;
        float distanceY = to.y - from.y;
        float totalDist = Vector.DistanceBetween(to, from);

        return new Vector(pull * (distanceX / totalDist),
                pull * distanceY / totalDist);
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

    //Applies a flat pull to the objects position.
    public Vector DirectionalPull(Vector TargetPosition, float _p) {
        Vector from = TargetPosition.get();
        Vector to = this.position.get();

        float distanceX = to.x - from.x;
        float distanceY = to.y - from.y;
        float totalDist = Math.abs(distanceX) + Math.abs(distanceY);

        return new Vector(_p * (distanceX / totalDist), _p
                * distanceY / totalDist);
    }

    public void ProjectileHit(Vector v) {

        this.paint.setColor(Color.RED);
        this.velocity = v;
        this.position.add(Vector.multiply(this.velocity, 2));

    }

    public Vector getCenter() {
        return new Vector(this.rect.left + this.rect.width() / 2, this.rect.top
                + this.rect.height() / 2);
    }

}
