package Spells.SpellSlots.Slot6;

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
public class RootSelfSpell extends Spell {
    public RootSelfSpell(Player _parent, LoadOutInfo s) {
        super(_parent, s);
    }

    @Override
    protected void Shoot(iVector Dest, Vector Origin) {
        this.parent.Debuffs.add(new SpellEffect(150, SpellEffect.EffectType.Root, this.parent, R.drawable.effect_glass, new iVector(0, 0)));
    }
}
