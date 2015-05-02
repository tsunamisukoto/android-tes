package SpellProjectiles;


import com.developmental.warlocks.R;

import Tools.Vector;
import developmental.warlocks.GL.NewHeirarchy.GameObject;

public class GravityProjectile extends Projectile {

    public GravityProjectile(Vector _from, Vector _to, GameObject _shooter,int Rank) {
        super(R.drawable.spell_gravity,_from, _to, _shooter,Rank);


        this.objectObjectType = ObjectType.GravityField;

        this.velocity = GetVel(_from.get(), _to.get());
        SetVelocity(this.maxVelocity);
        this.CollideDiesOnImpact = false;
        this.CollideAppliesImpulse = true;
//        this.damagevalue=1;
        this.CollideAppliesVelocity = false;
        this.CollideImpactsWithLightning = false;
    }


    @Override
    protected void Stats(int rank)
    {
        this.maxVelocity = 5;

        switch (rank)
        {
            case 1:
                this.health = 500;
                this.knockback =0.5;
                this.size = new Vector(350,350);
                this.damagevalue = 0.3f;

                break;
            case 2:
                this.health = 500;
                this.knockback =0.8;
                this.size = new Vector(350,350);
                this.damagevalue = 0.5f;
                break;
            case 3:
                this.health = 500;
                this.knockback =1.1;
                this.size = new Vector(350,350);
                this.damagevalue = 0.7f;
                break;
            case 4:
                this.health = 500;
                this.knockback =1.4;
                this.size = new Vector(350,350);
                this.damagevalue = 0.9f;
                break;
            case 5:
                this.health = 500;
                this.knockback =1.7;
                this.size = new Vector(350,350);
                this.damagevalue = 1.1f;
                break;
            case 6:
                this.health = 500;
                this.knockback =2.0;
                this.size = new Vector(350,350);
                this.damagevalue = 1.3f;
                break;
            case 7:
                this.health = 500;
                this.knockback =2.3;
                this.size = new Vector(350,350);
                this.damagevalue = 1.5f;
                break;
        }


    }

    @Override
    public void Update() {
        super.Update();
        this.CollideDealsDamage = lifePhase % 5 == 2;
        //Animate();
        this.bounds.Center = position;

    }

    @Override
    protected void setFrames() {
        FramesNoTail();
    }



}