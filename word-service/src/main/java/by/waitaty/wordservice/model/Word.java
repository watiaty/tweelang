package by.waitaty.wordservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "word", uniqueConstraints = @UniqueConstraint(columnNames = {"text", "language_id"}))
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_id")
    private Long id;

    @Column(name = "text", nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @JsonManagedReference
    @OneToMany(mappedBy = "word")
    private List<Transcription> transcriptions;

    @JsonManagedReference
    @OneToMany(mappedBy = "word")
    private List<Relation> relations;

    @JsonManagedReference
    @OneToMany(mappedBy = "word")
    private List<WordUsage> usages;

    @Column(name = "weight", nullable = false, columnDefinition = "int default 0")
    private int weight;
}