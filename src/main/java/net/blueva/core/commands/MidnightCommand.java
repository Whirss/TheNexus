package net.blueva.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.blueva.core.Main;
import net.blueva.core.utils.MessageUtil;

public class MidnightCommand implements CommandExecutor {

    private Main main;

    public MidnightCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        //player:
        if((sender instanceof Player)) {
            if(args.length > 0){
                if(sender.hasPermission("bluecore.*") ||
                        sender.hasPermission("bluecore.time.*") ||
                        sender.hasPermission("bluecore.time.midnight.others")){
                    if(args.length == 1){
                        World world = Bukkit.getWorld(args[0]);
                        if (world == null) {
                            sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.invalid_world"), ((Player) sender)));
                        } else {
                            world.setTime(18000);
                            sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.changed_time"), ((Player) sender))
                                    .replace("%world%", args[0])
                                    .replace("%time%", "Midnight")
                                    .replace("%ticks%", "18000"));
                        }
                    }
                } else {
                    sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.no_perms"), ((Player) sender)));
                }
            }else{
                if(sender.hasPermission("bluecore.*") ||
                        sender.hasPermission("bluecore.time.*") ||
                        sender.hasPermission("bluecore.time.midnight")) {
                    ((Player) sender).getWorld().setTime(18000);
                    sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.changed_time"), ((Player) sender))
                            .replace("%world%", ((Player) sender).getWorld().getName())
                            .replace("%time%", "Midnight")
                            .replace("%ticks%", "18000"));
                } else {
                    sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.no_perms"), ((Player) sender)));
                }
            }
        } else {
            //console:
            if(args.length > 0){
                if(args.length == 1){
                    World world = Bukkit.getWorld(args[0]);
                    if (world == null) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.error.invalid_world")));
                    } else {
                        world.setTime(18000);
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.success.changed_time"))
                                .replace("%world%", args[0])
                                .replace("%time%", "Midnight")
                                .replace("%ticks%", "18000"));
                    }
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.other.use_midnight_command")));
            }
        }
        return true;
    }
}