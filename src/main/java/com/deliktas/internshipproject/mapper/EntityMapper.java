package com.deliktas.internshipproject.mapper;


import com.deliktas.internshipproject.auth.model.User;
import com.deliktas.internshipproject.auth.model.UserDTO;
import com.deliktas.internshipproject.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;

public interface EntityMapper {

    @Mapping(target = "pay", ignore = true)
    @Mapping(target = "verdictDetails",ignore = true)
    TransactionBanDTO transactionBanToTransactionBanDTO(TransactionBan transactionBan);

    @Mapping(target = "pay", ignore = true)
    @Mapping(target = "verdictDetails",ignore = true)
    TransactionBan transactionBanDtoToTransactionBan(TransactionBanDTO transactionBanDTO);

    ShareDTO shareToShareDTO(Share share);

    Share shareDTOToShare(ShareDTO share);

    VerdictDetailsDTO verdictDetailsToVerdictDetailsDTO(VerdictDetails verdictDetails);

    VerdictDetails verdictDetailsDTOToVerdictDetails(VerdictDetailsDTO verdictDetailsDTO);



}
