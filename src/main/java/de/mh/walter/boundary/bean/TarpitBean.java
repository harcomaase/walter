package de.mh.walter.boundary.bean;

import java.time.Instant;

public class TarpitBean {

    private int failedAttempts;
    private Instant lastFailedAttempt;

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public Instant getLastFailedAttempt() {
        return lastFailedAttempt;
    }

    public void setLastFailedAttempt(Instant lastFailedAttempt) {
        this.lastFailedAttempt = lastFailedAttempt;
    }
}
