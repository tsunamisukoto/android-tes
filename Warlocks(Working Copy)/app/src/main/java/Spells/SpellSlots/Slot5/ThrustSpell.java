package Spells.SpellSlots.Slot5;

import com.developmental.warlocks.R;

import Actors.Player;
import Spells.LoadOutInfo;
import Spells.Spell;
import Spells.SpellEffect;
import Tools.Vector;
import Tools.iVector;

/**
 * Created by Scott on 21/10/2014.
 */
public class ThrustSpell extends Spell {
    public ThrustSpell(Player _parent, LoadOutInfo s) {
        super(_parent, s);
    }

    @Override
    protected void Shoot(iVector Dest, Vector Origin) {
        this.parent.velocity = parent.GetVel2(Origin, new Vector(Dest.x, Dest.y), 30f);
        parent.destination = new Vector(Dest.x, Dest.y);
        this.parent.Debuffs.add(new SpellEffect(this.range, SpellEffect.EffectType.Thrust, this.parent, R.drawable.effect_shield, new iVector(0, 0)));
    }
}
