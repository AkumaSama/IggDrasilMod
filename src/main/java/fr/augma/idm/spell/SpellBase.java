package fr.augma.idm.spell;

import net.minecraft.entity.player.EntityPlayer;

public abstract class SpellBase {
	
	private String spellName;
	private Long cooldown;
	private int timeEffect;
	
	public SpellBase(String spellName, Long cooldown, int timeEffect) {
		this.spellName = spellName;
		this.cooldown = cooldown;
		this.timeEffect = timeEffect; 
	}
	
	public String getSpellName() {
		return this.spellName;
	}
	
	public Long getCooldown() {
		return this.cooldown;
	}
	
	public int getTimeEffect() {
		return this.timeEffect;
	}
	
	public abstract void activateEffect(EntityPlayer player);
}
