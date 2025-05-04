package com.saurabh.fintrackbackend.service;

import com.saurabh.fintrackbackend.dto.CategorySummaryDTO;
import com.saurabh.fintrackbackend.dto.DashboardSummaryDTO;
import com.saurabh.fintrackbackend.dto.MonthlySummaryDTO;
import com.saurabh.fintrackbackend.dto.TransactionDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface TransactionService {

    List<TransactionDTO> getAllTransactions();

    TransactionDTO getTransactionById(Long transactionId);

    TransactionDTO createTransaction(Long categoryId, TransactionDTO transactionDTO);

    TransactionDTO updateTransaction(Long transactionId, Long categoryId, TransactionDTO transactionDTO);

    String deleteTransaction(Long transactionId);

    DashboardSummaryDTO getDashboardSummary();

    List<CategorySummaryDTO> getCategoryWiseSummary();

    List<MonthlySummaryDTO> getMonthlySummary();
}
