package com.example.embeddedactivemq.service.queue.publisher;

import com.example.embeddedactivemq.model.request.AddMahasiswaRequest;
import com.example.embeddedactivemq.model.response.ValidationResponse;
import com.example.embeddedactivemq.utility.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PublishMahasiswaService {

    private final JmsTemplate jmsTemplate;

    @Async
    public ValidationResponse execute(AddMahasiswaRequest addMahasiswaRequest){
        log.info("PublishMahasiswaService - convert and send");
        jmsTemplate.convertAndSend(Constants.MQ_TOPIC_MAHASISWA, addMahasiswaRequest);
        return ValidationResponse.builder().result(true).build();
    }
}
