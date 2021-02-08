package co.com.csanvel.restproduct;

import co.com.csanvel.model.balance.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class RestConsumerBalanceTest {

    public static MockWebServer mockBackEnd;


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

    private RsBalance getResponseBalance(){
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

    @Before
    public void init() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @Test
    public void getBalanceTestNotNull() throws JsonProcessingException {
        RestConsumerBalance restConsumerBalance = new RestConsumerBalance();
        ObjectMapper mapper = new ObjectMapper();
        restConsumerBalance.setUrl(mockBackEnd.url("/balances").toString());
        restConsumerBalance.setWebClient(WebClient.builder().build());
        mockBackEnd.enqueue(new MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(mapper.writeValueAsString(getResponseBalance())));

        StepVerifier.create(restConsumerBalance.getBalance(getRequestBalance()))
                .assertNext(Assert::assertNotNull)
                .verifyComplete();
    }

    @Test(expected=NullPointerException.class)
    public void getBalanceNull() throws JsonProcessingException {
        RestConsumerBalance restConsumerBalance = new RestConsumerBalance();
        ObjectMapper mapper = new ObjectMapper();
        restConsumerBalance.setUrl(mockBackEnd.url("/balances").toString());
        restConsumerBalance.setWebClient(WebClient.builder().build());
        mockBackEnd.enqueue(new MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(mapper.writeValueAsString(getResponseBalance())));

        restConsumerBalance.getBalance(null).subscribe();
    }

    @After
    public void shutDown() throws IOException {
        mockBackEnd.shutdown();
    }
}