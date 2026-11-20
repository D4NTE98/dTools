package pl.d4nte.tools.commands.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.d4nte.tools.managers.basic.PunishmentManager;
import pl.d4nte.tools.managers.usable.UserManager;
import pl.d4nte.tools.managers.objects.User;
import pl.d4nte.tools.utils.ChatUtils;

import java.util.UUID;

public class UnbanCommand implements CommandExecutor {
    private final UserManager userManager;

    public UnbanCommand(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("d4nte.tools.unban")) {
            sender.sendMessage(ChatUtils.color("&4&l⚠ &cBrak uprawnień! &8(&7d4nte.tools.unban&8)"));
            return false;
        }
        if (args.length < 1) {
            sender.sendMessage(ChatUtils.color("&cUżycie: /unban <gracz>"));
            return true;
        }

        UUID uuid = UUID.fromString(args[0]);
        User user = userManager.getUser(uuid, args[0]);

        if (!user.isBanned()) {
            sender.sendMessage(ChatUtils.color("&cTen gracz nie jest zbanowany!"));
            return true;
        }

        PunishmentManager.removePunishment(uuid, "BAN");
        user.setBanned(false);
        sender.sendMessage(ChatUtils.color("&aOdbanowano gracza &e" + args[0]));
        return true;
    }
}
