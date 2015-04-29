package SpellProjectiles;

import android.util.Log;

import com.developmental.warlocks.R;

import Tools.Vector;
import developmental.warlocks.GL.NewHeirarchy.Collideable;

/**
 * Created by Scott on 1/01/14.
 */
public class HealProjectile extends Projectile {
    public HealProjectile(Vector _from, Vector _to, Collideable _shooter,int Rank) {

        super(R.drawable.spell_heal,_from,_to,_shooter, Rank);
        this.destination= owner.bounds.Center.get();
        this.objectObjectType = ObjectType.HealHoming;
        this.maxVelocity=50;
        this.acceleration =4;
        knockback = 0;
        CollideAppliesImpulse = false;
        CollideAppliesVelocity = false;
        CollideDealsDamage = false;
        CollideHealsTarget = true;
        CollideDiesOnImpactWithParent = true;
        this.CollideDiesOnImpact = false;
        Log.e("DEBUG!", "CREATED HealPrjectile");

    }

    @Override
    protected void Stats(int rank) {
        this.size = new Vector(30, 30);
        this.maxVelocity = 50;
        this.acceleration = 4;

        this.damagevalue = 5;
    }

    @Override
    public void Update() {
        super.Update();
        this.destination = owner.bounds.Center.get();
        if (this.destination != null)
            MoveTowards(this.destination, maxVelocity, acceleration);
    }
}
