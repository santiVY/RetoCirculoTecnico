package co.com.csanvel.model.transaction.gateways;

import co.com.csanvel.model.transaction.RqTransaction;
import co.com.csanvel.model.transaction.RsTransaction;
import reactor.core.publisher.Mono;

public interface TransactionGateway {
    Mono<RsTransaction> getTransaction(RqTransaction rqTransaction);
    Mono<RsTransaction> getTransactionMoreMovements(RqTransaction rqTransaction);
}
