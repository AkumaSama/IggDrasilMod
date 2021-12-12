package fr.augma.idm.client.gui.iggdrasil.stats;

import java.io.IOException;

import org.lwjgl.input.Mouse;

import fr.augma.idm.capability.IPlayerDataCap;
import fr.augma.idm.capability.PlayerDataCapProvider;
import fr.augma.idm.client.gui.globalbutton.GuiCrossButton;
import fr.augma.idm.util.IDMReferences;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiStats extends GuiScreen {
	
	private static ResourceLocation TEXTURE = IDMReferences.texture.STATS;
	private int TEXTUREWIDTH, TEXTUREHEIGHT;
	private IPlayerDataCap cap;
	private boolean GrabbedMouse = true;

	
	public GuiStats() {
		this.TEXTUREWIDTH = 400;
		this.TEXTUREHEIGHT = 350;
		this.cap = PlayerDataCapProvider.get(Minecraft.getMinecraft().player);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		int POSX = (this.width / 2) - this.TEXTUREWIDTH / 2;
		int POSY = (this.height / 2) - this.TEXTUREHEIGHT / 2;
		GlStateManager.color(1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(TEXTURE);
		GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		drawModalRectWithCustomSizedTexture(POSX, POSY, 0, 0, this.TEXTUREWIDTH, this.TEXTUREHEIGHT, this.TEXTUREWIDTH, this.TEXTUREHEIGHT);
		GlStateManager.disableBlend();
		int POSXSTATS = POSX + 150;
		int POSYSTATS = POSY + 150;
		drawTexts(POSXSTATS, POSYSTATS);
		super.drawScreen(mouseX, mouseY, partialTicks);
		if(GrabbedMouse) {
			Mouse.setGrabbed(false);
			GrabbedMouse = !GrabbedMouse;
		}
	}
	
	@Override
	public void initGui() {
		this.buttonList.clear();
		int POSX = (this.width / 2) - this.TEXTUREWIDTH / 2;
		int POSY = (this.height / 2) - this.TEXTUREHEIGHT / 2;
		drawButtons(POSX, POSY);
		super.initGui();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch(button.id) {
		case 10:
			mc.displayGuiScreen(null);
			break;
		}
	}

	public void drawButtons(int posx, int posy) {
		this.buttonList.add(new GuiCrossButton(posx + 350, posy + 80, 23, 23));
	}

	public void drawTexts(int posxtext, int posy) {
		mc.fontRenderer.drawStringWithShadow(this.cap.getClasse().getClassName(), posxtext - 40, posy + 7, 0xffffff);
		mc.fontRenderer.drawStringWithShadow(Integer.toString(mc.player.experienceLevel), posxtext - 40, posy + 20, 0xffffff);
		mc.fontRenderer.drawStringWithShadow(Integer.toString(this.cap.getAvailablePoint()), posxtext - 4, posy + 31, 0xffffff);
		mc.fontRenderer.drawStringWithShadow(Float.toString(mc.player.experience * 100f) + "%", posxtext - 59, posy + 41, 0xffffff);
		mc.fontRenderer.drawStringWithShadow("Soon ...", posxtext - 23, posy + 52, 0xffffff);
		
		int offsetCol = 175;
		mc.fontRenderer.drawStringWithShadow(Integer.toString(this.cap.getVitality()), posxtext + offsetCol - 30, posy + 8, 0xffffff);
		mc.fontRenderer.drawStringWithShadow(Integer.toString(this.cap.getAgility()), posxtext + offsetCol - 38, posy + 20, 0xffffff);
		mc.fontRenderer.drawStringWithShadow(Integer.toString(this.cap.getChance()), posxtext + offsetCol - 37, posy + 32, 0xffffff);
		mc.fontRenderer.drawStringWithShadow(Integer.toString(this.cap.getStrength()), posxtext + offsetCol - 45, posy + 42, 0xffffff);
		mc.fontRenderer.drawStringWithShadow(Integer.toString(this.cap.getIntelligence()), posxtext + offsetCol - 11, posy + 53, 0xffffff);
		mc.fontRenderer.drawStringWithShadow(Integer.toString(this.cap.getWisdom()), posxtext + offsetCol - 38, posy + 63, 0xffffff);
	}
}
