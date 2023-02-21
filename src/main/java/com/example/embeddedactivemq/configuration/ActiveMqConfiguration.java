package com.example.embeddedactivemq.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.activemq.broker.BrokerService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.ObjectUtils;

import javax.jms.ConnectionFactory;

@Configuration
@Slf4j
public class ActiveMqConfiguration {

    private boolean enableDebug = true;
    private boolean overrideMemoryLimit = true;
    private Integer memoryLimitValue = 1024;
    private Integer tempLimitValue = 51200;
    private boolean overridePrefetch = true;
    private Integer queuePrefetchValue = 1000;
    private Integer queueBrowserPrefetchValue = 500;
    private Integer durableTopicPrefetchValue = 100;

    @Bean
    public DisposableBean brokerService() throws Exception {
        long memoryBytes = (ObjectUtils.isEmpty(this.memoryLimitValue.longValue()) ? 1024L : this.memoryLimitValue.longValue()) * 1024L * 1024L;
        long tempBytes = (ObjectUtils.isEmpty(this.tempLimitValue.longValue()) ? 51200L : this.tempLimitValue.longValue()) * 1024L * 1024L;
        BrokerService broker = new BrokerService();
        broker.setBrokerName("embedded");
        broker.setPersistent(false);
        broker.setUseShutdownHook(false);
        if (this.enableDebug) {
            log.info("Store Usage before = {} MB", broker.getSystemUsage().getStoreUsage().getLimit() / 1024L / 1024L);
            log.info("Memory Usage before = {} MB", broker.getSystemUsage().getMemoryUsage().getLimit() / 1024L / 1024L);
            log.info("Temp Usage before = {} MB", broker.getSystemUsage().getTempUsage().getLimit() / 1024L / 1024L);
        }

        if (this.overrideMemoryLimit) {
            broker.getSystemUsage().getStoreUsage().setLimit(0L);
            broker.getSystemUsage().getMemoryUsage().setLimit(memoryBytes);
            broker.getSystemUsage().getTempUsage().setLimit(tempBytes);
        }

        if (this.enableDebug) {
            log.info("Store Usage after = {} MB", broker.getSystemUsage().getStoreUsage().getLimit() / 1024L / 1024L);
            log.info("Memory Usage after = {} MB", broker.getSystemUsage().getMemoryUsage().getLimit() / 1024L / 1024L);
            log.info("Temp Usage after = {} MB", broker.getSystemUsage().getTempUsage().getLimit() / 1024L / 1024L);
        }

        broker.start();
        broker.waitUntilStarted();
        return () -> {
            broker.stop();
            broker.waitUntilStopped();
        };
    }

    @Bean
    @DependsOn({"brokerService"})
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("vm://embedded?broker.persistent=false,useShutdownHook=false");
        activeMQConnectionFactory.setProducerWindowSize(1024000);
        activeMQConnectionFactory.setPrefetchPolicy(this.getActiveMQPrefetchPolicy());
        return activeMQConnectionFactory;
    }

    private ActiveMQPrefetchPolicy getActiveMQPrefetchPolicy() {
        ActiveMQPrefetchPolicy activeMQPrefetchPolicy = new ActiveMQPrefetchPolicy();
        if (this.enableDebug) {
            log.info("default value Queue Prefetch = {}", activeMQPrefetchPolicy.getQueuePrefetch());
            log.info("default value Queue Browser Prefetch = {}", activeMQPrefetchPolicy.getQueueBrowserPrefetch());
            log.info("default value Topic Prefetch = {}", activeMQPrefetchPolicy.getTopicPrefetch());
            log.info("default value Durable Topic Prefetch = {}", activeMQPrefetchPolicy.getDurableTopicPrefetch());
        }

        if (this.overridePrefetch) {
            activeMQPrefetchPolicy.setQueuePrefetch(this.queuePrefetchValue);
            activeMQPrefetchPolicy.setQueueBrowserPrefetch(this.queueBrowserPrefetchValue);
            activeMQPrefetchPolicy.setTopicPrefetch(32767);
            activeMQPrefetchPolicy.setDurableTopicPrefetch(this.durableTopicPrefetchValue);
        }

        if (this.enableDebug) {
            log.info("latest value Queue Prefetch = {}", activeMQPrefetchPolicy.getQueuePrefetch());
            log.info("latest value Queue Browser Prefetch = {}", activeMQPrefetchPolicy.getQueueBrowserPrefetch());
            log.info("latest value Topic Prefetch = {}", activeMQPrefetchPolicy.getTopicPrefetch());
            log.info("latest value Durable Topic Prefetch = {}", activeMQPrefetchPolicy.getDurableTopicPrefetch());
        }

        return activeMQPrefetchPolicy;
    }

}
