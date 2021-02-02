package co.com.csanvel.restproduct;

import co.com.csanvel.model.transaction.RqTransaction;
import co.com.csanvel.model.transaction.RsTransaction;
import co.com.csanvel.model.transaction.gateways.TransactionGateway;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class RestConsumerTransaction implements TransactionGateway {

    //@Value("${transaction.url}")
    private String url = "https://practicabanco.getsandbox.com:443/movements";

    private WebClient webClient;

    public RestConsumerTransaction(){
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
    }

    @Override
    public Mono<RsTransaction> getTransaction(RqTransaction rqTransaction) {
        return webClient.post()
                .body(Mono.just(rqTransaction), RqTransaction.class)
                .retrieve()
                .bodyToMono(RsTransaction.class);

    }
}
