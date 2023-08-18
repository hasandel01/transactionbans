package com.deliktas.internshipproject.mapper;

import com.deliktas.internshipproject.auth.model.User;
import com.deliktas.internshipproject.auth.model.UserDTO;
import com.deliktas.internshipproject.model.*;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class EntityMapperCustomImpl implements EntityMapper {

    @Override
    public TransactionBanDTO transactionBanToTransactionBanDTO(TransactionBan transactionBan) {
        if (transactionBan == null) {
            return null;
        }

        TransactionBanDTO.TransactionBanDTOBuilder transactionBanDTO = TransactionBanDTO.builder();
        transactionBanDTO.unvan(transactionBan.getUnvan());
        transactionBanDTO.mkkSicilNo(transactionBan.getMkkSicilNo());
        transactionBanDTO.pay(transactionBan.getPay().getPay());
        transactionBanDTO.payKodu(transactionBan.getPay().getPayKodu());
        transactionBanDTO.kurulKararNo(transactionBan.verdictDetailsSetToVerdictDetails().getKurulKararNo());
        transactionBanDTO.kurulKararTarihi(transactionBan.verdictDetailsSetToVerdictDetails().getKurulKararTarihi());
        return transactionBanDTO.build();
    }

    @Override
    public TransactionBan transactionBanDtoToTransactionBan(TransactionBanDTO transactionBanDTO) {
        if ( transactionBanDTO == null ) {
            return null;
        }

        Set<VerdictDetails>  verdictDetailsSet = new HashSet<>();
        verdictDetailsSet.add(verdictDetailsDTOToVerdictDetails(
                new VerdictDetailsDTO(transactionBanDTO.getKurulKararTarihi(),transactionBanDTO.getKurulKararNo())));

        TransactionBan.TransactionBanBuilder transactionBan = TransactionBan.builder();
        transactionBan.unvan( transactionBanDTO.getUnvan() );
        transactionBan.mkkSicilNo( transactionBanDTO.getMkkSicilNo() );
        transactionBan.pay(new Share(transactionBanDTO.getPay(),transactionBanDTO.getPayKodu()));
        transactionBan.verdictDetails(verdictDetailsSet);

        return transactionBan.build();
    }

    @Override
    public ShareDTO shareToShareDTO(Share share) {

        ShareDTO shareDTO = new ShareDTO();

        shareDTO.setPay(share.getPay());
        shareDTO.setPayKodu(share.getPayKodu());

        return shareDTO;
    }

    @Override
    public Share shareDTOToShare(ShareDTO shareDTO) {
        Share share = new Share();

        share.setPay(shareDTO.getPay());
        share.setPayKodu(shareDTO.getPayKodu());

        return share;
    }

    @Override
    public VerdictDetailsDTO verdictDetailsToVerdictDetailsDTO(VerdictDetails verdictDetails) {
        return null;
    }

    @Override
    public VerdictDetails verdictDetailsDTOToVerdictDetails(VerdictDetailsDTO verdictDetailsDTO) {

        return new VerdictDetails(verdictDetailsDTO.getKurulKararTarihi(),
                verdictDetailsDTO.getKurulKararNo());
    }


}
