package com.plugins.listener;

import com.plugins.test.Plugins;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * ClassName: MyListener
 * Package: com.plugins.listener
 * Description:
 *
 * @Author 雨汐Lur
 * @Create 2026/1/30 15:36
 * @Version 1.0
 */
public class MyListener implements Listener {

    private final Plugins plugin;

    public MyListener(Plugins plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event){
        var player = event.getPlayer();
        var location = player.getLocation();
        player.getWorld().playSound(location, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

        event.setJoinMessage("欢迎" + ChatColor.GOLD + player.getName() + ChatColor.WHITE +
                "进入服务器！\n" + ChatColor.GOLD + "服务器中严禁使用" +
                ChatColor.RED + "作弊工具" + ChatColor.GOLD + "，请文明游戏！");
    }
}
