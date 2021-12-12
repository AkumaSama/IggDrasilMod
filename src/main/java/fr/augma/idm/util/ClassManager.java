package fr.augma.idm.util;

import java.util.ArrayList;
import java.util.List;

import fr.augma.idm.init.SpellInit;
import fr.augma.idm.spell.SpellBase;

public class ClassManager {

	private String CLASS;
	public static List<ClassManager> classeList = new ArrayList<>();
	private SpellBase spell;

	public ClassManager(String name, SpellBase spell) {
		this.CLASS = name;
		classeList.add(this);
		this.spell = spell;
	}

	public String getClassName() {
		return this.CLASS;
	}
	
	public SpellBase getSpell() {
		return this.spell;
	}
	
	public static ClassManager getClasseFromString(String classeName) {
		for(ClassManager c : classeList) if(c.getClassName().equalsIgnoreCase(classeName)) return c;
    	return null;
	}
	
	public static void initClasse() {
    	new ClassManager(EnumClasse.Guerrier.name(), SpellInit.getSpellGuerrier());
    	new ClassManager(EnumClasse.Archer.name(), SpellInit.getSpellArcher());
    	new ClassManager(EnumClasse.Assassin.name(), SpellInit.getSpellAssassin());
    }
	
	public static ClassManager getClasseFromString(EnumClasse classe) {
		return getClasseFromString(classe.name());
	}
	
	public static List<String> getClasseListString() {
		List<String> list = new ArrayList<>();
		for(ClassManager c : classeList) list.add(c.getClassName());
		return list;
	}
}
