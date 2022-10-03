package com.example.embeddedactivemq.route;

import com.example.embeddedactivemq.route.processor.MahasiswaProcessor;
import com.example.embeddedactivemq.utility.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MahasiswaRouter extends RouteBuilder {

    private final MahasiswaProcessor mahasiswaProcessor;

    @Override
    public void configure() {
        log.info("MahasiswaRouter is active and ready to receive message");
        from("activemq:" + Constants.MQ_TOPIC_MAHASISWA).process(mahasiswaProcessor);
    }

}
