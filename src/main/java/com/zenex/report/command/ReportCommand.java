package com.zenex.report.command;

import com.zenex.report.ZenexReport;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReportCommand implements CommandExecutor {
    
    private final ZenexReport plugin;
    
    public ReportCommand(ZenexReport plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cТолько для игроков!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (args.length < 2) {
            player.sendMessage(plugin.getConfig().getString("messages.report-usage", "&c/report <игрок> <причина>").replace("&", "§"));
            return true;
        }
        
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(plugin.getConfig().getString("messages.player-not-found", "&c❌ Игрок не найден!").replace("&", "§"));
            return true;
        }
        
        if (target.equals(player)) {
            player.sendMessage("§c❌ Вы не можете жаловаться на себя!");
            return true;
        }
        
        StringBuilder reason = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }
        
        plugin.getReportManager().createReport(player, target, reason.toString().trim());
        player.sendMessage(plugin.getConfig().getString("messages.report-success", "&a✅ Жалоба отправлена!").replace("&", "§"));
        
        return true;
    }
}
