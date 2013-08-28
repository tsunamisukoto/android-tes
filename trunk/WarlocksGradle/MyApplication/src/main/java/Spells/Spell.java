package Spells;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

import Actors.Player;
import Game.GameObject;
import Game.ObjectType;
import Game.SpellEffect;
import Input.Pointer;
import SpellProjectiles.BoomerangProjectile;
import SpellProjectiles.BounceProjectile;
import SpellProjectiles.DrainProjectile;
import SpellProjectiles.FireballProjectile;
import SpellProjectiles.HomingProjectile;
import SpellProjectiles.Projectile;
import SpellProjectiles.SplitterProjectile;
import Tools.Vector;
import Tools.iVector;

import com.developmental.myapplication.Global;
import com.developmental.myapplication.RenderThread;

public class Spell {
	public int Cooldown =50;
    int CastTime = 3;
	public int Current = 0;
	GameObject parent;
Paint p;
    Bitmap curr;
    int sz= 40;
	public Spell(GameObject _parent) {
		this.parent = _parent;
		p = new Paint();
        p.setColor(Color.RED);
        curr=Global.ButtonImages.get(4);
		// owner = parent.id;
	}
    public void DrawButton(Canvas c,int x, int y,float w,float h)
    {
        c.drawBitmap(curr,x,y+10,p);
    }

	public void Cast(List<iVector> dest) {
if(dest.size()>0)
			for (int x = 0; x < dest.size(); x++)

					if (this.Current == 0) {
						Shoot(dest.get(x));
						this.Current = this.Cooldown;

                        this.parent.Debuffs.add(new SpellEffect(this.CastTime,SpellEffect.EffectType.Cast, Global.Sprites.get(2),this.parent));
                        if(this.parent.objectObjectType== ObjectType.Enemy||this.parent.objectObjectType== ObjectType.Player)
                        {
                            ((Player) this.parent).Animate(new Vector(dest.get(x).x,dest.get(x).y));
                        }
                        return;
					}
	}



	public void Update() {
		if (this.Current > 0)
			this.Current -= 1;
	}

	void Shoot(iVector Dest) {
		RenderThread.addObject(new FireballProjectile(new Vector(this.parent.rect.left
				+ this.parent.rect.width() / 2, this.parent.rect.top
				+ this.parent.rect.height() / 2), new Vector(Dest.x,Dest.y), this.parent));
	}
}
