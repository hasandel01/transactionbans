package com.deliktas.islemyasaklari.repository;


import com.deliktas.islemyasaklari.model.TransactionBan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionBanRepository extends JpaRepository<TransactionBan, Integer> {
}
