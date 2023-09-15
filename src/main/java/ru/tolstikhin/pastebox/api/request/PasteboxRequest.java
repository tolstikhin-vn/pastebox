package ru.tolstikhin.pastebox.api.request;

import lombok.Data;

@Data
public class PasteboxRequest {

    private String data;
    private long expirationTimeSeconds;
    private String status;
}
