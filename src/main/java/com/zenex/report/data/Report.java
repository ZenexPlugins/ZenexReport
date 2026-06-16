package com.zenex.report.data;

import java.util.UUID;

public class Report {
    
    private final int id;
    private final UUID reporterUuid;
    private final String reporterName;
    private final UUID reportedUuid;
    private final String reportedName;
    private final String reason;
    private final String status;
    private final long createdAt;
    private final long resolvedAt;
    private final String resolvedBy;
    
    public Report(int id, UUID reporterUuid, String reporterName, UUID reportedUuid,
                  String reportedName, String reason, String status, long createdAt,
                  long resolvedAt, String resolvedBy) {
        this.id = id;
        this.reporterUuid = reporterUuid;
        this.reporterName = reporterName;
        this.reportedUuid = reportedUuid;
        this.reportedName = reportedName;
        this.reason = reason;
        this.status = status;
        this.createdAt = createdAt;
        this.resolvedAt = resolvedAt;
        this.resolvedBy = resolvedBy;
    }
    
    public int getId() { return id; }
    public UUID getReporterUuid() { return reporterUuid; }
    public String getReporterName() { return reporterName; }
    public UUID getReportedUuid() { return reportedUuid; }
    public String getReportedName() { return reportedName; }
    public String getReason() { return reason; }
    public String getStatus() { return status; }
    public long getCreatedAt() { return createdAt; }
    public long getResolvedAt() { return resolvedAt; }
    public String getResolvedBy() { return resolvedBy; }
    public boolean isPending() { return "PENDING".equals(status); }
    public boolean isResolved() { return "RESOLVED".equals(status); }
    public boolean isRejected() { return "REJECTED".equals(status); }
}
