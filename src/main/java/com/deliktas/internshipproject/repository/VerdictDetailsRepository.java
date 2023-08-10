package com.deliktas.internshipproject.repository;

import com.deliktas.internshipproject.model.VerdictDetails;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerdictDetailsRepository extends JpaRepository<VerdictDetails, Integer> {

}
