package fr.augma.idm.thread;

import net.minecraft.item.ItemStack;

public class ArtefactThread extends Thread {

	private Long cooldown;
	private ItemStack item;
	
	public ArtefactThread(Long cooldown, ItemStack item) {
		this.cooldown = cooldown;
		this.item = item;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("Début du thread");
			Thread.sleep(this.cooldown);
			System.out.println(this.item.getDisplayName());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
