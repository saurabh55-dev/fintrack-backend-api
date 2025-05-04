package com.saurabh.fintrackbackend.dto;

import com.saurabh.fintrackbackend.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Long transactionId;
    private String type;
    private Category category;
    private Double amount;
    private LocalDate date;
    private String description;
}
