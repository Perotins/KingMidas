package me.perotin.kingmidas.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.perotin.kingmidas.KingMidas;
import me.perotin.kingmidas.events.KingMidasLeaveEvent;

public class KingMidasCommand implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {
		ItemStack helm = createMidas(Material.GOLD_HELMET, "Midas Helmet");
		ItemStack chest = createMidas(Material.GOLD_CHESTPLATE, "Midas Chestplate");
		ItemStack leg = createMidas(Material.GOLD_LEGGINGS, "Midas Leggings");
		ItemStack boots = createMidas(Material.GOLD_BOOTS, "Midas Boots");

		if(!(s instanceof Player) && args.length == 0){
			s.sendMessage("Player only");
			return true;
		}
		if(args.length > 2){
			s.sendMessage(ChatColor.GOLD + "/kingmidas <player>");
			return true;
		}
		if(args.length == 0){
			Player p = (Player) s;
			if(!p.hasPermission("kingmidas.use")){
				p.sendMessage(ChatColor.RED + "No permission!");
				return true;
			}
			if(KingMidas.instance.kingMidas.contains(p.getUniqueId())){
				p.sendMessage(ChatColor.GOLD + "Your power starts to fade...");
				KingMidas.instance.kingMidas.remove(p.getUniqueId());
				Bukkit.getPluginManager().callEvent(new KingMidasLeaveEvent(p));

				return true;
			}
			KingMidas.instance.kingMidas.add(p.getUniqueId());
			p.sendMessage(ChatColor.GOLD + "You feel a rush of power overcome your body...");
			Bukkit.getScheduler().scheduleSyncDelayedTask(KingMidas.instance, new Runnable(){

				@SuppressWarnings("deprecation")
				@Override
				public void run() {
					p.sendMessage(ChatColor.GOLD +"Everything you touch will now become GOLD!");
					p.playEffect(p.getLocation(), Effect.BLAZE_SHOOT, 10);
					p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_BURN, 10, 1);
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*500, 2));
					KingMidas.instance.setArmorStand(p);
					if(KingMidas.instance.getConfig().getBoolean("to-equip")){
						p.getInventory().setHelmet(helm);
						p.getInventory().setChestplate(chest);
						p.getInventory().setLeggings(leg);

						p.getInventory().setBoots(boots);
					}
				}

			}, 0*30);

		}else if(args.length == 1){
			Player target = Bukkit.getPlayer(args[0]);
			if(!s.hasPermission("kingmidas.use.others")){
				s.sendMessage(ChatColor.RED + "No permission!");
				return true;
			}
			if(target == null){
				s.sendMessage(ChatColor.RED + args[0] + " is unknown!");
				return true;
			}else{
				if(KingMidas.instance.kingMidas.contains(target.getUniqueId())){
					s.sendMessage(ChatColor.GOLD + target.getName() + " is no longer turning all to gold!");
					target.sendMessage(ChatColor.GOLD + "Your power starts to fade...");
					
					KingMidas.instance.kingMidas.remove(target.getUniqueId());
					Bukkit.getPluginManager().callEvent(new KingMidasLeaveEvent(target));
					return true;
				}
				s.sendMessage(ChatColor.GOLD + target.getName() + " will now turn everything to gold!");
				KingMidas.instance.kingMidas.add(target.getUniqueId());
				target.sendMessage(ChatColor.GOLD + "You feel a rush of power overcome your body...");
				Bukkit.getScheduler().scheduleSyncDelayedTask(KingMidas.instance, new Runnable(){

					@SuppressWarnings("deprecation")
					@Override
					public void run() {
						target.sendMessage(ChatColor.GOLD +"Everything you touch will now become GOLD!");
						target.playEffect(target.getLocation(), Effect.BLAZE_SHOOT, 0);
						target.playSound(target.getLocation(), Sound.ENTITY_BLAZE_BURN, 10, 1);
						KingMidas.instance.setArmorStand(target);
						if(KingMidas.instance.getConfig().getBoolean("to-equip")){
							target.getInventory().setHelmet(helm);
							target.getInventory().setChestplate(chest);
							target.getInventory().setLeggings(leg);

							target.getInventory().setBoots(boots);
						}
					}

				}, 0*30);
			}
		}
		return true;
	}

	public ItemStack createMidas(Material m, String name){
		ItemStack im = new ItemStack(m);
		ItemMeta imm = im.getItemMeta();
		imm.setDisplayName(ChatColor.GOLD + name);
		im.setItemMeta(imm);
		im.addEnchantment(Enchantment.DURABILITY, 1);
		return im;
	}

}
