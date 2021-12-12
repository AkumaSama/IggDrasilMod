package fr.augma.idm.init;

import fr.augma.idm.spell.SpellArcher;
import fr.augma.idm.spell.SpellAssassin;
import fr.augma.idm.spell.SpellBase;
import fr.augma.idm.spell.SpellGuerrier;

public class SpellInit {
	
	private static SpellBase SPELL_GUERRIER, SPELL_ARCHER, SPELL_ASSASSIN;

	public static void init() {
		SPELL_GUERRIER = new SpellGuerrier("Spell Guerrier", 120000L, 5);
		SPELL_ARCHER = new SpellArcher("Spell Archer", 60000L);
		SPELL_ASSASSIN = new SpellAssassin("Spell Assassin", 60000L, 2);
	}
	
	public static SpellBase getSpellGuerrier() {
		return SPELL_GUERRIER;
	}
	
	public static SpellBase getSpellArcher() {
		return SPELL_ARCHER;
	}
	
	public static SpellBase getSpellAssassin() {
		return SPELL_ASSASSIN;
	}
}
