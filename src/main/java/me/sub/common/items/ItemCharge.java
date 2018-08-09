package me.sub.common.items;

import me.sub.Con17MobA;
import me.sub.common.entity.EntityFireCharge;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemCharge extends Item {

    public ItemCharge() {
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.MISC);
        setRegistryName(Con17MobA.MOD_ID, "charge");
        setUnlocalizedName("charge");
    }

    /**
     * Called when the equipped item is right clicked.
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (!playerIn.capabilities.isCreativeMode) {
            itemstack.shrink(1);
        }

        if (!worldIn.isRemote) {
            EntityFireCharge ball = new EntityFireCharge(worldIn, playerIn);
            ball.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
            worldIn.spawnEntity(ball);
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }
}
