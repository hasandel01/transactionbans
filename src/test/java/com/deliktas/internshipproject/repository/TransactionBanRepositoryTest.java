package com.deliktas.internshipproject.repository;

import com.deliktas.internshipproject.model.Share;
import com.deliktas.internshipproject.model.TransactionBan;
import com.deliktas.internshipproject.model.VerdictDetails;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TransactionBanRepositoryTest {

    @Autowired
    private TransactionBanRepository transactionBanRepository;


    private TransactionBan transactionBanPass;

    private TransactionBan transactionBanShouldNotPass;

    private void createTransactionBan() {

        Share share = Share.builder().pay("ASMAXTRAMERVCZ").payKodu("ASXT").build();

        VerdictDetails verdictDetails = VerdictDetails
                .builder()
                .kurulKararNo("B-12")
                .kurulKararTarihi("12313323T00:00:00")
                .build();

        transactionBanPass = TransactionBan
                .builder()
                .mkkSicilNo("326************")
                .pay(share)
                .unvan("SAMED KAYNACI (1)").build();

        Set<VerdictDetails> verdictDetailsSet = new HashSet<>();
        verdictDetailsSet.add(verdictDetails);

        //  transactionBan.addVerdict(verdictDetails);
        transactionBanPass.setVerdictDetails(verdictDetailsSet);

    }

    private void createTransactionBan2() {

        Share share = Share.builder().pay("ASMAXTRAMERVCZ").payKodu("ASXT").build();

        VerdictDetails verdictDetails = VerdictDetails
                .builder()
                .kurulKararNo("B-12")
                .kurulKararTarihi("12313323T00:00:00")
                .build();

        transactionBanShouldNotPass = TransactionBan
                .builder()
                .mkkSicilNo("32344*************")
                .pay(share)
                .unvan("HASAN DELİKTAŞ").build();

        Set<VerdictDetails> verdictDetailsSet = new HashSet<>();
        verdictDetailsSet.add(verdictDetails);

        //  transactionBan.addVerdict(verdictDetails);
        transactionBanShouldNotPass.setVerdictDetails(verdictDetailsSet);

    }



    @Test
    void FIND_BY_MKKSICILNO_SHOULD_PASS () {

        createTransactionBan();

        List<TransactionBan> list = transactionBanRepository.findByMkkSicilNo(transactionBanPass.getMkkSicilNo());

        Assertions.assertNotNull(list, "List should not be null.");
        Assertions.assertTrue(!list.isEmpty(),"List can not be empty.");

        boolean found = false;
        for (TransactionBan ban : list) {
                if(ban.getUnvan().equals(transactionBanPass.getUnvan())) {
                    found = true;
                    break;
                }
        }

        Assertions.assertTrue(found,"Element is not found.");

    }

    @Test
    void FIND_BY_NAME_SHOULD_PASS() {

        createTransactionBan();

        List<TransactionBan> list = transactionBanRepository.findByName(transactionBanPass.getUnvan());

        Assertions.assertNotNull(list,"List should not be null.");
        Assertions.assertTrue(!list.isEmpty(), "List should not be empty.");


        boolean found = false;
        for (TransactionBan ban : list) {
                if(ban.getUnvan().equals(transactionBanPass.getUnvan())) {
                    found = true;
                    break;
                }
        }

        Assertions.assertTrue(found,"Element not found in the list.");

    }

    @Test
    void FIND_BY_MKKSICILNO_SHOULD_NOT_PASS () {

        createTransactionBan2();

        List<TransactionBan> list = transactionBanRepository.findByMkkSicilNo(transactionBanShouldNotPass.getMkkSicilNo());

        Assertions.assertNotNull(list,"List should not be null.");
        Assertions.assertTrue(!list.isEmpty(), "List should not be empty.");


        boolean found = false;
        for (TransactionBan ban : list) {
            if(ban.getUnvan().equals(transactionBanShouldNotPass.getUnvan())) {
                found = true;
                break;
            }
        }

        Assertions.assertTrue(found,"Element not found in the list.");

    }

    @Test
    void FIND_BY_NAME_SHOULD_NOT_PASS () {

        createTransactionBan2();

        List<TransactionBan> list = transactionBanRepository.findByMkkSicilNo(transactionBanShouldNotPass.getMkkSicilNo());

        Assertions.assertNotNull(list,"List should not be null.");
        Assertions.assertTrue(!list.isEmpty(), "List should not be empty.");


        boolean found = false;
        for (TransactionBan ban : list) {
            if(ban.getUnvan().equals(transactionBanShouldNotPass.getUnvan())) {
                found = true;
                break;
            }
        }

        Assertions.assertTrue(found,"Element not found in the list.");


    }


}