package SpellProjectiles;

import com.developmental.myapplication.GL.NewHeirachy.GameObject;
import Tools.Vector;

/**
 * Created by Scott on 28/08/13.
 */
public class SplitterChildrenProjectile extends FireballProjectile {
    public SplitterChildrenProjectile(Vector _from, Vector _to, GameObject shooter) {
        super(_from, _to, shooter);
        this.maxVelocity = 15;
        this.health = 20;
        SetVelocity(maxVelocity);
        this.size = new Vector(15, 15);
    }
}
