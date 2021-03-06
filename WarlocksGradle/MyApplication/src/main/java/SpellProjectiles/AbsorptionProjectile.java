package SpellProjectiles;

import android.graphics.Canvas;

import com.developmental.myapplication.GL.SimpleGLRenderer;

import com.developmental.myapplication.GL.NewHeirachy.GameObject;
import Game.ObjectType;
import Tools.Vector;

/**
 * Created by Scott on 2/01/14.
 */
public class AbsorptionProjectile extends GravityProjectile {
    private int projectiles = 0;
    public AbsorptionProjectile(Vector _from, Vector _to, GameObject _shooter) {
        super(_from, _to, _shooter);
        this.bounds.Radius=20;
        this.objectObjectType= ObjectType.Absorb;
    }
    public void Absorb(GameObject g)
    {
        projectiles+=1;
        this.bounds.Radius+=g.bounds.Radius;
        SimpleGLRenderer.delObject(g.id);
    }

    @Override
    public void Draw(Canvas c, float playerx, float playery) {
        c.drawCircle(position.x - playerx, position.y - playery, bounds.Radius, paint);
        c.drawCircle(position.x - playerx, position.y - playery, bounds.Radius-lifePhase%bounds.Radius, paint);

        c.drawCircle(position.x - playerx, position.y - playery, lifePhase%bounds.Radius, paint);
    }
}
