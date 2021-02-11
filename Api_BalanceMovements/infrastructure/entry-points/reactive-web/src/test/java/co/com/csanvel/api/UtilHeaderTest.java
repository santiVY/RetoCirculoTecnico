package co.com.csanvel.api;

import co.com.csanvel.model.commons.HeaderBM;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;

@RunWith(MockitoJUnitRunner.class)
public class UtilHeaderTest {

    @Autowired
    UtilHeader utilHeader;

    @MockBean
    HeaderBM headerBM;

    @Mock
    ServerRequest serverRequest;

    MockServerHttpRequest request;
    MockServerWebExchange exchange;

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



    @Before
    public void init(){
        request = MockServerHttpRequest.post("http://demo.com")
                .contentType(MediaType.APPLICATION_JSON)
                .header(CHANNEL, VALUE_CHENNEL)
                .header(DATE, VALUE_DATE)
                .header(DATE_TIME, VALUE_DATE_TIME)
                .header(IP, VALUE_IP)
                .header(CLIENTID, VALUE_CLIENTID)
                .header(CLIENTTYPE, VALUE_CLIENTTYPE)
                .build();

        exchange = MockServerWebExchange.from(request);
    }

    @Test(expected = IllegalStateException.class)
    public void utilHeaderTest() {
        UtilHeader header = new UtilHeader();
    }

    @Test
    public void getHeaders() {
        ServerRequest serverRequest = ServerRequest.create(exchange, HandlerStrategies.withDefaults().messageReaders());

        HeaderBM header = HeaderBM.builder()
                .channel(VALUE_CHENNEL)
                .date(VALUE_DATE)
                .dateTime(VALUE_DATE_TIME)
                .ip(VALUE_IP)
                .clientId(VALUE_CLIENTID)
                .clientType(VALUE_CLIENTTYPE)
                .build();

        HeaderBM headerBM = (HeaderBM) UtilHeader.getHeaders(serverRequest, HeaderBM.class);

        Assert.assertNotNull(headerBM);
        Assert.assertEquals(header, headerBM);
    }
}