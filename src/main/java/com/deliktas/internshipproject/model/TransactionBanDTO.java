package com.deliktas.internshipproject.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionBanDTO {

    private String unvan;

    private String  mkkSicilNo;

    private String pay;

    private String payKodu;

    private String kurulKararTarihi;

    private String kurulKararNo;
}
