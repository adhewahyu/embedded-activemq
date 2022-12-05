package com.example.embeddedactivemq.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.jms.ConnectionFactory;

@Configuration
@Slf4j
public class ActiveMqConfiguration {

    @Bean
    public DisposableBean brokerService() throws Exception {
        long limitBytes = 64 * 1024 * 1024;
        BrokerService broker = new BrokerService();
        broker.setBrokerName("embedded");
        broker.setPersistent(false);
        broker.setUseShutdownHook(false);
        log.info("Store Usage before = {}", broker.getSystemUsage().getStoreUsage().getLimit() / 1024 / 1024);
        log.info("Memory Usage before = {}", broker.getSystemUsage().getMemoryUsage().getLimit() / 1024 / 1024);
        log.info("Temp Usage before = {}", broker.getSystemUsage().getTempUsage().getLimit() / 1024 / 1024);
        broker.getSystemUsage().getStoreUsage().setLimit(0L);
        broker.getSystemUsage().getMemoryUsage().setLimit(limitBytes);
        broker.getSystemUsage().getTempUsage().setLimit(limitBytes);
        log.info("Store Usage after = {}", broker.getSystemUsage().getStoreUsage().getLimit() / 1024 / 1024);
        log.info("Memory Usage after = {}", broker.getSystemUsage().getMemoryUsage().getLimit() / 1024 / 1024);
        log.info("Temp Usage after = {}", broker.getSystemUsage().getTempUsage().getLimit() / 1024 / 1024);
        broker.start();
        broker.waitUntilStarted();
        return () -> {
            broker.stop();
            broker.waitUntilStopped();
        };
    }

    @Bean @DependsOn("brokerService")
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("vm://embedded?broker.persistent=false,useShutdownHook=false");
        activeMQConnectionFactory.setProducerWindowSize(1024000);
        return activeMQConnectionFactory;
    }

}
