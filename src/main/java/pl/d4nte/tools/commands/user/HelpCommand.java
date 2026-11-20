package pl.d4nte.tools.commands.user;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.d4nte.tools.listeners.other.HelpListener;

public class HelpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Ta komenda jest tylko dla graczy!");
            return true;
        }

        sender.sendMessage("test");

        Player player = (Player) sender;
        HelpListener.openHelpGUI(player);
        return true;
    }
}
