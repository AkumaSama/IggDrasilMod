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

public class GiveSkillPointCommand extends CommandBase {

	@Override
	public String getName() {
		return "giveskillpoint";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/giveskillpoint <player> <number>";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return sender.canUseCommand(this.getRequiredPermissionLevel(), this.getName()) || PermissionAPI.hasPermission((EntityPlayerMP) sender, "idm.giveskillpoint");
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length <= 1) {
			sender.sendMessage(new TextComponentString(getUsage(sender)));
			return; 
		}
		EntityPlayerMP target = server.getPlayerList().getPlayerByUsername(args[0]);
		if(target != null) {
			int number;
			try {
				number = Integer.valueOf(args[1]);
			} catch(NumberFormatException e) {
				sender.sendMessage(new TextComponentString(getUsage(sender)));
				return; 
			}
			
			IPlayerDataCap cap = PlayerDataCapProvider.get(target);
			cap.addAvailablePoint(number);
			PlayerDataCapProvider.Sync(target);
			sender.sendMessage(new TextComponentString(number + " points de skill ont été donné à " + target.getName()));
		} else {
			sender.sendMessage(new TextComponentString("Le joueur n'as pas été trouver."));
		}
	}
}
