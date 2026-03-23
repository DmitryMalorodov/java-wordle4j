package ru.yandex.practicum.helpers;

import java.util.Random;

public class Helper {

    private Helper() {
    }

    public static int getRandom(int from, int to) {
        return new Random().nextInt(from, to);
    }
}
