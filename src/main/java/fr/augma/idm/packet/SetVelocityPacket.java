package fr.augma.idm.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SetVelocityPacket implements IMessage {
	
	private double x, z;
	
	public SetVelocityPacket() {}
	
	public SetVelocityPacket(double x, double z) {
		this.x = x;
		this.z = z;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.x = buf.readDouble();
		this.z = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(this.x);
		buf.writeDouble(this.z);
	}

	public static class Handler implements IMessageHandler<SetVelocityPacket, IMessage> {

		@SideOnly(Side.CLIENT)
		@Override
		public IMessage onMessage(SetVelocityPacket message, MessageContext ctx) {
			EntityPlayer p = Minecraft.getMinecraft().player;
			Minecraft.getMinecraft().player.setVelocity(p.motionX + message.x, p.motionY, p.motionZ + message.z);
			return null;
		}
		
	}
}
