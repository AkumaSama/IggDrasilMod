package fr.augma.idm.client.gui.iggdrasil.skill;

import java.io.IOException;

import org.lwjgl.input.Mouse;

import fr.augma.idm.IDM;
import fr.augma.idm.capability.IPlayerDataCap;
import fr.augma.idm.capability.PlayerDataCapProvider;
import fr.augma.idm.client.gui.globalbutton.GuiCrossButton;
import fr.augma.idm.client.gui.iggdrasil.skill.button.GuiAddButton;
import fr.augma.idm.packet.SkillUpPacket;
import fr.augma.idm.util.IDMReferences;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

public class GuiSkill extends GuiScreen {
	
	public boolean GrabbedMouse = true;
	private static ResourceLocation TEXTURE = IDMReferences.texture.SKILL;
	private IPlayerDataCap cap;
	public int TEXTUREWIDTH, TEXTUREHEIGHT;
	
	public GuiSkill() {
		this.TEXTUREWIDTH = 290;
		this.TEXTUREHEIGHT = 280;
		
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
		int STATSPOSX = POSX + 160;
		drawStats(STATSPOSX, POSY);
		super.drawScreen(mouseX, mouseY, partialTicks);
		if(GrabbedMouse) {
			Mouse.setGrabbed(false);
			GrabbedMouse = !GrabbedMouse;
		}
	}
	
	@Override
	public void initGui() {
		super.initGui();
		int POSX = (this.width / 2) - this.TEXTUREWIDTH / 2;
		int POSY = (this.height / 2) - this.TEXTUREHEIGHT / 2;
		int BUTTONPOSX = POSX + 190;
		drawButton(BUTTONPOSX, POSY);
	}
	
	public void drawStats(int statsposx, int posy) {
		mc.fontRenderer.drawStringWithShadow(Integer.toString(this.cap.getVitality()), statsposx, posy + 76, 0xffffff);
		if(this.cap.getAgility() == this.cap.getMaxAgility()) {
			mc.fontRenderer.drawStringWithShadow("Maxed", statsposx - 8, posy + 95, 0xffffff);
		} else {
			mc.fontRenderer.drawStringWithShadow(Integer.toString(cap.getAgility()), statsposx, posy + 95, 0xffffff);
		}
		mc.fontRenderer.drawStringWithShadow(Integer.toString(this.cap.getChance()), statsposx, posy + 115, 0xffffff);
		mc.fontRenderer.drawStringWithShadow(Integer.toString(this.cap.getStrength()), statsposx, posy + 135, 0xffffff);
		mc.fontRenderer.drawStringWithShadow(Integer.toString(this.cap.getIntelligence()), statsposx, posy + 155, 0xffffff);
		mc.fontRenderer.drawStringWithShadow(Integer.toString(this.cap.getWisdom()), statsposx, posy + 173, 0xffffff);
		mc.fontRenderer.drawStringWithShadow(Integer.toString(this.cap.getAvailablePoint()), statsposx + 25, posy + 196, 0xffffff);
	}
	
	public void drawButton(int bposx, int posy) {
		int btnsize = 15;
		this.buttonList.clear();
		this.buttonList.add(new GuiAddButton(IDMReferences.VITALITY, bposx, posy + 72, btnsize, btnsize));
		if(this.cap.getAgility() != this.cap.getMaxAgility()) this.buttonList.add(new GuiAddButton(IDMReferences.AGILITY, bposx, posy + 91, btnsize, btnsize));
		this.buttonList.add(new GuiAddButton(IDMReferences.CHANCE, bposx, posy + 111, btnsize, btnsize));
		this.buttonList.add(new GuiAddButton(IDMReferences.STRENGTH, bposx, posy + 131, btnsize, btnsize));
		this.buttonList.add(new GuiAddButton(IDMReferences.INTELLIGENCE, bposx, posy + 151, btnsize, btnsize));
		this.buttonList.add(new GuiAddButton(IDMReferences.WISDOM, bposx, posy + 169, btnsize, btnsize));
		this.buttonList.add(new GuiCrossButton(bposx + 20, posy + 30, 23, 23));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch(button.id) {
		case 10:
			mc.displayGuiScreen(null);
			break;
		case IDMReferences.VITALITY:
		case IDMReferences.AGILITY:
		case IDMReferences.CHANCE:
		case IDMReferences.STRENGTH:
		case IDMReferences.INTELLIGENCE:
		case IDMReferences.WISDOM:
			if(this.cap.getAgility() == this.cap.getMaxAgility() && button.id == IDMReferences.AGILITY) {
				mc.player.sendMessage(new TextComponentString("Agility already maxed"));
				if(this.buttonList.size() == 7) {
					Minecraft.getMinecraft().displayGuiScreen(new GuiSkill());
				}
				break;
			}
			if(this.cap.getAvailablePoint() == 0) {
				mc.player.sendMessage(new TextComponentString("Not enough available point"));
				break;
			}
			
			IDM.network.sendToServer(new SkillUpPacket(button.id));
			break;
		}
	}
}
