package me.sub.common.items;

import me.sub.Con17MobA;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class ItemTongue extends ItemFood {

    public ItemTongue() {
        super(7,false);
        setUnlocalizedName("a_tongue");
        setRegistryName(Con17MobA.MOD_ID, "a_tongue");
    }

    private Random RANDOM = new Random();

    public void makeItemEffects(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        int struct[] = new int[4];
        struct[0] = RANDOM.nextInt(10);
        struct[1] = RANDOM.nextInt(10);
        struct[2] = RANDOM.nextInt(10);
        struct[3] = RANDOM.nextInt(10);
        stack.getTagCompound().setIntArray("effects", struct);

    }
    
    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        if (!player.world.isRemote) {
            makeItemEffects(stack);
            if(stack.hasTagCompound() && stack.getTagCompound().hasKey("effects"))
                for (int i = 0; i < stack.getTagCompound().getIntArray("effects").length; i++) {

                    if (itemRand.nextBoolean()) {
                        player.sendStatusMessage(new TextComponentString("LUCKY!"), true);
                        if (stack.getTagCompound().getIntArray("effects")[i] == 0) {
                            player.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 600));
                        }
                        if (stack.getTagCompound().getIntArray("effects")[i] == 1) {
                            player.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 600));
                        }
                        if (stack.getTagCompound().getIntArray("effects")[i] == 2) {
                            player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 600));
                        }
                        if (stack.getTagCompound().getIntArray("effects")[i] == 3) {
                            player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 600));
                        }
                        if (stack.getTagCompound().getIntArray("effects")[i] == 4) {
                            player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 600));
                        }
                        if (stack.getTagCompound().getIntArray("effects")[i] == 5) {
                            player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 600));
                        }
                        if (stack.getTagCompound().getIntArray("effects")[i] == 6) {
                            player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 600));
                        }
                        if (stack.getTagCompound().getIntArray("effects")[i] == 7) {
                            player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 600));
                        }
                        if (stack.getTagCompound().getIntArray("effects")[i] == 8) {
                            player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 600));
                        }
                        if (stack.getTagCompound().getIntArray("effects")[i] == 9) {
                            player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 600));
                        }
                    } else {
                        player.sendStatusMessage(new TextComponentString("UNLUCKY!"), true);
                        if (stack.getTagCompound().getIntArray("effects")[i] == 0) {
                            player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 600));
                        }
                        if (stack.getTagCompound().getIntArray("effects")[i] == 1) {
                            player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 600));
                        }
                        if (stack.getTagCompound().getIntArray("effects")[i] == 2) {
                            player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 600));
                        }
                        if (stack.getTagCompound().getIntArray("effects")[i] == 3) {
                            player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 600));
                        }
                        if (stack.getTagCompound().getIntArray("effects")[i] == 4) {
                            player.addPotionEffect(new PotionEffect(MobEffects.POISON, 600));
                        }
                        if (stack.getTagCompound().getIntArray("effects")[i] == 5) {
                            player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 600));
                        }
                        if (stack.getTagCompound().getIntArray("effects")[i] == 6) {
                            player.addPotionEffect(new PotionEffect(MobEffects.WITHER, 600));
                        }
                        if (stack.getTagCompound().getIntArray("effects")[i] == 7) {
                            player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 600));
                        }
                        if (stack.getTagCompound().getIntArray("effects")[i] == 8) {
                            player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 600));
                        }
                        if (stack.getTagCompound().getIntArray("effects")[i] == 9) {
                            player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 600));
                        }
            }
                }

        }
        super.onFoodEaten(stack, worldIn, player);
    }

}


