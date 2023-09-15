package ru.tolstikhin.pastebox.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tolstikhin.pastebox.entity.StatusEntity;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Long> {
    StatusEntity getByName(String name);
}
