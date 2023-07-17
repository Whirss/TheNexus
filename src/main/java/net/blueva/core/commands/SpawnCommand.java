/*
 *  ____  _             ____
 * | __ )| |_   _  ___ / ___|___  _ __ ___
 * |  _ \| | | | |/ _ | |   / _ \| '__/ _ \
 * | |_) | | |_| |  __| |__| (_) | | |  __/
 * |____/|_|\__,_|\___|\____\___/|_|  \___|
 *
 * This file is part of Blue Core.
 *
 * Blue Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3 as
 * published by the Free Software Foundation.
 *
 * Blue Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License version 3 for more details.
 *
 * Blue Core plugin developed by Blueva Development.
 * Website: https://blueva.net/
 * GitHub repository: https://github.com/BluevaDevelopment/BlueMenu
 *
 * Copyright (c) 2023 Blueva Development. All rights reserved.
 */

package net.blueva.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;

public class SpawnCommand implements CommandExecutor {

    private Main main;

    public SpawnCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player) && args.length != 1) {
            sender.sendMessage(MessagesUtil.format(null, main.configManager.getLang().getString("messages.other.use_spawn_command")));
            return true;
        }

        Player target;
        if (args.length == 1) {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(MessagesUtil.format((Player) sender, main.configManager.getLang().getString("messages.error.player_offline")));
                return true;
            }
            if (!sender.hasPermission("bluecore.spawn.others")) {
                sender.sendMessage(MessagesUtil.format((Player) sender, main.configManager.getLang().getString("messages.error.no_perms")));
                return true;
            }
        } else {
            target = (Player) sender;
            if (!sender.hasPermission("bluecore.spawn")) {
                sender.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.error.no_perms")));
                return true;
            }
        }

        teleportSpawn(sender, target);
        if (args.length == 1) {
            sender.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.teleported_to_spawn_others").replace("%player%", target.getName())));
        }

        return true;
    }

    private void teleportSpawn(CommandSender sender, Player target) {
        String spawn = main.configManager.getWarps().getString("spawn");
        if (main.configManager.getWarps().isSet("warps." + spawn + ".world")) {
            String world = main.configManager.getWarps().getString("warps." + spawn + ".world");
            double x = Double.parseDouble(main.configManager.getWarps().getString("warps." + spawn + ".x"));
            double y = Double.parseDouble(main.configManager.getWarps().getString("warps." + spawn + ".y"));
            double z = Double.parseDouble(main.configManager.getWarps().getString("warps." + spawn + ".z"));
            float yaw = Float.parseFloat(main.configManager.getWarps().getString("warps." + spawn + ".yaw"));
            float pitch = Float.parseFloat(main.configManager.getWarps().getString("warps." + spawn + ".pitch"));
            Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
            target.teleport(loc);
            target.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.teleported_to_spawn")));
        } else {
            sender.sendMessage(MessagesUtil.format(null, main.configManager.getLang().getString("messages.error.spawn_not_set")));
        }
    }
}