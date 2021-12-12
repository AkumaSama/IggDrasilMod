package fr.augma.idm.spell;

import fr.augma.idm.IDM;
import fr.augma.idm.capability.PlayerDataCapProvider;
import fr.augma.idm.packet.SetVelocityPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.MathHelper;

public class SpellArcher extends SpellBase {

	public SpellArcher(String spellName, Long cooldown) {
		super(spellName, cooldown, 0);
	}

	@Override
	public void activateEffect(EntityPlayer player) {
		PlayerDataCapProvider.get(player).setQSpellLastUse(System.currentTimeMillis());
		float yaw = player.rotationYaw;
		float pitch = 1.0f;
		float f = 3.5f;
		if(!player.onGround) {
			f = 1.0F;
		}
		
		double motionX = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
		double motionZ = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
		
		IDM.network.sendTo(new SetVelocityPacket(-motionX, -motionZ), (EntityPlayerMP) player);
	}
}
