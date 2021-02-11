package co.com.csanvel.api;

import co.com.csanvel.model.commons.HeaderBM;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.reactive.function.server.ServerRequest;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class CommonTest {

    Common common;
    HeaderBM header;

    @Mock
    ServerRequest request;

    @Test
    public void testValidateHeaders(){


        header = HeaderBM.builder()
                .date("2020/08/23")
                .dateTime("15:59:00")
                .ip("192.168.164.25")
                .clientType("CC")
                .channel("APP")
                .clientId("1234567")
                .build();
        assertEquals(header = HeaderBM.builder()
                .date("2019/08/23")
                .dateTime("15:59:00")
                .ip("192.168.164.25")
                .clientType("CC")
                .channel("APP")
                .clientId("1234567")
                .build(), header);
    }


    @Test(expected = IllegalStateException.class)
    public void constructorTest(){
        Common common = new Common();
    }
}
