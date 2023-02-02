package net.blueva.core.events;

import java.util.Objects;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.blueva.core.Main;
import net.blueva.core.utils.MessageUtil;

public class OnPlayerChatAsync implements Listener {
	
	private final Main main;
	
	public OnPlayerChatAsync(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void OPCA(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		
		if(main.getConfig().getBoolean("chat.antiswear.enabled")) {
			if(!player.hasPermission("bluecore.chat.antiswear.bypass")) {
				for(String blockedWords : main.getConfig().getStringList("chat.antiswear.list")) {
					if(message.toLowerCase().replaceAll("[-_*. ]", "").contains(blockedWords.toLowerCase())) {
						if(Objects.equals(main.getConfig().getString("chat.antiswear.mode"), "replace")) {
							StringBuilder a = new StringBuilder();
							for(int c=0;c<blockedWords.length();c++) {
								a.append("*");
							}
							message = message.replace(blockedWords, a.toString());
						}
						if(Objects.equals(main.getConfig().getString("chat.antiswear.mode"), "block")) {
							player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.info.antiswear_block"), player));
							event.setCancelled(true);
							return;
						}
						
					}
				}
				event.setMessage(message);
			}
		}
		if(!Objects.equals(main.getConfig().getString("chat.format"), "none")) {
			String formated_message = Objects.requireNonNull(main.getConfig().getString("chat.format")).replaceFirst("%player_displayname%", player.getDisplayName()).replaceFirst("%message%", message);
			event.setFormat(MessageUtil.getColorMessage(formated_message, player));
		}
		if(main.getConfig().getBoolean("chat.per_world")) {
			Set<Player> r = event.getRecipients();
		    for (Player pls : Bukkit.getServer().getOnlinePlayers()) {
		      if (!pls.getWorld().getName().equals(player.getWorld().getName()))
		        r.remove(pls); 
		    } 
		}
	}

}
