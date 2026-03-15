package ru.yandex.practicum;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exceptions.error.DownloadException;
import ru.yandex.practicum.exceptions.error.NoWordsInDictionaryException;

import static ru.yandex.practicum.helpers.GeneralAssertions.isEqual;
import static ru.yandex.practicum.helpers.GeneralAssertions.isTrue;

@DisplayName("Проверка класса загрузки")
class LoaderTest extends TestData {

    @Test
    @DisplayName("Проверка, что все слова имеют 5 символов")
    public void checkCharsQuantity() throws DownloadException, NoWordsInDictionaryException {
        isEqual(0, dictionary.stream()
                .filter(word -> word.length() != 5)
                .toList().size(),
                "Ожидаемое кол-во слов с > или < 5 символами '%d' отличается от актуального '%d'");
    }

    @Test
    @DisplayName("Проверка, что ни одно слово не содержит символа - ё")
    public void checkSymbols() {
        isTrue(dictionary.stream()
                .anyMatch(word -> !word.contains("ё")),
                "В списке есть слова, содержащие букву - ё");
    }
}
