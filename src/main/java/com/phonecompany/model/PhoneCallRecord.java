package com.phonecompany.model;

import java.time.LocalDateTime;

public class PhoneCallRecord {

    private String phoneNumber;
    private LocalDateTime start;
    private LocalDateTime end;

    public PhoneCallRecord(String phoneNumber, LocalDateTime start, LocalDateTime end) {
        this.phoneNumber = phoneNumber;
        this.start = start;
        this.end = end;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}
