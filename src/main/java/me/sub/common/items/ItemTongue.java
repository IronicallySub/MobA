package me.sub.common.items;

import me.sub.Con17MobA;
import net.minecraft.item.ItemFood;

public class ItemTongue extends ItemFood {

    public ItemTongue() {
        super(7, 6, false);
        setUnlocalizedName("a_tongue");
        setRegistryName(Con17MobA.MOD_ID, "a_tongue");
    }

}
