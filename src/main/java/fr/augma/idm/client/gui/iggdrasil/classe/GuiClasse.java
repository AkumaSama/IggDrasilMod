package fr.augma.idm.client.gui.iggdrasil.classe;

import java.io.IOException;

import org.lwjgl.input.Mouse;

import fr.augma.idm.IDM;
import fr.augma.idm.capability.PlayerDataCapProvider;
import fr.augma.idm.client.gui.iggdrasil.classe.button.GuiButtonClasse;
import fr.augma.idm.client.gui.iggdrasil.classe.button.GuiButtonSelectClasse;
import fr.augma.idm.packet.SelectClassPacket;
import fr.augma.idm.util.ClassManager;
import fr.augma.idm.util.EnumClasse;
import fr.augma.idm.util.IDMReferences;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

public class GuiClasse extends GuiScreen {
	private static ResourceLocation TEXTURE = IDMReferences.texture.CLASSE;
	private static ResourceLocation DESC_GUERRIER = IDMReferences.texture.CLASSE_GUERRIER_DESC, DESC_ARCHER = IDMReferences.texture.CLASSE_ARCHER_DESC, DESC_ASSASSIN = IDMReferences.texture.CLASSE_ASSASSIN_DESC;
	private int TEXTURE_WIDTH, TEXTURE_HEIGHT;
	private ClassManager selectedClasse = null;
	private boolean bypass = false, GrabbedMouse = true;
	
	public GuiClasse() {
		this.TEXTURE_WIDTH = 300;
		this.TEXTURE_HEIGHT = 220;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		int posx = (this.width - this.TEXTURE_WIDTH) / 2, posy = (this.height - this.TEXTURE_HEIGHT) / 2;
		mc.getTextureManager().bindTexture(TEXTURE);
		GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		drawModalRectWithCustomSizedTexture(posx, posy, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);
		GlStateManager.disableBlend();
		super.drawScreen(mouseX, mouseY, partialTicks);
		if(this.selectedClasse != null) {
			if(this.selectedClasse.getClassName().equalsIgnoreCase(EnumClasse.Guerrier.name())) {
				mc.getTextureManager().bindTexture(DESC_GUERRIER);
			} else if(this.selectedClasse.getClassName().equalsIgnoreCase(EnumClasse.Archer.name())) {
				mc.getTextureManager().bindTexture(DESC_ARCHER);
			} else if(this.selectedClasse.getClassName().equalsIgnoreCase(EnumClasse.Assassin.name())) {
				mc.getTextureManager().bindTexture(DESC_ASSASSIN);
			}
			drawModalRectWithCustomSizedTexture(posx, posy, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);
		}
		
		if(GrabbedMouse) {
			Mouse.setGrabbed(false);
			GrabbedMouse = !GrabbedMouse;
		}
	}
	
	@Override
	public void initGui() {
		super.initGui();
		int posx = (this.width - this.TEXTURE_WIDTH) / 2, posy = (this.height - this.TEXTURE_HEIGHT) / 2;
		this.buttonList.clear();
		this.buttonList.add(new GuiButtonClasse(posx + 75, posy + 55, 30, 30, EnumClasse.Archer));
		this.buttonList.add(new GuiButtonClasse(posx + 75, posy + 90, 30, 30, EnumClasse.Guerrier));
		this.buttonList.add(new GuiButtonClasse(posx + 75, posy + 125, 30, 30, EnumClasse.Assassin));
		this.buttonList.add(new GuiButtonSelectClasse(posx + (this.TEXTURE_WIDTH / 2) + 33, posy + 180, 70, 20));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(PlayerDataCapProvider.get(Minecraft.getMinecraft().player).getClasse() != null) {
			this.bypass = true;
			mc.displayGuiScreen(null);
			mc.player.sendMessage(new TextComponentString("Vous avez déjà une classe !"));
		}
		if(button instanceof GuiButtonClasse) {
			this.selectedClasse = ((GuiButtonClasse) button).getClasse();
		} else if(button instanceof GuiButtonSelectClasse && this.selectedClasse != null) {
			IDM.network.sendToServer(new SelectClassPacket(this.selectedClasse));
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {}
	
	@Override
	public void onGuiClosed() {
		if(this.selectedClasse == null || this.bypass) mc.displayGuiScreen(new GuiClasse());
	}
}
