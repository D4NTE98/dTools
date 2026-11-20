package pl.d4nte.tools.commands.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.d4nte.tools.managers.usable.VanishManager;
import pl.d4nte.tools.utils.ChatUtils;

public class VanishCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Ta komenda jest tylko dla graczy!");
            return true;
        }
        if (!sender.hasPermission("d4nte.tools.vanish")) {
            sender.sendMessage(ChatUtils.color("&4&l⚠ &cBrak uprawnień! &8(&7d4nte.tools.vanish&8)"));
            return false;
        }
        Player player = (Player) sender;

        VanishManager.toggleVanish(player);
        return true;
    }
}
