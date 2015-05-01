package SpellProjectiles;

import com.developmental.warlocks.R;

import Tools.Vector;
import developmental.warlocks.GL.NewHeirarchy.Collideable;
import developmental.warlocks.GL.NewHeirarchy.GameObject;

/**
 * Created by Scott on 13/11/2014.
 */
public class TrapMineProjectile extends Projectile {
    public TrapMineProjectile(Vector _from, Vector _to, Collideable _shooter, int Rank) {
        super(R.drawable.spell_trapmines, _to, _to, _shooter, Rank);
        this.velocity = Vector.Zero();

    }

    @Override
    protected void setFrames() {
        FramesNoTail();
    }

    @Override
    protected void Stats(int rank)
    {
        this.maxVelocity = 0;

        switch (rank)
        {
            case 1:
                this.health = 100;
                this.knockback =7;
                this.size = new Vector(150,150);
                this.damagevalue = 6;

                break;
            case 2:
                this.health = 110;
                this.knockback =8.5;
                this.size = new Vector(150,150);
                this.damagevalue = 7;
                break;
            case 3:
                this.health = 120;
                this.knockback =10;
                this.size = new Vector(150,150);
                this.damagevalue = 8;
                break;
            case 4:
                this.health = 130;
                this.knockback =11.5;
                this.size = new Vector(150,150);
                this.damagevalue = 9;
                break;
            case 5:
                this.health = 140;
                this.knockback =13;
                this.size = new Vector(160,160);
                this.damagevalue = 10;
                break;
            case 6:
                this.health = 150;
                this.knockback =14.5;
                this.size = new Vector(160,160);
                this.damagevalue = 11;
                break;
            case 7:
                this.health = 160;
                this.knockback =16;
                this.size = new Vector(160,160);
                this.damagevalue = 12;
                break;
        }


    }

}
