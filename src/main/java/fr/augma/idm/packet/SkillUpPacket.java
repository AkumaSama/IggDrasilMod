package fr.augma.idm.packet;

import fr.augma.idm.capability.IPlayerDataCap;
import fr.augma.idm.capability.PlayerDataCapProvider;
import fr.augma.idm.events.ServerEventHandler;
import fr.augma.idm.util.IDMReferences;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SkillUpPacket implements IMessage {
	
	public int type;
	
	public SkillUpPacket() {}
	
	public SkillUpPacket(int type) {
		this.type = type;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.type = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.type);
	}
	
	public static class Handler implements IMessageHandler<SkillUpPacket, IMessage> {

		@Override
		public IMessage onMessage(SkillUpPacket message, MessageContext ctx) {
			IPlayerDataCap pcap = PlayerDataCapProvider.get(ctx.getServerHandler().player);
			
			if(pcap.getAvailablePoint() == 0) {
				ctx.getServerHandler().player.sendMessage(new TextComponentString("Not enough available point"));
				return null;
			}
			
			if(message.type == IDMReferences.AGILITY && pcap.getAgility() == pcap.getMaxAgility()) {
				ctx.getServerHandler().player.sendMessage(new TextComponentString("Agility maxed"));
				return null;
			}
			
			switch(message.type) {
			case IDMReferences.VITALITY:
				pcap.incrementVitality();
				break;
			case IDMReferences.AGILITY:
				pcap.incrementAgility();
				break;
			case IDMReferences.CHANCE:
				pcap.incrementChance();
				break;
			case IDMReferences.STRENGTH:
				pcap.incrementStrength();
				break;
			case IDMReferences.INTELLIGENCE:
				pcap.incrementIntelligence();
				break;
			case IDMReferences.WISDOM:
				pcap.incrementWisdom();
				break;
			}
			ServerEventHandler.refreshAbility(ctx.getServerHandler().player);
			pcap.decreaseAvailablePoint();
			PlayerDataCapProvider.Sync(ctx.getServerHandler().player);
 			return null;
		}
	}
}
