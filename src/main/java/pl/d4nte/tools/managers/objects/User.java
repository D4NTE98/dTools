package pl.d4nte.tools.managers.objects;

import java.util.UUID;

public class User {
    private final UUID uuid;
    private String name;
    private boolean isBanned;
    private boolean isMuted;

    public User(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.isBanned = false;
        this.isMuted = false;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public boolean isMuted() {
        return isMuted;
    }

    public void setMuted(boolean muted) {
        isMuted = muted;
    }
}