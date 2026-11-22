package com.plugins.listener;

import com.plugins.test.Plugins;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

/**
 * ClassName: item
 * Package: com.plugins.listener
 * Description:
 *
 * @Author 雨汐Lur
 * @Create 2026/1/31 14:07
 * @Version 1.0
 */
public class item implements Listener {

    private final Plugins plugin;

    public item(Plugins plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    void onItemSwitch(PlayerItemHeldEvent event){
        if (!plugin.isSoundEnabled(event.getPlayer().getUniqueId())) return;
        var player = event.getPlayer();
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 0.8F, 1.0F);
    }
}
