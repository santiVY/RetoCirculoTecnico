package co.com.csanvel.usecase.balancemovements;

import co.com.csanvel.model.balance.*;
import co.com.csanvel.model.balance.gateways.BalanceGateway;
import co.com.csanvel.model.balancemovements.RqBalanceMovements;
import co.com.csanvel.model.balancemovements.RqData;
import co.com.csanvel.model.balancemovements.RsBalanceMovements;
import co.com.csanvel.model.transaction.*;
import co.com.csanvel.model.transaction.gateways.TransactionGateway;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class BalancemovementsUseCaseTest {

    @InjectMocks
    BalancemovementsUseCase balancemovementsUseCase;

    @Mock
    BalanceGateway balanceGateway;

    @Mock
    TransactionGateway transactionGateway;

    private RqBalanceMovements getRequestBalanceMovement(){
        return RqBalanceMovements.builder()
                .data(List.of(RqData.builder()
                    .account(RqAccount.builder()
                            .number("45387654763")
                            .type("CUENTA_AHORROS")
                            .build())
                    .transaction(RqTransactionBody.builder()
                            .startDate("2020-01-01")
                            .endDate("2020-01-30")
                            .minAmount(2)
                            .maxAmount(4)
                            .type("DEBITO")
                            .checkNumber("")
                            .group("")
                            .description("")
                            .build())
                    .pagination(RqPagination.builder()
                            .key(1)
                            .size(30)
                            .build())
                    .office(RqOffice.builder()
                            .name("2005678")
                            .code("2005678")
                            .build())
                    .build()))
                .build();
    }

    private RqBalance getRequestBalance(){
        return RqBalance.builder()
                .data(List.of(RqDataBalance.builder()
                        .account(RqAccount.builder()
                                .type("CUENTA_AHORROS")
                                .number("45387654763")
                                .build())
                        .build()))
                .build();
    }

    private RqTransaction getRequestTransaction(){
        return RqTransaction.builder()
                .data(List.of(RqDataTransaction.builder()
                        .account(RqAccount.builder()
                                .number("45387654763")
                                .type("CUENTA_AHORROS")
                                .build())
                        .transaction(RqTransactionBody.builder()
                                .startDate("2020-01-01")
                                .endDate("2020-01-30")
                                .minAmount(2)
                                .maxAmount(4)
                                .type("DEBITO")
                                .checkNumber("")
                                .group("")
                                .description("")
                                .build())
                        .pagination(RqPagination.builder()
                                .key(1)
                                .size(30)
                                .build())
                        .office(RqOffice.builder()
                                .name("2005678")
                                .code("2005678")
                                .build())
                        .build()))
                .build();
    }

    private RsBalance getResponsebalance(){
        return RsBalance.builder().data(List.of(
                    RsDataBalance.builder().account(
                            RsAccount.builder().balances(
                                    RsBalances.builder()
                                            .available(new BigDecimal(1))
                                            .availableOverdraftBalance(new BigDecimal(1))
                                            .overdraftValue(new BigDecimal(1))
                                            .availableOverdraftQuota(new BigDecimal(1))
                                            .cash(new BigDecimal(1))
                                            .unavailableClearing(new BigDecimal(1))
                                            .receivable(new BigDecimal(1))
                                            .blocked(new BigDecimal(1))
                                            .unavailableStartDayClearingStartDay(new BigDecimal(1))
                                            .cashStartDay(new BigDecimal(1))
                                            .pockets(new BigDecimal(1))
                                            .remittanceQuota(new BigDecimal(1))
                                            .agreedRemittanceQuota(new BigDecimal(1))
                                            .remittanceQuotaUsage(new BigDecimal(1))
                                            .normalInterest(new BigDecimal(1))
                                            .suspensionInterest(new BigDecimal(1))
                                            .build()).
                                    build()).
                            build())).
                build();
    }

    private RsTransaction getResponseTransacion(){
        return RsTransaction.builder().
                data(List.of(RsDataTransaction.builder()
                        .flagMoreRecords(false)
                        .responseSize(1)
                        .transaction(List.of(RsTransactionBody.builder()
                                .id("123")
                                .postedDate("2020-03-01")
                                .description("Abono preautorizado donación")
                                .amount(123455)
                                .type("DEBITO")
                                .reference1("")
                                .reference2("")
                                .reference3("")
                                .checkNumber("1234")
                                .build()))
                        .office(RqOffice.builder()
                                .name("234")
                                .code("234")
                                .build())
                        .relatedTransferAccount(RsRelatedTransferAccount.builder()
                                .number("45387654763")
                                .type("CUENTA_AHORROS")
                                .build())
                        .customer(RsCustomer.builder()
                                .name("LUIS")
                                .build())
                        .build()))
                .build();
    }


    @Before
    public void init(){
        when(balanceGateway.getBalance(getRequestBalance())).thenReturn(Mono.just(getResponsebalance()));
        when(transactionGateway.getTransaction(getRequestTransaction())).thenReturn(Mono.just(getResponseTransacion()));
    }

    @Test
    public void getBalanceMovements() {
        Mono<RsBalanceMovements> rsBalanceMovements =  balancemovementsUseCase.getBalanceMovements(getRequestBalanceMovement());
        StepVerifier.create(rsBalanceMovements)
                .assertNext(Assert::assertNotNull)
                .verifyComplete();
    }
}