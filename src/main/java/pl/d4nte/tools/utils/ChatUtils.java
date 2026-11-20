package pl.d4nte.tools.utils;

import net.md_5.bungee.api.ChatColor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtils {
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");

    public static String color(String message) {
        Matcher matcher = HEX_PATTERN.matcher(message);
        while (matcher.find()) {
            String hex = matcher.group(1);
            ChatColor hexColor = ChatColor.of("#" + hex);
            message = message.replace("&#" + hex, hexColor.toString());
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}