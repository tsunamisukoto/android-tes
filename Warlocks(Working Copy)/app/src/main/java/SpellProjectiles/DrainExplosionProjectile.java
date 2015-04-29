package SpellProjectiles;

import com.developmental.warlocks.R;

import Tools.Vector;
import developmental.warlocks.GL.NewHeirarchy.Collideable;

/**
 * Created by Scott on 13/11/2014.
 */
public class DrainExplosionProjectile extends ExplosionProjectile {


        public DrainExplosionProjectile(Vector _to, Vector _s, Collideable shooter,int Rank) {

            super(R.drawable.spell_boundsircle, _to, shooter, _s, 11,Rank);


            this.objectObjectType = ObjectType.DrainExplosion;
            this.CollideDealsDamage = true;
            this.damagevalue = 7;
            this.healvalue = 5;
            this.CollideAppliesVelocity = false;
            this.CollideAppliesImpulse = false;
        }



}
