package me.sub.common.entity;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityMobA extends EntityMob implements IRangedAttackMob {

    public EntityMobA(World worldIn) {
        super(worldIn);
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.tasks.addTask(0, new EntityAIAttackMelee(this, 2, false));

        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 100, true, false, input -> !(input instanceof EntityMobA)));

        this.tasks.addTask(0, new EntityAIWander(this, 1.0D, 100));

        this.tasks.addTask(0, new EntityAIAttackRanged(this, 2, 4, 30));


        EntityAIMoveTowardsRestriction movingTask = new EntityAIMoveTowardsRestriction(this, 1.0D);
        this.tasks.addTask(5, movingTask);
        movingTask.setMutexBits(3);

    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public float getEyeHeight() {
        return this.height * 0.5F;
    }

    @Override
    public float getBlockPathWeight(BlockPos pos) {
        return this.world.getBlockState(pos).getMaterial() == Material.WATER ? 10.0F + this.world.getLightBrightness(pos) - 0.5F : super.getBlockPathWeight(pos);
    }

    @Override
    protected PathNavigate createNavigator(World worldIn) {
        return new PathNavigateSwimmer(this, worldIn);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

        @Override
        public void onUpdate() {
        super.onUpdate();

            if (!inWater && rand.nextInt(5) < 3) {
                attackEntityFrom(DamageSource.STARVE, 3.0F);
            }

            /*
             * Adding blindness to the player if they within 2 blocks
             */
        for(Entity entity : world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(50))) {
            if(entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                if (player.getDistanceSqToEntity(this) < 2 && !player.isCreative() && rand.nextBoolean()) {
                    player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 600));
                }
            }

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
        entity.attackEntityFrom(ModDamageSources.BITE, 4.0F);
        return super.attackEntityAsMob(entity);
    }

    /**
     * Attack the specified entity using a ranged attack.
     *
     * @param target
     * @param distanceFactor
     */
    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        faceEntity(target, 30, 30);
        EntityGas ball = new EntityGas(world, this);
        ball.setPosition(posX + this.getLookVec().x, posY + this.getEyeHeight(), posZ + this.getLookVec().z);
        world.spawnEntity(ball);
        world.playSound(null, this.getPosition(), SoundEvents.ENTITY_GHAST_SCREAM, SoundCategory.HOSTILE, 1F, 1F);
    }

    @Override
    protected void createRunningParticles() {

    }

    @Override
    public void spawnRunningParticles() {

    }

    @Override
    public void setSwingingArms(boolean swingingArms) {

    }
}
