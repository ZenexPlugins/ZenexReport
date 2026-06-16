package com.zenex.report.command;

import com.zenex.report.ZenexReport;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AdminCommand implements CommandExecutor {
    
    private final ZenexReport plugin;
    
    public AdminCommand(ZenexReport plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("zenexreport.admin")) {
            sender.sendMessage(plugin.getConfig().getString("messages.no-permission", "&c❌ У вас нет прав!").replace("&", "§"));
            return true;
        }
        
        if (args.length == 0) {
            sender.sendMessage("§cИспользование: /zp <reload|stats|protection|admincode>");
            return true;
        }
        
        switch (args[0].toLowerCase()) {
            case "reload":
                plugin.reloadConfig();
                sender.sendMessage(plugin.getConfig().getString("messages.reload", "&a✅ ZenexReport перезагружен!").replace("&", "§"));
                break;
                
            case "stats":
                sender.sendMessage("§6=== Статистика ZenexReport ===");
                sender.sendMessage("§7Плагин активен!");
                break;
                
            case "protection":
                boolean enabled = plugin.getConfig().getBoolean("admin-protection.enabled", true);
                sender.sendMessage("§6Защита администрации: " + (enabled ? "§aВКЛЮЧЕНА" : "§cВЫКЛЮЧЕНА"));
                break;
                
            default:
                sender.sendMessage("§cНеизвестная команда!");
        }
        
        return true;
    }
}
