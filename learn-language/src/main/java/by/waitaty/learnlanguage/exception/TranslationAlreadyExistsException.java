package by.waitaty.learnlanguage.exception;

import by.waitaty.learnlanguage.entity.Language;

public class TranslationAlreadyExistsException extends RuntimeException {
    public TranslationAlreadyExistsException(String word, String translation, Language lang) {
        super("Translation " + translation + " for word " + word + " already exists on language " + lang.name());
    }
}
