package com.deliktas.internshipproject.repository;

import com.deliktas.internshipproject.model.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRepository extends JpaRepository<Share,Integer> {
    Share findByPay(String pay);
}
