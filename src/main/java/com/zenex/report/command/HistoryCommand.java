package com.zenex.report.command;

import com.zenex.report.ZenexReport;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class HistoryCommand implements CommandExecutor {
    
    private final ZenexReport plugin;
    
    public HistoryCommand(ZenexReport plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§cИспользование: /history <игрок>");
            return true;
        }
        
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(plugin.getConfig().getString("messages.player-not-found", "&c❌ Игрок не найден!").replace("&", "§"));
            return true;
        }
        
        List<String> history = plugin.getHistoryManager().getHistory(target);
        
        sender.sendMessage(plugin.getConfig().getString("messages.history-title", "&6=== История наказаний {player} ===")
            .replace("{player}", target.getName())
            .replace("&", "§"));
        
        if (history.isEmpty()) {
            sender.sendMessage(plugin.getConfig().getString("messages.history-none", "&eУ игрока нет наказаний!").replace("&", "§"));
        } else {
            for (String entry : history) {
                sender.sendMessage(entry);
            }
        }
        
        return true;
    }
}
