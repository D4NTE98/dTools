CREATE TABLE IF NOT EXISTS d4nte_tools_punishments (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    player_uuid VARCHAR(36),
    player_name VARCHAR(16),
    type VARCHAR(10), -- BAN / MUTE
    reason TEXT,
    admin VARCHAR(16),
    expiry BIGINT, -- Czas wygaśnięcia kary (0 = perm)
    active BOOLEAN DEFAULT 1
);
