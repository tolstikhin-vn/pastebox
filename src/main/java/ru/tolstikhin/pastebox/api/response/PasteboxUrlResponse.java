package ru.tolstikhin.pastebox.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasteboxUrlResponse {
    private String url;
}
