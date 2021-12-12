package fr.augma.idm.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CloseGuiPacket implements IMessage {
	
	public CloseGuiPacket() {}

	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}

	public static class Handler implements IMessageHandler<CloseGuiPacket, IMessage> {

		@SideOnly(Side.CLIENT)
		@Override
		public IMessage onMessage(CloseGuiPacket message, MessageContext ctx) {
			Minecraft.getMinecraft().displayGuiScreen(null);
			return null;
		}
	}
}
