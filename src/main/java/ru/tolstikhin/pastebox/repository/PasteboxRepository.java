package ru.tolstikhin.pastebox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tolstikhin.pastebox.entity.PasteboxEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PasteboxRepository extends JpaRepository<PasteboxEntity, Long> {
    Optional<PasteboxEntity> getByHash(String hash);

    Optional<List<PasteboxEntity>> findTop10ByStatusNameAndLifeTimeAfterOrderByIdDesc(String statusName, LocalDateTime currentTime);
}
