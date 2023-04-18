package util;

public enum StatusCode {

    OK(200), FOUND(302);

    public int statusValue;

    StatusCode(int statusValue) {
        this.statusValue = statusValue;
    }

    public boolean isRedirect() {
        return this == FOUND;
    }

    public int getStatusValue() {
        return statusValue;
    }
}

