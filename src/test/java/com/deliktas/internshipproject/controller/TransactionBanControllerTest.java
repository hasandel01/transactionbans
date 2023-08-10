package com.deliktas.internshipproject.controller;

import com.deliktas.internshipproject.mapper.EntityMapper;
import com.deliktas.internshipproject.mapper.EntityMapperCustomImpl;
import com.deliktas.internshipproject.model.TransactionBan;
import com.deliktas.internshipproject.model.TransactionBanDTO;
import com.deliktas.internshipproject.service.TransactionBanService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TransactionBanController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class TransactionBanControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionBanService transactionBanService;

    private TransactionBanDTO transactionBanDTO;

    private String requestBodyJson;

    private EntityMapperCustomImpl entityMapper;

    @BeforeEach
    void setUp() {

        entityMapper = new EntityMapperCustomImpl();

        transactionBanDTO = TransactionBanDTO.builder()
                .pay("AMAZON A.Ş.")
                .payKodu("AMZ")
                .unvan("HASAN DELİKTAŞ")
                .kurulKararNo("B-61")
                .kurulKararTarihi(LocalDateTime.parse("2023-06-05T00:00:00"))
                .mkkSicilNo("3123****")
                .build();

        requestBodyJson = "{" +
                "\"unvan\":\"HASAN DELİKTAŞ\"," +
                "\"mkkSicilNo\":\"3123****\"," +
                "\"pay\":\"AMAZON A.Ş.\"," +
                "\"payKodu\":\"AMZ\"," +
                "\"kurulKararTarihi\":\"2023-06-05T00:00:00\"," +
                "\"kurulKararNo\":\"B-61\"" +
                "}";

    }

    @Test
    void givenDataFromClient_whenSaveToDatabase_thenStatus200() throws Exception {

        when(transactionBanService.fetchDataAndSave()).thenReturn(true);

        mockMvc.perform(get("/api/registry/save-to-database")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("SUCCESS"));

    }

    @Test
    void shouldReturnAllTransactionBanDTOPrintsSuccess() throws Exception {

        when(transactionBanService.getAllTransactionBansDTO())
                    .thenReturn(new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK));


        mockMvc.perform(get("/api/registry/all-bans-dto")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void shouldSaveATransactionPrintsSuccess() throws Exception {


        when(transactionBanService.saveATransaction(transactionBanDTO))
                    .thenReturn(new ResponseEntity<>("SUCCESS", HttpStatus.CREATED));

        mockMvc.perform(post("/api/registry/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("SUCCESS"));
    }

    @Test
    void shouldDeleteATransactionWhenServiceIsCalled() throws Exception {

        Integer idToBeDeleted = 4;

        when(transactionBanService.deleteATransaction(idToBeDeleted))
                .thenReturn(new ResponseEntity<>("SUCCESS",HttpStatus.OK));


        mockMvc.perform(delete("/api/registry/delete/{id}", idToBeDeleted)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("SUCCESS"))
                .andExpect(status().isOk());

    }

    @Test
    void shouldUpdateATransactionWhenUpdateATransactionMethodIsCalled() throws Exception {

        Integer idToBeUpdated = 5;


        when(transactionBanService.updateATransaction(idToBeUpdated,transactionBanDTO))
                    .thenReturn(new ResponseEntity<>("SUCCESS",HttpStatus.OK));


        mockMvc.perform(put("/api/registry/update/{id}", idToBeUpdated)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyJson))
                .andExpect(content().string("SUCCESS"))
                .andExpect(status().isOk());

    }

    @Test
    void shouldGetTransactionBanByRegistrationNumberAndReturnsOK() throws Exception {


        List<TransactionBan> transactionBanDTOS = new ArrayList<>();
        transactionBanDTOS.add(entityMapper.transactionBanDtoToTransactionBan(transactionBanDTO));

        when(transactionBanService.getTransactionBanByRegNumber(transactionBanDTO.getMkkSicilNo()))
                .thenReturn(new ResponseEntity<>(transactionBanDTOS, HttpStatus.OK));


        mockMvc.perform(get("/api/registry/verdict-by-registration-number")
                        .param("registrationNumber","3123****")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].unvan").value("HASAN DELİKTAŞ"))
                .andExpect(jsonPath("$[0].mkkSicilNo").value("3123****"));

    }

    @Test
    void shouldGetTransactionBanByNameAndReturnsOK() throws Exception {

        List<TransactionBan> transactionBanDTOS = new ArrayList<>();
        transactionBanDTOS.add(entityMapper.transactionBanDtoToTransactionBan(transactionBanDTO));

        when(transactionBanService.getTransactionBanByName(transactionBanDTO.getUnvan()))
                .thenReturn(new ResponseEntity<>(transactionBanDTOS, HttpStatus.OK));


        mockMvc.perform(get("/api/registry/verdict-by-name")
                        .param("name","HASAN DELİKTAŞ")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].unvan").value("HASAN DELİKTAŞ"))
                .andExpect(jsonPath("$[0].mkkSicilNo").value("3123****"));
    }
}