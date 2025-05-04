package com.saurabh.fintrackbackend.repository;

import com.saurabh.fintrackbackend.dto.CategorySummaryDTO;
import com.saurabh.fintrackbackend.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE LOWER(t.type) = LOWER(:type)")
    double sumAmountByType(@Param("type") String type);

    @Query("SELECT new com.saurabh.fintrackbackend.dto.CategorySummaryDTO(t.category.name, SUM(t.amount)) " +
            "FROM Transaction t GROUP BY t.category.name")
    List<CategorySummaryDTO> getCategoryWiseSummary();

    @Query(value = """
    SELECT 
      TO_CHAR(date, 'YYYY-MM') as month,
      SUM(CASE WHEN LOWER(type) = 'income' THEN amount ELSE 0 END) as income,
      SUM(CASE WHEN LOWER(type) = 'expense' THEN amount ELSE 0 END) as expense
    FROM transaction
    GROUP BY month
    ORDER BY month
""", nativeQuery = true)
    List<Object[]> getMonthlySummaryRaw();

}
