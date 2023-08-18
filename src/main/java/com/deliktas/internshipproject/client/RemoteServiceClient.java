package com.deliktas.internshipproject.client;


import com.deliktas.internshipproject.model.TransactionBanDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(name = "transactionBanService", url = "https://ws.spk.gov.tr/IdariYaptirimlar/api")
public interface RemoteServiceClient {

    @GetMapping("IslemYasaklari")
    List<TransactionBanDTO> getRemoteData();
}
