package ru.kremenia.market.core.configs;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;
import ru.kremenia.market.core.properties.CartServiceIntegrationProperties;

import java.util.concurrent.TimeUnit;

@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(CartServiceIntegrationProperties.class)
public class AppConfig {
    private final CartServiceIntegrationProperties cartServiceIntegrationProperties;

    @Bean
    public WebClient cartServiceWebClient() {
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, cartServiceIntegrationProperties.getConnectTimeout())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(cartServiceIntegrationProperties.getReadTimeout(), TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(cartServiceIntegrationProperties.getWriteTimeout(), TimeUnit.MILLISECONDS));
                });

        return WebClient.builder()
                .baseUrl(cartServiceIntegrationProperties.getUrl())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }
}
