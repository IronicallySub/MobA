package me.sub.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
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
                hit.moveToBlockPosAndAngles(thrower.getPosition(), hit.rotationYaw, hit.rotationPitch);
            }
        }
    }
}
