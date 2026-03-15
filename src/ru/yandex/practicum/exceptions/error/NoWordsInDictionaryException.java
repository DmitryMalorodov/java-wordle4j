package ru.yandex.practicum.exceptions.error;

import ru.yandex.practicum.exceptions.Levelable;

public class NoWordsInDictionaryException extends Exception implements Levelable {
    private static final String LEVEL = "ERROR";

    public NoWordsInDictionaryException(String message) {
        super(message);
    }

    @Override
    public String getLevel() {
        return LEVEL;
    }
}
