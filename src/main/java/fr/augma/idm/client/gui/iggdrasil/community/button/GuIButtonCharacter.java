package fr.augma.idm.client.gui.iggdrasil.community.button;

import org.lwjgl.opengl.GL11;

import fr.augma.idm.util.IDMReferences;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuIButtonCharacter extends GuiButton {
	private static final ResourceLocation BUTTON = IDMReferences.texture.CHARACTERBUTTON;
	private static final ResourceLocation BUTTON_HOVER = IDMReferences.texture.CHARACTERBUTTONHOVER;
	
	public GuIButtonCharacter(int buttonId, int x, int y, int width, int height) {
		super(buttonId, x, y, width, height, "");
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if(this.visible) {
        	boolean mouseHover = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            if(mouseHover) {
                mc.getTextureManager().bindTexture(BUTTON_HOVER);
            } else {
                mc.getTextureManager().bindTexture(BUTTON);
            }
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            Gui.drawModalRectWithCustomSizedTexture(this.x, this.y, 0.0F, 0.0F, this.width, this.height, this.width, this.height);
        }
	}
}
