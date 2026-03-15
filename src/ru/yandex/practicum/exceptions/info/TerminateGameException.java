package ru.yandex.practicum.exceptions.info;

import ru.yandex.practicum.exceptions.Levelable;

public class TerminateGameException extends Exception implements Levelable {
    private static final String LEVEL = "INFO";

    public TerminateGameException(String message) {
        super(message);
    }

    @Override
    public String getLevel() {
        return LEVEL;
    }
}
