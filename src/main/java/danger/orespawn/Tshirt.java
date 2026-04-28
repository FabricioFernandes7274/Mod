/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityAgeable
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.0f;

    public Tshirt(World par1World) {
        super(par1World);
        this.setSize(4.0f, 4.0f);
        this.experienceValue = 40;
        //this.fireResistance = 100;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0);
    }

    protected void entityInit() {
        super.entityInit();
    }

    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public void onUpdate() {
        super.onUpdate();
    }

    public int mygetMaxHealth() {
        return 1;
    }

    public int getTotalArmorValue() {
        return 0;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    protected String getLivingSound() {
        return null;
    }

    protected String getHurtSound() {
        return null;
    }

    protected String getDeathSound() {
        return null;
    }

    protected float getSoundVolume() {
        return 1.0f;
    }

    protected float getSoundPitch() {
        return 1.0f;
    }

    protected Item getDropItem() {
        return Items.EMERALD;
    }

    public void initCreature() {
    }

    public boolean interact(net.minecraft.entity.player.EntityPlayer par1EntityPlayer) {
        return false;
    }

    public boolean getCanSpawnHere() {
        if (!this.world.isDaytime()) {
            return false;
        }
        if (this.posY < 50.0) {
            return false;
        }
        Tshirt target = null;
        target = (Tshirt)this.world.findNearestEntityWithinAABB(Tshirt.class, this.boundingBox.expand(20.0, 8.0, 20.0), (Entity)this);
        return target == null;
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        return null;
    }
}

