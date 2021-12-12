package fr.augma.idm.client.gui.iggdrasil.community;

import java.io.IOException;

import fr.augma.idm.client.gui.globalbutton.GuiCrossButton;
import fr.augma.idm.client.gui.iggdrasil.community.button.GuIButtonCharacter;
import fr.augma.idm.client.gui.iggdrasil.community.button.GuiButtonGuild;
import fr.augma.idm.client.gui.iggdrasil.community.button.GuiButtonMount;
import fr.augma.idm.client.gui.iggdrasil.community.button.GuiButtonSkill;
import fr.augma.idm.client.gui.iggdrasil.skill.GuiSkill;
import fr.augma.idm.client.gui.iggdrasil.stats.GuiStats;
import fr.augma.idm.util.IDMReferences;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiCommunity extends GuiScreen {
	public ResourceLocation TEXTURE = IDMReferences.texture.COMMUNITY;
	public int TEXTUREWIDTH, TEXTUREHEIGHT;
	
	public GuiCommunity() {
		this.TEXTUREWIDTH = 350;
		this.TEXTUREHEIGHT = 220;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		int POSX = (this.width / 2) - this.TEXTUREWIDTH / 2;
		int POSY = (this.height / 2) - this.TEXTUREHEIGHT / 2;
		mc.getTextureManager().bindTexture(TEXTURE);
		GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		drawModalRectWithCustomSizedTexture(POSX, POSY, 0, 0, this.TEXTUREWIDTH, this.TEXTUREHEIGHT, this.TEXTUREWIDTH, this.TEXTUREHEIGHT);
		GlStateManager.disableBlend();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		int POSX = (this.width / 2) - this.TEXTUREWIDTH / 2;
		int POSY = (this.height / 2) - this.TEXTUREHEIGHT / 2;
		this.buttonList.clear();
		this.buttonList.add(new GuiButtonSkill(0, POSX + 55, POSY + 75, 65, 35));
		this.buttonList.add(new GuIButtonCharacter(1, POSX + 135, POSY + 75, 65, 35));
		this.buttonList.add(new GuiButtonMount(2, POSX + 215, POSY + 75, 65, 35));
		this.buttonList.add(new GuiButtonGuild(3, POSX + 135, POSY + 125, 65, 35));
		this.buttonList.add(new GuiCrossButton(POSX + 305, POSY + 15, 24, 24));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch(button.id) {
		case 0:
			mc.displayGuiScreen(new GuiSkill());
			break;
		case 1:
			mc.displayGuiScreen(new GuiStats());
			break;
		case 2:
			break;
		case 3:
			break;
		case 10:
			mc.displayGuiScreen(null);
			break;
		}
	}
}
