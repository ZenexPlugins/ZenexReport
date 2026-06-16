package com.zenex.report.data;

import java.util.UUID;

public class Punishment {
    
    private final int id;
    private final UUID playerUuid;
    private final String playerName;
    private final String type;
    private final String reason;
    private final UUID adminUuid;
    private final String adminName;
    private final long duration;
    private final long createdAt;
    private final long expiresAt;
    private final boolean active;
    
    public Punishment(int id, UUID playerUuid, String playerName, String type, String reason,
                      UUID adminUuid, String adminName, long duration, long createdAt,
                      long expiresAt, boolean active) {
        this.id = id;
        this.playerUuid = playerUuid;
        this.playerName = playerName;
        this.type = type;
        this.reason = reason;
        this.adminUuid = adminUuid;
        this.adminName = adminName;
        this.duration = duration;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.active = active;
    }
    
    public int getId() { return id; }
    public UUID getPlayerUuid() { return playerUuid; }
    public String getPlayerName() { return playerName; }
    public String getType() { return type; }
    public String getReason() { return reason; }
    public UUID getAdminUuid() { return adminUuid; }
    public String getAdminName() { return adminName; }
    public long getDuration() { return duration; }
    public long getCreatedAt() { return createdAt; }
    public long getExpiresAt() { return expiresAt; }
    public boolean isActive() { return active; }
    public boolean isExpired() { return System.currentTimeMillis() > expiresAt; }
}
