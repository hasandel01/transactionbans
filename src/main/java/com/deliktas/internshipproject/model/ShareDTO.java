package com.deliktas.internshipproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShareDTO {

    private String pay;

    private String payKodu;

    private List<TransactionBanDTO> transactionBanDTOS = new ArrayList<>();

}
