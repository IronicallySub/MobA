package me.sub.common.potions;

import me.sub.Con17MobA;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

public class EffectFrozen extends Potion {

    public EffectFrozen() {
        super(true, 8);
        setRegistryName(Con17MobA.MOD_ID, "freeze");
        setPotionName("mob.freeze.name");
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier){
        entity.setVelocity(0D, 0D, 0D);
        entity.velocityChanged = true;
        entity.moveVertical =0;
        entity.moveStrafing =0;
        entity.motionX =0;
        entity.motionZ=0;
    }

    @Override
    public int getStatusIconIndex() {
        return super.getStatusIconIndex();
    }
}
