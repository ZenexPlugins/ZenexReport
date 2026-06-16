package com.zenex.report.listener;

import com.zenex.report.ZenexReport;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PunishmentListener implements Listener {
    
    private final ZenexReport plugin;
    
    public PunishmentListener(ZenexReport plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (plugin.getPunishmentManager().isBanned(event.getPlayer())) {
            String message = plugin.getConfig().getString("punishments.ban.kick-message", "&cВы забанены!");
            event.getPlayer().kickPlayer(message.replace("&", "§"));
        }
    }
}
