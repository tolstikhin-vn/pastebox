package ru.tolstikhin.pastebox.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PasteboxResponse {
    private final String data;
    private String status;
}
