package com.deliktas.internshipproject.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerdictDetailsDTO {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime kurulKararTarihi;

    private String kurulKararNo;

    @JsonIgnore
    private Set<TransactionBan> transactionBans = new HashSet<>();
    
    public VerdictDetailsDTO(LocalDateTime kurulKararTarihi, String kurulKararNo) {
        this.kurulKararTarihi = kurulKararTarihi;
        this.kurulKararNo = kurulKararNo;
    }

}
