package SpellProjectiles;


import com.developmental.warlocks.R;

import Tools.Vector;
import developmental.warlocks.GL.NewHeirarchy.GameObject;
import developmental.warlocks.GL.SimpleGLRenderer;

/**
 * Created by Scott on 28/08/13.
 */
public class SplitterProjectile extends Projectile {

    int Rank = 1;
    int i = 0;
    int p = 0;

    public SplitterProjectile(Vector _from, Vector _to, GameObject shooter, int Rank) {
        super(R.drawable.spell_splitter2, _from, _to, shooter, Rank);
        this.Rank = Rank;
        this.health = 200;
        i = (int) (Math.random() * 360f);
        SetVelocity(maxVelocity);
    }

    @Override
    protected void Rotate() {
        super.Rotate();
        rotation = lifePhase * 3;
    }

    @Override
    protected void setFrames() {
        FramesNoTail();
    }

    @Override
    protected void Stats(int rank) {
        this.maxVelocity = 6f;

        switch (rank) {
            case 1:
                this.health = 100;
                this.knockback = 7;
                this.size = new Vector(120, 120);
                this.damagevalue = 6;

                break;
            case 2:
                this.health = 110;
                this.knockback = 8.5;
                this.size = new Vector(120, 120);
                this.damagevalue = 7;
                break;
            case 3:
                this.health = 120;
                this.knockback = 10;
                this.size = new Vector(120, 120);
                this.damagevalue = 8;
                break;
            case 4:
                this.health = 130;
                this.knockback = 11.5;
                this.size = new Vector(120, 120);
                this.damagevalue = 9;
                break;
            case 5:
                this.health = 140;
                this.knockback = 13;
                this.size = new Vector(120, 120);
                this.damagevalue = 10;
                break;
            case 6:
                this.health = 150;
                this.knockback = 14.5;
                this.size = new Vector(120, 120);
                this.damagevalue = 11;
                break;
            case 7:
                this.health = 160;
                this.knockback = 16;
                this.size = new Vector(120, 120);
                this.damagevalue = 12;
                break;
        }

    }

    @Override
    public void Update() {
        super.Update();
        if (i++ % 4 == 3) {

            double degrees1 = i * 2 + (120 * p++);


            float w = Vector.DistanceBetween(this.bounds.Center, this.velocity);
            Vector Dest1 = new Vector((float) (w * Math.cos(degrees1) + this.bounds.Center.x), (float) (w * Math.sin(degrees1) + this.bounds.Center.y));

            SimpleGLRenderer.addObject(new SplitterChildrenProjectile(this.bounds.Center, Dest1, this.owner, this.Rank));

        }
    }
}
