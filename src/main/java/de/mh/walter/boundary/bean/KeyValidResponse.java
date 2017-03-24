package de.mh.walter.boundary.bean;

public class KeyValidResponse {

    private boolean valid;

    public KeyValidResponse() {
    }

    public KeyValidResponse(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
