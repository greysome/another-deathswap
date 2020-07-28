package com.wayyan.deathswap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class DeathSwapPlugin extends JavaPlugin implements Listener {
	private BukkitScheduler scheduler;
	private Scoreboard board;
	private Objective objective;
	
	public List<Player> players;
	public List<Player> shuffled;
	public int rounds = 0;
	
    @Override
    public void onEnable() {
    	this.getServer().getPluginManager().registerEvents(this, this);
    	this.scheduler = this.getServer().getScheduler();
    	
    	ScoreboardManager manager = this.getServer().getScoreboardManager();
    	this.board = manager.getNewScoreboard();
    	this.objective = board.registerNewObjective("kills", "dummy");
    	objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    	objective.setDisplayName("Kills");
    }

    @Override
    public void onDisable() {
    	
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		p.setScoreboard(this.board);
		this.objective.getScore(p).setScore(0);
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
    	Player p = event.getEntity();
        if (!this.players.contains(p))
            return;
    	int idx = this.shuffled.indexOf(p);
    	
    	Player killer = this.players.get(idx);
    	Score score = this.objective.getScore(killer);
    	score.setScore(score.getScore()+1);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		this.players = new ArrayList<>(Bukkit.getOnlinePlayers());
		this.shuffled = new ArrayList<>(Bukkit.getOnlinePlayers());

    	String name = command.getName();
    	if (name.equals("dsstart")) {
    		// Reset scores
    		for (Player p : this.players) {
    			Score score = this.objective.getScore(p);
    			score.setScore(0);
    		}
    		Bukkit.broadcastMessage(ChatColor.GREEN + "Started game! All the best :)");
            this.scheduler.runTask(this, new DeathSwapLoop(this, this.scheduler));
    	}
    	else if (name.equals("dsstop")) {
    		Bukkit.broadcastMessage(ChatColor.BLUE + "Game stopped!");
        	this.scheduler.cancelTasks(this);
    	}
    	return true;
    }
}
