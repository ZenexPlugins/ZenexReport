package com.zenex.report.command;

import com.zenex.report.ZenexReport;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReportsCommand implements CommandExecutor {
    
    private final ZenexReport plugin;
    
    public ReportsCommand(ZenexReport plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("zenexreport.admin") && !sender.hasPermission("zenexreport.moderator")) {
            sender.sendMessage(plugin.getConfig().getString("messages.no-permission", "&c❌ У вас нет прав!").replace("&", "§"));
            return true;
        }
        
        sender.sendMessage("§6=== Список жалоб ===");
        sender.sendMessage("§7Используйте GUI для просмотра жалоб");
        sender.sendMessage("§7Команда: §e/reports");
        
        return true;
    }
}
