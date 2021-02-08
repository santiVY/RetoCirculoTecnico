package co.com.csanvel.restproduct;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import javax.net.ssl.SSLException;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class WebClientConfigTest {

    @InjectMocks
    WebClientConfig webClientConfig;


    @Before
    public void init() {
        webClientConfig = new WebClientConfig();
    }

    @Test
    public void createWebclient() throws SSLException {
        assertNotNull(webClientConfig.createWebClient());
    }
}