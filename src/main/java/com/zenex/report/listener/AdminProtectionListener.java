package com.zenex.report.listener;

import com.zenex.report.ZenexReport;
import com.zenex.report.protection.AdminProtectionManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class AdminProtectionListener implements Listener {
    
    private final ZenexReport plugin;
    
    public AdminProtectionListener(ZenexReport plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        if (!plugin.getConfig().getBoolean("admin-protection.enabled", true)) return;
        if (!player.hasPermission("zenexreport.admin") && !player.hasPermission("zenexreport.moderator")) return;
        
        AdminProtectionManager apm = plugin.getAdminProtectionManager();
        
        if (!apm.hasCode(player.getUniqueId())) {
            // Первый вход - установка кода
            player.sendMessage("§6=== Установка кода администратора ===");
            player.sendMessage("§eПридумайте 4-значный код и введите его в чат:");
            // TODO: Обработка ввода кода
            return;
        }
        
        // Проверка кода при входе
        player.sendMessage(plugin.getConfig().getString("admin-protection.enter-message", "&e🔐 Введите ваш 4-значный код администратора:").replace("&", "§"));
        
        // TODO: Обработка ввода кода через отдельный механизм
    }
}
