package me.sub.common.items;

import me.sub.Con17MobA;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemTougueSword extends ItemSword {

    public ItemTougueSword(ToolMaterial material) {
        super(material);
        setRegistryName(Con17MobA.MOD_ID, "t_sword");
        setUnlocalizedName("t_sword");
    }

    @Override
    public int getItemEnchantability() {
        return 60;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }
}
