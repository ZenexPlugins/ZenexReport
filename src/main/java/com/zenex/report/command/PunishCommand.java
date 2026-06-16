package com.zenex.report.command;

import com.zenex.report.ZenexReport;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PunishCommand implements CommandExecutor {
    
    private final ZenexReport plugin;
    
    public PunishCommand(ZenexReport plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("zenexreport.admin") && !sender.hasPermission("zenexreport.moderator")) {
            sender.sendMessage(plugin.getConfig().getString("messages.no-permission", "&c❌ У вас нет прав!").replace("&", "§"));
            return true;
        }
        
        if (args.length < 3) {
            sender.sendMessage(plugin.getConfig().getString("messages.punish-usage", "&c/punish <игрок> <ban|mute|kick|warn> <причина>").replace("&", "§"));
            return true;
        }
        
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(plugin.getConfig().getString("messages.player-not-found", "&c❌ Игрок не найден!").replace("&", "§"));
            return true;
        }
        
        String type = args[1].toUpperCase();
        StringBuilder reason = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }
        
        Player admin = sender instanceof Player ? (Player) sender : null;
        String adminName = admin != null ? admin.getName() : "Console";
        
        // Наказание
        int duration = plugin.getConfig().getInt("punishments." + type.toLowerCase() + ".duration", 3600);
        
        if (admin != null) {
            plugin.getPunishmentManager().punish(target, admin, type, reason.toString().trim(), duration);
        }
        
        // Выполнение действия
        switch (type) {
            case "BAN":
                String banMsg = plugin.getConfig().getString("punishments.ban.kick-message", "&cВы забанены!")
                    .replace("{reason}", reason.toString().trim())
                    .replace("{admin}", adminName)
                    .replace("&", "§");
                target.kickPlayer(banMsg);
                String banBroadcast = plugin.getConfig().getString("punishments.ban.broadcast-message", "&c{player} &7забанен &c{admin}&7! Причина: &e{reason}")
                    .replace("{player}", target.getName())
                    .replace("{admin}", adminName)
                    .replace("{reason}", reason.toString().trim())
                    .replace("&", "§");
                Bukkit.broadcastMessage(banBroadcast);
                break;
                
            case "KICK":
                String kickMsg = plugin.getConfig().getString("punishments.kick.message", "&cВы были кикнуты!")
                    .replace("{reason}", reason.toString().trim())
                    .replace("{admin}", adminName)
                    .replace("&", "§");
                target.kickPlayer(kickMsg);
                break;
                
            case "WARN":
                String warnMsg = plugin.getConfig().getString("punishments.warn.warn-message", "&c⚠️ Вы получили предупреждение!")
                    .replace("{admin}", adminName)
                    .replace("{reason}", reason.toString().trim())
                    .replace("&", "§");
                target.sendMessage(warnMsg);
                break;
                
            case "MUTE":
                target.sendMessage(plugin.getConfig().getString("punishments.mute.mute-message", "&cВы замьючены!")
                    .replace("{reason}", reason.toString().trim())
                    .replace("{admin}", adminName)
                    .replace("&", "§"));
                break;
        }
        
        sender.sendMessage(plugin.getConfig().getString("messages.punish-success", "&a✅ {player} наказан! Тип: {type}")
            .replace("{player}", target.getName())
            .replace("{type}", type)
            .replace("&", "§"));
        
        return true;
    }
}
