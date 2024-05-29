package by.waitaty.wordservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "synonym")
@Data
public class Synonym {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "synonym_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "word_id", nullable = false)
    private Word word;

    @ManyToOne
    @JoinColumn(name = "synonym_word_id", nullable = false)
    private Word synonymWord;
}
