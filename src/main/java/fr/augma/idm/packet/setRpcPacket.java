package fr.augma.idm.packet;

import fr.augma.idm.client.IDMClient;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class setRpcPacket implements IMessage {

	private String details;
	private String state;
	
	public setRpcPacket() {}
	
	public setRpcPacket(String details, String state) {
		this.details = details;
		this.state = state;
	}
	@Override
	public void fromBytes(ByteBuf buf) {
		this.details = ByteBufUtils.readUTF8String(buf);
		this.state = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, this.details);
		ByteBufUtils.writeUTF8String(buf, this.state);
	}

	public static class Handler implements IMessageHandler<setRpcPacket, IMessage> {

		@SideOnly(Side.CLIENT)
		@Override
		public IMessage onMessage(setRpcPacket message, MessageContext ctx) {
			IDMClient.setRPC(message.details, message.state);
			return null;
		}
		
	}
}
