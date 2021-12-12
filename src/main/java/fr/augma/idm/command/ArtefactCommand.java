package fr.augma.idm.command;

import java.util.List;

import fr.augma.idm.util.ArtefactUtils;
import fr.augma.idm.util.EnumArtefactDurability;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.server.permission.PermissionAPI;

public class ArtefactCommand extends CommandBase {

	@Override
	public String getName() {
		return "artefact";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/artefact";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		if (!(sender instanceof EntityPlayer)) return false;
		return sender.canUseCommand(this.getRequiredPermissionLevel(), this.getName()) || PermissionAPI.hasPermission((EntityPlayerMP) sender, "idm.artefact");
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer p = getCommandSenderAsPlayer(sender);
		
		if(!p.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).isEmpty()) {
			ItemStack item = p.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);
			
			ArtefactUtils.setArtefact(item);
			ArtefactUtils.setArtefactDurability(EnumArtefactDurability.COOLDOWN, 10000L, item);
		}
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		return null;
	}
}
