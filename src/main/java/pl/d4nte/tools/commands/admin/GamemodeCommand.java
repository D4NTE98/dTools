package pl.d4nte.tools.commands.admin;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.d4nte.tools.utils.ChatUtils;

public class GamemodeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("d4nte.tools.gamemode")) {
            sender.sendMessage(ChatUtils.color("&4&l⚠ &cBrak uprawnień! &8(&7d4nte.tools.gamemode&8)"));
            return false;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatUtils.color("&cTylko gracze mogą używać tej komendy!"));
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            sender.sendMessage(ChatUtils.color("&cUżycie: /gamemode <survival/creative/adventure/spectator>"));
            return true;
        }

        GameMode gameMode = null;
        String input = args[0].toLowerCase();

        switch (input) {
            case "survival":
            case "s":
            case "0":
                gameMode = GameMode.SURVIVAL;
                break;
            case "creative":
            case "c":
            case "1":
                gameMode = GameMode.CREATIVE;
                break;
            case "adventure":
            case "a":
            case "2":
                gameMode = GameMode.ADVENTURE;
                break;
            case "spectator":
            case "sp":
            case "3":
                gameMode = GameMode.SPECTATOR;
                break;
            default:
                sender.sendMessage(ChatUtils.color("&cNiepoprawny tryb gry! Użyj: survival, creative, adventure, spectator"));
                return true;
        }

        player.setGameMode(gameMode);
        player.sendMessage(ChatUtils.color("&aZmieniono tryb gry na &e" + gameMode.toString().toLowerCase()));
        return true;
    }
}
