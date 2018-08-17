package lucraft.mods.lucraftcore.util.helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@EventBusSubscriber(value = Side.CLIENT)
public class LCRenderHelper {

	public static Minecraft mc = Minecraft.getMinecraft();

	public static float renderTick;

	@SubscribeEvent
	public static void renderTick(TickEvent.RenderTickEvent e) {
		renderTick = e.renderTickTime;
	}

	public static void drawStringWithOutline(String string, int posX, int posY, int fontColor, int outlineColor) {
		mc.fontRenderer.drawString(string, posX + 1, posY, outlineColor);
		mc.fontRenderer.drawString(string, posX - 1, posY, outlineColor);
		mc.fontRenderer.drawString(string, posX, posY + 1, outlineColor);
		mc.fontRenderer.drawString(string, posX, posY - 1, outlineColor);

		mc.fontRenderer.drawString(string, posX, posY, fontColor);
	}

	public static void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
		float f = (float) (startColor >> 24 & 255) / 255.0F;
		float f1 = (float) (startColor >> 16 & 255) / 255.0F;
		float f2 = (float) (startColor >> 8 & 255) / 255.0F;
		float f3 = (float) (startColor & 255) / 255.0F;
		float f4 = (float) (endColor >> 24 & 255) / 255.0F;
		float f5 = (float) (endColor >> 16 & 255) / 255.0F;
		float f6 = (float) (endColor >> 8 & 255) / 255.0F;
		float f7 = (float) (endColor & 255) / 255.0F;
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.shadeModel(7425);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldrenderer = tessellator.getBuffer();
		worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
		worldrenderer.pos((double) right, (double) top, 300).color(f1, f2, f3, f).endVertex();
		worldrenderer.pos((double) left, (double) top, 300).color(f1, f2, f3, f).endVertex();
		worldrenderer.pos((double) left, (double) bottom, 300).color(f5, f6, f7, f4).endVertex();
		worldrenderer.pos((double) right, (double) bottom, 300).color(f5, f6, f7, f4).endVertex();
		tessellator.draw();
		GlStateManager.shadeModel(7424);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}

	public static void drawGradientRect(int left, int top, int right, int bottom, Vec3i color1, float alpha1, Vec3i color2, float alpha2) {
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.shadeModel(7425);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldrenderer = tessellator.getBuffer();
		worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
		worldrenderer.pos((double) right, (double) top, 300).color((float) color1.getX(), (float) color1.getY(), (float) color1.getZ(), alpha1).endVertex();
		worldrenderer.pos((double) left, (double) top, 300).color((float) color1.getX(), (float) color1.getY(), (float) color1.getZ(), alpha1).endVertex();
		worldrenderer.pos((double) left, (double) bottom, 300).color((float) color2.getX(), (float) color2.getY(), (float) color2.getZ(), alpha2).endVertex();
		worldrenderer.pos((double) right, (double) bottom, 300).color((float) color2.getX(), (float) color2.getY(), (float) color2.getZ(), alpha2).endVertex();
		tessellator.draw();
		GlStateManager.shadeModel(7424);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}

	public static void drawRect(int left, int top, int right, int bottom, float red, float green, float blue, float alpha) {
		if (left < right) {
			int i = left;
			left = right;
			right = i;
		}

		if (top < bottom) {
			int j = top;
			top = bottom;
			bottom = j;
		}

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldrenderer = tessellator.getBuffer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(red, green, blue, alpha);
		worldrenderer.begin(7, DefaultVertexFormats.POSITION);
		worldrenderer.pos((double) left, (double) bottom, 0.0D).endVertex();
		worldrenderer.pos((double) right, (double) bottom, 0.0D).endVertex();
		worldrenderer.pos((double) right, (double) top, 0.0D).endVertex();
		worldrenderer.pos((double) left, (double) top, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}


	public static void drawStringList(List<String> list, int posX, int posY, boolean drawBackground) {
		drawStringList(list, posX, posY, 10, drawBackground);
	}

	public static void drawStringList(List<String> list, int posX, int posY, int differencePerLine, boolean drawBackground) {
		GlStateManager.disableRescaleNormal();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
		int longestString = 0;

		for (int i = 0; i < list.size(); i++) {
			if (longestString < mc.fontRenderer.getStringWidth(list.get(i))) {
				longestString = mc.fontRenderer.getStringWidth(list.get(i));
			}
		}

		if (drawBackground) {
			int color1 = -267386864;
			int color2 = 1347420415;
			int color3 = (color2 & 16711422) >> 1 | color2 & -16777216;

			drawGradientRect(posX - 3, posY - 3, posX + longestString + 3, posY + (list.size() * differencePerLine) + 3, color1, color1);

			drawGradientRect(posX - 4, posY - 4, posX + longestString + 4, posY - 3, color2, color2);
			drawGradientRect(posX - 4, posY - 3, posX + -3, posY + (list.size() * differencePerLine) + 3, color2, color3);
			drawGradientRect(posX + 3 + longestString, posY - 3, posX + 4 + longestString, posY + (list.size() * differencePerLine) + 3, color2, color3);
			drawGradientRect(posX - 4, posY + (list.size() * differencePerLine) + 3, posX + longestString + 4, posY + (list.size() * differencePerLine) + 4, color3, color3);
		}

		for (int i = 0; i < list.size(); i++) {
			mc.fontRenderer.drawStringWithShadow(list.get(i), posX, posY + (i * differencePerLine), -1);
		}

		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
		RenderHelper.enableStandardItemLighting();
		GlStateManager.enableRescaleNormal();
	}

	public static void renderFire(Minecraft mc, String texture) {
		mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();
		TextureAtlasSprite sprite = mc.getTextureMapBlocks().getTextureExtry(texture);

		GlStateManager.pushMatrix();

		buffer.begin(7, DefaultVertexFormats.POSITION_TEX);

		buffer.pos(0, 0, 0).tex(sprite.getMaxU(), sprite.getMinV()).endVertex();
		buffer.pos(1, 0, 0).tex(sprite.getMinU(), sprite.getMinV()).endVertex();
		buffer.pos(1, 1, 0).tex(sprite.getMinU(), sprite.getMaxV()).endVertex();
		buffer.pos(0, 1, 0).tex(sprite.getMaxU(), sprite.getMaxV()).endVertex();

		buffer.pos(1, 0, 1).tex(sprite.getMaxU(), sprite.getMinV()).endVertex();
		buffer.pos(1, 0, 0).tex(sprite.getMinU(), sprite.getMinV()).endVertex();
		buffer.pos(1, 1, 0).tex(sprite.getMinU(), sprite.getMaxV()).endVertex();
		buffer.pos(1, 1, 1).tex(sprite.getMaxU(), sprite.getMaxV()).endVertex();

		buffer.pos(0, 0, 1).tex(sprite.getMaxU(), sprite.getMinV()).endVertex();
		buffer.pos(1, 0, 1).tex(sprite.getMinU(), sprite.getMinV()).endVertex();
		buffer.pos(1, 1, 1).tex(sprite.getMinU(), sprite.getMaxV()).endVertex();
		buffer.pos(0, 1, 1).tex(sprite.getMaxU(), sprite.getMaxV()).endVertex();

		buffer.pos(0, 0, 1).tex(sprite.getMaxU(), sprite.getMinV()).endVertex();
		buffer.pos(0, 0, 0).tex(sprite.getMinU(), sprite.getMinV()).endVertex();
		buffer.pos(0, 1, 0).tex(sprite.getMinU(), sprite.getMaxV()).endVertex();
		buffer.pos(0, 1, 1).tex(sprite.getMaxU(), sprite.getMaxV()).endVertex();

		// BOTTOM
		buffer.pos(0, 1, 0).tex(sprite.getMaxU(), sprite.getMinV()).endVertex();
		buffer.pos(1, 1, 0).tex(sprite.getMinU(), sprite.getMinV()).endVertex();
		buffer.pos(1, 1, 1).tex(sprite.getMinU(), sprite.getMaxV()).endVertex();
		buffer.pos(0, 1, 1).tex(sprite.getMaxU(), sprite.getMaxV()).endVertex();

		buffer.pos(0, 1, 0).tex(sprite.getMinU(), sprite.getMinV()).endVertex();
		buffer.pos(1, 1, 0).tex(sprite.getMinU(), sprite.getMaxV()).endVertex();
		buffer.pos(1, 1, 1).tex(sprite.getMaxU(), sprite.getMaxV()).endVertex();
		buffer.pos(0, 1, 1).tex(sprite.getMaxU(), sprite.getMinV()).endVertex();

		buffer.pos(0, 1, 0).tex(sprite.getMinU(), sprite.getMaxV()).endVertex();
		buffer.pos(1, 1, 0).tex(sprite.getMaxU(), sprite.getMaxV()).endVertex();
		buffer.pos(1, 1, 1).tex(sprite.getMaxU(), sprite.getMinV()).endVertex();
		buffer.pos(0, 1, 1).tex(sprite.getMinU(), sprite.getMinV()).endVertex();

		buffer.pos(0, 1, 0).tex(sprite.getMaxU(), sprite.getMaxV()).endVertex();
		buffer.pos(1, 1, 0).tex(sprite.getMaxU(), sprite.getMinV()).endVertex();
		buffer.pos(1, 1, 1).tex(sprite.getMinU(), sprite.getMinV()).endVertex();
		buffer.pos(0, 1, 1).tex(sprite.getMinU(), sprite.getMaxV()).endVertex();

		tessellator.draw();

		GlStateManager.popMatrix();
	}

	private static float lastBrightnessX = OpenGlHelper.lastBrightnessX;
	private static float lastBrightnessY = OpenGlHelper.lastBrightnessY;

	public static void setLightmapTextureCoords(float x, float y) {
		lastBrightnessX = OpenGlHelper.lastBrightnessX;
		lastBrightnessY = OpenGlHelper.lastBrightnessY;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, x, y);
	}

	public static void restoreLightmapTextureCoords() {
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
	}

	public static void setupRenderLightning() {
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GL11.glBlendFunc(770, 32772);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glDepthMask(false);
		GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.blendFunc(770, 1);
		GL11.glAlphaFunc(516, 0.003921569F);

		LCRenderHelper.setLightmapTextureCoords(240, 240);
	}

	public static void finishRenderLightning() {
		LCRenderHelper.restoreLightmapTextureCoords();

		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.enableTexture2D();
		GL11.glDepthMask(true);
		GL11.glBlendFunc(770, 771);
		GlStateManager.popMatrix();
	}

	public static void drawLine(Vec3d start, Vec3d end, float lineWidth, float innerLineWidth, Vec3d color, float alpha) {
		if (start == null || end == null)
			return;

		Tessellator tes = Tessellator.getInstance();
		BufferBuilder wr = tes.getBuffer();

		if (lineWidth > 0) {
			GlStateManager.color((float) color.x, (float) color.y, (float) color.z, alpha);
			GL11.glLineWidth(lineWidth);
			wr.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);
			wr.pos(start.x, start.y, start.z).endVertex();
			wr.pos(end.x, end.y, end.z).endVertex();
			tes.draw();
		}

		if (innerLineWidth > 0) {
			GlStateManager.color(1, 1, 1, MathHelper.clamp(alpha - 0.2F, 0, 1));
			GL11.glLineWidth(innerLineWidth);
			wr.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);
			wr.pos(start.x, start.y, start.z).endVertex();
			wr.pos(end.x, end.y, end.z).endVertex();
			tes.draw();
		}
	}

	public static void drawGlowingLine(Vec3d start, Vec3d end, float thickness, Vec3d color) {
		drawGlowingLine(start, end, thickness, color, 1F);
	}

	public static void drawGlowingLine(Vec3d start, Vec3d end, float thickness, Vec3d color, float alpha) {
		if (start == null || end == null)
			return;

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bb = tessellator.getBuffer();
		int smoothFactor = Minecraft.getMinecraft().gameSettings.ambientOcclusion;
		int layers = 10 + smoothFactor * 20;

		GL11.glPushMatrix();
		start = start.scale(-1D);
		end = end.scale(-1D);
		GlStateManager.translate(-start.x, -start.y, -start.z);
		start = end.subtract(start);
		end = end.subtract(end);

		GL11.glDisable(3553);
		GL11.glDisable(2896);
		GL11.glDisable(2884);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 32772);
		GL11.glAlphaFunc(516, 0.003921569F);
		LCRenderHelper.setLightmapTextureCoords(240, 240);

		{
			double x = end.x - start.x;
			double y = end.y - start.y;
			double z = end.z - start.z;
			double diff = MathHelper.sqrt(x * x + z * z);
			float yaw = (float) (Math.atan2(z, x) * 180.0D / 3.141592653589793D) - 90.0F;
			float pitch = (float) -(Math.atan2(y, diff) * 180.0D / 3.141592653589793D);
			GL11.glRotated(-yaw, 0.0D, 1.0D, 0.0D);
			GL11.glRotated(pitch, 1.0D, 0.0D, 0.0D);
		}

		for (int layer = 0; layer <= layers; layer++) {
			if (layer < layers) {
				GL11.glColor4d(color.x, color.y, color.z, 1.0F / layers / 2.0F);
				GL11.glDepthMask(false);
			} else {
				GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
				GL11.glDepthMask(true);
			}
			double size = thickness + (layer < layers ? layer * (1.25D / layers) : 0.0D);
			double d = (layer < layers ? 1.0D - layer * (1.0D / layers) : 0.0D) * 0.1D;
			double width = 0.0625D * size;
			double height = 0.0625D * size;
			double length = start.distanceTo(end) + d;

			bb.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION);
			bb.pos(-width, height, length).endVertex();
			bb.pos(width, height, length).endVertex();
			bb.pos(width, height, -d).endVertex();
			bb.pos(-width, height, -d).endVertex();
			bb.pos(width, -height, -d).endVertex();
			bb.pos(width, -height, length).endVertex();
			bb.pos(-width, -height, length).endVertex();
			bb.pos(-width, -height, -d).endVertex();
			bb.pos(-width, -height, -d).endVertex();
			bb.pos(-width, -height, length).endVertex();
			bb.pos(-width, height, length).endVertex();
			bb.pos(-width, height, -d).endVertex();
			bb.pos(width, height, length).endVertex();
			bb.pos(width, -height, length).endVertex();
			bb.pos(width, -height, -d).endVertex();
			bb.pos(width, height, -d).endVertex();
			bb.pos(width, -height, length).endVertex();
			bb.pos(width, height, length).endVertex();
			bb.pos(-width, height, length).endVertex();
			bb.pos(-width, -height, length).endVertex();
			bb.pos(width, -height, -d).endVertex();
			bb.pos(width, height, -d).endVertex();
			bb.pos(-width, height, -d).endVertex();
			bb.pos(-width, -height, -d).endVertex();
			tessellator.draw();
		}
		LCRenderHelper.restoreLightmapTextureCoords();
		GL11.glEnable(2896);
		GL11.glEnable(3553);
		GL11.glAlphaFunc(516, 0.1F);
		GL11.glDisable(3042);
		GL11.glPopMatrix();
	}

	public static void drawLightnings(float thickness, Vec3d color, List<Vec3d[]> fragments) {
		setupRenderLightning();
		for (Vec3d[] v : fragments) {
			drawGlowingLine(v[0], v[1], thickness, color);
		}
		finishRenderLightning();
	}

	public static List<Vec3d[]> getRandomLightningCoords(Vec3d start, Vec3d end, Random rand) {
		List<Vec3d> parts = new ArrayList<>();
		int fragments = 3 + rand.nextInt(3);
		Vec3d vec = end.subtract(start).scale(1D / (double) fragments);

		parts.add(start);
		for (int i = 0; i < fragments; i++) {
			Vec3d v = vec.scale(i);
			parts.add(start.addVector(v.x + (rand.nextFloat() - 0.5F) / 1.5D, v.y + (rand.nextFloat() - 0.5F) / 1.5D, v.z + (rand.nextFloat() - 0.5F) / 1.5D));
		}
		parts.add(end);

		List<Vec3d[]> coords = new ArrayList<>();

		for (int i = 0; i < parts.size(); i++) {
			if (i < parts.size() - 1) {
				coords.add(new Vec3d[] { parts.get(i), parts.get(i + 1) });
			}
		}

		return coords;
	}

	public static void drawRandomLightningCoordsInAABB(float thickness, Vec3d color, AxisAlignedBB box, Random rand) {
		int lightningAmount = 3 + rand.nextInt(4);

		for (int i = 0; i < lightningAmount; i++) {
			Vec3d start = new Vec3d(box.minX + rand.nextDouble() * (box.maxX - box.minX), box.minY + rand.nextDouble() * (box.maxY - box.minY), box.minZ + rand.nextDouble() * (box.maxZ - box.minZ));
			Vec3d end = new Vec3d(box.minX + rand.nextDouble() * (box.maxX - box.minX), box.minY + rand.nextDouble() * (box.maxY - box.minY), box.minZ + rand.nextDouble() * (box.maxZ - box.minZ));

			while (start.distanceTo(end) > 0.2F) {
				end = new Vec3d(box.minX + rand.nextDouble() * (box.maxX - box.minX), box.minY + rand.nextDouble() * (box.maxY - box.minY), box.minZ + rand.nextDouble() * (box.maxZ - box.minZ));
			}

			// drawLightnings(thickness, color, getRandomLightningCoords(start,
			// end, rand));
			drawGlowingLine(start, end, thickness, color);
		}
	}

	public static double interpolateValue(double start, double end, double pct) {
		return start + (end - start) * pct;
	}

	public static void renderTiledFluid(int x, int y, int width, float depth, FluidTank fluidStack) {
		if (fluidStack != null && fluidStack.getFluid() != null && fluidStack.getFluid().getFluid() != null) {
			float fluidTankHeight = 60F;
			float o = fluidTankHeight / fluidStack.getCapacity();
			int h = Math.round(o * fluidStack.getFluidAmount());

			int x2 = x;
			int y2 = (int) (y + fluidTankHeight - h);

			TextureAtlasSprite fluidSprite = mc.getTextureMapBlocks().getAtlasSprite(fluidStack.getFluid().getFluid().getStill(fluidStack.getFluid()).toString());
			setColorRGBA(fluidStack.getFluid().getFluid().getColor(fluidStack.getFluid()));
			renderTiledTextureAtlas(x2, y2, width, h, depth, fluidSprite);
		}

	}

	public static void renderTiledTextureAtlas(int x, int y, int width, int height, float depth, TextureAtlasSprite sprite) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bb = tessellator.getBuffer();
		bb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

		putTiledTextureQuads(bb, x, y, width, height, depth, sprite);

		tessellator.draw();
	}

	public static void putTiledTextureQuads(BufferBuilder renderer, int x, int y, int width, int height, float depth, TextureAtlasSprite sprite) {
		float u1 = sprite.getMinU();
		float v1 = sprite.getMinV();

		do {
			int renderHeight = Math.min(sprite.getIconHeight(), height);
			height -= renderHeight;

			float v2 = sprite.getInterpolatedV((16f * renderHeight) / (float) sprite.getIconHeight());

			int x2 = x;
			int width2 = width;
			do {
				int renderWidth = Math.min(sprite.getIconWidth(), width2);
				width2 -= renderWidth;

				float u2 = sprite.getInterpolatedU((16f * renderWidth) / (float) sprite.getIconWidth());

				renderer.pos(x2, y, depth).tex(u1, v1).endVertex();
				renderer.pos(x2, y + renderHeight, depth).tex(u1, v2).endVertex();
				renderer.pos(x2 + renderWidth, y + renderHeight, depth).tex(u2, v2).endVertex();
				renderer.pos(x2 + renderWidth, y, depth).tex(u2, v1).endVertex();

				x2 += renderWidth;
			} while (width2 > 0);

			y += renderHeight;
		} while (height > 0);
	}

	public static void setColorRGBA(int color) {
		float a = (float) alpha(color) / 255.0F;
		float r = (float) red(color) / 255.0F;
		float g = (float) green(color) / 255.0F;
		float b = (float) blue(color) / 255.0F;

		GlStateManager.color(r, g, b, a);
	}

	public static int alpha(int c) {
		return (c >> 24) & 0xFF;
	}

	public static int red(int c) {
		return (c >> 16) & 0xFF;
	}

	public static int green(int c) {
		return (c >> 8) & 0xFF;
	}

	public static int blue(int c) {
		return (c) & 0xFF;
	}

	public static void animX(BufferedImage image, int startX, int startY, int endX, int endY, float progress) {
		for (int x = Math.min(startX, endX); x < Math.max(startX, endX); x++) {
			for (int y = Math.min(startY, endY); y < Math.max(startY, endY); y++) {
				int i = image.getRGB(x, y);
				if (i != 0) {
					boolean v = startX < endX ? (y < startX + progress * (endX - startX)) : (y >= startX + progress * (endX - startX));
					if(!v)
						image.setRGB(x, y, 0);
				}
			}
		}
	}

	public static void animY(BufferedImage image, int startX, int startY, int endX, int endY, float progress) {
		animY(image, startX, startY, endX, endY, progress, false);
	}

	public static void animY(BufferedImage image, int startX, int startY, int endX, int endY, float progress, boolean trans) {
		for (int x = Math.min(startX, endX); x < Math.max(startX, endX); x++) {
			for (int y = Math.min(startY, endY); y < Math.max(startY, endY); y++) {
				int i = image.getRGB(x, y);
				if (i != 0 && Integer.toHexString(i).length() > 2) {
					boolean v = startY < endY ? (y < startY + progress * (endY - startY)) : (y >= startY + progress * (endY - startY));
					if(!v)
						image.setRGB(x, y, 0);
				}

				if (trans && y == (int) (startY + progress * (endY - startY))) {
					Random rand = new Random();
					String red = Integer.toHexString(191 + rand.nextInt(255 - 191));
					String green = Integer.toHexString(rand.nextInt(34));
					String blue = Integer.toHexString(153 + rand.nextInt(210 - 153));
					image.setRGB(x, y, (int) Long.parseLong("ff" + red + green + blue, 16));
				}
			}
		}
	}

	public static void setOpacity(BufferedImage image, int startX, int startY, int endX, int endY, float opacity) {
		for (int x = Math.min(startX, endX); x < Math.max(startX, endX); x++) {
			for (int y = Math.min(startY, endY); y < Math.max(startY, endY); y++) {
				int i = image.getRGB(x, y);

				if(i != 0) {
					if(opacity == 0F)
						image.setRGB(x, y, 0);
					else if(Integer.toHexString(i).length() >= 2){
						String rgb = Integer.toHexString(i).substring(2);
						String newRgb = (Integer.toHexString((int) (opacity * 255))) + rgb;
						image.setRGB(x, y, (int) Long.parseLong(newRgb, 16));
					}
				}
			}
		}
	}

}
