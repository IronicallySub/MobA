package me.sub.common.entity;

import me.sub.common.AObjects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityMobA extends EntityMob {

    private static final DataParameter<Boolean> MOVING = EntityDataManager.createKey(EntityMobA.class, DataSerializers.BOOLEAN);

    private float randomMotionSpeed;
    private float randomMotionVecX;
    private float randomMotionVecY;
    private float randomMotionVecZ;

    public EntityMobA(World worldIn) {
        super(worldIn);
        this.setSize(0.85F, 0.85F);
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 100, true, false, input -> !(input instanceof EntityMobA)));
        this.tasks.addTask(0, new AIMoveRandom(this));
    }


    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
    }

    public static boolean isInSight(EntityLivingBase livingBase, EntityLivingBase angel) {
        return isInFrontOfEntity(livingBase, angel) && !viewBlocked(livingBase, angel);
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

    public static boolean isInFrontOfEntity(Entity entity, Entity target) {
        Vec3d vec3d = target.getPositionVector();
        Vec3d vec3d1 = entity.getLook(1.0F);
        Vec3d vec3d2 = vec3d.subtractReverse(new Vec3d(entity.posX, entity.posY, entity.posZ)).normalize();
        vec3d2 = new Vec3d(vec3d2.x, 0.0D, vec3d2.z);
        return vec3d2.dotProduct(vec3d1) < 0.0;
    }

    public static boolean viewBlocked(EntityLivingBase viewer, EntityLivingBase angel) {
        AxisAlignedBB vB = viewer.getEntityBoundingBox();
        AxisAlignedBB aB = angel.getEntityBoundingBox();
        Vec3d[] viewerPoints = {
                new Vec3d(vB.minX, vB.minY, vB.minZ),
                new Vec3d(vB.minX, vB.minY, vB.maxZ),
                new Vec3d(vB.minX, vB.maxY, vB.minZ),
                new Vec3d(vB.minX, vB.maxY, vB.maxZ),
                new Vec3d(vB.maxX, vB.maxY, vB.minZ),
                new Vec3d(vB.maxX, vB.maxY, vB.maxZ),
                new Vec3d(vB.maxX, vB.minY, vB.maxZ),
                new Vec3d(vB.maxX, vB.minY, vB.minZ),
        };
        Vec3d[] angelPoints = {
                new Vec3d(aB.minX, aB.minY, aB.minZ),
                new Vec3d(aB.minX, aB.minY, aB.maxZ),
                new Vec3d(aB.minX, aB.maxY, aB.minZ),
                new Vec3d(aB.minX, aB.maxY, aB.maxZ),
                new Vec3d(aB.maxX, aB.maxY, aB.minZ),
                new Vec3d(aB.maxX, aB.maxY, aB.maxZ),
                new Vec3d(aB.maxX, aB.minY, aB.maxZ),
                new Vec3d(aB.maxX, aB.minY, aB.minZ),
        };

        for (int i = 0; i < viewerPoints.length; i++) {
            if (viewer.world.rayTraceBlocks(viewerPoints[i], angelPoints[i], false, true, false) == null)
                return false;
        }
        return true;
    }

    @Override
    public void travel(float strafe, float vertical, float forward) {
        if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(strafe, vertical, forward, 0.1F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.8999999761581421D;
            this.motionY *= 0.8999999761581421D;
            this.motionZ *= 0.8999999761581421D;

            if (this.getAttackTarget() == null) {
                this.motionY -= 0.005D;
            }
        } else {
            super.travel(strafe, vertical, forward);
        }
    }

        @Override
        public void onUpdate() {
        super.onUpdate();


            if (getAttackTarget() == null && world.getClosestPlayerToEntity(this, 16) != null) {
                EntityPlayer player = world.getClosestPlayerToEntity(this, 16);
                if (!player.isCreative()) {
                    setAttackTarget(player);
                }
            }


            if (world.getBlockState(getPosition().up()).getBlock() == Blocks.AIR) {
                this.motionY = -2;
            }


            if (getAttackTarget() != null) {
                Entity target = getAttackTarget();
                if (target.getDistance(this) < 2) {
                    target.attackEntityFrom(ModDamageSources.BITE, 1F);
                }
            }

        if(getAttackTarget() != null) {

            if (getAttackTarget() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) getAttackTarget();
                if (player.isCreative()) return;
            }

            if (getAttackTarget().getDistance(this) < 10 && rand.nextBoolean() && rand.nextInt(10) < 3 && ticksExisted % 300 != 0) {
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

            if (getAttackTarget() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) getAttackTarget();
                if (player.getDistanceSq(this) < 5) {

                    if (rand.nextBoolean() && isInSight(this, player) && ticksExisted % 200 == 0 && canEntityBeSeen(getAttackTarget())) {
                        player.addPotionEffect(new PotionEffect(AObjects.FREEZE, 200));
                    }

                    boolean flag = player.isPotionActive(AObjects.FREEZE);
                    if (flag) {
                        this.navigator.tryMoveToEntityLiving(player, 0.7);
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

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (this.inWater) {
            this.randomMotionSpeed = 0.5F;
            if (!this.world.isRemote) {
                this.motionX = (this.randomMotionVecX * this.randomMotionSpeed);
                this.motionY = (this.randomMotionVecY * this.randomMotionSpeed);
                this.motionZ = (this.randomMotionVecZ * this.randomMotionSpeed);
            }
            this.renderYawOffset += (-(float) MathHelper.atan2(this.motionX, this.motionZ) * 57.295776F - this.renderYawOffset) * 0.1F;
            this.rotationYaw = this.renderYawOffset;
        } else if (!this.world.isRemote) {
            this.motionX = 0.0D;
            this.motionZ = 0.0D;
            this.motionY *= 0.9800000190734863D;
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

    @Override
    public int getMaxSpawnedInChunk() {
        return 5;
    }

    public void setMovementVector(float randomMotionVecXIn, float randomMotionVecYIn, float randomMotionVecZIn) {
        this.randomMotionVecX = randomMotionVecXIn;
        this.randomMotionVecY = randomMotionVecYIn;
        this.randomMotionVecZ = randomMotionVecZIn;
    }

    public boolean hasMovementVector() {
        return (this.randomMotionVecX != 0.0F) || (this.randomMotionVecY != 0.0F) || (this.randomMotionVecZ != 0.0F);
    }

    public static class AIMoveRandom extends EntityAIBase {
        private final EntityMobA mobA;

        public AIMoveRandom(EntityMobA p_i45859_1_) {
            this.mobA = p_i45859_1_;
        }

        public void updateTask() {
            int i = this.mobA.getIdleTime();
            if (i > 100) {
                this.mobA.setMovementVector(0.0F, 0.0F, 0.0F);
            } else if ((this.mobA.getRNG().nextInt(50) == 0) || (!this.mobA.inWater) || (!this.mobA.hasMovementVector())) {
                float f = this.mobA.getRNG().nextFloat() * 6.2831855F;
                float f1 = MathHelper.cos(f) * 0.2F;
                float f2 = -0.1F + this.mobA.getRNG().nextFloat() * 0.2F;
                float f3 = MathHelper.cos(f) * 0.2F;
                this.mobA.setMovementVector(f1, f2, f3);
            }
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        @Override
        public boolean shouldExecute() {
            return true;
        }
    }

}
