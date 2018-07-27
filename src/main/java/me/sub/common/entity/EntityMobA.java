package me.sub.common.entity;

import me.sub.common.AObjects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityMobA extends EntityMob {

    private static final DataParameter<Boolean> MOVING = EntityDataManager.createKey(EntityMobA.class, DataSerializers.BOOLEAN);


    public EntityMobA(World worldIn) {
        super(worldIn);
        this.setSize(0.85F, 0.85F);
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.tasks.addTask(0, new EntityAIAttackMelee(this, 2, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 100, true, false, input -> !(input instanceof EntityMobA)));
        this.tasks.addTask(0, new EntityAISwimming(this));
        EntityAIWander wander = new EntityAIWander(this, 2, 80);
        this.tasks.addTask(0, wander);
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
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public float getEyeHeight() {
        return this.height * 0.5F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SQUID_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SQUID_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SQUID_DEATH;
    }

    @Override
    public void onKillEntity(EntityLivingBase entityLivingIn) {
        super.onKillEntity(entityLivingIn);
        world.playSound(null, this.getPosition(), AObjects.BITE, SoundCategory.HOSTILE, 1F, 1F);
    }

    @Override
    protected PathNavigate createNavigator(World worldIn) {
        return new PathNavigateSwimmer(this, worldIn);
    }

    @Override
    public void onDeath(DamageSource cause) {
        super.onDeath(cause);
        if (!world.isRemote) {
            dropItem(AObjects.TONGUE, 1);
        }
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isPushedByWater() {
        return false;
    }

        @Override
        public void onUpdate() {
        super.onUpdate();

            if (getAttackTarget() == null && world.getClosestPlayerToEntity(this, 8) != null) {
                EntityPlayer player = world.getClosestPlayerToEntity(this, 8);
                if (!player.isCreative()) {
                    setAttackTarget(player);
                } else {
                    if (world.getBlockState(getPosition().up()).getBlock() == Blocks.AIR) {
                        this.motionY = -4;
                    }
                }
            }

        if(getAttackTarget() != null) {
            if (getAttackTarget().getDistance(this) < 10 && rand.nextInt(250) < 100 && rand.nextBoolean()) {
                faceEntity(getAttackTarget(), 30, 30);
                EntityGas ball = new EntityGas(world, this);
                ball.setPosition(posX + this.getLookVec().x, posY + this.getEyeHeight(), posZ + this.getLookVec().z);
                world.spawnEntity(ball);
                world.playSound(null, this.getPosition(), SoundEvents.ENTITY_GHAST_SCREAM, SoundCategory.HOSTILE, 1F, 1F);
            }
        }


            /*
             * Adding blindness to the player if they within 2 blocks
             */
        for(Entity entity : world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().grow(50))) {
            if(entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                if (player.getDistance(this) < 1 && !player.isCreative() && rand.nextBoolean()) {
                    player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100));
                }
            }

        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(MOVING, false);
    }    
    
    
    @Override
    public void onEntityUpdate() {
        int i = this.getAir();
        super.onEntityUpdate();

        if (this.isEntityAlive() && !this.isInWater()) {
            --i;
            this.setAir(i);

            if (this.getAir() == -20) {
                this.setAir(0);
                this.attackEntityFrom(DamageSource.DROWN, 2.0F);
            }
        } else {
            this.setAir(300);
        }
    }


    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    @Override
    public boolean getCanSpawnHere()
    {
        return this.posY > 45.0D && this.posY < (double)this.world.getSeaLevel() && super.getCanSpawnHere();
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return super.attackEntityFrom(ModDamageSources.BITE, 4);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        world.playSound(null, this.getPosition(), AObjects.BITE, SoundCategory.HOSTILE, 1F, 1F);
        entity.attackEntityFrom(ModDamageSources.BITE, 4.0F);
        return super.attackEntityAsMob(entity);
    }


}
