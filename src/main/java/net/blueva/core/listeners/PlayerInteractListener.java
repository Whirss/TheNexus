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

package net.blueva.core.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import net.blueva.core.Main;

public class PlayerInteractListener implements Listener {

    private final Main main;

    public PlayerInteractListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void OPI(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String worldname = event.getPlayer().getWorld().getName();
        if(!main.configManager.getWorlds().getBoolean("worlds." + worldname + ".interact")) {
            event.setCancelled(!player.hasPermission("bluecore.worldmanager.bypass.interact"));
        }

    }
}
