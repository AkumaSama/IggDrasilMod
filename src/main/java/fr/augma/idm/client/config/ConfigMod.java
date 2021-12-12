package fr.augma.idm.client.config;

import fr.augma.idm.util.IDMReferences;
import net.minecraftforge.common.config.Config;

@Config(modid = IDMReferences.MODID)
public class ConfigMod {
	
	@Config.Comment("Comment la vie va être afficher pourcentage ou brute, true par defaut (true = %, false = brute)")
	public static boolean lifeType = true;
}
