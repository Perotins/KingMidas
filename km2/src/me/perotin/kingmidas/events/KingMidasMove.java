package me.perotin.kingmidas.events;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import me.perotin.kingmidas.KingMidas;


public class KingMidasMove implements Listener {

	HashMap<Location, Material> blockUndo = new HashMap<>();

	@EventHandler
	public void onMove(PlayerMoveEvent e){
		Player p = e.getPlayer();
		Location loc = p.getLocation();
		Block block = loc.getWorld().getBlockAt(loc).getRelative(BlockFace.DOWN);
		Material bm = block.getType();
		if(!KingMidas.instance.kingMidas.contains(p.getUniqueId())) return;
		KingMidas.instance.getArmorStand().teleport(p.getLocation());
		for(String m : KingMidas.instance.getConfig().getStringList("blocked-blocks")){

			if(bm.toString().equalsIgnoreCase(m)){
				return;
			}
		}
		blockUndo.put(block.getLocation(), bm);
		block.setType(Material.GOLD_BLOCK);
		Bukkit.getScheduler().scheduleSyncDelayedTask(KingMidas.instance, new Runnable(){

			@Override
			public void run() {
				block.setType(blockUndo.get(block.getLocation()));


			}

		}, 20*10);


	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(!KingMidas.instance.kingMidas.contains(p.getUniqueId())) return;

		Material m = p.getInventory().getItemInMainHand().getType();
		Block b = e.getClickedBlock();
		for(String im : KingMidas.instance.getConfig().getStringList("blocked-blocks")){
			if(b != null){
				if(b.getType().toString().equalsIgnoreCase(im)){
					return;
				}
			}
		}
		blockUndo.put(b.getLocation(), b.getType());
		b.setType(Material.GOLD_BLOCK);
		Bukkit.getScheduler().scheduleSyncDelayedTask(KingMidas.instance, new Runnable(){

			@Override
			public void run() {
				b.setType(blockUndo.get(b.getLocation()));


			}

		}, 20*10);
		if(m.toString().contains("SWORD") && !m.toString().contains("GOLD")){
			p.getInventory().getItemInMainHand().setType(Material.GOLD_SWORD);
			return;
		}else if(m.toString().contains("PICKAXE") && !m.toString().contains("GOLD")){
			p.getInventory().getItemInMainHand().setType(Material.GOLD_PICKAXE);
			return;

		}else if(m.toString().contains("SPADE") && !m.toString().contains("GOLD")){
			p.getInventory().getItemInMainHand().setType(Material.GOLD_SPADE);
			return;

		}else if(m.toString().contains("HOE") && !m.toString().contains("GOLD")){
			p.getInventory().getItemInMainHand().setType(Material.GOLD_HOE);
			return;

		}else if(m.toString().contains("AXE") && !m.toString().contains("GOLD")){
			p.getInventory().getItemInMainHand().setType(Material.GOLD_AXE);
			return;

		}
	}


}

