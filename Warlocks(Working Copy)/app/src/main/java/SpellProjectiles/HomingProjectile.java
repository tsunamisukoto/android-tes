package SpellProjectiles;

import com.developmental.warlocks.R;

import Tools.Vector;
import developmental.warlocks.GL.NewHeirarchy.GameObject;

/**
 * Created by Scott on 27/07/13.
 */
public class HomingProjectile extends Projectile {
    GameObject target;
    int i = 0;
    public HomingProjectile(Vector _from, Vector _to, GameObject shooter,int Rank) {
        super(R.drawable.spell_homing,_from, _to, shooter,Rank);

        this.acceleration = 0.1f;


        this.damagevalue = 7;
    }

    @Override
    protected void Stats(int rank)
    {
        this.maxVelocity = 5.5f;

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

    @Override
    public void Update() {
        super.Update();

            if (this.destination != null)
                MoveTowards(this.destination, maxVelocity , acceleration );


        if (i++ % 15 == 0) {
            float td = 10000f;

            GameObject p = this.FindClosestPlayer(td);
            if(p !=null)
            {
                target=p;
            this.destination = target.feet;
            }
        }
       // SimpleGLRenderer.addParticle(new FireParticle(this.bounds.Center, Vector.multiply(this.velocity, 0.5f), 10, R.drawable.spell_homing));

    }
}
