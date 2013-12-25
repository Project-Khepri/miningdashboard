package org.feathercoin.monitoring.json.rest;

import org.feathercoin.monitoring.CurrencyValueFormatter;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;

import static org.junit.Assert.assertEquals;

public class NumberFormatTest {
    @Test
    public void testSimpleNumberFormatting() throws ParseException {
        Object [][] testValues =
                {
                        {BigDecimal.valueOf(10.45988).setScale(2, RoundingMode.HALF_UP),"10.46"},
                        {BigDecimal.valueOf(10.4).setScale(2, RoundingMode.HALF_UP),"10.4"},
                        {BigDecimal.valueOf(10).setScale(2, RoundingMode.HALF_UP),"10"},
                };

        for (Object[] testValue : testValues) {
            assertEquals((String)testValue[1],testValue[1], CurrencyValueFormatter.formatToEnCurrency((BigDecimal) testValue[0]));
        }



    }
}
