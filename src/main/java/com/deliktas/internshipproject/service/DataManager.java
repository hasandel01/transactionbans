package com.deliktas.internshipproject.service;

import com.deliktas.internshipproject.model.TransactionBanDTO;

import java.util.List;

public interface DataManager {

    void fetchDataAndSendToKafka();

}
