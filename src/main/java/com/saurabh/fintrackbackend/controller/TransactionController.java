package com.saurabh.fintrackbackend.controller;

import com.saurabh.fintrackbackend.dto.CategorySummaryDTO;
import com.saurabh.fintrackbackend.dto.DashboardSummaryDTO;
import com.saurabh.fintrackbackend.dto.MonthlySummaryDTO;
import com.saurabh.fintrackbackend.dto.TransactionDTO;
import com.saurabh.fintrackbackend.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {
    private final TransactionService transactionService;
    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions(){
        return new ResponseEntity<>(transactionService.getAllTransactions(), HttpStatus.OK);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long transactionId){
        return new ResponseEntity<>(transactionService.getTransactionById(transactionId), HttpStatus.OK);
    }

    @PostMapping("/category/{categoryId}/transaction")
    public ResponseEntity<TransactionDTO> createTransaction(@PathVariable Long categoryId, @Valid @RequestBody TransactionDTO transactionDTO){
        return new ResponseEntity<>(transactionService.createTransaction(categoryId, transactionDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<TransactionDTO> updateTransaction(@PathVariable Long transactionId, @RequestParam(required = false) Long categoryId, @Valid @RequestBody TransactionDTO transactionDTO){
        return new ResponseEntity<>(transactionService.updateTransaction(transactionId, categoryId, transactionDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long transactionId){
        return new ResponseEntity<>(transactionService.deleteTransaction(transactionId), HttpStatus.OK);
    }

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryDTO> getDashboardSummary(){
        return new ResponseEntity<>(transactionService.getDashboardSummary(), HttpStatus.OK);
    }

    @GetMapping("/summary/category")
    public ResponseEntity<List<CategorySummaryDTO>> getCategorySummary(){
        return new ResponseEntity<>(transactionService.getCategoryWiseSummary(), HttpStatus.OK);
    }

    @GetMapping("/summary/monthly")
    public ResponseEntity<List<MonthlySummaryDTO>> getMonthlySummary() {
        return new ResponseEntity<>(transactionService.getMonthlySummary(), HttpStatus.OK);
    }

}
