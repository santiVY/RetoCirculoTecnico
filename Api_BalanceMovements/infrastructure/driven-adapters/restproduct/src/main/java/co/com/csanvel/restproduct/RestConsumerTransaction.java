package co.com.csanvel.restproduct;

import co.com.csanvel.model.transaction.RqTransaction;
import co.com.csanvel.model.transaction.RsTransaction;
import co.com.csanvel.model.transaction.gateways.TransactionGateway;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Setter
public class RestConsumerTransaction implements TransactionGateway {

    @Value("${transaction.url}")
    private String url;

    @Value("${transactionMoreMovement.url}")
    private String urlMoreMovements;

    @Autowired
    private WebClient webClient;

    @Override
    public Mono<RsTransaction> getTransaction(RqTransaction rqTransaction) {
        return webClient.post()
                .uri(url)
                .body(Mono.just(rqTransaction), RqTransaction.class)
                .retrieve()
                .bodyToMono(RsTransaction.class);
    }

    @Override
    public Mono<RsTransaction> getTransactionMoreMovements(RqTransaction rqTransaction) {
        return webClient.post()
                .uri(url + "/" + rqTransaction.getData().get(0).getPagination().getKey())
                .body(Mono.just(rqTransaction), RqTransaction.class)
                .retrieve()
                .bodyToMono(RsTransaction.class);
    }
}
