package com.phonecompany.billing;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;


public class TelephoneBillCalculatorTest {

    TelephoneBillCalculator toTest = new TelephoneBillCalculatorImpl();

    @Test
    public void givenOneRecord_whenCalculate_returnZero() {
        final BigDecimal result = toTest.calculate("420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57");
        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    public void givenThreeMinutesCallInLowTariff_whenCalculate_returnOneAndHalf() {
        final BigDecimal result = toTest.calculate("420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57" + System.lineSeparator() + "420776562353,18-01-2020 08:59:20,18-01-2020 09:10:00" + System.lineSeparator() + "420776562353,19-01-2020 08:59:20,19-01-2020 09:10:00");
        assertEquals(new BigDecimal("1.5"), result);
    }

    @Test
    public void givenThreeMinutesCallInHighTariff_whenCalculate_returnOneAndHalf() {
        final BigDecimal result = toTest.calculate("420774577453,13-01-2020 15:10:15,13-01-2020 15:12:57" + System.lineSeparator() + "420776562353,18-01-2020 08:59:20,18-01-2020 09:10:00" + System.lineSeparator() + "420776562353,19-01-2020 08:59:20,19-01-2020 09:10:00");
        assertEquals(new BigDecimal("3.0"), result);
    }

    @Test
    public void givenTwoMinutesCallInLowTariff_whenCalculate_returnOne() {
        final BigDecimal result = toTest.calculate("420774577453,13-01-2020 16:00:15,13-01-2020 16:01:57" + System.lineSeparator() + "420776562353,18-01-2020 08:59:20,18-01-2020 09:10:00" + System.lineSeparator() + "420776562353,19-01-2020 08:59:20,19-01-2020 09:10:00");
        assertEquals(new BigDecimal("1.0"), result);
    }

    @Test
    public void givenTwoMinutesInLowTariffAndTwoInHigh_whenCalculate_returnThree() {
        final BigDecimal result = toTest.calculate("420774577453,13-01-2020 07:58:15,13-01-2020 08:01:57" + System.lineSeparator() + "420776562353,18-01-2020 08:59:20,18-01-2020 09:10:00" + System.lineSeparator() + "420776562353,19-01-2020 08:59:20,19-01-2020 09:10:00");
        assertEquals(new BigDecimal("3.0"), result);
    }
}
