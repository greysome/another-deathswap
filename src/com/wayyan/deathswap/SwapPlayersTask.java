package com.wayyan.deathswap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class SwapPlayersTask implements Runnable {
	private final BukkitScheduler scheduler;
	private final DeathSwapPlugin plugin;
	
	public SwapPlayersTask(DeathSwapPlugin plugin, BukkitScheduler scheduler) {
		this.plugin = plugin;
		this.scheduler = scheduler;
	}
	
	@Override
	public void run() {
		this.plugin.players = new ArrayList<>(Bukkit.getOnlinePlayers());
		this.plugin.shuffled = new ArrayList<>(Bukkit.getOnlinePlayers());
		
		int n_players = this.plugin.players.size();
		this.plugin.rounds++;
		if (this.plugin.rounds >= n_players)
			this.plugin.rounds = 1;
		
		// Shift elements by ``plugin.rounds`` places
		for (int i = 0; i < this.plugin.rounds; i++) {
			this.plugin.shuffled.add(this.plugin.players.get(i));
			this.plugin.shuffled.remove(0);
		}
		
		Location[] new_locations = new Location[n_players];
		for (int i = 0; i < n_players; i++) {
			new_locations[i] = this.plugin.shuffled.get(i).getLocation();
		}
		
		// Teleport players accordingly
		for (int i = 0; i < n_players; i++) {
			Player p = this.plugin.players.get(i);
			Player p_new = this.plugin.shuffled.get(i);
			p.teleport(new_locations[i]);
			p.sendMessage(ChatColor.GOLD + "You have swapped places with " + ChatColor.BOLD + p_new.getName() + "!");
		}
		
		this.scheduler.runTask(this.plugin, new DeathSwapLoop(this.plugin, this.scheduler));
	}
}
