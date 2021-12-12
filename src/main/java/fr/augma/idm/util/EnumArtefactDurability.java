package fr.augma.idm.util;

public enum EnumArtefactDurability {
	COUNT, COOLDOWN, ONEUSE;
	
	public static EnumArtefactDurability get(String str) {
		EnumArtefactDurability result = null;
		
		for(EnumArtefactDurability v : EnumArtefactDurability.values())
			if(v.name().equalsIgnoreCase(str)) {
				result = v;
				break;
			}
		
		return result;
	}
}
