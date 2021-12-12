package fr.augma.idm.util;

import net.minecraft.util.ResourceLocation;

public class IDMReferences {
	public static final String MODID = "idm";
    public static final String NAME = "IggDrasil Mod";
    public static final String VERSION = "1.0";
    public static final String ACCEPTED_VERSIONS = "[1.12.2]";
    public static final String CLIENTPROXY = "fr.augma.idm.client.IDMClient";
    public static final String SERVERPROXY = "fr.augma.idm.common.IDMServer";
    
    public static final int VITALITY = 0;
    public static final int AGILITY = 1;
    public static final int CHANCE = 2;
    public static final int STRENGTH = 3;
    public static final int INTELLIGENCE = 4;
    public static final int WISDOM = 5;
    
    public static class texture {
    	
    	public static ResourceLocation getResource(String path) {
    		return new ResourceLocation(IDMReferences.MODID, path);
    	}
    	
    	private static final String GLOBALFOLDER = "textures/gui/";
    	private static final String COMMUNITYFOLDER = GLOBALFOLDER + "community/";
    	private static final String SKILLFOLDER = GLOBALFOLDER + "skill/";
    	private static final String STATSFOLDER = GLOBALFOLDER + "stats/";
    	private static final String CLASSEFOLDER = GLOBALFOLDER + "classe/";
    	
    	//GUI global
    	public static ResourceLocation CROSSBUTTON = texture.getResource(GLOBALFOLDER + "cross_button_idle.png");
    	public static ResourceLocation CROSSBUTTONHOVER = texture.getResource(GLOBALFOLDER + "cross_button_hover.png");
    	
    	//GUI community
    	public static ResourceLocation COMMUNITY = texture.getResource(COMMUNITYFOLDER + "background.png");
    	public static ResourceLocation SKILLBUTTON = texture.getResource(COMMUNITYFOLDER + "skill_idle.png");
    	public static ResourceLocation SKILLBUTTONHOVER = texture.getResource(COMMUNITYFOLDER + "skill_hover.png");
    	public static ResourceLocation CHARACTERBUTTON = texture.getResource(COMMUNITYFOLDER + "personnage_idle.png");
    	public static ResourceLocation CHARACTERBUTTONHOVER = texture.getResource(COMMUNITYFOLDER + "personnage_hover.png");
    	public static ResourceLocation MOUNTBUTTON = texture.getResource(COMMUNITYFOLDER + "monture_idle.png");
    	public static ResourceLocation MOUNTBUTTONHOVER = texture.getResource(COMMUNITYFOLDER + "monture_hover.png");
    	public static ResourceLocation GUILDBUTTON = texture.getResource(COMMUNITYFOLDER + "confrerie_idle.png");
    	public static ResourceLocation GUILDBUTTONHOVER = texture.getResource(COMMUNITYFOLDER + "confrerie_hover.png");
    	
    	//GUI skill
    	public static ResourceLocation SKILL = texture.getResource(SKILLFOLDER + "background.png");
    	public static ResourceLocation ADDBUTTON = texture.getResource(SKILLFOLDER + "add_idle.png");
    	public static ResourceLocation ADDBUTTONHOVER = texture.getResource(SKILLFOLDER + "add_hover.png");
    	
    	//GUI stats
    	public static ResourceLocation STATS = texture.getResource(STATSFOLDER + "background.png");
    	
    	//GUI classe
    	public static ResourceLocation CLASSE = texture.getResource(CLASSEFOLDER + "gui_classe.png");
    	public static ResourceLocation CLASSE_ARCHER = texture.getResource(CLASSEFOLDER + "class_archer_icon.png");
    	public static ResourceLocation CLASSE_ASSASSIN = texture.getResource(CLASSEFOLDER + "class_assassin_icon.png");
    	public static ResourceLocation CLASSE_GUERRIER = texture.getResource(CLASSEFOLDER + "class_guerrier_icon.png");
    	public static ResourceLocation CLASSE_SELECT = texture.getResource(CLASSEFOLDER + "select_classe.png");
    	public static ResourceLocation CLASSE_SELECT_HOVER = texture.getResource(CLASSEFOLDER + "select_classe_hover.png");
    	public static ResourceLocation CLASSE_GUERRIER_DESC = texture.getResource(CLASSEFOLDER + "guerrier_desc.png");
    	public static ResourceLocation CLASSE_ARCHER_DESC = texture.getResource(CLASSEFOLDER + "archer_desc.png");
    	public static ResourceLocation CLASSE_ASSASSIN_DESC = texture.getResource(CLASSEFOLDER + "assassin_desc.png");
    }
}
