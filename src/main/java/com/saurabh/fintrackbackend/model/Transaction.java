package com.saurabh.fintrackbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity @Data @AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @NotBlank(message = "Transaction type cannot be blank")
    @Size(min = 6, max = 20, message = "Transaction type must be between 6 and 20 characters")
    @Column(nullable = false)
    private String type; // income or expense

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private LocalDate date;
    private String description;
}
