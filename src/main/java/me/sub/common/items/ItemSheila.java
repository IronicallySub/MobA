package me.sub.common.items;

import me.sub.Con17MobA;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemSheila extends ItemShield {
    public ItemSheila() {
        super();
        setRegistryName(Con17MobA.MOD_ID, "a_shield_plate");
        setUnlocalizedName("a_shield_plate");
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return "Shield!";
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if (isSelected) {
            if (entityIn instanceof EntityLivingBase) {
                EntityLivingBase livingBase = (EntityLivingBase) entityIn;
                if (livingBase.isHandActive() && livingBase.getActiveItemStack() == stack)
                    livingBase.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE));
            }
        }
    }
}
