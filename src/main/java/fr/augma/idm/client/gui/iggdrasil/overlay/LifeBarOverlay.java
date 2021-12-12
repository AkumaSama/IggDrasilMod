package fr.augma.idm.client.gui.iggdrasil.overlay;

import java.text.DecimalFormat;

import org.lwjgl.opengl.GL11;

import fr.augma.idm.client.config.ConfigMod;
import fr.augma.idm.util.IDMReferences;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LifeBarOverlay extends Gui {
	
	private final ResourceLocation bar = new ResourceLocation(IDMReferences.MODID, "textures/gui/life_bar.png");
	private Minecraft mc;
	
	public LifeBarOverlay() {
		this.mc = Minecraft.getMinecraft();
	}
	
	@SubscribeEvent
	public void onRenderOverlay(RenderGameOverlayEvent.Pre e) {
		if(e.getType() == ElementType.TEXT) {
			EntityPlayer player = mc.player;
			GL11.glPushMatrix();
			GL11.glScalef(1.1F, 1.1F, 1.1F);
			drawString(mc.fontRenderer, player.getName(), 70, 9, 0xffffff);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(3, 3, 3);
			drawCenteredString(mc.fontRenderer, player.experienceLevel + "", 13, 7, 0xffffff);
			GL11.glPopMatrix();
			mc.renderEngine.bindTexture(bar);
			GL11.glPushMatrix();
			GL11.glScalef(0.8F, 0.8F, 0.8F);
			GlStateManager.enableBlend();
	        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			drawTexturedModalRect(1, 1, 0, 0, 256, 78);
			GlStateManager.disableBlend();
			int scale = (int) ((player.getHealth() * 168F) / player.getMaxHealth());
			drawTexturedModalRect(81, 24, 81, 82, scale, 60);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.1F, 1.1F, 1.1F);
			String lifeInPercentage;
			if(ConfigMod.lifeType) {
				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(3);
				lifeInPercentage = df.format((player.getHealth() * 100) / player.getMaxHealth()) + " %";
			} else {
				lifeInPercentage = (int) player.getHealth() + "/" + (int) player.getMaxHealth();
			}
			drawString(mc.fontRenderer, lifeInPercentage, 110, 28, 0xffffff);
			GL11.glPopMatrix();
		} else if(e.getType() == ElementType.HEALTH) {
			e.setCanceled(true);
		}
	}

}
