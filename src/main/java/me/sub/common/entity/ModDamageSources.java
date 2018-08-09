package me.sub.common.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class ModDamageSources extends DamageSource {

    public static ModDamageSources BITE = new ModDamageSources(" was bitten to death....");
    public static ModDamageSources TOO_HOT = new ModDamageSources(" held a hot item too long...");


    public ModDamageSources(String damageTypeIn) {
        super(damageTypeIn);
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
        return new TextComponentString(entityLivingBaseIn.getName() + damageType);
    }
}
