package com.deliktas.internshipproject.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionBanDTO {

    private String unvan;

    private String mkkSicilNo;

    private String pay;

    private String payKodu;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime kurulKararTarihi;

    private String kurulKararNo;

}
