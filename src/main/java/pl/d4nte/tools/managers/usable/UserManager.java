package pl.d4nte.tools.managers.usable;

import pl.d4nte.tools.managers.basic.DatabaseManager;
import pl.d4nte.tools.managers.objects.User;

import java.util.HashMap;
import java.util.UUID;

public class UserManager {
    private final HashMap<UUID, User> users = new HashMap<>();
    private final DatabaseManager databaseManager;

    public UserManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public User getUser(UUID uuid, String name) {
        return users.computeIfAbsent(uuid, id -> new User(uuid, name));
    }
}