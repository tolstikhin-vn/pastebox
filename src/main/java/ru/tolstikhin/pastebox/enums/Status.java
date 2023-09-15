package ru.tolstikhin.pastebox.enums;

public enum Status {
    PUBLIC("public"),
    UNLISTED("unlisted");

    private String statusString;

    Status(String statusString) {
        this.statusString = statusString;
    }

    public String getStatusString() {
        return statusString;
    }
}
