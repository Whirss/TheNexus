package net.blueva.core.commands;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand implements CommandExecutor {
    private final Main main;

    public TeleportCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player splayer = null;
        if(sender instanceof Player) {
            splayer = (Player) sender;
        }

        if (!sender.hasPermission("bluecore.teleport")) {
            sender.sendMessage(MessagesUtil.format(splayer, main.configManager.getLang().getString("messages.error.no_perms")));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(MessagesUtil.format(splayer, main.configManager.getLang().getString("messages.other.use_teleport_command")));
            return true;
        }

        if (args.length == 1) {
            // Teleport player to another player
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                if (sender instanceof Player) {

                    if(splayer == target) {
                        sender.sendMessage(MessagesUtil.format(splayer, main.configManager.getLang().getString("messages.error.self_teleport")));
                        return true;
                    }

                    splayer.teleport(target);
                    sender.sendMessage(MessagesUtil.format(splayer, main.configManager.getLang().getString("messages.success.teleported_to_player").replace("%player%", target.getName())));
                } else {
                    sender.sendMessage(MessagesUtil.format(null, main.configManager.getLang().getString("messages.other.use_teleport_command")));
                }
            } else {
                sender.sendMessage(MessagesUtil.format(splayer, main.configManager.getLang().getString("messages.error.player_offline")));
            }
        } else if (args.length == 2) {
            // Teleport one player to another player
            if (!sender.hasPermission("bluecore.teleport.others")) {
                sender.sendMessage(MessagesUtil.format(splayer, main.configManager.getLang().getString("messages.error.no_perms")));
                return true;
            }

            Player player1 = Bukkit.getPlayer(args[0]);
            Player player2 = Bukkit.getPlayer(args[1]);

            if(player1 == player2) {
                sender.sendMessage(MessagesUtil.format(splayer, main.configManager.getLang().getString("messages.error.same_players")));
                return true;
            }

            if (player1 != null && player2 != null) {
                player1.teleport(player2);
                sender.sendMessage(MessagesUtil.format(splayer, main.configManager.getLang().getString("messages.success.teleported_to_player_others").replace("%player1%", player1.getName()).replace("%player2%", player2.getName())));
            } else {
                sender.sendMessage(MessagesUtil.format(splayer, main.configManager.getLang().getString("messages.error.both_players_online")));
            }
        } else if (args.length == 3) {
            // Teleport sender to coordinates
            if (sender instanceof Player) {
                double x = Double.parseDouble(args[0]);
                double y = Double.parseDouble(args[1]);
                double z = Double.parseDouble(args[2]);
                splayer.teleport(new Location(((Player) sender).getWorld(), x, y, z, 0, 0));
                sender.sendMessage(MessagesUtil.format(splayer, main.configManager.getLang().getString("messages.success.teleported_to_coord")
                        .replace("%x%", Double.toString(x))
                        .replace("%y%", Double.toString(y))
                        .replace("%z%", Double.toString(z))));
            } else {
                sender.sendMessage(MessagesUtil.format(null, main.configManager.getLang().getString("messages.other.use_teleport_command")));
            }
        } else if (args.length == 4) {
            // Teleport player to coordinates
            if (!sender.hasPermission("bluecore.teleport.others")) {
                sender.sendMessage(MessagesUtil.format(splayer, main.configManager.getLang().getString("messages.error.no_permission")));
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {

                double x = Double.parseDouble(args[1]);
                double y = Double.parseDouble(args[2]);
                double z = Double.parseDouble(args[3]);
                target.teleport(new Location(target.getWorld(), x, y, z, 0, 0));
                sender.sendMessage(MessagesUtil.format(splayer, main.configManager.getLang().getString("messages.success.teleported_to_coord")
                        .replace("%x%", Double.toString(x))
                        .replace("%y%", Double.toString(y))
                        .replace("%z%", Double.toString(z))
                        .replace("%player%", target.getName())));
            } else {
                sender.sendMessage(MessagesUtil.format(splayer, main.configManager.getLang().getString("messages.error.player_offline")));
            }
        } else {
            sender.sendMessage(MessagesUtil.format(null, main.configManager.getLang().getString("messages.other.use_teleport_command")));
        }
        return true;
    }
}
