package fr.augma.idm.client;

import java.io.File;

import org.lwjgl.input.Keyboard;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;

import fr.augma.idm.IDM;
import fr.augma.idm.client.config.ConfigMod;
import fr.augma.idm.client.gui.iggdrasil.community.GuiCommunity;
import fr.augma.idm.client.gui.iggdrasil.overlay.LifeBarOverlay;
import fr.augma.idm.client.gui.main.CustomMain;
import fr.augma.idm.common.IDMCommon;
import fr.augma.idm.packet.UseQSpellPacket;
import fr.augma.idm.util.ArtefactUtils;
import fr.augma.idm.util.IDMReferences;
import fr.augma.idm.util.RequireUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class IDMClient extends IDMCommon {
	
	private KeyBinding CommunityKey, LifeTypeKey, QSpell;
	
	private static IPCClient client;
	
	public static void setRPC(String details, String state) {
		if(client == null) {
			client = new IPCClient(828421583998550027L);
			client.setListener(new IPCListener(){
			    @Override
			    public void onReady(IPCClient client) {
			        client.sendRichPresence(getRPC(details, state));
			    }
			});
			try {
				client.connect();
			} catch (NoDiscordClientException e) {
				e.printStackTrace();
			}
		} else {
			client.sendRichPresence(getRPC(details, state));
		}
	}
	
	private static RichPresence getRPC(String details, String state) {
		RichPresence.Builder builder = new RichPresence.Builder();
        builder.setState(state)
            .setDetails(details)
            .setLargeImage("logo")
        	.setButton1("Discord", "https://discord.gg/q62TXWbyGJ")
        	.setButton2("Site", "https://letmegooglethat.com/?q=Il+est+en+dev");
        return builder.build();
	}
	
	@Override
	public void preInit(File configFile) {
		super.preInit(configFile);
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new LifeBarOverlay());
		CommunityKey = new KeyBinding("Community", Keyboard.KEY_C, "IggDrasil");
		LifeTypeKey = new KeyBinding("Life Display", Keyboard.KEY_F, "IggDrasil");
		QSpell = new KeyBinding("Spell", Keyboard.KEY_B, "IggDrasil");
		ClientRegistry.registerKeyBinding(CommunityKey);
		ClientRegistry.registerKeyBinding(LifeTypeKey);
		ClientRegistry.registerKeyBinding(QSpell);
		setRPC(Minecraft.getMinecraft().getSession().getUsername(), "Dans les menus");
	}
	
	@Override
	public void init() {
		super.init();
	}
	
	@SubscribeEvent
    public void onOpenGui(GuiOpenEvent event) {
		if (event.getGui() != null && event.getGui().getClass().equals(GuiMainMenu.class)) {
            event.setGui(new CustomMain());
        } else if(event.getGui() != null && event.getGui().getClass().equals(GuiMultiplayer.class)) {
        	event.setGui(new CustomMain());
        }
	}
	
	@SubscribeEvent
	public void onKeyPressed(KeyInputEvent event) {
		if(CommunityKey.isPressed()) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiCommunity());
		} else if(LifeTypeKey.isPressed()) {
			ConfigMod.lifeType = !ConfigMod.lifeType;
			File file = new File(Loader.instance().getConfigDir(), IDMReferences.MODID + ".cfg");
			Configuration cfg = new Configuration(file);
			cfg.getCategory("general").get("lifeType").set(ConfigMod.lifeType);
			cfg.save();
		} else if(QSpell.isPressed()) {
			IDM.network.sendToServer(new UseQSpellPacket());
		}
	}
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onItemTooltip(ItemTooltipEvent event) {
		ItemStack item = event.getItemStack();
		if(ArtefactUtils.isArtefact(item)) {
			event.getToolTip().add("Cet item est un artefact !");
		}
		if(!(item.getItem() instanceof ItemFood) && !(item.getItem() instanceof ItemSword) && !(item.getItem() instanceof ItemBow) && !(item.getItem() instanceof ItemArmor)) return;
		
		event.getToolTip().add("");
		event.getToolTip().add("§e§m§l---==§6§m§liii[§6§l Requiert §6§m§l]iii§e§m§l==---");
		event.getToolTip().add("");
		if(RequireUtils.itemHasRequire(item)) {
			if(RequireUtils.hasLevelRequire(item)) {
				event.getToolTip().add("§6Niveau " + item.getTagCompound().getInteger("level-require") + " minimum");
			} else {
				event.getToolTip().add("§6Niveau 0 minimum");
			}
			
			int i = 0;
			for(String req : RequireUtils.getClassRequire(item)) {
				if(i == 0) {
					event.getToolTip().add("");
					event.getToolTip().add("§6Classe :");
					i++;
				}
				event.getToolTip().add("§c - " + req);
			}
			i = 0;
			for(String skill : RequireUtils.getSkillRequire(item)) {
				if(i == 0) {
					event.getToolTip().add("");
					event.getToolTip().add("§6Skill :");
					i++;
				}
				event.getToolTip().add("§c - " + skill.split("_")[1] + " points en " + getTranslateSkill(skill.split("_")[0]));
			}
		} else {
			event.getToolTip().add("§6Niveau 0 minimum");
		}
	}
	
	private static String getTranslateSkill(String skill) {
		switch(skill) {
		case "vitality":
			return "Vitalité";
		case "intelligence":
			return "Intelligence";
		case "chance":
			return "Chance";
		case "wisdom":
			return "Sagesse";
		case "agility":
			return "Agilité";
		case "strength":
			return "Force";
		default:
			return "error";
		}
	}
}
