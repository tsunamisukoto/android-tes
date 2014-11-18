package Spells.SpellSlots.Slot5;

import Actors.Player;
import SpellProjectiles.SwapProjectile;
import Spells.Spell;
import Spells.SpellInfo;
import Tools.Vector;
import Tools.iVector;
import developmental.warlocks.GL.SimpleGLRenderer;

/**
 * Created by Scott on 21/10/2014.
 */
public class SwapSpell extends Spell {
    public SwapSpell(Player _parent, SpellInfo s) {
        super(_parent, s);
    }
    @Override
    protected void Shoot(iVector Dest, Vector Origin) {
        SimpleGLRenderer.addObject(new SwapProjectile(Origin, new Vector(Dest.x, Dest.y), this.parent,this.Rank));
    }
}
