package fr.augma.idm.util;

import java.util.ArrayList;
import java.util.List;

import fr.augma.idm.capability.IPlayerDataCap;
import fr.augma.idm.capability.PlayerDataCapProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
public class RequireUtils {
	
	public static List<String> getClassRequire(ItemStack item) {
		int i = 0;
		List<String> result = new ArrayList<>();
		while(item.getTagCompound().hasKey("classe-" + i)) {
			result.add(item.getTagCompound().getString("classe-" + i));
			i++;
		}
		return result;
	}
	
	public static List<String> getSkillRequire(ItemStack item) {
		int i = 0;
		List<String> result = new ArrayList<>();
		while(item.getTagCompound().hasKey("skill-" + i)) {
			result.add(item.getTagCompound().getString("skill-" + i));
			i++;
		}
		return result;
	}
	
	public static boolean itemHasRequire(ItemStack item) {
		if(!item.isEmpty()) {
			if(item.hasTagCompound()) return item.getTagCompound().getBoolean("require");
		}
		return false;
	}
	
	public static void addRequire(ItemStack item, String type, EntityPlayer p) {
		addRequire(item, type, 0, p);
	}
	
	public static void addRequire(ItemStack item, String type, int value, EntityPlayer p) {
		if(!itemHasRequire(item)) createTag(item);
		List<String> req;
		if(value == 0) {
			req = getClassRequire(item);
			if(!req.contains(type)) {
				item.getTagCompound().setString("classe-" + req.size(), type);
			} else {
				p.sendMessage(new TextComponentString("Cet item possede deja ce require"));
			}
		} else {
			req = getSkillRequire(item);
			boolean contain = false;
			for(String skill : req) {
				if(skill.split("_")[0].equalsIgnoreCase(type)) {
					contain = true;
					break;
				}
			}
			if(!contain) {
				item.getTagCompound().setString("skill-" + req.size(), type + "_" + value);
			} else {
				p.sendMessage(new TextComponentString("Cet item possede deja ce require"));
			}
		}
	}
	
	private static void createTag(ItemStack item) {
		if(!item.hasTagCompound()) {
			item.setTagCompound(new NBTTagCompound());
			item.getTagCompound().setBoolean("require", true);
		} else {
			item.getTagCompound().setBoolean("require", true);
		}
	}
	
	private static boolean itemHasClassOrSkillRequire(ItemStack item, String value) {
		boolean hasSkillRequire = false;
		for(String skill : getSkillRequire(item)) {
			if(skill.split("_")[0].equalsIgnoreCase(value)) {
				hasSkillRequire = true;
				break;
			}
		}
		return getClassRequire(item).contains(value) || hasSkillRequire;
	}
	
	public static void removeRequire(ItemStack item, EntityPlayer p, String value) {
		if(itemHasClassOrSkillRequire(item, value)) {
			int i = 0;
			if(ClassManager.getClasseListString().contains(value)) {
				while(item.getTagCompound().hasKey("classe-" + i)) {
					if(item.getTagCompound().getString("classe-" + i).equalsIgnoreCase(value)) {
						item.getTagCompound().removeTag("classe-" + i);
						break;
					}
					i++;
				}
			} else {
				while(item.getTagCompound().hasKey("skill-" + i)) {
					if(item.getTagCompound().getString("skill-" + i).split("_")[0].equalsIgnoreCase(value)) {
						item.getTagCompound().removeTag("skill-" + i);
					}
					i++;
				}
			}
			
			if(getClassRequire(item).size() == 0 && getSkillRequire(item).size() == 0 && !hasLevelRequire(item)) {
				item.getTagCompound().setBoolean("require", false);
			}
			p.sendMessage(new TextComponentString("Require retirer avec succes"));
		} else {
			p.sendMessage(new TextComponentString("Cet item ne possede pas ce require"));
		}
	}
	
	public static boolean[] canHit(ItemStack item, EntityPlayer player) {
		boolean[] result = new boolean[3];
		result[0] = canHitClasse(item, player);
		result[1] = canHitSkill(item, player);
		result[2] = canHitLevel(item, player);
		
		return result;
	}
	
	public static boolean[] canHitTwoHands(ItemStack itemMainHand, ItemStack ItemOffHand, EntityPlayer player) {
		boolean[] result = new boolean[6];
		result[0] = canHitClasse(itemMainHand, player);
		result[1] = canHitSkill(itemMainHand, player);
		result[2] = canHitLevel(itemMainHand, player);
		result[3] = canHitClasse(ItemOffHand, player);
		result[4] = canHitSkill(ItemOffHand, player);
		result[5] = canHitLevel(ItemOffHand, player);
		
		return result;
	}
	
	public static boolean canHitClasse(ItemStack item, EntityPlayer player) {
		if(itemHasRequire(item)) {
			IPlayerDataCap cap = PlayerDataCapProvider.get(player);
			boolean hasClass = getClassRequire(item).size() > 0;
			boolean canStrikeClasse = false;
			if(hasClass) {
				for(String classe : getClassRequire(item)) {
					if(classe.equalsIgnoreCase(cap.getClasse().getClassName())) {
						canStrikeClasse = true;
						break;
					}
				}
				return !canStrikeClasse;
			}
		}
		return false;
	}
	
	public static boolean canHitSkill(ItemStack item, EntityPlayer player) {
		if(itemHasRequire(item)) {
			IPlayerDataCap cap = PlayerDataCapProvider.get(player);
			boolean hasSkill = getSkillRequire(item).size() > 0;
			List<Boolean> skillListItem = new ArrayList<>();
			if(hasSkill) {
				for(String skill : getSkillRequire(item)) {
					skillListItem.add(isSkillUseable(cap, skill));
				}
				return compareListBoolean(skillListItem);
			}
		}
		return false;
	}
	
	public static boolean canHitLevel(ItemStack item, EntityPlayer player) {
		boolean hasLevel = RequireUtils.hasLevelRequire(item);
		boolean canStrikeLevel = false;
		if(hasLevel) {
			canStrikeLevel = player.experienceLevel >= item.getTagCompound().getInteger("level-require");
			
			return !canStrikeLevel;
		}
		return false;
	}
	
	private static boolean compareListBoolean(List<Boolean> list) {
		if(list.contains(false) && list.contains(true)) {
			return true;
		}
		return false;
	}
	
	private static boolean isSkillUseable(IPlayerDataCap cap, String skillLine) {
		switch(skillLine.split("_")[0]) {
		case "agility":
			if(cap.getAgility() >= Integer.valueOf(skillLine.split("_")[1])) return true;
			return false;
		case "vitality":
			if(cap.getVitality() >= Integer.valueOf(skillLine.split("_")[1])) return true;
			return false;
		case "strength":
			if(cap.getStrength() >= Integer.valueOf(skillLine.split("_")[1])) return true;
			return false;
		case "wisdom":
			if(cap.getWisdom() >= Integer.valueOf(skillLine.split("_")[1])) return true;
			return false;
		case "intelligence":
			if(cap.getIntelligence() >= Integer.valueOf(skillLine.split("_")[1])) return true;
			return false;
		case "chance":
			if(cap.getChance() >= Integer.valueOf(skillLine.split("_")[1])) return true;
			return false;
		default:
			return false;
		}
	}
	
	public static boolean hasLevelRequire(ItemStack item) {
		if(item.hasTagCompound()) {
			if(item.getTagCompound().hasKey("level-require")) return true;
		}
		return false;
	}

	public static void addLevelRequire(ItemStack heldItemMainhand, int level) {
		if(!hasLevelRequire(heldItemMainhand)) {
			if(!itemHasRequire(heldItemMainhand)) createTag(heldItemMainhand);
			heldItemMainhand.getTagCompound().setInteger("level-require", level);
		}
	}
	
	public static boolean removeLevelRequire(ItemStack item) {
		if(hasLevelRequire(item)) {
			item.getTagCompound().removeTag("level-require");
			if(getClassRequire(item).size() == 0 && getSkillRequire(item).size() == 0 && !hasLevelRequire(item)) {
				item.getTagCompound().setBoolean("require", false);
			}
			return false;
		} else {
			return true;
		}
	}
}
