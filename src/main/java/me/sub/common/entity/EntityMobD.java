package me.sub.common.entity;

import me.sub.common.AObjects;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityMobD extends EntityMob implements IRangedAttackMob {

    private static final DataParameter<Boolean> IS_HIDING = EntityDataManager.createKey(EntityMobD.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> HIDDEN_TIME = EntityDataManager.createKey(EntityMobD.class, DataSerializers.VARINT);


    public EntityMobD(World worldIn) {
        super(worldIn);
        isImmuneToFire = true;

        //Paths
        setPathPriority(PathNodeType.WATER, -1.0F);
        setPathPriority(PathNodeType.LAVA, 8.0F);
        setPathPriority(PathNodeType.DANGER_FIRE, 0.0F);
        setPathPriority(PathNodeType.DAMAGE_FIRE, 0.0F);

        //Tasks
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D, 0.0F));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.tasks.addTask(0, new EntityAIAttackRanged(this, 1, 20, 23));
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 100, true, false, input -> !(input instanceof EntityMobD)));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
    }

    @Override
    public boolean getIsInvulnerable() {
        return isHiding();
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(IS_HIDING, false);
        this.getDataManager().register(HIDDEN_TIME, 0);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_BLAZE_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_BLAZE_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BLAZE_DEATH;
    }


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setBoolean("hiding", isHiding());
        compound.setInteger("hiddenTime", getHiddenTime());
        return super.writeToNBT(compound);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        setHiding(compound.getBoolean("hiding"));
        setHiddenTime(compound.getInteger("hiddenTime"));
        super.readEntityFromNBT(compound);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (isHiding()) {
            setHiddenTime(getHiddenTime() + 1);
        } else {
            setHiddenTime(0);
        }

        if (getHiddenTime() > 100) {
            setHiding(false);
        }
    }

    @Override
    public void onLivingUpdate() {
        if (!this.onGround && this.motionY < 0.0D) {
            this.motionY *= 0.6D;
        }

        if (this.world.isRemote) {
            if (this.rand.nextInt(24) == 0 && !this.isSilent()) {
                this.world.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, SoundEvents.ENTITY_BLAZE_BURN, this.getSoundCategory(), 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
            }

            for (int i = 0; i < 2; ++i) {
                if (!isHiding()) {
                    this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, 0.0D, 0.0D, 0.0D);
                } else {
                    this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, 0.0D, 0.0D, 0.0D);
                }
            }
        }

        super.onLivingUpdate();
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        super.dropFewItems(wasRecentlyHit, lootingModifier);
        dropItem(AObjects.PLATE, 1);
    }

    public boolean isHiding() {
        return getDataManager().get(IS_HIDING);
    }

    public void setHiding(boolean hiding) {
        getDataManager().set(IS_HIDING, hiding);
    }

    public int getHiddenTime() {
        return getDataManager().get(HIDDEN_TIME);
    }

    public void setHiddenTime(int hideTime) {
        getDataManager().set(HIDDEN_TIME, hideTime);
    }

    /**
     * Attack the specified entity using a ranged attack.
     *
     * @param target
     * @param distanceFactor
     */
    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        if (isHiding() || getAttackTarget() == null) return;
        System.out.println("targeting: " + target.getName());
        faceEntity(getAttackTarget(), 30, 30);
        EntityRock ball = new EntityRock(world, this);
        ball.setPosition(posX + this.getLookVec().x, posY + this.getEyeHeight(), posZ + this.getLookVec().z);
        world.spawnEntity(ball);
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {

    }
}
