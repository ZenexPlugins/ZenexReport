package com.zenex.report.data;

import java.util.UUID;

public class AdminData {
    
    private final UUID uuid;
    private final String name;
    private final String codeHash;
    private final long createdAt;
    private boolean codeVerified;
    
    public AdminData(UUID uuid, String name, String codeHash, long createdAt) {
        this.uuid = uuid;
        this.name = name;
        this.codeHash = codeHash;
        this.createdAt = createdAt;
        this.codeVerified = false;
    }
    
    public UUID getUuid() { return uuid; }
    public String getName() { return name; }
    public String getCodeHash() { return codeHash; }
    public long getCreatedAt() { return createdAt; }
    public boolean isCodeVerified() { return codeVerified; }
    public void setCodeVerified(boolean verified) { this.codeVerified = verified; }
}
