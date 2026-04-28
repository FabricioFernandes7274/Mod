/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.EntityVillager
 */
package danger.orespawn;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;

public class MyUtils {
    public static boolean isRoyalty(Entity e) {
        if (!(e instanceof net.minecraft.entity.EntityLivingBase)) {
            return false;
        }
        if (e instanceof ThePrince) {
            return true;
        }
        if (e instanceof ThePrinceTeen) {
            return true;
        }
        if (e instanceof ThePrinceAdult) {
            return true;
        }
        if (e instanceof ThePrincess) {
            return true;
        }
        if (e instanceof TheKing) {
            return true;
        }
        if (e instanceof KingHead) {
            return true;
        }
        if (e instanceof TheQueen) {
            return true;
        }
        if (e instanceof QueenHead) {
            return true;
        }
        return e instanceof PurplePower;
    }

    public static boolean isAttackableNonMob(net.minecraft.entity.EntityLivingBase par1EntityLiving) {
        if (par1EntityLiving instanceof EntityMob) {
            return true;
        }
        if (par1EntityLiving instanceof Mothra) {
            return true;
        }
        if (par1EntityLiving instanceof Leon) {
            return true;
        }
        if (par1EntityLiving instanceof Dragon) {
            return true;
        }
        if (par1EntityLiving instanceof Spyro) {
            return true;
        }
        if (MyUtils.isRoyalty((Entity)par1EntityLiving)) {
            return true;
        }
        if (par1EntityLiving instanceof GammaMetroid) {
            return true;
        }
        if (par1EntityLiving instanceof Cephadrome) {
            return true;
        }
        if (par1EntityLiving instanceof WaterDragon) {
            return true;
        }
        if (par1EntityLiving instanceof Girlfriend) {
            return true;
        }
        if (par1EntityLiving instanceof Boyfriend) {
            return true;
        }
        if (par1EntityLiving instanceof EntityVillager) {
            return true;
        }
        return par1EntityLiving instanceof Stinky;
    }

    public static boolean isIgnoreable(net.minecraft.entity.EntityLivingBase par1EntityLiving) {
        if (par1EntityLiving instanceof RockBase) {
            return true;
        }
        if (par1EntityLiving instanceof EntityAnt) {
            return true;
        }
        if (par1EntityLiving instanceof EntityButterfly) {
            return true;
        }
        if (par1EntityLiving instanceof EntityMosquito) {
            return true;
        }
        if (par1EntityLiving instanceof Dragonfly) {
            return true;
        }
        if (par1EntityLiving instanceof Firefly) {
            return true;
        }
        if (par1EntityLiving instanceof Cricket) {
            return true;
        }
        if (par1EntityLiving instanceof Cockateil) {
            return true;
        }
        if (par1EntityLiving instanceof Termite) {
            return true;
        }
        if (par1EntityLiving instanceof Ghost) {
            return true;
        }
        if (par1EntityLiving instanceof GhostSkelly) {
            return true;
        }
        return par1EntityLiving instanceof Elevator;
    }
}

