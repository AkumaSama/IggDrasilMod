package fr.augma.idm.packet;

import fr.augma.idm.client.IDMClient;
import fr.augma.idm.client.gui.iggdrasil.classe.GuiClasse;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class OpenClassGuiPacket implements IMessage {
	
	public OpenClassGuiPacket() {}

	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}
	
	public static class Handler implements IMessageHandler<OpenClassGuiPacket, IMessage> {

		@SideOnly(Side.CLIENT)
		@Override
		public IMessage onMessage(OpenClassGuiPacket message, MessageContext ctx) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiClasse());
			IDMClient.setRPC(Minecraft.getMinecraft().player.getName(), "Sélectionne sa classe");
			return null;
		}
		
	}

}
