package com.deliktas.internshipproject.service;

import com.deliktas.internshipproject.model.TransactionBan;
import com.deliktas.internshipproject.repository.TransactionBanRepository;
import com.deliktas.internshipproject.model.TransactionBanDTO;
import com.deliktas.internshipproject.client.RemoteServiceClient;
import com.deliktas.internshipproject.model.Share;
import com.deliktas.internshipproject.repository.ShareRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
class TransactionBanServiceTest {


    @Autowired
    private RemoteServiceClient remoteServiceClient;

    @Mock
    private TransactionBanRepository transactionBanRepository;

    private AutoCloseable autoCloseable;

    @Autowired
    private TransactionBanService transactionBanService;


    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        // transactionBanService = new TransactionBanService(); // Remove this line
    }


    @AfterEach
    void tearDown() throws Exception {
            autoCloseable.close();
    }

    @Test
    void fetchDataAndSave() {
    }

    @Test
    void SHOULD_THROW_IF_DATA_IS_NOT_CATCHED() {

        List<TransactionBanDTO> dataDTO = remoteServiceClient.getRemoteData();
        if (dataDTO == null) {
            Assertions.assertThrows(RestClientException.class, () ->
                    {
                                if(dataDTO.isEmpty()) {
                                    throw new RestClientException("Error occurred while fetching data from the server.");
                                }
                    },"Expected exception is not thrown.");
        }
    }
    @Test
    void SHOULD_FETCH_DATA_AND_SAVE_IT_TO_THE_SERVER() {

        List<TransactionBanDTO> dataDTO = remoteServiceClient.getRemoteData();

        if (dataDTO == null) {
            Assertions.assertThrows(RestClientException.class, () ->
            {
                if (dataDTO.isEmpty()) {
                    throw new RestClientException("Error occurred while fetching data from the server.");
                }
            }, "Expected exception is not thrown.");
        }

       /* for (TransactionBanDTO data : dataDTO) {
            // Fetch or create the associated Share entity based on the pay field
            Share share = shareRepository.findByPay(data.getPay());
            if (share == null) {
                share = new Share(data.getPay(), data.getPayKodu());
                shareRepository.save(share);
            }

            VerdictDetails verdictDetails = new VerdictDetails(data.getKurulKararTarihi(), data.getKurulKararNo());

            TransactionBan transactionBan = new TransactionBan();
            transactionBan.setPay(share);
            transactionBan.addVerdict(verdictDetails);
            transactionBan.setUnvan(data.getUnvan());
            transactionBan.setMkkSicilNo(data.getMkkSicilNo());

            transactionBanRepository.save(transactionBan); // Save the TransactionBan first

            verdictDetails.addBan(transactionBan); // Add TransactionBan to VerdictDetails
            verdictDetailsRepository.save(verdictDetails); // Save VerdictDetails after adding TransactionBan

            // Since you have cascading settings, there's no need to explicitly save VerdictDetails again here.
            // The changes made in verdictDetails.addBan(transactionBan) will be automatically persisted.*/

        //}
    }


    @Test
    void TRANSACTION_SAVE_SHOULD_PASS() {

    }

    @Test
    void deleteATransaction() {
    }

    @Test
    void updateATransaction() {
    }

    @Test
    void getTransactionBanByName() {
    }

    @Test
    void getTransactionBanByRegNumber() {
    }
}