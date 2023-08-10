package com.deliktas.internshipproject.service;

import com.deliktas.internshipproject.client.RemoteServiceClient;
import com.deliktas.internshipproject.model.Share;
import com.deliktas.internshipproject.model.TransactionBan;
import com.deliktas.internshipproject.model.TransactionBanDTO;
import com.deliktas.internshipproject.model.VerdictDetails;
import com.deliktas.internshipproject.repository.ShareRepository;
import com.deliktas.internshipproject.repository.TransactionBanRepository;
import com.deliktas.internshipproject.repository.VerdictDetailsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
class TransactionBanServiceTest {

    @Mock
    private TransactionBanRepository transactionBanRepository;

    @Mock
    private RemoteServiceClient remoteServiceClient;

    @Mock
    private ShareRepository shareRepository;

    @Mock
    private VerdictDetailsRepository verdictDetailsRepository;

    private AutoCloseable autoCloseable;

    @InjectMocks
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
    @DisplayName("FETCHES DATA AND SAVES")
    void shouldFetchDataAndSaveWhenGetRemoteDataFromRemoteServiceIsCalled() {

        List<TransactionBanDTO> dataDTO = Collections.singletonList(
                new TransactionBanDTO("Hasan Deliktaş",
                        "12313*****",
                        "AMAZON A.Ş.",
                        "AMZ",
                        LocalDateTime.parse("2023-04-04T00:00:00"),
                        "B-55")
        );

        when(remoteServiceClient.getRemoteData()).thenReturn(dataDTO);

        Share share = new Share("AMAZON A.Ş.", "AMZ");

        // Stub the findByPay method to return null for the first invocation (to simulate not found),
        // and return the share object for the second invocation (after it's saved).
        when(shareRepository.findByPay(share.getPay())).thenReturn(null).thenReturn(share);

        // Stub the save method using the any() matcher.
        when(shareRepository.save(Mockito.any(Share.class))).thenReturn(share);

        VerdictDetails verdictDetails = new VerdictDetails( LocalDateTime.parse("2023-04-04T00:00:00"),
                "B-55");

        Set<VerdictDetails> verdictDetailsSet = new HashSet<>();
            verdictDetailsSet.add(verdictDetails);

        TransactionBan transactionBan = TransactionBan
                                            .builder()
                                                .unvan("Hasan Deliktaş")
                                                    .mkkSicilNo("12313*****")
                                                        .pay(share)
                                                        .verdictDetails(verdictDetailsSet)
                                                                .build();

        verdictDetails.addBan(transactionBan); // Add TransactionBan to VerdictDetails

        when(transactionBanRepository.save(Mockito.any(TransactionBan.class))).thenReturn(transactionBan);
        when(verdictDetailsRepository.save(Mockito.any(VerdictDetails.class))).thenReturn(verdictDetails);

        assertTrue(transactionBanService.fetchDataAndSave());

        verify(transactionBanRepository, times(1)).save(any());
        verify(verdictDetailsRepository, times(1)).save(any());
        verify(shareRepository,times(1)).save(any());
        verify(remoteServiceClient, times(1)).getRemoteData();
        verify(shareRepository, times(1)).findByPay(any());
    }

    @Test
    void shouldFetchDataAndSaveItToTheDatabase() {



    }



    @Test
    void shouldGetAllTransactionBansDto() {
        //GIVEN
        List<TransactionBan> transactionBans = new ArrayList<>();
        List<VerdictDetails> verdictDetails = new ArrayList<>();

        when(transactionBanRepository.findAll()).thenReturn(transactionBans);
        when(verdictDetailsRepository.findAll()).thenReturn(verdictDetails);

        // Act
        ResponseEntity<List<TransactionBanDTO>> responseEntity = transactionBanService.getAllTransactionBansDTO();
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());

        // THEN
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        List<TransactionBanDTO> allData = responseEntity.getBody();
        assertNotNull(allData); // Assert that the response body is not null

        // Assert
        verify(transactionBanRepository, times(1)).findAll(); // Verify that the repository findAll() method was called
        verify(verdictDetailsRepository, times(1)).findAll();

    }


    @Test
    void shouldSaveATransactionWhenSaveATransactionMethodIsCalled() {

        // GIVEN
        TransactionBanDTO transactionBanDTO = new TransactionBanDTO(
                "Hasan Deliktaş",
                "12313*****",
                "AMAZON A.Ş.",
                "AMZ",
                LocalDateTime.parse("2023-04-04T00:00:00"),
                "B-55"
        );

        Share share = new Share(transactionBanDTO.getPay(), transactionBanDTO.getPayKodu());
        VerdictDetails verdictDetails = new VerdictDetails(
                transactionBanDTO.getKurulKararTarihi(),
                transactionBanDTO.getKurulKararNo()
        );

        TransactionBan transactionBan = TransactionBan.builder()
                        .build();

        verdictDetails.addBan(transactionBan);


        when(shareRepository.findByPay(transactionBanDTO.getPay())).thenReturn(share);
        when(shareRepository.save(share)).thenReturn(share);
        when(verdictDetailsRepository.save(verdictDetails)).thenReturn(verdictDetails);


        ResponseEntity<String> responseEntity = transactionBanService.saveATransaction(transactionBanDTO);


        assertEquals("SUCCESS",responseEntity.getBody());
        assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());

        // Verify method calls
        verify(shareRepository, times(1)).findByPay(transactionBanDTO.getPay());
        verify(shareRepository, times(1)).save(share);
        verify(verdictDetailsRepository, times(1)).save(verdictDetails);
        verify(transactionBanRepository, times(1)).save(any(TransactionBan.class)); // You can use any() for this assertion
    }

    @Test
    void shouldHandleFailureWhenSaveATransactionMethodIsCalled() {

        TransactionBanDTO transactionBanDTO = new TransactionBanDTO(
                "Hasan Deliktaş",
                "12313*****",
                "AMAZON A.Ş.",
                "AMZ",
                LocalDateTime.parse("2023-04-04T00:00:00"),
                "B-55"
        );

        when(shareRepository.findByPay(transactionBanDTO.getPay())).thenReturn(null);
        // Simulate an exception when saving the transactionBan
        when(transactionBanRepository.save(any(TransactionBan.class))).thenThrow(new RuntimeException("Some error occurred"));


        ResponseEntity<String> responseEntity = transactionBanService.saveATransaction(transactionBanDTO);

        assertEquals("FAILURE",responseEntity.getBody());
        assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());

        // Verify method calls
        verify(shareRepository, times(1)).findByPay(transactionBanDTO.getPay());
        verify(shareRepository, times(1)).save(any(Share.class));
        verify(transactionBanRepository, times(1)).save(any(TransactionBan.class));

    }


    @Test
    void shouldDeleteATransactionWhenDeleteATransactionMethodIsCalled() {

        Integer idToDelete = 3;
        TransactionBan transactionBan = new TransactionBan();

        when(transactionBanRepository.findById(idToDelete)).thenReturn(Optional.of(transactionBan));

        // Stub the delete behavior for the repository methods
        doNothing().when(shareRepository).delete(any()); // Use any() matcher to ignore the argument
        doNothing().when(transactionBanRepository).delete(any());

        ResponseEntity<String> responseEntity = transactionBanService.deleteATransaction(idToDelete);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("SUCCESS", responseEntity.getBody());

        verify(transactionBanRepository, times(1)).findById(idToDelete);
        verify(verdictDetailsRepository, times(1)).findAll();
        verify(transactionBanRepository, times(1)).findAll();
    }



    @Test
    void shouldHandleFailureDeleteATransactionWhenDeleteATransactionMethodIsCalled() {

        // GIVEN
        Integer idToDelete = 3; // Adjust with the ID you want to delete

        when(transactionBanRepository.findById(idToDelete)).thenReturn(Optional.empty());

        // WHEN
        ResponseEntity<String> responseEntity = transactionBanService.deleteATransaction(idToDelete);

        // THEN
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("FAILURE", responseEntity.getBody());

        // Verify method calls
        verify(transactionBanRepository, times(1)).findById(idToDelete);
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