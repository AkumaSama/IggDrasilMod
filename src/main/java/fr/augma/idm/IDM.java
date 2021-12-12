package fr.augma.idm;

import fr.augma.idm.command.*;
import fr.augma.idm.packet.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;

import java.util.List;

import fr.augma.idm.capability.PlayerDataCapProvider;
import fr.augma.idm.common.IDMCommon;
import fr.augma.idm.init.SpellInit;
import fr.augma.idm.util.ClassManager;
import fr.augma.idm.util.IDMReferences;
import org.apache.logging.log4j.Logger;

@Mod(modid = IDMReferences.MODID, name = IDMReferences.NAME, version = IDMReferences.VERSION)
public class IDM {

    private static Logger logger;
    
    public static List<ClassManager> classeList;
    
    @Instance(IDMReferences.MODID)
    public static IDM INSTANCE;
    
    @SidedProxy(clientSide=IDMReferences.CLIENTPROXY, serverSide=IDMReferences.SERVERPROXY)
    public static IDMCommon PROXY;
    
    public static SimpleNetworkWrapper network;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        PROXY.preInit(event.getSuggestedConfigurationFile());
        loadNetwork();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        PROXY.init();
        PlayerDataCapProvider.register();
        SpellInit.init();
        ClassManager.initClasse();
        loadPermission();
    }
    
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
    	event.registerServerCommand(new RequireCommand());
    	event.registerServerCommand(new ResetClasseCommand());
    	event.registerServerCommand(new ResetSkillCommand());
    	event.registerServerCommand(new ResetSkillPointsCommand());
    	event.registerServerCommand(new ResetSkillAllCommand());
    	event.registerServerCommand(new GiveSkillPointCommand());
    	event.registerServerCommand(new ArtefactCommand());
    	event.registerServerCommand(new AttributeSkill());
    }
    
    private void loadNetwork() {
    	network = NetworkRegistry.INSTANCE.newSimpleChannel(IDMReferences.MODID);
        network.registerMessage(SkillUpPacket.Handler.class, SkillUpPacket.class, 0, Side.SERVER);
        network.registerMessage(SelectClassPacket.Handler.class, SelectClassPacket.class, 1, Side.SERVER);
        network.registerMessage(CloseGuiPacket.Handler.class, CloseGuiPacket.class, 2, Side.CLIENT);
        network.registerMessage(OpenClassGuiPacket.Handler.class, OpenClassGuiPacket.class, 3, Side.CLIENT);
        network.registerMessage(setRpcPacket.Handler.class, setRpcPacket.class, 4, Side.CLIENT);
        network.registerMessage(SCPacketDataCap.Handler.class, SCPacketDataCap.class, 5, Side.CLIENT);
        network.registerMessage(UseQSpellPacket.Handler.class, UseQSpellPacket.class, 6, Side.SERVER);
        network.registerMessage(SetVelocityPacket.Handler.class, SetVelocityPacket.class, 7, Side.CLIENT);
        network.registerMessage(PacketCriticParticle.Handler.class, PacketCriticParticle.class, 8, Side.CLIENT);
    }
    
    private void loadPermission() {
    	PermissionAPI.registerNode("idm.require", DefaultPermissionLevel.OP, "Allows players to use the require command");
        PermissionAPI.registerNode("idm.resetclasse", DefaultPermissionLevel.OP, "Allows players to use the resetclasse command");
        PermissionAPI.registerNode("idm.resetskill", DefaultPermissionLevel.OP, "Allows players to use the resetskill command");
        PermissionAPI.registerNode("idm.resetskillpoints", DefaultPermissionLevel.OP, "Allows players to use the resetskillpoints command");
        PermissionAPI.registerNode("idm.resetskillall", DefaultPermissionLevel.OP, "Allows players to use the resetskillall command");
        PermissionAPI.registerNode("idm.giveskillpoint", DefaultPermissionLevel.OP, "Allows players to use the giveskillpoint command");
        PermissionAPI.registerNode("idm.artefact", DefaultPermissionLevel.OP, "Allows players to use the Artefact command");
    }
}
