package fr.augma.idm.spell;

import java.util.HashMap;
import java.util.Map;

import fr.augma.idm.IDM;
import fr.augma.idm.capability.PlayerDataCapProvider;
import fr.augma.idm.packet.SetVelocityPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.MathHelper;

public class SpellAssassin extends SpellBase {
	
	private static HashMap<EntityPlayer, Integer> TIME_EFFECT_HANDLER = new HashMap<>();

	public SpellAssassin(String spellName, Long cooldown, int timeEffect) {
		super(spellName, cooldown, timeEffect);
	}

	@Override
	public void activateEffect(EntityPlayer player) {
		PlayerDataCapProvider.get(player).setQSpellLastUse(System.currentTimeMillis());
		TIME_EFFECT_HANDLER.put(player, this.getTimeEffect());
		float yaw = player.rotationYaw;
		float pitch = 1.0f;
		float f = 5F;
		if(!player.onGround) {
			f = 1.7f;
		}
		double motionX = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
		double motionZ = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
		
		IDM.network.sendTo(new SetVelocityPacket(motionX, motionZ), (EntityPlayerMP) player);
	}
	
	public static void onSecond() {
		for(Map.Entry<EntityPlayer, Integer> entry : TIME_EFFECT_HANDLER.entrySet()) {
			TIME_EFFECT_HANDLER.put(entry.getKey(), entry.getValue() - 1);
			
			if(TIME_EFFECT_HANDLER.get(entry.getKey()) == 0) {
				TIME_EFFECT_HANDLER.remove(entry.getKey());
				secondState(entry.getKey());
			}
		}
	}
	
	private static void secondState(EntityPlayer player) {
		float yaw = player.rotationYaw;
		float pitch = 1.0f;
		float f = 5F;
		if(!player.onGround) {
			f = 1.7f;
		}
		double motionX = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
		double motionZ = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
		
		IDM.network.sendTo(new SetVelocityPacket(-motionX, -motionZ), (EntityPlayerMP) player);
	}

}
