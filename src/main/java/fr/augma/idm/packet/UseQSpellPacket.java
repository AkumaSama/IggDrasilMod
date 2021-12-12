package fr.augma.idm.packet;

import fr.augma.idm.events.custom.UseQSpellEvent;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class UseQSpellPacket implements IMessage {
	
	public UseQSpellPacket() {}

	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}

	public static class Handler implements IMessageHandler<UseQSpellPacket, IMessage> {

		@Override
		public IMessage onMessage(UseQSpellPacket message, MessageContext ctx) {
			MinecraftForge.EVENT_BUS.post(new UseQSpellEvent(ctx.getServerHandler().player));
			return null;
		}
	}
}
