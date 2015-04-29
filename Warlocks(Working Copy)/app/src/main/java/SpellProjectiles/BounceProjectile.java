package SpellProjectiles;

import android.util.Log;

import com.developmental.warlocks.R;

import Tools.Vector;
import developmental.warlocks.GL.NewHeirarchy.Collideable;
import developmental.warlocks.GL.NewHeirarchy.GameObject;
import developmental.warlocks.GL.SimpleGLRenderer;

/**
 * Created by Scott on 28/08/13.
 */
public class BounceProjectile extends Projectile {
    public Collideable lastTarget = null;
    public BounceProjectile(Vector _from, Vector _to, GameObject shooter,int Rank) {
        super(R.drawable.spell_boomerang,_from, _to, shooter, Rank);

        SetVelocity(maxVelocity);
        lastTarget = owner;
        objectObjectType = ObjectType.Bounce;
        CollideBouncesOnImpact = true;
        CollideDiesOnImpact = false;
        CollideAppliesImpulse = false;
        CollideAppliesVelocity = false;
        stacks = 25;
    }

    public static void findNewTarget(Collideable me) {
        Collideable CurrentTarget = null;
        float minD = 10000;

        for (GameObject p : SimpleGLRenderer.players) {
            if (p.id != me.owner.id) {
                if ((me.lastTarget == null) || (me.lastTarget.id != p.id)) {
                    float totalDist = Vector.DistanceBetween(me.bounds.Center, p.bounds.Center);
                    if (totalDist < minD) {
                        minD = totalDist;
                        CurrentTarget = p;
                        Log.d("INET", "TARGET SET TO" + CurrentTarget.id);
                    }
                }
            }
        }


        if (CurrentTarget != null) {

            me.velocity = me.GetVel(me.position, CurrentTarget.position);
            me.SetVelocity(me.maxVelocity);
            Log.d("INET", "TARGET SET TO" + CurrentTarget.feet.x + "," + CurrentTarget.feet.y);
        }
    }

    @Override
    protected void Stats(int rank)
    {
        this.maxVelocity = 9f;

        switch (rank)
        {
            case 1:
                this.health = 300;
                this.knockback =7;
                this.size = new Vector(30,30);
                this.damagevalue = 6;

                break;
            case 2:
                this.health = 300;
                this.knockback =8.5;
                this.size = new Vector(30,30);
                this.damagevalue = 7;
                break;
            case 3:
                this.health = 300;
                this.knockback =10;
                this.size = new Vector(30,30);
                this.damagevalue = 8;
                break;
            case 4:
                this.health = 300;
                this.knockback =11.5;
                this.size = new Vector(30,30);
                this.damagevalue = 9;
                break;
            case 5:
                this.health = 300;
                this.knockback =13;
                this.size = new Vector(30,30);
                this.damagevalue = 10;
                break;
            case 6:
                this.health = 300;
                this.knockback =14.5;
                this.size = new Vector(30,30);
                this.damagevalue = 11;
                break;
            case 7:
                this.health = 300;
                this.knockback =16;
                this.size = new Vector(30,30);
                this.damagevalue = 12;
                break;
        }


    }

}
