package com.phonecompany.billing;

import com.phonecompany.model.MinutePrice;
import com.phonecompany.model.PhoneCallRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelephoneBillCalculatorImpl implements TelephoneBillCalculator {

    private Map<String, Integer> numberOfCalls = new HashMap<>();

    @Override
    public BigDecimal calculate(String phoneLog) {
        final List<PhoneCallRecord> phoneCalls = new ArrayList<>();
        BigDecimal result = BigDecimal.ZERO;
        if (phoneLog != null) {
            final String[] phoneLogByLines = phoneLog.split(System.lineSeparator());
            for (String line : phoneLogByLines) {
                phoneCalls.add(createPhoneCallRecord(line));
            }

            final Integer numberOfCallsToMostCalledNumber = getNumberOfCallsToMostCalledNumber(phoneCalls);

            for (PhoneCallRecord phoneCallRecord : phoneCalls) {
                if (numberOfCalls.get(phoneCallRecord.getPhoneNumber()).intValue() < numberOfCallsToMostCalledNumber) {
                    result = result.add(calculatePhoneCallPrice(phoneCallRecord));
                }
            }
        }
        return result;
    }

    private PhoneCallRecord createPhoneCallRecord(String phoneLog) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        final String[] split = phoneLog.split(",");
        if (split != null && split.length == 3) {
            return new PhoneCallRecord(split[0], LocalDateTime.parse(split[1], dateTimeFormatter), LocalDateTime.parse(split[2], dateTimeFormatter));
        } else {
            throw new IllegalArgumentException("Wrong line=" + phoneLog);
        }
    }

    private BigDecimal calculatePhoneCallPrice(final PhoneCallRecord phoneCallRecord) {
        final List<MinutePrice> minutePriceList = new ArrayList<>();
        LocalDateTime start = phoneCallRecord.getStart();
        while (start.isBefore(phoneCallRecord.getEnd())) {
            LocalDateTime end = start.plusMinutes(1);
            minutePriceList.add(new MinutePrice(start, end, null));
            start = end;
        }

        int minuteCount = 0;
        for (MinutePrice minutePrice : minutePriceList) {
            minuteCount++;
            if (minuteCount > 5) {
                minutePrice.setPrice(new BigDecimal("0.2"));
            } else {
                final LocalTime localTime = minutePrice.getFrom().toLocalTime();
                if (localTime.isAfter(LocalTime.parse("07:59:59")) && localTime.isBefore(LocalTime.parse("16:00:00"))) {
                    minutePrice.setPrice(new BigDecimal("1.0"));
                } else {
                    minutePrice.setPrice(new BigDecimal("0.5"));
                }
            }
        }

        return minutePriceList.stream()
                .map(MinutePrice::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Integer getNumberOfCallsToMostCalledNumber(final List<PhoneCallRecord> phoneCalls) {
        for (PhoneCallRecord phoneCallRecord : phoneCalls) {
            Integer val = numberOfCalls.get(phoneCallRecord.getPhoneNumber());
            numberOfCalls.put(phoneCallRecord.getPhoneNumber(), val == null ? 1 : val + 1);
        }

        Map.Entry<String, Integer> max = null;

        for (Map.Entry<String, Integer> entry : numberOfCalls.entrySet()) {
            if (max == null || entry.getValue() > max.getValue())
                max = entry;
        }
        return max.getValue();
    }
}
