package com.wayyan.deathswap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitScheduler;

public class DeathSwapLoop implements Runnable {
	private final BukkitScheduler scheduler;
	private final DeathSwapPlugin plugin;
	
	public DeathSwapLoop(DeathSwapPlugin plugin, BukkitScheduler scheduler) {
		this.plugin = plugin;
		this.scheduler = scheduler;
	}
	
    public void scheduleMessage(final long ticks, String message) {
    	this.scheduler.scheduleSyncDelayedTask(this.plugin, new Runnable() {
        	public void run() {
        		Bukkit.broadcastMessage(message);
        	}
        }, ticks);
    }
	
	@Override
    public void run() {  
		this.scheduleMessage(240*20, ChatColor.RED + "Swapping in 1 minute...");
		this.scheduleMessage(290*20, ChatColor.RED + "Swapping in 10 seconds...");
		this.scheduleMessage(291*20, ChatColor.RED + "Swapping in 9 seconds...");
		this.scheduleMessage(292*20, ChatColor.RED + "Swapping in 8 seconds...");
		this.scheduleMessage(293*20, ChatColor.RED + "Swapping in 7 seconds...");
		this.scheduleMessage(294*20, ChatColor.RED + "Swapping in 6 seconds...");
		this.scheduleMessage(295*20, ChatColor.RED + "Swapping in 5 seconds...");
		this.scheduleMessage(296*20, ChatColor.RED + "Swapping in 4 seconds...");
		this.scheduleMessage(297*20, ChatColor.RED + "Swapping in 3 seconds...");
		this.scheduleMessage(298*20, ChatColor.RED + "Swapping in 2 seconds...");
		this.scheduleMessage(299*20, ChatColor.RED + "Swapping in 1 second...");
        this.scheduler.scheduleSyncDelayedTask(this.plugin, new SwapPlayersTask(this.plugin, this.scheduler), 300*20);
    }
}
