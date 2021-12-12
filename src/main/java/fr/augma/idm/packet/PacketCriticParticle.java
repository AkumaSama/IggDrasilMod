package fr.augma.idm.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketCriticParticle implements IMessage {

	private double X, Y, Z;
	
	public PacketCriticParticle() {}
	
	public PacketCriticParticle(Entity entity) {
		this.X = entity.posX;
		this.Y = entity.posY;
		this.Z = entity.posZ;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.X = buf.readDouble();
		this.Y = buf.readDouble();
		this.Z = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(this.X);
		buf.writeDouble(this.Y);
		buf.writeDouble(this.Z);
	}
	
	public static class Handler implements IMessageHandler<PacketCriticParticle, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(PacketCriticParticle message, MessageContext ctx) {
			Minecraft mc = Minecraft.getMinecraft();
			
			mc.player.world.spawnParticle(EnumParticleTypes.CRIT, message.X + 0.5, message.Y + 1, message.Z + 0.5, 0d, 0d, 0d, 0);

			return null;
		}
		
	}

}
