package co.com.csanvel.api;

import co.com.csanvel.model.balancemovements.RqBalanceMovements;
import co.com.csanvel.model.balancemovements.RsBalanceMovements;
import co.com.csanvel.usecase.balancemovements.BalancemovementsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
    private final BalancemovementsUseCase balancemovementsUseCase;

    public Mono<ServerResponse> getBalanceMovements(ServerRequest serverRequest) {
        Mono<RsBalanceMovements> response = serverRequest.bodyToMono(RqBalanceMovements.class).flatMap(body -> balancemovementsUseCase.getBalanceMovements(body));
        return ServerResponse.ok()
                .body(response, RsBalanceMovements.class);
    }


}
