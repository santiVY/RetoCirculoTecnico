package co.com.csanvel.api;

import co.com.csanvel.model.balance.RqAccount;
import co.com.csanvel.model.balance.RsBalances;
import co.com.csanvel.model.balancemovements.*;
import co.com.csanvel.model.transaction.*;
import co.com.csanvel.usecase.balancemovements.BalancemovementsUseCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Router.class,  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class RouterTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    BalancemovementsUseCase balancemovementsUseCase;

    @MockBean
    HeaderFilter headerFilter;

    @MockBean
    Handler handler;

    static final String BALANCEMOVEMENTS = "/balance/movements";


    //Headers
    static final String CHANNEL = "Channel";
    static final String DATE = "Date";
    static final String DATE_TIME = "DateTime";
    static final String IP = "Ip";
    static final String CLIENTID = "ClientId";
    static final String CLIENTTYPE = "ClientType";

    static final String VALUE_CHENNEL = "APP";
    static final String VALUE_DATE = "2019/12/12";
    static final String VALUE_DATE_TIME = "19:00:00";
    static final String VALUE_IP = "1.1.1.1";
    static final String VALUE_CLIENTID = "1234556788";
    static final String VALUE_CLIENTTYPE = "CC";

    private RqBalanceMovements getRequestBalanceMovements(){
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

    private RsBalanceMovements getResponseBalanceMovements(){
        return RsBalanceMovements.builder().data(List.of(
                RsData.builder()
                        .responseSize(1)
                        .flagMoreRecords(false)
                        .account(RqAccount.builder()
                                .number("45387654763")
                                .type("CUENTA_AHORROS")
                                .build())
                        .balance(RsBalances.builder()
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
                                .build())
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
                        .relatedTransferAccount(RsRelatedTransferAccount.builder()
                                .type("")
                                .number("")
                                .build())
                        .office(RqOffice.builder()
                                .name("2005678")
                                .code("2005678")
                                .build())
                        .customer(RsCustomer.builder()
                                .name("LUIS")
                                .build())
                        .build()
        )).status(RsStatus.builder()
                .code("000")
                .severity("INFO")
                .detail("TRANSACION EXITOSA")
                .title("TRANSACION EXITOSA")
                .build())
                .build();
    }

    @Before
    public void init(){
        webTestClient = webTestClient.mutate().responseTimeout(Duration.ofMillis(300000)).build();
        Mockito.when(balancemovementsUseCase.getBalanceMovements(getRequestBalanceMovements())).thenReturn(Mono.just(getResponseBalanceMovements()));
    }

    @Test
    public void routerFunction() {

        webTestClient
                .post()
                .uri(BALANCEMOVEMENTS)
                .body(Mono.just(getRequestBalanceMovements()), RqBalanceMovements.class)
                .header("Content-Type","application/json")
                .header(CHANNEL, VALUE_CHENNEL)
                .header(DATE, VALUE_DATE)
                .header(DATE_TIME, VALUE_DATE_TIME)
                .header(IP, VALUE_IP)
                .header(CLIENTID, VALUE_CLIENTID)
                .header(CLIENTTYPE, VALUE_CLIENTTYPE)
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }
}