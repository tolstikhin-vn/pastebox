package ru.tolstikhin.pastebox.api.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.tolstikhin.pastebox.api.request.PublicStatus;

@Data
@RequiredArgsConstructor
public class PasteboxResponse {
    private final String data;
    private final boolean isPublic;
}
