package com.deliktas.internshipproject.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerdictDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime kurulKararTarihi;

    private String kurulKararNo;

    @ManyToMany(mappedBy = "verdictDetails" , fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<TransactionBan> transactionBans = new HashSet<>();

    public VerdictDetails(LocalDateTime kurulKararTarihi, String kurulKararNo) {
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
