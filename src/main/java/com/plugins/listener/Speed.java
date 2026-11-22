package com.plugins.listener;

import com.plugins.test.Plugins;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;


/**
 * ClassName: Speed
 * Package: com.plugins.listener
 * Description:
 *
 * @Author 雨汐Lur
 * @Create 2026/1/31 00:18
 * @Version 1.0
 */
public class Speed implements Listener {

    private final Plugins plugin;

    public Speed(Plugins plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        if (!plugin.isSpeedEnabled(event.getPlayer().getUniqueId())) return;

        if (!event.hasChangedPosition()) {
            return;
        }
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();

        if (to == null) return;

        double dx = to.getX() - from.getX();
        double dz = to.getZ() - from.getZ();
        double horizontalDistance = Math.sqrt(dx * dx + dz * dz);

        double speed = horizontalDistance / 0.05;

        if (speed > 0.1) {
            String speedText = String.format("速度: %.1f m/s", speed);
            player.sendActionBar(speedText);
        }
    }
}
