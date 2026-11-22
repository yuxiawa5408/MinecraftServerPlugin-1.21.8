package com.plugins.test;

import com.plugins.listener.MyListener;
import com.plugins.listener.Speed;
import com.plugins.listener.item;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

public final class Plugins extends JavaPlugin {

    public final Logger logger = getLogger();

    private HashMap<UUID, PlayerSettings> playerSettings = new HashMap<>();

    private class PlayerSettings {
        boolean memoryEnabled = true;
        boolean soundEnabled = true;
        boolean speedEnabled = true;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        logger.info("插件已被加载！");
        var pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new MyListener(this),this);
        pluginManager.registerEvents(new Speed(this),this);
        pluginManager.registerEvents(new item(this),this);

        getCommand("fun").setExecutor(new FunCommand(this));

        new BukkitRunnable() {
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (isMemoryEnabled(player.getUniqueId())) {
                        updateScoreboard(player);
                    } else {
                        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                    }
                }
            }
        }.runTaskTimer(this, 0, 1);
    }

    private void updateScoreboard(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        Objective obj = board.registerNewObjective("memory", "dummy", "§e§l内存使用率");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory() / 1048576;
        long usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / 1048576;
        int usagePercent = (int) ((usedMemory * 100) / maxMemory);

        String color;
        if (usagePercent < 60) {
            color = "§a";
        } else if (usagePercent < 80) {
            color = "§e";
        } else {
            color = "§c";
        }

        obj.getScore("§7================ ").setScore(7);
        obj.getScore("§f最大内存:").setScore(6);
        obj.getScore("  §a" + maxMemory + " MB").setScore(5);
        obj.getScore("§f已用内存:").setScore(4);
        obj.getScore("  " + color + usedMemory + " MB").setScore(3);
        obj.getScore("§f使用率: " + color + usagePercent + "%").setScore(2);
        obj.getScore("§7================").setScore(1);

        player.setScoreboard(board);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("插件已被卸载！");
    }

    private PlayerSettings getPlayerSettings(UUID playerId) {
        if (!playerSettings.containsKey(playerId)) {
            playerSettings.put(playerId, new PlayerSettings());
        }
        return playerSettings.get(playerId);
    }

    public boolean isMemoryEnabled(UUID playerId) {
        return getPlayerSettings(playerId).memoryEnabled;
    }

    public boolean isSoundEnabled(UUID playerId) {
        return getPlayerSettings(playerId).soundEnabled;
    }

    public boolean isSpeedEnabled(UUID playerId) {
        return getPlayerSettings(playerId).speedEnabled;
    }

    public boolean isJoinEnabled() {
        return true;
    }

    public void setMemoryEnabled(UUID playerId, boolean enabled) {
        getPlayerSettings(playerId).memoryEnabled = enabled;
    }

    public void setSoundEnabled(UUID playerId, boolean enabled) {
        getPlayerSettings(playerId).soundEnabled = enabled;
    }

    public void setSpeedEnabled(UUID playerId, boolean enabled) {
        getPlayerSettings(playerId).speedEnabled = enabled;
    }

    public void removePlayerSettings(UUID playerId) {
        playerSettings.remove(playerId);
    }

}
