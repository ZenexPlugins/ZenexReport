package com.zenex.report.manager;

import com.zenex.report.ZenexReport;
import java.sql.*;
import java.util.UUID;

public class DatabaseManager {
    
    private final ZenexReport plugin;
    private Connection connection;
    
    public DatabaseManager(ZenexReport plugin) {
        this.plugin = plugin;
    }
    
    public void initialize() {
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:" + plugin.getDataFolder().getAbsolutePath() + 
                         "/" + plugin.getConfig().getString("database.sqlite.file", "ZenexReport.db");
            connection = DriverManager.getConnection(url);
            createTables();
            plugin.getLogger().info("✅ Database connected!");
        } catch (Exception e) {
            plugin.getLogger().severe("❌ Database error: " + e.getMessage());
        }
    }
    
    private void createTables() throws SQLException {
        // Таблица наказаний
        String punishments = """
            CREATE TABLE IF NOT EXISTS punishments (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                player_uuid TEXT NOT NULL,
                player_name TEXT NOT NULL,
                type TEXT NOT NULL,
                reason TEXT,
                admin_uuid TEXT NOT NULL,
                admin_name TEXT NOT NULL,
                duration INTEGER,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                expires_at TIMESTAMP,
                active BOOLEAN DEFAULT 1
            )
        """;
        
        // Таблица жалоб
        String reports = """
            CREATE TABLE IF NOT EXISTS reports (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                reporter_uuid TEXT NOT NULL,
                reporter_name TEXT NOT NULL,
                reported_uuid TEXT NOT NULL,
                reported_name TEXT NOT NULL,
                reason TEXT NOT NULL,
                status TEXT DEFAULT 'PENDING',
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                resolved_at TIMESTAMP,
                resolved_by TEXT
            )
        """;
        
        // Таблица администраторов
        String admins = """
            CREATE TABLE IF NOT EXISTS admins (
                uuid TEXT PRIMARY KEY,
                name TEXT NOT NULL,
                code_hash TEXT NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """;
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(punishments);
            stmt.execute(reports);
            stmt.execute(admins);
        }
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("Failed to close database: " + e.getMessage());
        }
    }
}
