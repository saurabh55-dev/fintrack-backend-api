package com.saurabh.fintrackbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class DashboardSummaryDTO {
    private double totalIncome;
    private double totalExpense;
    private double balance;
}
