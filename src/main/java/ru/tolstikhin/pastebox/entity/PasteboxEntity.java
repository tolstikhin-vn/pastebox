package ru.tolstikhin.pastebox.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "pasteboxes")
@Entity
public class PasteboxEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "data", nullable = false)
    private String data;

    @Column(name = "hash", nullable = false)
    private String hash;

    @Column(name = "life_time", nullable = false)
    private LocalDateTime lifeTime;

    @Column(name = "is_public", nullable = false)
    private boolean isPublic;
}
