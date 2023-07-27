package com.deliktas.internshipproject.repository;

import com.deliktas.internshipproject.controller.TransactionBanController;
import com.deliktas.internshipproject.model.TransactionBan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionBanRepository extends JpaRepository<TransactionBan, Integer> {
}
