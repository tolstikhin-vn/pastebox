package ru.tolstikhin.pastebox.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tolstikhin.pastebox.api.request.PasteboxRequest;
import ru.tolstikhin.pastebox.api.response.PasteboxResponse;
import ru.tolstikhin.pastebox.service.PasteboxService;

import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor
public class MainController {

    private final PasteboxService pasteboxService;

    @GetMapping("/")
    public List<String> getPublicPasteList() {
        return Collections.emptyList();
    }

    @GetMapping("/{hash}")
    public PasteboxResponse getByHash(@PathVariable String hash) {
        return pasteboxService.getByHash(hash);
    }

    @PostMapping("/")
    public String add(@RequestBody PasteboxRequest request) {
        return request.getData();
    }
}
