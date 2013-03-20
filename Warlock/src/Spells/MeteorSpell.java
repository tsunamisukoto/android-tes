package Spells;

import Game.GameObject;
import SpellProjectiles.Meteor;
import Tools.Vector;

import com.example.warlockgame.RenderThread;

public class MeteorSpell extends Spell {

	public MeteorSpell(GameObject _parent) {
		super(_parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	void Shoot(Vector Dest) {
		RenderThread.addObject(new Meteor(new Vector(this.parent.rect.left
				+ this.parent.rect.width() / 2, this.parent.rect.top
				+ this.parent.rect.height() / 2), Dest.get(), this.parent));
	}

}