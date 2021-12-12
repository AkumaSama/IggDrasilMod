package fr.augma.idm.packet;

import fr.augma.idm.IDM;
import fr.augma.idm.capability.IPlayerDataCap;
import fr.augma.idm.capability.PlayerDataCapProvider;
import fr.augma.idm.util.ClassManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SelectClassPacket implements IMessage {
	
	public ClassManager classe;
	
	public SelectClassPacket() {}
	
	public SelectClassPacket(ClassManager classe) {
		this.classe = classe;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.classe = ClassManager.getClasseFromString(ByteBufUtils.readUTF8String(buf));
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, this.classe.getClassName());
	}
	
	public static class Handler implements IMessageHandler<SelectClassPacket, CloseGuiPacket> {

		@Override
		public CloseGuiPacket onMessage(SelectClassPacket message, MessageContext ctx) {
			IPlayerDataCap cap = PlayerDataCapProvider.get(ctx.getServerHandler().player);
			if(cap.getClasse() == null) {
				cap.setClasse(message.classe);
				ctx.getServerHandler().player.sendMessage(new TextComponentString("Vous avez choisi la classe " + message.classe.getClassName() + "."));
				IDM.network.sendTo(new setRpcPacket(ctx.getServerHandler().player.getName(), cap.getClasse().getClassName()), ctx.getServerHandler().player);
				PlayerDataCapProvider.Sync(ctx.getServerHandler().player);
			}
			return new CloseGuiPacket();
		}
		
	}
}
