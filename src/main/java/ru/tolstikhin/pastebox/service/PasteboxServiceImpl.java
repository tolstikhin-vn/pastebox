package ru.tolstikhin.pastebox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tolstikhin.pastebox.api.request.PasteboxRequest;
import ru.tolstikhin.pastebox.enums.Status;
import ru.tolstikhin.pastebox.api.response.PasteboxResponse;
import ru.tolstikhin.pastebox.api.response.PasteboxUrlResponse;
import ru.tolstikhin.pastebox.entity.PasteboxEntity;
import ru.tolstikhin.pastebox.exception.HashNotFoundException;
import ru.tolstikhin.pastebox.repository.PasteboxRepository;
import ru.tolstikhin.pastebox.repository.StatusRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PasteboxServiceImpl implements PasteboxService {

    private final PasteboxRepository pasteboxRepository;
    private final StatusRepository statusRepository;

    private static final String URL = "http://my-awesome-pastebin.tld/";

    @Autowired
    public PasteboxServiceImpl(PasteboxRepository pasteboxRepository, StatusRepository statusRepository) {
        this.pasteboxRepository = pasteboxRepository;
        this.statusRepository = statusRepository;
    }

    /**
     * Получить пасту по ссылке
     * @param hash хеш пасты для вставки в ссылку
     * @return json пасты, содержащий пасту и статус
     */
    @Override
    public PasteboxResponse getByHash(String hash) {
        if (pasteboxRepository.getByHash(hash).isEmpty()) {
            throw new HashNotFoundException("Hash not found");
        }
        PasteboxEntity pastebox = pasteboxRepository.getByHash(hash).get();
        return new PasteboxResponse(pastebox.getData(), pastebox.getStatus().getName());
    }

    /**
     * Получить 10 публичных паст
     * @return json с пастами
     */
    @Override
    public List<PasteboxResponse> getFirstPublicPastebox() {
        LocalDateTime currentTime = LocalDateTime.now();

        // Проверить на наличие пасты с таким хешом
        Optional<List<PasteboxEntity>> pasteboxesList =
                pasteboxRepository.findTop10ByStatusNameAndLifeTimeAfterOrderByIdDesc(Status.PUBLIC.getStatusString(), currentTime);
        if (pasteboxesList.isEmpty()) {
            throw new HashNotFoundException("Hash not found");
        }

        List<PasteboxEntity> pasteboxEntities = pasteboxesList.get();
        return pasteboxEntities.stream().map(PasteboxEntity -> new PasteboxResponse(PasteboxEntity.getData())).collect(Collectors.toList());
    }

    /**
     * Создать пасту в сервисе
     * @param request тело запроса
     * @return ссылка на пасту
     */
    @Override
    public PasteboxUrlResponse create(PasteboxRequest request) {
        PasteboxEntity pastebox = new PasteboxEntity();
        pastebox.setData(request.getData());

        String hash = generateRandomHash();

        pastebox.setHash(hash);
        pastebox.setLifeTime(calculateExpirationTime(request.getExpirationTimeSeconds()));
        pastebox.setStatusId(statusRepository.getByName(request.getStatus()).getId());

        pasteboxRepository.save(pastebox);

        return new PasteboxUrlResponse(getGeneratedLink(hash));
    }

    /**
     * Рассчитать дату и время окончания хранения пасты
     * @param expirationTimeSeconds время хранения пасты в секундах
     * @return дата и время окончания хранения пасты
     */
    private Timestamp calculateExpirationTime(long expirationTimeSeconds) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expirationTime = currentTime.plusSeconds(expirationTimeSeconds);
        return Timestamp.valueOf(expirationTime);
    }

    /**
     * Сгенерировать уникальный хеш для пасты
     * @return хеш
     */
    private String generateRandomHash() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "").substring(0, 8); // Получаем первые 8 символов UUID
    }

    /**
     * Сформировать итоговую ссылку для пасты
     * @param hash хеш
     * @return итоговая ссылка
     */
    private String getGeneratedLink(String hash) {
        return URL + hash;
    }
}
