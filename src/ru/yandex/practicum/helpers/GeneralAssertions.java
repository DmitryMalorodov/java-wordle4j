package ru.yandex.practicum.helpers;

import org.junit.jupiter.api.Assertions;

public class GeneralAssertions {

    public static <T> void isEqual(T expValue, T actValue, String errorMessage) {
        Assertions.assertEquals(expValue, actValue, String.format(errorMessage, expValue, actValue));
    }

    public static void isTrue(Boolean actValue, String errorMessage) {
        Assertions.assertTrue(actValue, errorMessage);
    }

    public static void isFalse(Boolean actValue, String errorMessage) {
        Assertions.assertFalse(actValue, errorMessage);
    }
}
