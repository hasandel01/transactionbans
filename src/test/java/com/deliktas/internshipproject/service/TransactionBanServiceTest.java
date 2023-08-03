package com.deliktas.internshipproject.service;

import com.deliktas.internshipproject.model.TransactionBan;
import com.deliktas.internshipproject.repository.TransactionBanRepository;
import org.junit.jupiter.api.AfterEach;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
class TransactionBanServiceTest {

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
    void getAllTransactionBans() {
        // Arrange: Assuming you have some data to return from the repository for testing
        // Example: Create a mock list of TransactionBan objects
        List<TransactionBan> yourListOfTransactionBans = new ArrayList<>();
        // Add some mock TransactionBan objects to the list...

        // Mock the repository behavior to return yourListOfTransactionBans when findAll() is called
        when(transactionBanRepository.findAll()).thenReturn(yourListOfTransactionBans);

        // Act
        transactionBanService.getAllTransactionBans();

        // Assert
        verify(transactionBanRepository).findAll(); // Verify that the repository findAll() method was called
        // Add additional assertions to verify the behavior of your service method if needed.
    }


    @Test
    void saveATransaction() {
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