package com.zenex.report.manager;

import com.zenex.report.ZenexReport;
import org.bukkit.entity.Player;

import java.sql.*;

public class ReportManager {
    
    private final ZenexReport plugin;
    
    public ReportManager(ZenexReport plugin) {
        this.plugin = plugin;
    }
    
    public void createReport(Player reporter, Player reported, String reason) {
        try {
            String sql = "INSERT INTO reports (reporter_uuid, reporter_name, reported_uuid, reported_name, reason) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = plugin.getDatabaseManager().getConnection().prepareStatement(sql);
            stmt.setString(1, reporter.getUniqueId().toString());
            stmt.setString(2, reporter.getName());
            stmt.setString(3, reported.getUniqueId().toString());
            stmt.setString(4, reported.getName());
            stmt.setString(5, reason);
            stmt.executeUpdate();
            stmt.close();
            
            // Уведомление администраторов
            if (plugin.getConfig().getBoolean("reports.notifications", true)) {
                String message = plugin.getConfig().getString("reports.notification-message", 
                    "&e📝 Новая жалоба от &6{reporter} &eна &6{reported}&e! Причина: &e{reason}")
                    .replace("{reporter}", reporter.getName())
                    .replace("{reported}", reported.getName())
                    .replace("{reason}", reason)
                    .replace("&", "§");
                
                for (Player p : plugin.getServer().getOnlinePlayers()) {
                    if (p.hasPermission("zenexreport.admin") || p.hasPermission("zenexreport.moderator")) {
                        p.sendMessage(message);
                    }
                }
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("Failed to create report: " + e.getMessage());
        }
    }
}
