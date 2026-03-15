package ru.yandex.practicum.helpers;

import ru.yandex.practicum.exceptions.Levelable;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class PrintLog {

    public static void printLog(Levelable e) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("log.txt", true))) {
            pw.println(String.format("%s: %s; %s", LocalDateTime.now(), e.getMessage(), e.getLevel()));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}