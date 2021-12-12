package fr.augma.idm.client.gui.iggdrasil.classe.button;

import org.lwjgl.opengl.GL11;

import fr.augma.idm.util.IDMReferences;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiButtonSelectClasse extends GuiButton {
	
	public static ResourceLocation BUTTON = IDMReferences.texture.CLASSE_SELECT, BUTTON_HOVER = IDMReferences.texture.CLASSE_SELECT_HOVER;
	
	public GuiButtonSelectClasse(int x, int y, int widthIn, int heightIn) {
		super(0, x, y, widthIn, heightIn, "");
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
