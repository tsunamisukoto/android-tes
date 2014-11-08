package Spells;

import android.graphics.Bitmap;
import android.graphics.Paint;

import com.developmental.warlocks.R;

import SpellProjectiles.FireballProjectile;
import Spells.SpellSlots.Slot1.FireballSpell;
import Spells.SpellSlots.Slot2.BoomerangSpell;
import Spells.SpellSlots.Slot2.HomingSpell;
import Spells.SpellSlots.Slot2.IllusionSpell;
import Spells.SpellSlots.Slot2.LightningSpell;
import Spells.SpellSlots.Slot3.GravitySpell;
import Spells.SpellSlots.Slot3.IceSpell;
import Spells.SpellSlots.Slot3.IllusionBallSpell;
import Spells.SpellSlots.Slot3.LinkSpell;
import Spells.SpellSlots.Slot3.MeteorSpell;
import Spells.SpellSlots.Slot4.AbsorptionSpell;
import Spells.SpellSlots.Slot4.BouncerSpell;
import Spells.SpellSlots.Slot4.DrainSpell;
import Spells.SpellSlots.Slot4.FireSpraySpell;
import Spells.SpellSlots.Slot4.IceSpraySpell;
import Spells.SpellSlots.Slot4.SplitterSpell;
import Spells.SpellSlots.Slot5.PhaseSpell;
import Spells.SpellSlots.Slot5.SwapSpell;
import Spells.SpellSlots.Slot5.TeleportSpell;
import Spells.SpellSlots.Slot5.ThrustSpell;
import Spells.SpellSlots.Slot5.WindWalkSpell;
import Spells.SpellSlots.Slot6.DrainExplodeSpell;
import Spells.SpellSlots.Slot6.FireExplosionSpell;
import Spells.SpellSlots.Slot6.IceExplosionSpell;
import Spells.SpellSlots.Slot6.MagnetExplodeSpell;
import Spells.SpellSlots.Slot6.OrbitalsSpell;
import Spells.SpellSlots.Slot6.ReflectSpell;
import Spells.SpellSlots.Slot6.RootSelfSpell;
import Tools.Vector;
import Tools.iVector;
import developmental.warlocks.GL.NewHeirarchy.Collideable;
import developmental.warlocks.GL.NewHeirarchy.GameObject;
import developmental.warlocks.GL.SimpleGLRenderer;
import developmental.warlocks.Global;


public class Spell {
    public static Spell[] GenerateSpellList(GameObject parent,SpellInfo[] spellList) {
       Spell[] s= new Spell[7];
        for (int x = 0; x < 7; x++) {
            Spell sp = null;
            switch(spellList[x].spellType)
            {

                case Fireball:
                   sp = new FireballSpell(parent,spellList[x]);
                    break;
                case Lightning:
                    sp = new LightningSpell(parent,spellList[x]);

                    break;
                case Illusion:
                    sp = new IllusionSpell(parent,spellList[x]);
                    break;
                case Homing:
                    sp = new HomingSpell(parent,spellList[x]);
                    break;
                case Boomerang:
                    sp = new BoomerangSpell(parent,spellList[x]);
                    break;
                case Link:
                    sp = new LinkSpell(parent,spellList[x]);
                    break;
                case Ice:
                    sp = new IceSpell(parent,spellList[x]);
                    break;
                case Gravity:
                    sp = new GravitySpell(parent,spellList[x]);
                    break;
                case Meteor:
                    sp = new MeteorSpell(parent,spellList[x]);
                    break;
                case Drain:
                    sp = new DrainSpell(parent,spellList[x]);
                    break;
                case IllusionBall:
                    sp = new IllusionBallSpell(parent,spellList[x]);
                    break;
                case Absorb:
                    sp = new AbsorptionSpell(parent,spellList[x]);
                    break;
                case Splitter:
                    sp = new SplitterSpell(parent,spellList[x]);
                    break;
                case FireSpray:
                    sp = new FireSpraySpell(parent,spellList[x]);
                    break;
                case IceSpray:
                    sp = new IceSpraySpell(parent,spellList[x]);
                    break;
                case Bounce:
                    sp = new BouncerSpell(parent,spellList[x]);
                    break;
                case Teleport:
                    sp = new TeleportSpell(parent,spellList[x]);
                    break;
                case Swap:
                    sp = new SwapSpell(parent,spellList[x]);
                    break;
                case Thrust:
                    sp = new ThrustSpell(parent,spellList[x]);
                    break;
                case Reflect:
                    sp = new ReflectSpell(parent,spellList[x]);
                    break;
                case Orbitals:
                    sp = new OrbitalsSpell(parent,spellList[x]);
                    break;
                case Root:
                    sp = new RootSelfSpell(parent,spellList[x]);
                    break;
                case JuggerNaught:
                    sp = new LightningSpell(parent,spellList[x]);
                    break;
                case WindWalk:
                    sp = new WindWalkSpell(parent,spellList[x]);
                    break;
                case Phase:
                    sp = new PhaseSpell(parent,spellList[x]);
                    break;
                case BurnAura:
                    break;
                case HealAura:
                    break;
                case Bezerk:
                    break;
                case Fervour:
                    break;
                case Boots:
                    break;
                case HealthStone:
                    break;
                case Shield:
                    break;
                case FreezeAura:
                    break;
                case FireExplode:
                    sp = new FireExplosionSpell(parent,spellList[x]);

                    break;
                case IceExplode:
                    sp = new IceExplosionSpell(parent,spellList[x]);

                    break;
                case BurnExplode:
                    sp = new MagnetExplodeSpell(parent,spellList[x]);

                    break;
                case DrainExplode:
                    sp = new DrainExplodeSpell(parent,spellList[x]);

                    break;
            }
            s[x] = sp;
        }
        return s;
    }

    public enum CastType{Projectile,Explosion,Passive,Spray,ActivateBuff}

    CastType castType;
    public int Cooldown = 50;
    int CastTime = 5;
    public int Current = 0;
    protected GameObject parent;
    Paint p;
    Bitmap curr;
    int castphase;
    boolean fired = false;
    int sz = 40;
   protected iVector targetLocation;
    SpellType spellType;
    public int Rank;
    float damage= 5;
    int radius = 15;
    int range;
    public int texture;
    void setValues(int casttime, int cooldown, float damage,int radius,int rng)
    {
        this.CastTime = casttime;
        this.Cooldown = cooldown;
        this.damage = damage;
        this.radius = radius;
        this.range = rng;
    }
    public void loadResouce()
    {
        this.texture = Global.resources.get(SpellInfo.setResource(s.spellType));

    }
    SpellInfo s;
    public Spell(GameObject _parent,SpellInfo s) {
        this.parent = _parent;
        this.s = s;
        castphase = CastTime;
        Rank = s.Rank;
        spellType = s.spellType;

        switch (spellType) {
            case Fireball:
            case Lightning:
            case Illusion:
            case Homing:
            case Boomerang:
            case Link:
            case Ice:
            case Gravity:
            case Meteor:
            case Drain:
            case IllusionBall:
            case Absorb:
            case Splitter:
            case Bounce:
            case Teleport:
            case Swap:
            case Thrust:
                this.castType =CastType.Projectile;
                break;
            case FireSpray:
            case IceSpray:
                this.castType = CastType.Spray;
                break;

            case Reflect:
            case Orbitals:
            case Root:
            case JuggerNaught:
            case WindWalk:
            case Phase:
                this.castType = CastType.ActivateBuff;
                break;
            case BurnAura:
            case HealAura:
            case Bezerk:
            case Fervour:
            case Boots:
            case HealthStone:
            case Shield:
            case FreezeAura:
                this.castType = CastType.Passive;
                break;
            case FireExplode:
            case IceExplode:
            case BurnExplode:
            case DrainExplode:
                this.castType = CastType.Explosion;
                break;

        }
        setAttributes(spellType,s.Rank);

        // owner = parent.id;
    }
private void setAttributes(SpellType s, int rank)
{
    switch (s)
    {

        case Fireball:
            switch (rank)
            {
                case 1:
                this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case Lightning:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case Homing:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case Boomerang:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case Link:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case Ice:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case Gravity:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case Meteor:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case Drain:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case Absorb:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case Splitter:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case FireSpray:
            switch (rank)
            {
                case 1:
                    this.setValues(20,20,5,20,30);
                    break;
                case 2:
                    this.setValues(20,20,5,20,30);
                    break;
                case 3:
                    this.setValues(20,20,5,20,30);
                    break;
                case 4:
                    this.setValues(20,20,5,20,30);
                    break;
                case 5:
                    this.setValues(20,20,5,20,30);
                    break;
                case 6:
                    this.setValues(20,20,5,20,30);
                    break;
                case 7:
                    this.setValues(20,20,5,20,30);
                    break;
            }
            break;
        case IceSpray:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case Bounce:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case Teleport:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case Swap:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case Thrust:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case Reflect:
            switch (rank)
            {
                case 1:
                    this.setValues(50,20,5,20,30);
                    break;
                case 2:
                    this.setValues(50,20,5,20,30);
                    break;
                case 3:
                    this.setValues(50,20,5,20,30);
                    break;
                case 4:
                    this.setValues(50,20,5,20,30);
                    break;
                case 5:
                    this.setValues(50,20,5,20,30);
                    break;
                case 6:
                    this.setValues(50,20,5,20,30);
                    break;
                case 7:
                    this.setValues(50,20,5,20,30);
                    break;
            }
            break;
        case Orbitals:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case Root:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case JuggerNaught:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case WindWalk:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case Phase:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case BurnAura:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case HealAura:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case Bezerk:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case Fervour:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case Boots:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case HealthStone:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case Shield:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case FreezeAura:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case FireExplode:
            switch (rank)
            {
                case 1:
                    this.setValues(8,20,5,20,30);
                    break;
                case 2:
                    this.setValues(8,20,5,20,30);
                    break;
                case 3:
                    this.setValues(8,20,5,20,30);
                    break;
                case 4:
                    this.setValues(8,20,5,20,30);
                    break;
                case 5:
                    this.setValues(8,20,5,20,30);
                    break;
                case 6:
                    this.setValues(8,20,5,20,30);
                    break;
                case 7:
                    this.setValues(8,20,5,20,30);
                    break;
            }
            break;
        case IceExplode:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case BurnExplode:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
        case DrainExplode:
            switch (rank)
            {
                case 1:
                    this.setValues(5,20,5,20,30);
                    break;
                case 2:
                    this.setValues(5,20,5,20,30);
                    break;
                case 3:
                    this.setValues(5,20,5,20,30);
                    break;
                case 4:
                    this.setValues(5,20,5,20,30);
                    break;
                case 5:
                    this.setValues(5,20,5,20,30);
                    break;
                case 6:
                    this.setValues(5,20,5,20,30);
                    break;
                case 7:
                    this.setValues(5,20,5,20,30);
                    break;
            }
            break;
    }
}



    public void glDraw()
    {

    }

    public boolean Cast(iVector[] dest) {
        if(!parent.frozen&&!parent.dead)
            switch (castType)
            {

                case Spray:
                case Projectile:
                    if (dest.length > 0)
                        for (int x = 0; x < dest.length; x++)

                            if (this.Current == 0) {
                                this.targetLocation = parent.position.subtract(dest[x]);
                                castphase= 0;
                                fired=  true;
                                this.Current = this.Cooldown;

                                this.parent.Debuffs.add(new SpellEffect(this.CastTime, SpellEffect.EffectType.Cast,  this.parent,R.drawable.effect_shield));
                                if (this.parent.objectObjectType == Collideable.ObjectType.Enemy || this.parent.objectObjectType == Collideable.ObjectType.Player) {
                                    (this.parent).Animate(new Vector(dest[x].x, dest[x].y));
                                }
                                return true;
                            }
                    return false;
                case Explosion:
                    if(!parent.frozen&&!parent.dead)
                        if (this.Current == 0) {
                            this.Current = this.Cooldown;
                            this.parent.Debuffs.add(new SpellEffect(this.CastTime, SpellEffect.EffectType.Explode, this.parent,R.drawable.effect_explode));
                            this.targetLocation =new iVector(0,0);
                            castphase= 0;
                            fired=  true;
                            return true;
                        }
                    return false;
                case ActivateBuff:
                       if(!parent.frozen&&!parent.dead)
                        if (this.Current == 0) {
                            this.Current = this.Cooldown;

                            this.parent.Debuffs.add(new SpellEffect(this.CastTime, SpellEffect.EffectType.Reflect, this.parent,R.drawable.effect_shield));
                            return true;
                        }
                    return false;
            }
        return false;

    }
    public void Cast(iVector dest) {

        if(!parent.frozen&&!parent.dead&&!parent.casting)

                if (this.Current == 0) {
                    this.targetLocation = parent.position.subtract(dest);
                    castphase= 0;
                    fired=  true;
                    this.Current = this.Cooldown;

                    this.parent.Debuffs.add(new SpellEffect(this.CastTime, SpellEffect.EffectType.Cast, this.parent,R.drawable.effect_shield));
                    if (this.parent.objectObjectType == Collideable.ObjectType.Enemy || this.parent.objectObjectType == Collideable.ObjectType.Player) {
                        ( this.parent).Animate(new Vector(dest.x, dest.y));
                    }
                    return;
                }
    }


    public void Update() {
        if(fired)
        {
            castphase+=1;
            if(castType == CastType.Spray)
            {

                if(castphase%3==2)
                {
                    Shoot((parent.position.subtract(this.targetLocation)));
                }

            }
            if(castphase==CastTime)
            {
                Shoot((parent.position.subtract(this.targetLocation)));
                fired= false;
            }
        }
        else
        {
        if (this.Current > 0)
            this.Current -= 1;
        }
    }

    protected void Shoot(iVector Dest) {
        SimpleGLRenderer.addObject(new FireballProjectile(this.parent.bounds.Center, new Vector(Dest.x, Dest.y), this.parent));


    }

}