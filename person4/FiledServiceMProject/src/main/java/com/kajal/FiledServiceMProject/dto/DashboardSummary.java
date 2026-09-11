package com.kajal.FiledServiceMProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DashboardSummary {
    private long newCount;
    private long assignedCount;
    private long inProgressCount;
    private long onHoldCount;
    private long completedCount;
    private long closedCount;
    private long breachedCount;
    private long atRiskCount;
}
