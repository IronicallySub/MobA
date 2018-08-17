package me.sub.common.entity;

import me.sub.common.AObjects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityVillager;
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
    private static final DataParameter<Boolean> SPECIAL_ATTACK = EntityDataManager.createKey(EntityMobD.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> SPECIAL_TIME = EntityDataManager.createKey(EntityMobD.class, DataSerializers.VARINT);

    public EntityMobD(World worldIn) {
        super(worldIn);
        isImmuneToFire = true;
    
        //Tasks
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(0, new EntityAIAttackRanged(this, 1, 20, 23));
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 100, true, false, input -> !(input instanceof EntityMobD)));
  
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {EntityPigZombie.class}));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
    }
    
    /**
     * Called by the /kill command.
     */
    @Override
    public void onKillCommand()
    {
        this.setDead();
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
    }

    @Override
    public boolean getIsInvulnerable() {
        return false;
    }


    @Override
    public boolean canBreatheUnderwater() {
        return false;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(IS_HIDING, false);
        this.getDataManager().register(HIDDEN_TIME, 0);
        this.getDataManager().register(SPECIAL_ATTACK, false);
        this.getDataManager().register(SPECIAL_TIME, 0);
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
        compound.setBoolean("attacking", isAttackActive());
        return super.writeToNBT(compound);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        setHiding(compound.getBoolean("hiding"));
        setHiddenTime(compound.getInteger("hiddenTime"));
        setAttackActive(compound.getBoolean("attacking"));
        super.readEntityFromNBT(compound);
    }

    @Override
    public void travel(float strafe, float vertical, float forward) {
        if (!isHiding()) {
            super.travel(strafe, vertical, forward);
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (ticksExisted % 400 == 0 && world.getClosestPlayerToEntity(this, 6) != null) {
           EntityPlayer player = world.getClosestPlayerToEntity(this, 6); 
        	if(player.isCreative()) return;
        	setAttackActive(true);
        }

        if (isAttackActive()) {
            setSpecialTime(getSpecialTime() + 1);
            int time = getSpecialTime();
            if (time < 100) {
                this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, 0D, 0D, 0D);
            }

            if (time == 100) {
            	playSound(SoundEvents.ENTITY_ENDERMEN_SCREAM, 1, 1);
                for (Entity entity : world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().grow(6))) {
                    entity.setFire(10);
                    this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, 0D, 0D, 0D);
                    entity.world.spawnParticle(EnumParticleTypes.FLAME, entity.posX + (this.rand.nextDouble() - 0.5D) * (double) entity.width, entity.posY + this.rand.nextDouble() * (double) entity.height, entity.posZ + (this.rand.nextDouble() - 0.5D) * (double) entity.width, 0D, 0D, 0D);
                }
                setSpecialTime(0);
                setAttackActive(false);
            }
        }

        if (isWet()) {
            attackEntityFrom(DamageSource.DROWN, 1.0F);
        }

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


                if (!isAttackActive()) {
                    if (!isHiding()) {
                        this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, 0.0D, 0.0D, 0.0D);
                    } else {
                        this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, 0.0D, 0.0D, 0.0D);
                    }
                }

                if (getHealth() < 5) {
                    this.world.spawnParticle(EnumParticleTypes.DAMAGE_INDICATOR, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, 1D, 1D, 1D);
                }

            }
        }

        super.onLivingUpdate();
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        super.dropFewItems(wasRecentlyHit, lootingModifier);
        dropItem(AObjects.PLATE, 1);
        dropItem(AObjects.CHARGE, rand.nextInt(5));

        if (rand.nextBoolean()) {
            for (int i = 0; i < 4; i++) {
                EntityBlaze blaze = new EntityBlaze(world);
                blaze.setLocationAndAngles(getPosition().getX() + world.rand.nextInt(4), getPosition().getY(), getPosition().getZ() + world.rand.nextInt(4), 0, 0);
                world.spawnEntity(blaze);
            }
        }
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

    public boolean isAttackActive() {
        return getDataManager().get(SPECIAL_ATTACK);
    }

    public void setAttackActive(boolean hiding) {
        getDataManager().set(SPECIAL_ATTACK, hiding);
    }

    public int getSpecialTime() {
        return getDataManager().get(SPECIAL_TIME);
    }

    public void setSpecialTime(int time) {
        getDataManager().set(SPECIAL_TIME, time);
    }


    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        if (isHiding() || getAttackTarget() == null || target.isDead) return;
        faceEntity(getAttackTarget(), 30, 30);
        EntityRock ball = new EntityRock(world, this);
        ball.setPosition(posX + this.getLookVec().x, posY + this.getEyeHeight(), posZ + this.getLookVec().z);
        world.spawnEntity(ball);
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {

    }
}
