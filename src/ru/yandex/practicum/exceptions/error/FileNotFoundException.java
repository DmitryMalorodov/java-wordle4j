package ru.yandex.practicum.exceptions.error;

import ru.yandex.practicum.exceptions.Levelable;

public class FileNotFoundException extends Exception implements Levelable {
    private static final String LEVEL = "ERROR";

    public FileNotFoundException(String message) {
        super(message);
    }
    @Override
    public String getLevel() {
        return LEVEL;
    }
}
