package co.com.csanvel;

import org.junit.Assert;
import org.junit.Test;

public class MainApplicationTest {

    @Test
    public void main() {
        try {
            MainApplication.main(null);
        }catch (Exception e){
            Assert.assertEquals("Args must not be null", e.getMessage());
        }
    }
}