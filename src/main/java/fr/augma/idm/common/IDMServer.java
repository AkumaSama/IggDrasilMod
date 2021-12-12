package fr.augma.idm.common;

import java.io.File;

import fr.augma.idm.capability.IPlayerDataCap;
import fr.augma.idm.capability.PlayerDataCapProvider;
import fr.augma.idm.spell.SpellAssassin;
import fr.augma.idm.spell.SpellGuerrier;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.event.PlayerEvent;

public class IDMServer extends IDMCommon {
	public static NpcAPI npcapi;
	private static int COUNTER = 0;
	
	@SuppressWarnings("deprecation")
	@Override
	public void preInit(File configFile) {
		super.preInit(configFile);
		npcapi = NpcAPI.Instance();
		npcapi.events().register(this);
		FMLCommonHandler.instance().bus().register(this);
	}
	
	@Override
	public void init() {
		super.init();
	}
	
	 @SubscribeEvent
	 public void onLevelUp(PlayerEvent.LevelUpEvent event) {
		 IPlayerDataCap cap = PlayerDataCapProvider.get(event.player.getMCEntity());
		 event.player.getMCEntity().sendMessage(new TextComponentString("Vous avez gagner 1 point de skill !"));
		 cap.addAvailablePoint(1);
		 PlayerDataCapProvider.Sync(event.player.getMCEntity());
	 }
	 
	 @SubscribeEvent
	 public void onTick(TickEvent.ServerTickEvent e) {
		 if(e.phase == TickEvent.Phase.END) {
			 COUNTER++;
			 if(COUNTER % 20 == 0) {
				 COUNTER = 0;
				 SpellGuerrier.onSecond();
				 SpellAssassin.onSecond();
			 }
		 }
	 }
}
