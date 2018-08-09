package me.sub.common.entity;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class EntityMobD extends EntityMob {

    private static final DataParameter<Boolean> IS_HIDING = EntityDataManager.createKey(EntityMobD.class, DataSerializers.BOOLEAN);


    public EntityMobD(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(IS_HIDING, false);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setBoolean("hiding", isHiding());
        return super.writeToNBT(compound);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        setHiding(compound.getBoolean("hiding"));
        super.readEntityFromNBT(compound);
    }

    @Override
    public boolean canBeAttackedWithItem() {
        return !isHiding();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        setHiding(false);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        super.dropFewItems(wasRecentlyHit, lootingModifier);
        //TODO drop items here
        // "This mob also drops magma balls, which can be thrown like snowballs - but they deal damage and set mobs on fire."
    }

    public boolean isHiding() {
        return getDataManager().get(IS_HIDING);
    }

    public void setHiding(boolean hiding) {
        getDataManager().set(IS_HIDING, hiding);
    }
}
