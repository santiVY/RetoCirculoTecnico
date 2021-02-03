package co.com.csanvel.restproduct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${urlBase}")
    private String urlBase;

    @Bean
    public WebClient webClient(){
        return  WebClient.builder()
                .baseUrl(urlBase)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
    }
}

