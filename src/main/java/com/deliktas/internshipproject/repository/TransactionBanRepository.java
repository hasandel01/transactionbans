package com.deliktas.internshipproject.repository;

import com.deliktas.internshipproject.model.TransactionBan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionBanRepository extends JpaRepository<TransactionBan, Integer> {

    @Query("SELECT tb FROM TransactionBan tb WHERE tb.mkkSicilNo = :mkkSicilNo")
    List<TransactionBan> findByMkkSicilNo(String mkkSicilNo);

    @Query("SELECT tb FROM TransactionBan tb WHERE tb.unvan = :name")
    List<TransactionBan> findByName(String name);


}

