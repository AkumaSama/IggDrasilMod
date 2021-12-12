package fr.augma.idm.client.gui.iggdrasil.classe.button;

import fr.augma.idm.util.ClassManager;
import fr.augma.idm.util.EnumClasse;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiButtonClasse extends GuiButton {
	
	public ResourceLocation BUTTON;
	private ClassManager classe;

	public GuiButtonClasse(int x, int y, int widthIn, int heightIn, EnumClasse classe) {
		super(0, x, y, widthIn, heightIn, "");
		this.BUTTON = classe.getButton();
		this.classe = ClassManager.getClasseFromString(classe);
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if(this.visible) {
        	//boolean mouseHover = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            mc.getTextureManager().bindTexture(BUTTON);
            Gui.drawModalRectWithCustomSizedTexture(this.x, this.y, 0.0F, 0.0F, this.width, this.height, this.width, this.height);
        }
	}
	
	public ClassManager getClasse() {
		return this.classe;
	}
}
