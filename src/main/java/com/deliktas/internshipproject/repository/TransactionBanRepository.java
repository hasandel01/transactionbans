package com.deliktas.internshipproject.repository;

import com.deliktas.internshipproject.controller.TransactionBanController;
import com.deliktas.internshipproject.model.TransactionBan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionBanRepository extends JpaRepository<TransactionBan, Integer> {

    @Query("SELECT tb FROM TransactionBan tb WHERE tb.kurulKararNo = :verdictNo")
    List<TransactionBan> findByVerdictNo(String verdictNo);

    @Query("SELECT tb FROM TransactionBan tb WHERE tb.unvan = :name")
    List<TransactionBan> findByName(String name);


}

