package fr.augma.idm.util;

import net.minecraft.util.ResourceLocation;

public enum EnumClasse {
	Archer(IDMReferences.texture.CLASSE_ARCHER),
	Guerrier(IDMReferences.texture.CLASSE_GUERRIER),
	Assassin(IDMReferences.texture.CLASSE_ASSASSIN);
	
	
	ResourceLocation BUTTION;
	
	private EnumClasse(ResourceLocation button) {
		this.BUTTION = button;
	}
	
	public ResourceLocation getButton() {
		return this.BUTTION;
	}
}
