package ru.tolstikhin.pastebox.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "pasteboxes")
@Entity
public class PasteboxEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data", nullable = false)
    private String data;

    @Column(name = "hash", nullable = false, unique = true)
    private String hash;

    @Column(name = "life_time", nullable = false)
    private Timestamp lifeTime;

    @Column(name = "status_id", nullable = false, unique = true)
    private Integer statusId;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false, insertable = false, updatable = false)
    private StatusEntity status;
}
