package me.perotin.kingmidas;

import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import me.perotin.kingmidas.commands.KingMidasCommand;
import me.perotin.kingmidas.events.KingMidasLeave;
import me.perotin.kingmidas.events.KingMidasMove;
import net.md_5.bungee.api.ChatColor;

public class KingMidas extends JavaPlugin {


	public static KingMidas instance;
	public HashSet<UUID>kingMidas;
	public ScoreboardManager manager;
	public Scoreboard board;
	private ArmorStand stand;
	private Objective objective;

	@Override
	public void onEnable(){
		kingMidas = new HashSet<>();
		instance = this;
		manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
		objective = board.registerNewObjective("test", "dummy");
		objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
		objective.setDisplayName(ChatColor.GOLD + "§lKing Midas");
		saveDefaultConfig();
		getCommand("kingmidas").setExecutor(new KingMidasCommand());
		Bukkit.getPluginManager().registerEvents(new KingMidasMove(), this);
		Bukkit.getPluginManager().registerEvents(new KingMidasLeave(), this);


	
	}

	public void setArmorStand(Player kingMidas){
		stand = kingMidas.getLocation().getWorld().spawn(kingMidas.getLocation(), ArmorStand.class);
		stand.setCustomNameVisible(true);
		stand.setCustomName(ChatColor.GOLD + "§lKing Midas");
		stand.setAI(false);
		stand.setVisible(false);




	}

	public ArmorStand getArmorStand(){
		return stand;
	}
}





