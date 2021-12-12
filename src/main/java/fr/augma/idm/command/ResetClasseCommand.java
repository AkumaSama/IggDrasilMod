package fr.augma.idm.command;

import fr.augma.idm.IDM;
import fr.augma.idm.capability.IPlayerDataCap;
import fr.augma.idm.capability.PlayerDataCapProvider;
import fr.augma.idm.common.IDMServer;
import fr.augma.idm.packet.OpenClassGuiPacket;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.server.permission.PermissionAPI;
import noppes.npcs.api.entity.IPlayer;

public class ResetClasseCommand extends CommandBase {

	@Override
	public String getName() {
		return "resetclasse";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/resetclasse <player>";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return sender.canUseCommand(this.getRequiredPermissionLevel(), this.getName())  || PermissionAPI.hasPermission((EntityPlayerMP) sender, "idm.require");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length == 0) {
			sender.sendMessage(new TextComponentString(getUsage(sender)));
			return; 
		}
		EntityPlayerMP target = server.getPlayerList().getPlayerByUsername(args[0]);
		if(target != null) {
			IPlayerDataCap cap = PlayerDataCapProvider.get(target);
			cap.setClasse(null);
			target.inventory.clear();
			target.getInventoryEnderChest().clear();
			target.experienceLevel = 0;
			target.experience = 0;
			target.setPositionAndUpdate(target.world.getSpawnPoint().getX(), target.world.getSpawnPoint().getY(), target.world.getSpawnPoint().getZ());
			IPlayer<EntityPlayerMP> ITarget = (IPlayer<EntityPlayerMP>) IDMServer.npcapi.getIEntity(target);
			ITarget.clearData();
			PlayerDataCapProvider.Sync(target);
			IDM.network.sendTo(new OpenClassGuiPacket(), target);
			sender.sendMessage(new TextComponentString("Classe de " + target.getName() + " à été reset."));
		} else {
			sender.sendMessage(new TextComponentString("Le joueur n'as pas été trouver."));
		}
	}
}
