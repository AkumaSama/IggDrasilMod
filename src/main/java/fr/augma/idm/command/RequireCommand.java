package fr.augma.idm.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.augma.idm.util.ClassManager;
import fr.augma.idm.util.RequireUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.server.permission.PermissionAPI;

public class RequireCommand extends CommandBase {
	
	public List<String> skillList = Arrays.asList(new String[] {"vitality", "agility", "chance", "strength", "intelligence", "wisdom"});
	
	public NBTTagCompound itemTag;
	
	public void sendMessage(EntityPlayer player, String msg) {
		player.sendMessage(new TextComponentString(msg));
	}

	@Override
	public String getName() {
		return "require";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/require add/remove classe/skill/level name/levelNeed point(only for skills)";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		if (!(sender instanceof EntityPlayer)) return false;
		return sender.canUseCommand(this.getRequiredPermissionLevel(), this.getName())  || PermissionAPI.hasPermission((EntityPlayerMP) sender, "idm.require");
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = getCommandSenderAsPlayer(sender);
		if(player.getHeldItemMainhand().isEmpty()) {
			sendMessage(player, "Merci d'avoir un item dans votre main principale");
			return;
		}
		
		if(args.length == 0) {
			sendMessage(player, getUsage(sender));
			return;
		}
		
		if(!args[0].equalsIgnoreCase("add") && !args[0].equalsIgnoreCase("remove")) {
			sendMessage(player, getUsage(sender));
			return;
		}
		
		if(!args[1].equalsIgnoreCase("classe") && !args[1].equalsIgnoreCase("skill") && !args[1].equalsIgnoreCase("level")) {
			sendMessage(player, getUsage(sender));
			return;
		}
		
		if(args[1].equalsIgnoreCase("classe")) {
			if(!ClassManager.getClasseListString().contains(args[2]))  {
				sendMessage(player, getUsage(sender));
				return;
			}
		} else if(args[1].equalsIgnoreCase("skill")){
			if(!skillList.contains(args[2])) {
				sendMessage(player, getUsage(sender));
				return;
			}
		} else if(args[0].equalsIgnoreCase("remove") && args[1].equalsIgnoreCase("level")) {
			
		} else if(!isNotANumber(args[2])) {
			sendMessage(player, getUsage(sender));
			return;
		}
		
		if(args[0].equalsIgnoreCase("add")) {
			if(args.length > 3 && args[1].equalsIgnoreCase("skill")) {
				try {
					int value = Integer.valueOf(args[3]);
					if(value > 0) {
						RequireUtils.addRequire(player.getHeldItemMainhand(), args[2], value, player);
					} else {
						sendMessage(player, "Merci de renseigner un chiffre superieur a 0");
					}
				} catch(NumberFormatException e) {
					sendMessage(player, "Merci de renseigner un chiffre valide");
				}
			} else if(args[1].equalsIgnoreCase("classe")){
				RequireUtils.addRequire(player.getHeldItemMainhand(), args[2], player);
			} else if(args[1].equalsIgnoreCase("level")){
				int level;
				try {
					level = Integer.valueOf(args[2]);
				} catch(NumberFormatException e) {
					sendMessage(player, getUsage(sender));
					return;
				}
				RequireUtils.addLevelRequire(player.getHeldItemMainhand(), level);
			} else {
				sendMessage(player, getUsage(sender));
			}
		} else if(args[0].equalsIgnoreCase("remove")) {
			if(args[1].equalsIgnoreCase("level")) {
				if(RequireUtils.removeLevelRequire(player.getHeldItemMainhand())) {
					sendMessage(player, "Cet item ne possède pas ce require");
				} else {
					sendMessage(player, "Require retirer avec succès");
				}
			} else {
				RequireUtils.removeRequire(player.getHeldItemMainhand(), player, args[2]);
			}
		}
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		List<String> tab = new ArrayList<>();
		
		if(args.length == 1) {
			tab.add("add");
			tab.add("remove");
		} else if(args.length == 2) {
			tab.add("classe");
			tab.add("skill");
			tab.add("level");
		} else if(args.length == 3) {
			switch(args[1]) {
			case "classe":
				tab = ClassManager.getClasseListString();
				break;
			case "skill":
				tab = skillList;
				break;
			}
		}
		
		return tab;
	}
	
	private boolean isNotANumber(String sequence) {
		try {
			Integer.valueOf(sequence);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
}