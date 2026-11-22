package com.plugins.test;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * ClassName: FunCommand
 * Package: com.plugins.test
 * Description:
 *
 * @Author 雨汐Lur
 * @Create 2026/2/1 23:27
 * @Version 1.0
 */
public class FunCommand implements CommandExecutor {

    private final Plugins plugin;

    public FunCommand(Plugins plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("只有玩家可以使用此命令！");
            return true;
        }

        Player player = (Player) sender;
        UUID playerId = player.getUniqueId();

        if (args.length == 0) {
            player.sendMessage("§6===== 你的功能设置 =====");
            player.sendMessage("§e内存显示: " + getStatus(plugin.isMemoryEnabled(playerId)));
            player.sendMessage("§e物品栏音效: " + getStatus(plugin.isSoundEnabled(playerId)));
            player.sendMessage("§e速度显示: " + getStatus(plugin.isSpeedEnabled(playerId)));
            player.sendMessage("§e进服播报: §a全局功能");
            player.sendMessage("§6/fun <功能> <on/off> §7切换你的个人设置");
            player.sendMessage("§6功能列表: memory, sound, speed");
            return true;
        }

        if (args.length != 2) {
            player.sendMessage("§c用法: /fun <功能> <on/off>");
            return true;
        }

        String function = args[0].toLowerCase();
        String action = args[1].toLowerCase();

        if (!action.equals("on") && !action.equals("off")) {
            player.sendMessage("§c第二个参数只能是 on 或 off");
            return true;
        }

        boolean enable = action.equals("on");

        switch (function) {
            case "memory":
                plugin.setMemoryEnabled(playerId, enable);
                player.sendMessage(enable ? "§a已开启内存显示" : "§c已关闭内存显示");
                break;

            case "sound":
                plugin.setSoundEnabled(playerId, enable);
                player.sendMessage(enable ? "§a已开启物品栏音效" : "§c已关闭物品栏音效");
                break;

            case "speed":
                plugin.setSpeedEnabled(playerId, enable);
                player.sendMessage(enable ? "§a已开启速度显示" : "§c已关闭速度显示");
                break;

            case "join":
                player.sendMessage("§c进服播报是全局功能，无法单独开关");
                break;

            default:
                player.sendMessage("§c未知功能！可用功能: memory, sound, speed");
                break;
        }

        return true;
    }

    private String getStatus(boolean enabled) {
        return enabled ? "§a开启" : "§c关闭";
    }
}
