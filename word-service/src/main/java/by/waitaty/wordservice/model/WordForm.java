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
@Table(name = "word_form")
@Data
public class WordForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_form_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "main_word_id", nullable = false)
    private Word mainWord;

    @ManyToOne
    @JoinColumn(name = "derived_word_id", nullable = false)
    private Word derivedWord;

    @Column(name = "relationship_type")
    private String relationshipType;
}
