package me.sub.common.items;

import me.sub.Con17MobA;
import me.sub.common.entity.EntityFireCharge;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemSheila extends ItemShield {

    private int timer;

    public ItemSheila() {
        super();
        setRegistryName(Con17MobA.MOD_ID, "a_shield_plate");
        setUnlocalizedName("a_shield_plate");
        timer = 0;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return "Plated Shield!";
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

        if (isSelected) {
            if (entityIn instanceof EntityLivingBase) {
                EntityLivingBase livingBase = (EntityLivingBase) entityIn;

                if (timer > 100 && livingBase.isHandActive()) {
                    if (!worldIn.isRemote) {
                        EntityFireCharge ball = new EntityFireCharge(worldIn, livingBase);
                        ball.shoot(livingBase, livingBase.rotationPitch, livingBase.rotationYaw, 0.0F, 1.5F, 1.0F);
                        worldIn.spawnEntity(ball);
                    }
                    timer = 0;
                }

                if (livingBase.isHandActive() && livingBase.getActiveItemStack() == stack) {
                    livingBase.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 100, 1, true, false));
                }

                if (livingBase instanceof EntityPlayer && livingBase.isSneaking()) {
                    EntityPlayer player = (EntityPlayer) livingBase;
                    player.sendStatusMessage(new TextComponentString("Charge time: " + timer), true);
                }


                if (entityIn.isSneaking()) {
                    timer++;
                } else {
                    timer = 0;
                }
            }
        }
    }
}
