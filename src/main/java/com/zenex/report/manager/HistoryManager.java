package com.zenex.report.manager;

import com.zenex.report.ZenexReport;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryManager {
    
    private final ZenexReport plugin;
    
    public HistoryManager(ZenexReport plugin) {
        this.plugin = plugin;
    }
    
    public List<String> getHistory(Player player) {
        List<String> history = new ArrayList<>();
        String sql = "SELECT type, reason, admin_name, created_at FROM punishments WHERE player_uuid = ? ORDER BY created_at DESC LIMIT 10";
        try (PreparedStatement stmt = plugin.getDatabaseManager().getConnection().prepareStatement(sql)) {
            stmt.setString(1, player.getUniqueId().toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String entry = "&e" + rs.getString("type") + " &7- " + rs.getString("reason") + " &7(Админ: " + rs.getString("admin_name") + ")";
                history.add(entry.replace("&", "§"));
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("Failed to get history: " + e.getMessage());
        }
        return history;
    }
}
