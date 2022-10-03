package com.example.embeddedactivemq.route.processor;

import com.alibaba.fastjson2.JSON;
import com.example.embeddedactivemq.model.request.AddMahasiswaRequest;
import com.example.embeddedactivemq.model.response.ValidationResponse;
import com.example.embeddedactivemq.service.queue.consumer.ConsumeMahasiswaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MahasiswaProcessor implements Processor {

    private final ConsumeMahasiswaService consumeMahasiswaService;

    @Override
    public void process(Exchange exchange) throws CamelException {
        log.info("MahasiswaProcessor - begin");
        String message = exchange.getMessage().getBody().toString();
        log.info("Processing Data = {}", message);
        ValidationResponse validationResponse =
                consumeMahasiswaService.execute(JSON.to(AddMahasiswaRequest.class, message));
        if(!validationResponse.getResult()){
            throw new CamelException("Failed to consume");
        }
    }
}
