package SpellProjectiles;

import android.graphics.Canvas;
import android.graphics.Color;

import com.example.warlockgame.RenderThread;

import Game.GameObject;
import Game.ObjectType;
import Tools.Vector;

/**
 * Created by Scott on 7/29/13.
 */
public class SwapProjectile extends Projectile{
    public SwapProjectile(Vector _from, Vector _to, GameObject shooter) {
        super(_from, _to, shooter);
        this.paint.setColor(Color.GREEN);
        this.objectObjectType = ObjectType.SwapProjectile;
    }
    @Override
    public void Collision(GameObject obj) {
Vector l;
        l = obj.position;
        obj.position=this.owner.position;
        this.owner.position=l;
        RenderThread.delObject(this.id);

    }
    @Override
    public void Draw(Canvas c,float playerx,float playery) {

        c.drawCircle(this.rect.centerX()-playerx, this.rect.centerY()-playery, this.size.x / 2,
                this.paint);

    }

}
