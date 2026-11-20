package pl.d4nte.tools.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.d4nte.tools.utils.ChatUtils;

public class D4ToolsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatUtils.color("&cTylko gracze mogą używać tej komendy!"));
            return true;
        }

        Player player = (Player) sender;
        player.sendMessage(ChatUtils.color("&3&lⓘ &bPlugin: &3d4nte_tools"));
        player.sendMessage(ChatUtils.color("&3&lⓘ &bAutor: &3D4NTE"));
        player.sendMessage(ChatUtils.color("&3&lⓘ &bWersja: &31.0"));
        return true;
    }
}