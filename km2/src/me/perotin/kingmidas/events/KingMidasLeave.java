package me.perotin.kingmidas.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import me.perotin.kingmidas.KingMidas;

public class KingMidasLeave implements Listener {

	@EventHandler
	public void leaveMidas(KingMidasLeaveEvent e){
		Player p =e.getMidas();
		p.getInventory().setHelmet(new ItemStack(Material.AIR));
		p.getInventory().setChestplate(new ItemStack(Material.AIR));
		p.getInventory().setLeggings(new ItemStack(Material.AIR));
		p.getInventory().setBoots(new ItemStack(Material.AIR));
		KingMidas.instance.getArmorStand().remove();
		for (PotionEffect effect : p.getActivePotionEffects())
			p.removePotionEffect(effect.getType());

	}

	@EventHandler
	public void takeArmorOff(InventoryClickEvent e){
		if(e.getWhoClicked() instanceof Player){
			Player p = (Player) e.getWhoClicked();
			if(KingMidas.instance.kingMidas.contains(p.getUniqueId())){
				if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getType().toString().contains("GOLD")){
					e.setCancelled(true);
				}
			}
		}
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
