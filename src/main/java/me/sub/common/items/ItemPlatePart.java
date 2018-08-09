package me.sub.common.items;

import me.sub.Con17MobA;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemPlatePart extends Item {

    public ItemPlatePart() {
        setRegistryName(Con17MobA.MOD_ID, "plate_part");
        setUnlocalizedName("plate_part");
        setCreativeTab(CreativeTabs.MATERIALS);
    }
}
