package com.deliktas.internshipproject.client;


import com.deliktas.internshipproject.model.TransactionBan;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "transactionBanService", url = "https://ws.spk.gov.tr/IdariYaptirimlar/api")
public interface RemoteServiceClient {

    @GetMapping("IslemYasaklari")
    TransactionBan [] getRemoteData();
}
