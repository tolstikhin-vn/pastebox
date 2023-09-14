package ru.tolstikhin.pastebox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tolstikhin.pastebox.api.request.PasteboxRequest;
import ru.tolstikhin.pastebox.api.response.PasteboxResponse;
import ru.tolstikhin.pastebox.api.response.PasteboxUrlResponse;
import ru.tolstikhin.pastebox.entity.PasteboxEntity;
import ru.tolstikhin.pastebox.exception.HashNotFoundException;
import ru.tolstikhin.pastebox.repository.PasteboxRepository;

import java.util.List;

@Service
public class PasteboxServiceImpl implements PasteboxService {

    private final PasteboxRepository pasteboxRepository;

    @Autowired
    public PasteboxServiceImpl(PasteboxRepository pasteboxRepository) {
        this.pasteboxRepository = pasteboxRepository;
    }

    @Override
    public PasteboxResponse getByHash(String hash) {
        if (pasteboxRepository.getByHash(hash).isEmpty()) {
            throw new HashNotFoundException("Hash not found");
        }
        PasteboxEntity pastebox = pasteboxRepository.getByHash(hash).get();
        return new PasteboxResponse(pastebox.getData(), pastebox.isPublic());
    }

    @Override
    public List<PasteboxResponse> getFirstPublicPastebox(int amount) {
//        LocalDateTime currentTime = LocalDateTime.now();
//
//        if (pasteboxRepository.findByIsPublicAndLifeTimeAfterOrderByLifeTimeAsc(true, currentTime).isEmpty()) {
//            throw new HashNotFoundException("Hash not found");
//        }
//
//        List<PasteboxEntity> pasteboxEntities = pasteboxRepository.findByIsPublicAndLifeTimeAfterOrderByLifeTimeAsc(true, currentTime).get();
//        return pasteboxEntities.subList(0, amount);
        return null;
    }

    @Override
    public PasteboxUrlResponse create(PasteboxRequest request) {
        return null;
    }
}
