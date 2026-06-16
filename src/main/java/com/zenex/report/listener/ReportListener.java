package com.zenex.report.listener;

import com.zenex.report.ZenexReport;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ReportListener implements Listener {
    
    private final ZenexReport plugin;
    
    public ReportListener(ZenexReport plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (plugin.getPunishmentManager().isMuted(event.getPlayer())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§cВы замьючены и не можете писать в чат!");
        }
    }
}
