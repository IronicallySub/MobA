package me.sub.common;

import javafx.scene.effect.Effect;
import me.sub.MobA;
import me.sub.proxy.CommonProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

public class EffectFrozen extends Potion {

    public EffectFrozen() {
        super(true, 8);
        setRegistryName(MobA.MOD_ID, "freeze");
        setPotionName("freeze");
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

}
