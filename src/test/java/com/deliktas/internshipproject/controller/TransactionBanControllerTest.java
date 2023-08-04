package com.deliktas.internshipproject.controller;

import com.deliktas.internshipproject.service.TransactionBanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = TransactionBanController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class TransactionBanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionBanService transactionBanService;

    @Test
    void fetchDataAndSave() {
    }

    @Test
    void getAllTransactionBans() {
    }

    @Test
    void getAllTransaction() {
    }

    @Test
    void deleteATransaction() {
    }

    @Test
    void updateATransaction() {
    }

    @Test
    void getTransactionBanByRegistrationNumber() {
    }

    @Test
    void getTransactionBanByName() {
    }
}