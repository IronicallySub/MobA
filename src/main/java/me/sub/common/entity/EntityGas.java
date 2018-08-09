package me.sub.common.entity;

import me.sub.common.AObjects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityGas extends EntityThrowable {

    Entity thrower;

    public EntityGas(World worldIn) {
        super(worldIn);
    }

    public EntityGas(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public EntityGas(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
        thrower = throwerIn;
        this.shoot(thrower, thrower.rotationPitch, thrower.rotationYaw, 0, 2F, 0);
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     *
     * @param result
     */
    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.entityHit != null) {
            Entity hit = result.entityHit;
            if (thrower != null) {
                if (hit instanceof EntityLiving) {
                    EntityLiving living = (EntityLiving) hit;
                    living.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 800, 0));
                    living.addPotionEffect(new PotionEffect(AObjects.FREEZE, 800));
                    living.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 800));
                    living.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 800));
                    living.attackEntityFrom(DamageSource.MAGIC, 1);
                }
            }
        }
    }


    @Override
    protected float getGravityVelocity() {
        return 0.000F;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if(this.motionX + this.motionY + this.motionZ == 0 || this.ticksExisted >= 300) {
            this.setDead();
        }
    }
}
