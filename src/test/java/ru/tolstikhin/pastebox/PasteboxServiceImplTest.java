package ru.tolstikhin.pastebox;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.tolstikhin.pastebox.api.request.PasteboxRequest;
import ru.tolstikhin.pastebox.api.response.PasteboxResponse;
import ru.tolstikhin.pastebox.api.response.PasteboxUrlResponse;
import ru.tolstikhin.pastebox.entity.PasteboxEntity;
import ru.tolstikhin.pastebox.entity.StatusEntity;
import ru.tolstikhin.pastebox.enums.Status;
import ru.tolstikhin.pastebox.exception.HashNotFoundException;
import ru.tolstikhin.pastebox.repository.PasteboxRepository;
import ru.tolstikhin.pastebox.repository.StatusRepository;
import ru.tolstikhin.pastebox.service.PasteboxServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PasteboxServiceImplTest {

    @MockBean
    private PasteboxRepository pasteboxRepository;

    @MockBean
    private StatusRepository statusRepository;

    private PasteboxServiceImpl pasteboxService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        pasteboxService = new PasteboxServiceImpl(pasteboxRepository, statusRepository);
    }

    /**
     * Тест метода {@link PasteboxServiceImpl#getByHash(String)}
     * Проверяет, возвращает ли метод правильный ответ на запрос
     */
    @Test
    @DisplayName("Проверка получения пасты по существующему хешу")
    public void testGetByHashReturnsCorrectResponse() {
        String testHash = "testHash";
        PasteboxEntity pasteboxEntity = new PasteboxEntity();
        pasteboxEntity.setData("Test Data");

        StatusEntity status = new StatusEntity();
        status.setId(1);
        status.setName(Status.PUBLIC.getStatusString());
        pasteboxEntity.setStatus(status);

        when(pasteboxRepository.getByHash(testHash)).thenReturn(Optional.of(pasteboxEntity));

        PasteboxResponse response = pasteboxService.getByHash(testHash);

        assertNotNull(response);
        assertEquals("Test Data", response.getData());
    }

    /**
     * Тест метода {@link PasteboxServiceImpl#getByHash(String)}
     * Проверяет, вызывает ли метод исключение HashNotFoundException в случае отсутствия хеша
     */
    @Test
    @DisplayName("Проверка вызова HashNotFoundException по несуществующему хешу")
    public void testGetByHashThrowsHashNotFoundException() {
        String nonExistentHash = "nonExistentHash";
        when(pasteboxRepository.getByHash(nonExistentHash)).thenReturn(Optional.empty());

        assertThrows(HashNotFoundException.class, () -> pasteboxService.getByHash(nonExistentHash));
    }

    /**
     * Тест метода {@link PasteboxServiceImpl#getFirstPublicPastebox()}
     * Проверяет, возвращает ли метод список публичных паст
     */
    @Test
    @DisplayName("Проверка получения списка публичных паст")
    public void testGetFirstPublicPastebox() {
        LocalDateTime currentTime = LocalDateTime.now();
        List<PasteboxEntity> pasteboxEntities = new ArrayList<>();

        // Создание нескольких примеров объектов PasteboxEntity и добавление их в список
        PasteboxEntity pastebox1 = new PasteboxEntity();
        pastebox1.setData("Test data 1");
        pasteboxEntities.add(pastebox1);

        PasteboxEntity pastebox2 = new PasteboxEntity();
        pastebox2.setData("Test data 2");
        pasteboxEntities.add(pastebox2);

        when(pasteboxRepository.findTop10ByStatusNameAndLifeTimeAfterOrderByIdDesc(Status.PUBLIC.getStatusString(), currentTime))
                .thenReturn(pasteboxEntities);

        List<PasteboxResponse> responseList = pasteboxService.getFirstPublicPastebox();

        assertNotNull(responseList);
        assertEquals(2, responseList.size());
    }

    /**
     * Тест метода {@link PasteboxServiceImpl#create(PasteboxRequest)}
     * Проверяет, создает ли метод новую запись и возвращает URL
     */
    @Test
    @DisplayName("Проверка создания пасты и возвращения ссылки")
    public void testCreate() {
        PasteboxRequest request = new PasteboxRequest();
        request.setData("Test data");
        request.setExpirationTimeSeconds(3600L);
        request.setStatus(Status.UNLISTED.getStatusString());

        when(pasteboxRepository.save(any())).thenReturn(new PasteboxEntity());
        when(statusRepository.getByName(Status.UNLISTED.getStatusString())).thenReturn(new StatusEntity());

        PasteboxUrlResponse response = pasteboxService.create(request);

        assertNotNull(response);
        assertNotNull(response.getUrl());
    }
}
