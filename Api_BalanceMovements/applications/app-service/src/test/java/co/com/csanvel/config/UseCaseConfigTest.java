package co.com.csanvel.config;

import co.com.csanvel.model.balance.gateways.BalanceGateway;
import co.com.csanvel.model.transaction.gateways.TransactionGateway;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UseCaseConfigTest {

    private UseCaseConfig useCaseConfig;

    @Mock
    BalanceGateway balanceGateway;

    @Mock
    TransactionGateway transactionGateway;

    @Before
    public void init(){
        useCaseConfig = new UseCaseConfig();
    }

    @Test
    public void balancemovementsUseCase() {
        Assert.assertNotNull(useCaseConfig.balancemovementsUseCase(balanceGateway, transactionGateway));
    }
}