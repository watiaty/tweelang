package by.waitaty.learnlanguage.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Word {
    private Long id;
    private Language lang;
    private String word;
    private List<Translation> translations;
}
