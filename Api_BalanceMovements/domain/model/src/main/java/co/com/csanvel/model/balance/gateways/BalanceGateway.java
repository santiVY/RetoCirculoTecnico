package co.com.csanvel.model.balance.gateways;

import co.com.csanvel.model.balance.RqBalance;
import co.com.csanvel.model.balance.RsBalance;
import reactor.core.publisher.Mono;

public interface BalanceGateway {
    Mono<RsBalance> getBalance(RqBalance rqBalance);
}
