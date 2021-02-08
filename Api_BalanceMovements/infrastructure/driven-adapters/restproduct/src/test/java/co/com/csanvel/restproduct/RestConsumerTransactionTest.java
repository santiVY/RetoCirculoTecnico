package co.com.csanvel.restproduct;

import co.com.csanvel.model.balance.*;
import co.com.csanvel.model.transaction.*;
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
public class RestConsumerTransactionTest {

    public static MockWebServer mockBackEnd;


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
    public void init() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @Test
    public void getTransactionTestNotNull() throws JsonProcessingException {
        RestConsumerTransaction restConsumerTransaction = new RestConsumerTransaction();
        ObjectMapper mapper = new ObjectMapper();
        restConsumerTransaction.setUrl(mockBackEnd.url("/movements").toString());
        restConsumerTransaction.setWebClient(WebClient.builder().build());
        mockBackEnd.enqueue(new MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(mapper.writeValueAsString(getResponseTransacion())));

        StepVerifier.create(restConsumerTransaction.getTransaction(getRequestTransaction()))
                .assertNext(Assert::assertNotNull)
                .verifyComplete();
    }

    @Test(expected=NullPointerException.class)
    public void getBalanceNull() throws JsonProcessingException {
        RestConsumerBalance restConsumerBalance = new RestConsumerBalance();
        ObjectMapper mapper = new ObjectMapper();
        restConsumerBalance.setUrl(mockBackEnd.url("/movements").toString());
        restConsumerBalance.setWebClient(WebClient.builder().build());
        mockBackEnd.enqueue(new MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(mapper.writeValueAsString(getResponseTransacion())));

        restConsumerBalance.getBalance(null).subscribe();
    }

    @Test
    public void getTransactionMoreMovementsTestNotNull() throws JsonProcessingException {
        RestConsumerTransaction restConsumerTransaction = new RestConsumerTransaction();
        ObjectMapper mapper = new ObjectMapper();
        restConsumerTransaction.setUrl(mockBackEnd.url("/movements/2").toString());
        restConsumerTransaction.setWebClient(WebClient.builder().build());
        mockBackEnd.enqueue(new MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(mapper.writeValueAsString(getResponseTransacion())));

        StepVerifier.create(restConsumerTransaction.getTransactionMoreMovements(getRequestTransaction()))
                .assertNext(Assert::assertNotNull)
                .verifyComplete();
    }


    @After
    public void shutDown() throws IOException {
        mockBackEnd.shutdown();
    }
}