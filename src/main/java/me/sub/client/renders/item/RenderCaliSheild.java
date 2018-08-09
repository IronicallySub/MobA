package me.sub.client.renders.item;

import me.sub.Con17MobA;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelShield;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderCaliSheild extends TileEntityItemStackRenderer {

    private final ModelShield modelShield = new ModelShield();
    private ResourceLocation texture = new ResourceLocation(Con17MobA.MOD_ID, "textures/entity/plated_shield.png");


    @Override
    public void renderByItem(ItemStack itemStackIn) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.0F, -1.0F, -1.0F);
            Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
            this.modelShield.render();
            GlStateManager.popMatrix();
    }
}
