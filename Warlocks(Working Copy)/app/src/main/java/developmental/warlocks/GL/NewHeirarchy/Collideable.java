package developmental.warlocks.GL.NewHeirarchy;

import java.util.ArrayList;
import java.util.List;

import Actors.Player;
import Actors.ShadowClone;
import Collision.ColliderBase;
import Game.DamageType;
import Game.Destination;
import HUD.PopupText;
import SpellProjectiles.LightningProjectile;
import Spells.Archetype.ArchetypeManager;
import Spells.Archetype.ArchetypePower;
import Spells.SpellEffect;
import Tools.BoundingCircle;
import Tools.Vector;
import developmental.warlocks.GL.Grid;
import developmental.warlocks.GL.SimpleGLRenderer;

/**
 * Created by Scott on 19/01/14.
 * An object that can impact with/collide with another object.
 */
public abstract class Collideable extends Moveable implements Comparable<Collideable> {
    public ShadowClone shadowClone;
    public boolean shielded = false;
    public boolean juggernaught = false;
    public int jugstacks = 0;
    public float health = 500;
    public float shield = 0;
    public float maxhealth = this.health;
    public float mana = 0;
    public boolean jumping = false;
    public float damageDealtThisRound = 0;
    /**
     * Collision Variables
     */
    public ArchetypePower archetypePower = new ArchetypePower(0, 0, 0, 0, 0, 0, 0);
    public boolean thrusting = false;
    public ArchetypeManager archetypeManager;
    public boolean CanTakeDamage = false;
    public boolean DiesOnImpact = false;
    public boolean KillsOnImpact = true;
    public boolean LinksToThings = false;
    public boolean CanBeLinked = true;
    public Collideable linked = null;
    public boolean CanHealOffOfThis = false;
    public boolean CanBeExploded = false;
    public boolean CanExplodeOtherThings = false;
    public boolean CanBeSwapped = true;
    public boolean CanSwapThings = false;
    public boolean HealsTarget = false;
    public boolean DiesOnImpactWithParent = false;
    public boolean BouncesOnImpact = false;
    public Collideable lastTarget = null;
    public int stacks = 0;
    public boolean InflictsSlow = false;
    public boolean AppliesImpulse = false;
    public boolean AppliesVelocity = false;
    public boolean DealsDamage = true;
    public boolean CanAbsorbThings = false;
    public boolean CanBeAbsorbed = false;
    public boolean IsBoomerang = false;
    public List<SpellEffect> Debuffs = new ArrayList<SpellEffect>();
    public Vector feet;
    public float damagevalue = 0;
    public float healvalue = 0;
    public ObjectType objectObjectType;
    /**
     * How far the object will send another object flying if it impacts
     */
    public double knockback = 5;
    /**
     * A Circle, defined by a radius and a position, that will determine whether or not it is impacting with another Collideable
     */
    public BoundingCircle bounds;
    public int HealthRegenPer150Updates = 5;
    public Vector destination;
    public float pull = 0.2f;
    protected boolean invisible = false;
    protected int framecount = 4;
    protected Destination Marker;
    ColliderBase colliderBase;

    protected Collideable(int _mResourceID, Vector _pos, Vector _size, float _health, float _damage) {
        super(_mResourceID);
        this.position = _pos;
        this.size = _size;
        damagevalue = _damage;
        SetMaxHealth(_health);
        this.feet = new Vector(this.position.x + this.size.x / 2,
                this.position.y - size.y * 7 / 10);
        bounds = new BoundingCircle(feet, _size.x / 2);
        colliderBase = new ColliderBase(this);
    }

    public static Vector GetVel2(Vector from, Vector to, double pull) {
        float distanceX = to.x - from.x;
        float distanceY = to.y - from.y;
        float totalDist = Vector.DistanceBetween(to, from);

        return new Vector((float) pull * (distanceX / totalDist),
                (float) pull * distanceY / totalDist);
    }

    public int compareTo(Collideable o) {
        return (int) (this.bounds.Center.y - o.bounds.Center.y);
    }

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

    //Applies a flat pull to the objects position.
    public Vector DirectionalPull(Vector TargetPosition, double _p) {
        Vector from = TargetPosition.get();
        Vector to = this.position.get();

        float distanceX = to.x - from.x;
        float distanceY = to.y - from.y;
        float totalDist = Math.abs(distanceX) + Math.abs(distanceY);

        return new Vector((float) _p * (distanceX / totalDist), (float) _p
                * distanceY / totalDist);
    }

    protected void setFrames() {
        FramesTail();

    }

    protected void FramesTail() {
        this.mGrid = new ArrayList<Grid>();
        for (int i = 0; i < framecount; i++) {
            Grid backgroundGrid = new Grid(2, 2, false);
            backgroundGrid.set(0, 0, -1.5f * size.x, -size.y / 2, 0.0f, 0.25f * i, 1.0f, null);
            backgroundGrid.set(1, 0, size.x / 2, -size.y / 2, 0.0f, 0.25f * (i + 1), 1.0f, null);
            backgroundGrid.set(0, 1, -1.5f * size.x, size.y / 2, 0.0f, 0.25f * i, 0.0f, null);
            backgroundGrid.set(1, 1, size.x / 2, size.y / 2, 0.0f, 0.25f * (i + 1), 0.0f, null);
            mGrid.add(backgroundGrid);
        }
    }

    protected void FramesNoTail() {
        this.mGrid = new ArrayList<Grid>();
        for (int i = 0; i < framecount; i++) {
            Grid backgroundGrid = new Grid(2, 2, false);
            backgroundGrid.set(0, 0, -size.x / 2, -size.y / 2, 0.0f, 0.25f * i, 1.0f, null);
            backgroundGrid.set(1, 0, size.x / 2, -size.y / 2, 0.0f, 0.25f * (i + 1), 1.0f, null);
            backgroundGrid.set(0, 1, -size.x / 2, size.y / 2, 0.0f, 0.25f * i, 0.0f, null);
            backgroundGrid.set(1, 1, size.x / 2, size.y / 2, 0.0f, 0.25f * (i + 1), 0.0f, null);
            mGrid.add(backgroundGrid);
        }
    }

    public void Absorb(Collideable g) {
        this.stacks += 1;
        this.bounds.Radius += g.bounds.Radius;
        this.size = this.size.add(new Vector(g.bounds.Radius * 2, g.bounds.Radius * 2));
        this.FramesNoTail();
        SimpleGLRenderer.delObject(g.id);
    }

    public void CollisionNew(Collideable obj) {
        colliderBase.CollidesWithLogic(obj);
        obj.colliderBase.CollidesWithLogic(this);
    }

    public void Swap(Collideable obj) {
        Vector l;
        l = obj.position;
        obj.position = this.owner.position;
        this.owner.position = l;
        SimpleGLRenderer.delObject(this.id);
    }

    public void Damage(float dmgDealt, DamageType d) {

    }

    public void Heal(float HealAmount) {
        SimpleGLRenderer.popupTexts.add(new PopupText(PopupText.TextType.Poison, "Heal:" + HealAmount, bounds.Center, 20));
        //     Log.e("FSAFSFSFS","Heal:" + HealAmount);
    }

    @Override
    public void Update() {
        super.Update();
        this.bounds.Center = this.position;

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
            this.Marker = null;

            ArrivedAtDestination();

        }
    }

    protected void ArrivedAtDestination() {
        this.velocity = new Vector(0, 0);
    }

    protected Player FindClosestPlayer(float maxDistance) {
        Player player = null;
        for (Player p : SimpleGLRenderer.players) {
            if (p.id != owner.id) {
                if (!p.dead) {
                    float totalDist = Vector.DistanceBetween(this.bounds.Center, p.bounds.Center);
                    if (totalDist < maxDistance) {
                        maxDistance = totalDist;
                        player = p;

                    }
                }
            }
        }
        return player;
    }

    public Vector getCenter() {
        return this.bounds.Center.get();
    }

    public void SetMaxHealth(float _health)
    {
        health = _health;
        maxhealth = health;
    }

    boolean lightningCollidesWith(Collideable obj1, Collideable obj2) {
        if (obj2.objectObjectType == ObjectType.GravityField)
            return false;
        if (obj2.owner != null)
            if (obj1.id == obj2.owner.id)
                return false;
        if (obj1.owner != null)
            if (obj2.id == obj1.owner.id)
                return false;
        LightningProjectile l = (LightningProjectile) obj2;
        Vector ClosestPoint = obj1.bounds.closestpointonline(l.Dest, l.Start);
        double distance = Math.sqrt((ClosestPoint.x - obj1.bounds.Center.x) * (ClosestPoint.x - obj1.bounds.Center.x) + (ClosestPoint.y - obj1.bounds.Center.y) * (ClosestPoint.y - obj1.bounds.Center.y));
        double distance2 = Math.sqrt((ClosestPoint.x - l.Start.x) * (ClosestPoint.x - l.Start.x) + (ClosestPoint.y - l.Start.y) * (ClosestPoint.y - l.Start.y));
        if (distance < obj1.bounds.Radius && distance2 < l.Range && l.Start.x > ClosestPoint.x == l.Start.x > l.Dest.x && l.Start.y > ClosestPoint.y == l.Start.y > l.Dest.y) {
            l.Dest.x = ClosestPoint.x;
            l.Dest.y = ClosestPoint.y;
            l.mGrid = Grid.LightningLineGrid(Vector.DistanceBetween(l.Start, l.Dest));
            return true;
        }
        return false;
    }

    public boolean CollidesWith(Collideable objj) {
        if ((this.objectObjectType == ObjectType.LineSpell) && (objj.objectObjectType != ObjectType.LineSpell)) {
            return this.lightningCollidesWith(objj, this);
        } else if ((objj.objectObjectType == ObjectType.LineSpell) && (objectObjectType != ObjectType.LineSpell)) {
            return lightningCollidesWith(this, objj);
        } else if (objj.objectObjectType != ObjectType.LineSpell && objectObjectType != ObjectType.LineSpell) {
            return objj.bounds.CollidesWith(this.bounds);

        }
        return false;
    }

    /**
     * the classification of the object. This is used to determine how the two objects involved in the collision will respond to it
     */
    public enum ObjectType {
        GameObject, Player, Enemy, Projectile, Bounce, LineSpell, Boomerang, Drain, HealHoming, Absorb, GravityField, LinkSpell, SwapProjectile, Explosion, Illusion, DrainExplosion, PowerBall, Piercing, Meteor
    }
}
