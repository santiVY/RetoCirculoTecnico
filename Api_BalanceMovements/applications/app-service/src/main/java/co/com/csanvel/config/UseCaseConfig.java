package co.com.csanvel.config;

import co.com.csanvel.model.balance.gateways.BalanceGateway;
import co.com.csanvel.model.transaction.gateways.TransactionGateway;
import co.com.csanvel.usecase.balancemovements.BalancemovementsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public BalancemovementsUseCase balancemovementsUseCase(BalanceGateway balanceGateway, TransactionGateway transactionGateway){
        return new BalancemovementsUseCase(balanceGateway, transactionGateway);
    }
}
