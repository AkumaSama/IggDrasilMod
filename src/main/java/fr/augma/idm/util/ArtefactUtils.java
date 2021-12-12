package fr.augma.idm.util;

import fr.augma.idm.thread.ArtefactThread;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ArtefactUtils {
	
	private static void createTag(ItemStack item) {
		if(!item.hasTagCompound()) {
			item.setTagCompound(new NBTTagCompound());
		}
	}
	
	public static boolean isArtefact(ItemStack item) {
		return item.hasTagCompound() && item.getTagCompound().getBoolean("artefact");
	}
	
	public static void setArtefact(ItemStack item) {
		if(!isArtefact(item)) {
			createTag(item);
			item.getTagCompound().setBoolean("artefact", true);
		}
	}
	
	public static ArtefactDurability getDurability(ItemStack item) {
		return isArtefact(item) ? new ArtefactDurability(item) : null;
	}
	
	public static void setArtefactDurability(EnumArtefactDurability type, Long time, ItemStack item) {
		if(isArtefact(item)) {
			item.getTagCompound().setString("durability", type.name());
			item.getTagCompound().setLong("cooldown", time);
		}
	}
	
	public static class ArtefactDurability {
		private EnumArtefactDurability type;
		private ItemStack item;
		private int maxuse, actualuse;
		private Long cooldown;
		
		public ArtefactDurability(ItemStack item) {
			this.item = item;
			this.type = EnumArtefactDurability.get(item.getTagCompound().getString("durability"));
			
			switch(this.type) {
			case COOLDOWN:
				this.cooldown = this.item.getTagCompound().getLong("cooldown");
				break;
			case COUNT:
				this.maxuse = this.item.getTagCompound().getInteger("maxuse");
				this.actualuse = this.item.getTagCompound().getInteger("actualuse");
				break;
			default:
				break;
			}
		}
		
		public boolean willBeDestroy() {
			switch(this.type) {
			case COOLDOWN:
				new ArtefactThread(this.cooldown, this.item).start();
				return true;
			case COUNT:
				
				return false;
			default: 
				return true;
			}
		}
		
		public EnumArtefactDurability getType() {
			return this.type;
		}

		public Long getCooldown() {
			return cooldown;
		}

		public int getMaxuse() {
			return maxuse;
		}

		public int getActualuse() {
			return actualuse;
		}
	}
}
