package com.deliktas.internshipproject.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerdictDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String kurulKararTarihi;

    private String kurulKararNo;

    @ManyToMany(mappedBy = "verdictDetails" , fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<TransactionBan> transactionBans = new HashSet<>();

    public VerdictDetails(String kurulKararTarihi, String kurulKararNo) {
        this.kurulKararTarihi = kurulKararTarihi;
        this.kurulKararNo = kurulKararNo;
    }

    public void addBan(TransactionBan transactionBan) {
        this.transactionBans.add(transactionBan);
    }

    public void removeBan(TransactionBan existingBan) {
        this.transactionBans.remove(existingBan);
    }
}
