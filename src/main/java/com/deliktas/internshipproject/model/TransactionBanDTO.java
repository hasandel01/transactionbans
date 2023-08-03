package com.deliktas.internshipproject.model;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionBanDTO {

    private String unvan;

    private String  mkkSicilNo;

    private String pay;

    private String payKodu;

    private String kurulKararTarihi;

    private String kurulKararNo;
}
