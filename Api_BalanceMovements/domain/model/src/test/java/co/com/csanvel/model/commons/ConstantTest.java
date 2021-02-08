package co.com.csanvel.model.commons;

import org.junit.Assert;
import org.junit.Test;

public class ConstantTest {

    @Test
    public void Constan(){
        Assert.assertThrows(IllegalStateException.class, () -> new Constant());
    }

}