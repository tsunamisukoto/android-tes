package Spells.SpellSlots.Slot6;

import Actors.Player;
import SpellProjectiles.DrainExplosionProjectile;
import Spells.LoadOutInfo;
import Spells.Spell;
import Tools.Vector;
import Tools.iVector;
import developmental.warlocks.GL.SimpleGLRenderer;

/**
 * Created by Scott on 21/10/2014.
 */
public class DrainExplodeSpell extends Spell {
    public DrainExplodeSpell(Player _parent, LoadOutInfo s) {
        super(_parent, s);
    }

    @Override
    protected void Shoot(iVector Dest, Vector Origin) {
        SimpleGLRenderer.addObject(new DrainExplosionProjectile(Origin, new Vector(1000, 1000), parent,this.Rank));
    }
}
