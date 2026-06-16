package com.zenex.report.protection;

import com.zenex.report.ZenexReport;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AdminProtectionManager {
    
    private final ZenexReport plugin;
    private final Map<UUID, Integer> attempts = new HashMap<>();
    private final Map<UUID, Long> blockTime = new HashMap<>();
    
    public AdminProtectionManager(ZenexReport plugin) {
        this.plugin = plugin;
    }
    
    public boolean hasCode(UUID uuid) {
        String sql = "SELECT 1 FROM admins WHERE uuid = ?";
        try (PreparedStatement stmt = plugin.getDatabaseManager().getConnection().prepareStatement(sql)) {
            stmt.setString(1, uuid.toString());
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean setCode(UUID uuid, String name, String code) {
        String hashed = BCrypt.hashpw(code, BCrypt.gensalt());
        String sql = "INSERT OR REPLACE INTO admins (uuid, name, code_hash) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = plugin.getDatabaseManager().getConnection().prepareStatement(sql)) {
            stmt.setString(1, uuid.toString());
            stmt.setString(2, name);
            stmt.setString(3, hashed);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            plugin.getLogger().severe("Failed to set admin code: " + e.getMessage());
            return false;
        }
    }
    
    public boolean validateCode(UUID uuid, String code) {
        String sql = "SELECT code_hash FROM admins WHERE uuid = ?";
        try (PreparedStatement stmt = plugin.getDatabaseManager().getConnection().prepareStatement(sql)) {
            stmt.setString(1, uuid.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return BCrypt.checkpw(code, rs.getString("code_hash"));
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("Failed to validate code: " + e.getMessage());
        }
        return false;
    }
    
    public void addAttempt(UUID uuid) {
        attempts.put(uuid, attempts.getOrDefault(uuid, 0) + 1);
    }
    
    public int getAttempts(UUID uuid) {
        return attempts.getOrDefault(uuid, 0);
    }
    
    public void resetAttempts(UUID uuid) {
        attempts.remove(uuid);
    }
    
    public boolean isBlocked(UUID uuid) {
        if (!blockTime.containsKey(uuid)) return false;
        long now = System.currentTimeMillis();
        long blockUntil = blockTime.get(uuid);
        if (now >= blockUntil) {
            blockTime.remove(uuid);
            return false;
        }
        return true;
    }
    
    public void block(UUID uuid, int seconds) {
        blockTime.put(uuid, System.currentTimeMillis() + (seconds * 1000L));
    }
    
    public long getBlockTimeRemaining(UUID uuid) {
        if (!blockTime.containsKey(uuid)) return 0;
        long remaining = blockTime.get(uuid) - System.currentTimeMillis();
        return Math.max(0, remaining / 1000);
    }
}
