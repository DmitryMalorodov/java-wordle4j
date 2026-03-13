package ru.yandex.practicum;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PrintLog {

    public static void printLog(String message) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("log.txt", true))) {
            pw.println(message);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
