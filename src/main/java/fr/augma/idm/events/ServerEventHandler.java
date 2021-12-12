package fr.augma.idm.events;

import java.text.DecimalFormat;
import java.util.Random;

import fr.augma.idm.IDM;
import fr.augma.idm.capability.IPlayerDataCap;
import fr.augma.idm.capability.PlayerDataCapProvider;
import fr.augma.idm.events.custom.UseQSpellEvent;
import fr.augma.idm.packet.OpenClassGuiPacket;
import fr.augma.idm.packet.PacketCriticParticle;
import fr.augma.idm.packet.setRpcPacket;
import fr.augma.idm.util.ArtefactUtils;
import fr.augma.idm.util.RequireUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

@Mod.EventBusSubscriber
public class ServerEventHandler {
	
	private static IPlayerDataCap cap;
	
	public static void refreshAbility(EntityPlayer player) {
		refreshStrength(player);
		refreshSpeed(player);
		refreshHealth(player);
	}
	
	private static void refreshStrength(EntityPlayer player) {
		IAttributeInstance attribute = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		cap = PlayerDataCapProvider.get(player);
		for(AttributeModifier attm : attribute.getModifiers()) {
			if(attm.getName().equalsIgnoreCase("IDMattack")) {
				attribute.removeModifier(attm);
			}
		}
		AttributeModifier modifier = new AttributeModifier("IDMattack", cap.getStrength() / 2D, 0);
		attribute.applyModifier(modifier);
	}

	private static void refreshSpeed(EntityPlayer player) {
		IAttributeInstance attribute = player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
		cap = PlayerDataCapProvider.get(player);
		for(AttributeModifier attm : attribute.getModifiers()) {
			if(attm.getName().equalsIgnoreCase("IDMspeed")) {
				attribute.removeModifier(attm);
			}
		}
		double speed = (0.1D / cap.getMaxAgility()) * cap.getAgility();
		AttributeModifier modifier = new AttributeModifier("IDMspeed", speed, 0);
		attribute.applyModifier(modifier);
	}

	private static void refreshHealth(EntityPlayer player) {
		IAttributeInstance attribute = player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
		cap = PlayerDataCapProvider.get(player);
		for(AttributeModifier attm : attribute.getModifiers()) {
			if(attm.getName().equalsIgnoreCase("IDMhealth")) {
				attribute.removeModifier(attm);
			}
		}
		double health = 2.0D * cap.getVitality();
		AttributeModifier modifier = new AttributeModifier("IDMhealth", health, 0);
		attribute.applyModifier(modifier);
	}
	
	@SubscribeEvent
    public static void onAttachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(PlayerDataCapProvider.NAME, new PlayerDataCapProvider());
        }
    }
	
	@SubscribeEvent
    public static void onPlayerCloned(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            if (event.getOriginal().hasCapability(PlayerDataCapProvider.CAPABILITY, null)) {
                cap = PlayerDataCapProvider.get(event.getOriginal());
                IPlayerDataCap newCap = PlayerDataCapProvider.get(event.getEntityPlayer());
                newCap.data(cap.data().copy());
            }
        }
    }
	
	@SubscribeEvent
	public static void onPlayerRespawn(PlayerRespawnEvent e) {
		PlayerDataCapProvider.Sync(e.player);
		refreshAbility(e.player);
	}
	
	@SubscribeEvent
	public static void onPlayerLog(PlayerLoggedInEvent  e) {
		if(!e.player.world.isRemote) {
			EntityPlayer player = (EntityPlayer) e.player;
			PlayerDataCapProvider.Sync(player);
			cap = PlayerDataCapProvider.get(player);
			if(cap.getClasse() == null) {
				IDM.network.sendTo(new OpenClassGuiPacket(), (EntityPlayerMP) player);
			} else {
				IDM.network.sendTo(new setRpcPacket(player.getName(), cap.getClasse().getClassName()), (EntityPlayerMP) player);
			}
			refreshAbility(player);
		}
	}
	
	@SubscribeEvent
	public static void onPlayerHitEntity(AttackEntityEvent e) {
		if(!e.getEntityPlayer().world.isRemote) {
			EntityPlayerMP p = (EntityPlayerMP) e.getEntityPlayer();
			if(p.canUseCommand(4, "require")) {
				return;
			}
			boolean[] result = RequireUtils.canHit(p.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND), p);
			
			for(boolean b : result) {
				if(b) {
					p.sendMessage(new TextComponentString("Tu n'as pas le droit d'utiliser cet item."));
					e.setCanceled(b);
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onCrit(CriticalHitEvent e) {
		e.setResult(Event.Result.DENY);
	}
	
	@SubscribeEvent
	public static void onLivingHurt(LivingHurtEvent e) {
		if(e.getSource().getTrueSource() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) e.getSource().getTrueSource();
			cap = PlayerDataCapProvider.get(player);
			float crit = cap.getChance() * 0.5f;
			Random r = new Random();
			if(r.nextInt(100) <= crit) {
				e.setAmount(e.getAmount() * 1.5f + r.nextFloat() / 2.0f);
				IDM.network.sendTo(new PacketCriticParticle(e.getEntity()), (EntityPlayerMP) player);
			}
		}
	}
	
	@SubscribeEvent
	public static void onPlayerUseItem(LivingEntityUseItemEvent.Start e) {
		if(e.getEntityLiving() instanceof EntityPlayer && !e.getEntityLiving().world.isRemote) {
			if(e.getItem().getItem() instanceof ItemBow || e.getItem().getItem() instanceof ItemFood) {
				EntityPlayer p = (EntityPlayer) e.getEntityLiving();
				if(p.canUseCommand(4, "require")) {
					return;
				}
				boolean[] result = RequireUtils.canHitTwoHands(p.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND), p.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND), p);
				
				for(boolean b : result) {
					if(b) {
						p.sendMessage(new TextComponentString("Tu n'as pas le droit d'utiliser cet item."));
						e.setCanceled(b);
					}
				}
			}
			
			if(ArtefactUtils.isArtefact(e.getItem())) {
				if(ArtefactUtils.getDurability(e.getItem()).willBeDestroy()) {
					EntityPlayer p = (EntityPlayer) e.getEntityLiving();
					p.sendMessage(new TextComponentString("Votre artefact va être détruit"));
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void useQspell(UseQSpellEvent e) {
		if(e.canUseSpell()) {
			e.getSpell().activateEffect(e.getPlayer());
		} else {
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(1);
			e.getPlayer().sendMessage(new TextComponentString("Vous pourrez utiliser votre spell dans " + df.format(e.getRemainingTimeInSecond()) + " secondes"));
		}
	}
}
