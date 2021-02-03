package co.com.bancolombia.integration.balancetransaction;

import org.junit.runner.RunWith;

import com.intuit.karate.KarateOptions;
import com.intuit.karate.junit4.Karate;

@RunWith(Karate.class)
@KarateOptions(features = "src/test/java/co/com/bancolombia/integration/balancetransaction/balances_movements.feature")
public class BalanceMovementsRunner {

}
