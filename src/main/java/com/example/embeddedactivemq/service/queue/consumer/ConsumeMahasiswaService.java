package com.example.embeddedactivemq.service.queue.consumer;

import com.alibaba.fastjson2.JSON;
import com.example.embeddedactivemq.model.request.AddMahasiswaRequest;
import com.example.embeddedactivemq.model.response.ValidationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumeMahasiswaService {

    public ValidationResponse execute(AddMahasiswaRequest addMahasiswaRequest){
        log.info("ConsumeMahasiswaService - begin");
        log.info("Consuming Mahasiswa Request = {}", JSON.toJSONString(addMahasiswaRequest));
        log.info("ConsumeMahasiswaService - end");
        return ValidationResponse.builder().result(true).build();
    }



}
