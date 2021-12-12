package fr.augma.idm.spell;

import java.util.HashMap;
import java.util.Map;

import fr.augma.idm.capability.PlayerDataCapProvider;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;

public class SpellGuerrier extends SpellBase {
	
	private static final String ATT_NAME = "QSpellGuerrier";
	private static HashMap<EntityPlayer, Integer> TIME_EFFECT_HANDLER = new HashMap<>();

	public SpellGuerrier(String spellName, Long cooldown, int timeEffect) {
		super(spellName, cooldown, timeEffect);
	}

	@Override
	public void activateEffect(EntityPlayer player) {
		PlayerDataCapProvider.get(player).setQSpellLastUse(System.currentTimeMillis());
		TIME_EFFECT_HANDLER.put(player, this.getTimeEffect());
		AttributeModifier att = new AttributeModifier(ATT_NAME, 10d, 0);
		player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(att);
		player.setHealth((float) (player.getHealth() + att.getAmount()));
	}
	
	public static void onSecond() {
		for(Map.Entry<EntityPlayer, Integer> entry : TIME_EFFECT_HANDLER.entrySet()) {
			TIME_EFFECT_HANDLER.put(entry.getKey(), entry.getValue() - 1);
			
			if(TIME_EFFECT_HANDLER.get(entry.getKey()) == 0) {
				TIME_EFFECT_HANDLER.remove(entry.getKey());
				removeEffect(entry.getKey());
			}
		}
	}
	
	private static void removeEffect(EntityPlayer player) {
		for(AttributeModifier att : player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getModifiers()) {
			if(att.getName().equalsIgnoreCase(ATT_NAME)) {
				player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(att);
				if(player.getHealth() > player.getMaxHealth()) player.setHealth(player.getMaxHealth());
			}
		}
	}
}
