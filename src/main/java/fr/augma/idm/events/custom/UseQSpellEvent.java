package fr.augma.idm.events.custom;

import fr.augma.idm.capability.IPlayerDataCap;
import fr.augma.idm.capability.PlayerDataCapProvider;
import fr.augma.idm.spell.SpellBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

public class UseQSpellEvent extends Event {
	
	private final EntityPlayer player;
	private final SpellBase spell;
	private IPlayerDataCap capability;
	private Long QSpellLastUse;
	private boolean canUseSpell;
	private Long remainingTime;
	
	public UseQSpellEvent(EntityPlayer player) {
		this.player = player;
		this.capability = PlayerDataCapProvider.get(this.player);
		this.spell = this.capability.getClasse().getSpell();
		this.QSpellLastUse = this.capability.getQSpellLastUse();
		this.canUseSpell = this.QSpellLastUse == 0L ? true : System.currentTimeMillis() >= this.QSpellLastUse + this.spell.getCooldown();
		this.remainingTime = !this.canUseSpell ?  System.currentTimeMillis() - this.QSpellLastUse : null;
	}
	
	public IPlayerDataCap getPlayerCapability() {
		return this.capability;
	}
	
	public EntityPlayer getPlayer() {
		return this.player;
	}
	
	public SpellBase getSpell() {
		return this.spell;
	}
	
	public Long getQSpellLastUse() {
		return this.QSpellLastUse;
	}
	
	public boolean canUseSpell() {
		return this.canUseSpell;
	}
	
	public float getRemainingTimeInSecond() {
		return (this.spell.getCooldown() / 1000f) - (this.remainingTime / 1000f);
	}
	
	public Long getRemainingTimeInMillis() {
		return this.remainingTime;
	}

}
