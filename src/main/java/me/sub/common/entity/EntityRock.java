package me.sub.common.entity;

import me.sub.common.AObjects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityRock extends EntityThrowable {

    Entity thrower;

    public EntityRock(World worldIn) {
        super(worldIn);
    }

    public EntityRock(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public EntityRock(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
        thrower = throwerIn;
        this.shoot(thrower, thrower.rotationPitch, thrower.rotationYaw, 0, 0.5F, 0);
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
                hit.attackEntityFrom(AObjects.ROCK, 2F);
                setDead();
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
        if (this.motionX + this.motionY + this.motionZ == 0 || this.ticksExisted >= 100) {
            this.setDead();
        }
    }
}
