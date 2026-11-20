package pl.d4nte.tools.commands.admin;
;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.d4nte.tools.utils.ChatUtils;

public class FlyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("d4nte.tools.fly")) {
            sender.sendMessage(ChatUtils.color("&4&l⚠ &cBrak uprawnień! &8(&7d4nte.tools.fly&8)"));
            return false;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatUtils.color("&cTylko gracze mogą używać tej komendy!"));
            return true;
        }

        Player player = (Player) sender;
        boolean flyMode = !player.getAllowFlight();
        player.setAllowFlight(flyMode);
        player.sendMessage(ChatUtils.color("&aTryb latania: " + (flyMode ? "&2włączony" : "&4wyłączony")));
        return true;
    }
}
