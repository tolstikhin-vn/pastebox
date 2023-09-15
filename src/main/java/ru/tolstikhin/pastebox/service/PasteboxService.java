package ru.tolstikhin.pastebox.service;

import ru.tolstikhin.pastebox.api.request.PasteboxRequest;
import ru.tolstikhin.pastebox.api.response.PasteboxResponse;
import ru.tolstikhin.pastebox.api.response.PasteboxUrlResponse;

import java.util.List;

public interface PasteboxService {

    PasteboxResponse getByHash(String hash);
    List<PasteboxResponse> getFirstPublicPastebox();
    PasteboxUrlResponse create(PasteboxRequest request);
}