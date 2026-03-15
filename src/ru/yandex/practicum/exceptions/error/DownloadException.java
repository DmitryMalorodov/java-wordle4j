package ru.yandex.practicum.exceptions.error;

import ru.yandex.practicum.exceptions.Levelable;

import java.io.IOException;

public class DownloadException extends IOException implements Levelable {
    private static final String LEVEL = "ERROR";

    public DownloadException(String message) {
        super(message);
    }

    @Override
    public String getLevel() {
        return LEVEL;
    }
}
