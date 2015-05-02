package Collision;

import com.developmental.warlocks.R;

import Game.DamageType;
import HUD.PopupText;
import SpellProjectiles.BounceProjectile;
import SpellProjectiles.ExplosionProjectile;
import SpellProjectiles.HealProjectile;
import Spells.SpellEffect;
import Tools.Vector;
import Tools.iVector;
import developmental.warlocks.GL.NewHeirarchy.Collideable;
import developmental.warlocks.GL.SimpleGLRenderer;

/**
 * Created by Scott on 25/04/2015.
 */
public class ColliderBase {
    protected Collideable parent;

    public ColliderBase(Collideable _parent) {
        parent = _parent;
    }

    public void CollidesWithLogic(Collideable collideable) {
        Vector v1 = new Vector(0, 0);
        if (!collideable.jumping) {
            if (!collideable.shielded) {

                //gravity
                if (parent.CollideAppliesImpulse) {
                    if (parent.owner != null && collideable.id != parent.owner.id)
                    {
                        if (!parent.CollideIsExpolosion || collideable.owner == null || collideable.owner.id != parent.owner.id)
                        v1 = parent.DirectionalPull(collideable.position, parent.knockback);
                    }

                }

                if (parent.CollideAppliesVelocity) {
                    if (collideable.CollideCanHaveVelocityApplied) {
                        boolean apply = false;
                        //enemy player
                        if (parent.owner != null && collideable.owner == null && collideable.id != parent.owner.id)
                            apply = true;

                        //enemy projectile
                        if (collideable.owner != null && parent.owner != null && collideable.id != parent.owner.id && collideable.owner.id != parent.owner.id)
                            apply = true;
                        if (apply)
                            // v1= parent.velocity;
                            v1 = (Collideable.GetVel2(parent.bounds.Center, collideable.bounds.Center, parent.knockback));
                    }
                }
                if (collideable.archetypeManager != null) {
                    if (parent.owner == null || collideable.id != parent.owner.id)
                        collideable.archetypeManager.AddStacks(parent.archetypePower, parent.owner);
                }
                if (collideable.CollideCanTakeDamage && parent.damagevalue != 0 && parent.CollideDealsDamage) {
                    if (parent.owner != null && parent.owner.id != collideable.id && (!parent.CollideBouncesOnImpact || parent.lastTarget == null || parent.lastTarget.id != collideable.id))
                        collideable.Damage(parent.damagevalue, DamageType.Spell);
                    if (parent.owner != null) {
                        parent.owner.damageDealtThisRound += parent.damagevalue;
                    }
                }
                if (parent.CollideBouncesOnImpact) {
                    if (parent.owner.id != collideable.id && collideable.owner == null)
                        if (parent.lastTarget == null || collideable.id != parent.lastTarget.id) {

                            if (parent.stacks > 0) {
                                parent.lastTarget = collideable;
                                BounceProjectile.findNewTarget(parent);
                                parent.stacks -= 1;
                            } else {
                                SimpleGLRenderer.delObject(parent.id);
                            }
                        }
                }

                if (collideable.CollideKillsOnImpact && parent.CollideDiesOnImpact) {
                    if (parent.owner.id != collideable.id)
                        SimpleGLRenderer.delObject(parent.id);

                }
                if (collideable.CollideCanBeExploded && parent.CollideCanExplodeOtherThings) {
                    SimpleGLRenderer.delObject(collideable.id);
                    float size = (collideable.bounds.Radius + collideable.bounds.Radius) * 1.5f;
                    SimpleGLRenderer.addObject(new ExplosionProjectile(0, collideable.bounds.Center.get(), parent.owner, new Vector(size, size), (int) (parent.damagevalue + collideable.damagevalue), 3));
                }
                if (collideable.CollideCanBeLinked && parent.CollideLinksToThings) {
                    if (parent.owner.id != collideable.id)
                        parent.linked = collideable;
                }
                if (collideable.CollideCanHealOffOfThis) {


                    if (parent.healvalue > 0)
                        if (parent.owner != null && parent.owner.id != collideable.id) {

                            SimpleGLRenderer.addObject(new HealProjectile(parent.position, parent.bounds.Center.get(), parent.owner, (int) parent.healvalue));

                        }

                }

                if (collideable.CollideCanBeSwapped && parent.CollideCanSwapThings) {
                    if (parent.owner != null && parent.owner.id != collideable.id)
                        parent.Swap(collideable);
                }
                if (parent.CollideCanAbsorbThings && collideable.CollideCanBeAbsorbed) {
                    parent.Absorb(collideable);
                }
            } else {
                if (collideable.CollideDiesOnImpact) {
                    parent.velocity = Vector.multiply(parent.velocity, -1);
                    parent.owner = collideable;
                }
            }
            if (parent.CollideIsBoomerang) {
                if(collideable.id!=parent.owner.id) {
                    if(!collideable.CollideIsBoomerang||collideable.owner.id!=parent.owner.id)
                    parent.velocity = Collideable.GetVel2(collideable.bounds.Center, parent.bounds.Center, parent.knockback);
                }
            }

        }
        if (parent.owner == null)
            if (parent.CollideInflictsSlow) {
                collideable.Debuffs.add(new SpellEffect(500, SpellEffect.EffectType.Slow, collideable, R.drawable.effect_shield, new iVector(0, 0)));
            }
        if (parent.owner != null && collideable.id == parent.owner.id) {

            if (parent.CollideHealsTarget) {

                collideable.Heal(parent.damagevalue);
            }
            if (parent.CollideDiesOnImpactWithParent) {
                SimpleGLRenderer.delObject(parent.id);
            }
        }

        if (Vector.CurrentVelocity(v1) > 0) {
            int counter = 0;
            for (SpellEffect s : collideable.Debuffs) {
                if (s.effectType == SpellEffect.EffectType.Burn)
                    counter++;
            }
            float Multiplier = (collideable.mana + 400) / 400 * (float) Math.pow(1.2, counter);
            v1 = Vector.multiply(v1, Multiplier);
            if (!collideable.juggernaught)
                collideable.velocity = collideable.velocity.add(v1);
            else {
                collideable.jugstacks += 1;

                SimpleGLRenderer.popupTexts.add(new PopupText(PopupText.TextType.Message, "Stacks:" + collideable.jugstacks, collideable.position, 20));
            }
        }

    }

}
