package org.bobpark.bobchatsapi.configure.chat;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeTypeUtils;

import reactor.util.retry.Retry;

@Configuration
public class ChatConfiguration {

    @Bean
    public RSocketRequester getRSocketRequester(RSocketStrategies rSocketStrategies) {
        return RSocketRequester.builder()
            .rsocketConnector(connector -> connector.reconnect(Retry.backoff(10, Duration.ofMillis(500))))
            .rsocketStrategies(rSocketStrategies)
            .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
            .tcp("localhost", 8081);
    }
}
