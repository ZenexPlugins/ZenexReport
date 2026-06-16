package com.zenex.report.manager;

import com.zenex.report.ZenexReport;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

public class PunishmentManager {
    
    private final ZenexReport plugin;
    
    public PunishmentManager(ZenexReport plugin) {
        this.plugin = plugin;
    }
    
    public void punish(Player target, Player admin, String type, String reason, int duration) {
        try {
            String sql = "INSERT INTO punishments (player_uuid, player_name, type, reason, admin_uuid, admin_name, duration, expires_at) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, datetime('now', '+' || ? || ' seconds'))";
            PreparedStatement stmt = plugin.getDatabaseManager().getConnection().prepareStatement(sql);
            stmt.setString(1, target.getUniqueId().toString());
            stmt.setString(2, target.getName());
            stmt.setString(3, type);
            stmt.setString(4, reason);
            stmt.setString(5, admin.getUniqueId().toString());
            stmt.setString(6, admin.getName());
            stmt.setInt(7, duration);
            stmt.setInt(8, duration);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            plugin.getLogger().severe("Failed to punish: " + e.getMessage());
        }
    }
    
    public boolean isBanned(Player player) {
        String sql = "SELECT 1 FROM punishments WHERE player_uuid = ? AND type = 'BAN' AND active = 1 AND expires_at > datetime('now')";
        try (PreparedStatement stmt = plugin.getDatabaseManager().getConnection().prepareStatement(sql)) {
            stmt.setString(1, player.getUniqueId().toString());
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean isMuted(Player player) {
        String sql = "SELECT 1 FROM punishments WHERE player_uuid = ? AND type = 'MUTE' AND active = 1 AND expires_at > datetime('now')";
        try (PreparedStatement stmt = plugin.getDatabaseManager().getConnection().prepareStatement(sql)) {
            stmt.setString(1, player.getUniqueId().toString());
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }
}
