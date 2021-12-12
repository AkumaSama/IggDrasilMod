package fr.augma.idm.command;

import fr.augma.idm.capability.IPlayerDataCap;
import fr.augma.idm.capability.PlayerDataCapProvider;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.server.permission.PermissionAPI;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class AttributeSkill extends CommandBase {

    @Override
    public String getName() {
        return "attributeskill";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/attributeskill <player> <stats> <number>";
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
        if(target == null) {
            sender.sendMessage(new TextComponentString("Le joueur n'as pas été trouver."));
            return;
        }
        int number;
        try {
            number = Integer.valueOf(args[2]);
        } catch(NumberFormatException e) {
            sender.sendMessage(new TextComponentString(getUsage(sender)));
            return;
        }

        IPlayerDataCap cap = PlayerDataCapProvider.get(target);
        switch(args[1]) {
            case "vitality":
                cap.setVitality(cap.getVitality() + number);
                break;
            case "agility":
                cap.setAgility(cap.getAgility() + number);
                break;
            case "chance":
                cap.setChance(cap.getChance() + number);
                break;
            case "strength":
                cap.setStrength(cap.getStrength() + number);
                break;
            case "intelligence":
                cap.setIntelligence(cap.getIntelligence() + number);
                break;
            case "wisdom":
                cap.setWisdom(cap.getWisdom() + number);
                break;
        }
        PlayerDataCapProvider.Sync(target);
        target.sendMessage(new TextComponentString("Vous avez reçu " + number + " points en " + args[1]));


    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if(args.length == 1) {
            return Arrays.asList(server.getOnlinePlayerNames());
        } else if(args.length == 2){
            return Arrays.asList("vitality", "agility", "chance", "strength", "intelligence", "wisdom");
        }
        return null;
    }
}
