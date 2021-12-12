package fr.augma.idm.command;

import fr.augma.idm.capability.IPlayerDataCap;
import fr.augma.idm.capability.PlayerDataCapProvider;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.server.permission.PermissionAPI;

public class ResetSkillPointsCommand extends CommandBase {

	@Override
	public String getName() {
		return "resetskillpoints";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/resetskillpoints <player>";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return sender.canUseCommand(this.getRequiredPermissionLevel(), this.getName())  || PermissionAPI.hasPermission((EntityPlayerMP) sender, "idm.require");
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length == 0) {
			sender.sendMessage(new TextComponentString(getUsage(sender)));
			return; 
		}
		EntityPlayerMP target = server.getPlayerList().getPlayerByUsername(args[0]);
		if(target != null) {
			IPlayerDataCap cap = PlayerDataCapProvider.get(target);
			cap.setAvailablePoint(0);
			sender.sendMessage(new TextComponentString("Skill points de " + target.getName() + " ont été remis à 0."));
			PlayerDataCapProvider.Sync(target);
		} else {
			sender.sendMessage(new TextComponentString("Le joueur n'as pas été trouver."));
		}
	}
}
