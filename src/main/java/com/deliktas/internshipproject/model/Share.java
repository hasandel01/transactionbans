package com.deliktas.internshipproject.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Share {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String pay;

    private String payKodu;

    @OneToMany(targetEntity = TransactionBan.class, mappedBy = "pay", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<TransactionBan> transactionBanList = new ArrayList<>();


    public Share(String pay, String payKodu) {
        this.pay = pay;
        this.payKodu = payKodu;
    }

}
