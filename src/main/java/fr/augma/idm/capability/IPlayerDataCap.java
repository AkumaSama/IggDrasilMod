package fr.augma.idm.capability;

import javax.annotation.Nullable;

import fr.augma.idm.util.ClassManager;
import net.minecraft.nbt.NBTTagCompound;

public interface IPlayerDataCap {

	NBTTagCompound data();
	
	void data(NBTTagCompound data);
	
	default ClassManager getClasse() {
		return ClassManager.getClasseFromString(this.data().getString("classe"));
	}
	
	default void setClasse(@Nullable ClassManager classe) {
		this.data().setString("classe", classe == null ? "" : classe.getClassName());
	}
	
	default void resetAttribute() {
		addAvailablePoint(getTotalStat());
		clearAllStat();
	}
	
	default void hardResetAttribute() {
		setAvailablePoint(0);
		clearAllStat();
	}
	
	default int getTotalStat() {
		return getVitality() + getStrength() + getAgility() + getChance() + getIntelligence() + getWisdom();
	}
	
	default void clearAllStat() {
		setVitality(0);
		setStrength(0);
		setAgility(0);
		setChance(0);
		setIntelligence(0);
		setWisdom(0);
	}
	
	default int getMaxAgility() {
		return 25;
	}
	
	default void setAvailablePoint(int ap) {
		this.data().setInteger("available", ap);
	}
	
	default int getAvailablePoint() {
		return this.data().getInteger("available");
	}
	
	default void incrementAvailablePoint() {
		this.data().setInteger("available", getAvailablePoint() + 1);
	}
	
	default void decreaseAvailablePoint() {
		this.data().setInteger("available", getAvailablePoint() - 1);
	}
	
	default void addAvailablePoint(int availablepoint) {
		this.data().setInteger("available", getAvailablePoint() + availablepoint);
	}
	
	default int getVitality() {
		return this.data().getInteger("vitality");
	}
	
	default void incrementVitality() {
		this.data().setInteger("vitality", getVitality() + 1);
	}
	
	default void setVitality(int vitality) {
		this.data().setInteger("vitality", vitality);
	}
	
	default int getStrength() {
		return this.data().getInteger("strength");
	}
	
	default void incrementStrength() {
		this.data().setInteger("strength", getStrength() + 1);
	}
	
	default void setStrength(int strength) {
		this.data().setInteger("strength", strength);
	}
	
	default int getAgility() {
		return this.data().getInteger("agility");
	}
	
	default void incrementAgility() {
		this.data().setInteger("agility", getAgility() + 1);
	}
	
	default void setAgility(int agility) {
		this.data().setInteger("agility", agility);
	}
	
	default int getChance() {
		return this.data().getInteger("chance");
	}
	
	default void incrementChance() {
		this.data().setInteger("chance", getChance() + 1);
	}
	
	default void setChance(int chance) {
		this.data().setInteger("chance", chance);
	}
	
	default int getIntelligence() {
		return this.data().getInteger("intelligence");
	}
	
	default void incrementIntelligence() {
		this.data().setInteger("intelligence", getIntelligence() + 1);
	}
	
	default void setIntelligence(int intelligence) {
		this.data().setInteger("intelligence", intelligence);
	}
	
	default int getWisdom() {
		return this.data().getInteger("wisdom");
	}
	
	default void incrementWisdom() {
		this.data().setInteger("wisdom", getWisdom() + 1);
	}
	
	default void setWisdom(int wisdom) {
		this.data().setInteger("wisdom", wisdom);
	}
	
	default long getQSpellLastUse() {
		return this.data().getLong("QSpellLastUse");
	}
	
	default void setQSpellLastUse(Long time) {
		this.data().setLong("QSpellLastUse", System.currentTimeMillis());
	}
	
	class Impl implements IPlayerDataCap {

        private NBTTagCompound data;

        public Impl(NBTTagCompound data) {
            this.data = data;
        }

        public Impl() {
            this(new NBTTagCompound());
        }

        public NBTTagCompound data() {
            return this.data;
        }

        public void data(NBTTagCompound data) {
            this.data = data;
        }
    }
}
