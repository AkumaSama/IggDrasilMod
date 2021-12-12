package fr.augma.idm.client.gui.globalbutton;


import fr.augma.idm.util.IDMReferences;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiCrossButton extends GuiButton {
	
	public static final ResourceLocation BUTTON = IDMReferences.texture.CROSSBUTTON;
	public static final ResourceLocation BUTTON_HOVER = IDMReferences.texture.CROSSBUTTONHOVER;

	public GuiCrossButton(int x, int y, int width, int height) {
		super(10, x, y, width, height, "");
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
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            Gui.drawModalRectWithCustomSizedTexture(this.x, this.y, 0.0F, 0.0F, this.width, this.height, this.width, this.height);
            GlStateManager.disableBlend();
        }
	}
}
