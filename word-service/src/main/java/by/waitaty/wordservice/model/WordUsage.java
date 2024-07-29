package by.waitaty.wordservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "word_usage")
public class WordUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usage_id")
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "word_id", nullable = false)
    private Word word;

    @JsonManagedReference
    @OneToMany(mappedBy = "usage")
    private List<Translation> translations;

    @Enumerated(EnumType.STRING)
    @Column(name = "part_of_speech", nullable = false)
    private PartOfSpeech partOfSpeech;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private ProficiencyLevel level;

    @Column(name = "definition", nullable = false)
    private String definition;
}
