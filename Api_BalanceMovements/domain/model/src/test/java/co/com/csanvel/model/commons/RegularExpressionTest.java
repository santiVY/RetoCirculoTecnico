package co.com.csanvel.model.commons;

import org.junit.Assert;
import org.junit.Test;

public class RegularExpressionTest {

    @Test
    public void RegularExpresion() {
        Assert.assertThrows(IllegalStateException.class, () -> new RegularExpression());
    }
}