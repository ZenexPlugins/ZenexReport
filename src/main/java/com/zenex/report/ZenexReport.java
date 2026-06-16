package com.zenex.report;

import com.zenex.report.command.*;
import com.zenex.report.listener.*;
import com.zenex.report.manager.*;
import com.zenex.report.protection.AdminProtectionManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ZenexReport extends JavaPlugin {
    
    private static ZenexReport instance;
    private DatabaseManager databaseManager;
    private PunishmentManager punishmentManager;
    private ReportManager reportManager;
    private AdminProtectionManager adminProtectionManager;
    private HistoryManager historyManager;
    
    @Override
    public void onEnable() {
        instance = this;
        
        saveDefaultConfig();
        saveResource("messages.yml", false);
        
        // Инициализация менеджеров
        databaseManager = new DatabaseManager(this);
        databaseManager.initialize();
        
        punishmentManager = new PunishmentManager(this);
        reportManager = new ReportManager(this);
        adminProtectionManager = new AdminProtectionManager(this);
        historyManager = new HistoryManager(this);
        
        // Регистрация команд
        getCommand("report").setExecutor(new ReportCommand(this));
        getCommand("reports").setExecutor(new ReportsCommand(this));
        getCommand("punish").setExecutor(new PunishCommand(this));
        getCommand("history").setExecutor(new HistoryCommand(this));
        getCommand("zp").setExecutor(new AdminCommand(this));
        
        // Регистрация слушателей
        Bukkit.getPluginManager().registerEvents(new ReportListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PunishmentListener(this), this);
        Bukkit.getPluginManager().registerEvents(new AdminProtectionListener(this), this);
        
        getLogger().info("✅ ZenexReport v" + getDescription().getVersion() + " enabled!");
        getLogger().info("🛡️ Система модерации ZenexReport активна!");
    }
    
    @Override
    public void onDisable() {
        if (databaseManager != null) {
            databaseManager.close();
        }
        getLogger().info("ZenexReport disabled!");
    }
    
    public static ZenexReport getInstance() {
        return instance;
    }
    
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
    
    public PunishmentManager getPunishmentManager() {
        return punishmentManager;
    }
    
    public ReportManager getReportManager() {
        return reportManager;
    }
    
    public AdminProtectionManager getAdminProtectionManager() {
        return adminProtectionManager;
    }
    
    public HistoryManager getHistoryManager() {
        return historyManager;
    }
}
