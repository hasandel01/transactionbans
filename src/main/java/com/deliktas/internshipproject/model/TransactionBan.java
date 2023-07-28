package com.deliktas.internshipproject.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionBan {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String unvan;

    private String  mkkSicilNo;

    @ManyToOne(fetch = FetchType.LAZY,optional=false)
    @JoinColumn(name="share_id")
    private Share pay;

    private String kurulKararTarihi;

    private String kurulKararNo;

}
