package com.saurabh.fintrackbackend.service.impl;

import com.saurabh.fintrackbackend.dto.CategorySummaryDTO;
import com.saurabh.fintrackbackend.dto.DashboardSummaryDTO;
import com.saurabh.fintrackbackend.dto.MonthlySummaryDTO;
import com.saurabh.fintrackbackend.dto.TransactionDTO;
import com.saurabh.fintrackbackend.exception.ResourceNotFoundException;
import com.saurabh.fintrackbackend.model.Category;
import com.saurabh.fintrackbackend.model.Transaction;
import com.saurabh.fintrackbackend.repository.CategoryRepository;
import com.saurabh.fintrackbackend.repository.TransactionRepository;
import com.saurabh.fintrackbackend.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<TransactionDTO> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        if(transactions.isEmpty()){throw new ResourceNotFoundException("No transactions found");}
        return transactions.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDTO.class))
                .toList();
    }

    @Override
    public TransactionDTO getTransactionById(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", "transactionId", transactionId));
        return modelMapper.map(transaction, TransactionDTO.class);
    }

    @Override
    public TransactionDTO createTransaction(Long categoryId, TransactionDTO transactionDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Transaction transaction = modelMapper.map(transactionDTO, Transaction.class);
        transaction.setCategory(category);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return modelMapper.map(savedTransaction, TransactionDTO.class);
    }

    @Override
    public TransactionDTO updateTransaction(Long transactionId, Long categoryId, TransactionDTO transactionDTO) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", "transactionId", transactionId));

        transaction.setAmount(transactionDTO.getAmount());
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setDate(transactionDTO.getDate());
        transaction.setType(transactionDTO.getType());

        if(categoryId != null){
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
            transaction.setCategory(category);
        }
        Transaction updatedTransaction = transactionRepository.save(transaction);
        return modelMapper.map(updatedTransaction, TransactionDTO.class);
    }

    @Override
    public String deleteTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", "transactionId", transactionId));
        transactionRepository.delete(transaction);
        return "Transaction with id: '" + transactionId + "' deleted successfully";
    }

    @Override
    public DashboardSummaryDTO getDashboardSummary(){
        double income = transactionRepository.sumAmountByType("income");
        double expense = transactionRepository.sumAmountByType("expense");
        return new DashboardSummaryDTO(income, expense, income - expense);
    }

    @Override
    public List<CategorySummaryDTO> getCategoryWiseSummary() {
        return transactionRepository.getCategoryWiseSummary();
    }

    @Override
    public List<MonthlySummaryDTO> getMonthlySummary() {
        List<Object[]> rawData = transactionRepository.getMonthlySummaryRaw();
        return rawData.stream()
                .map(obj -> new MonthlySummaryDTO(
                        (String) obj[0],
                        ((Number) obj[1]).doubleValue(),
                        ((Number) obj[2]).doubleValue()
                )).toList();
    }
}
