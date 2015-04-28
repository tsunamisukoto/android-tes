package SpellProjectiles;


import com.developmental.warlocks.R;

import Tools.Vector;
import developmental.warlocks.GL.NewHeirarchy.GameObject;

/**
 * Created by Scott on 28/08/13.
 */
public class DrainProjectile extends Projectile {

    public DrainProjectile(Vector _from, Vector _to, GameObject shooter,int Rank) {
        super(R.drawable.spell_drain,_from, _to, shooter,Rank);
        this.objectObjectType = ObjectType.Drain;
        this.AppliesVelocity = false;
        this.frameRate = 10;
    }
    @Override
    protected void Stats(int rank)
    {
        this.maxVelocity = 5;

        switch (rank)
        {
            case 1:
                this.health = 100;
                this.knockback =7;
                this.size = new Vector(50,50);
                this.damagevalue = 6;
                this.healvalue = 3;
                break;
            case 2:
                this.health = 110;
                this.knockback =8.5;
                this.size = new Vector(50,50);
                this.damagevalue = 7;
                this.healvalue = 4;
                break;
            case 3:
                this.health = 120;
                this.knockback =10;
                this.size = new Vector(50,50);
                this.damagevalue = 8;
                this.healvalue = 5;
                break;
            case 4:
                this.health = 130;
                this.knockback =11.5;
                this.size = new Vector(50,50);
                this.damagevalue = 9;
                this.healvalue = 6;
                break;
            case 5:
                this.health = 140;
                this.knockback =13;
                this.size = new Vector(60,60);
                this.damagevalue = 10;
                this.healvalue = 7;
                break;
            case 6:
                this.health = 150;
                this.knockback =14.5;
                this.size = new Vector(60,60);
                this.damagevalue = 11;
                this.healvalue = 9;
                break;
            case 7:
                this.health = 160;
                this.knockback =16;
                this.size = new Vector(60,60);
                this.damagevalue = 12;
                this.healvalue = 11;
                break;
        }


    }


    @Override
    public void Update() {
        super.Update();
        if (lifePhase == 50) {
            health = 50;
            float td = 10000;
            GameObject target = this.FindClosestPlayer(td);
            this.frameRate = 3;
            this.maxVelocity *= 2;
                if (target != null)
                    this.velocity = GetVel(this.position, target.position);

        }
    }
}
