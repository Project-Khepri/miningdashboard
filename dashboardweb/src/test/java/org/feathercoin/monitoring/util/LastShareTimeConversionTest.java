package org.feathercoin.monitoring.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LastShareTimeConversionTest {
    @Test
    public void testConversion(){
        assertEquals("2013-12-27 07:38:55", LastShareTimeConverter.convertShareTime(1388126335L));
    }

}
