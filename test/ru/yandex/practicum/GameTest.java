package ru.yandex.practicum;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.yandex.practicum.exceptions.info.NotValidWordException;
import ru.yandex.practicum.exceptions.info.TerminateGameException;
import ru.yandex.practicum.exceptions.info.WordDoesNotExistsException;

import java.util.List;
import java.util.stream.Stream;

import static ru.yandex.practicum.helpers.GeneralAssertions.*;

@DisplayName("Проверка игровых методов")
public class GameTest extends TestData {

    @Test
    @DisplayName("Проверка завершения игры по истечению кол-ва попыток")
    public void checkGameTerminateBySteps() {
        try {
            wordleGame.decrementSteps();
        } catch (TerminateGameException _) {}

        isFalse(wordleGame.getGameActive(), "Игра не завершилась по истечении попыток!");
    }

    @Test
    @DisplayName("Проверка завершения игры при угаданном слове")
    public void checkGameTerminateByCorrectWord() {
        try {
            wordleGame.checkUserWord(wordleGame.getAnswer());
        } catch (TerminateGameException _) {}

        isFalse(wordleGame.getGameActive(), "Игра не завершилась после угаданного слова!");
    }

    private static Stream<Arguments> getMasks() {
        return Stream.of(
                Arguments.of("ааааа", "-----"),
                Arguments.of("весло", "++--+"),
                Arguments.of("тропа", "-^^--"),
                Arguments.of("волос", "+^-^-")
        );
    }

    @ParameterizedTest
    @MethodSource("getMasks")
    @DisplayName("Проверка маски показываемой пользователю при угадангных буквах в слове")
    public void checkMask(String answer, String expMask) {
        String actMask = "";
        try {
            actMask = wordleGame.checkUserWord(answer);
        } catch (TerminateGameException _) {}

        isEqual(expMask, actMask,
                "Ожидаемая маска '%s' отличается от фактической '%s'");
    }

    private static List<String> getValidWords() {
        return List.of(
                "абвер",
                "огонь"
        );
    }

    @ParameterizedTest
    @MethodSource("getValidWords")
    @DisplayName("Проверка, что слова состоящие из русских букв и 5 символов валидны")
    public void checkValid(String validWord) {
        boolean result = false;
        try {
            result = wordleGame.isWordValid(validWord);
        } catch (NotValidWordException | WordDoesNotExistsException _) {}

        isTrue(result,
                STR."Ошибка! Ожидаемое валидное слово '\{validWord}' фактически не валидно");
    }

    private static List<String> getNotValidWords() {
        return List.of(
                "аааа",
                "аааааа",
                " ",
                "     ",
                "CHECK",
                "check",
                "12345"
        );
    }

    @ParameterizedTest
    @MethodSource("getNotValidWords")
    @DisplayName("Проверка не валидных слов")
    public void checkNotValid(String notValidWord) {
        boolean result = false;
        try {
            result = wordleGame.isWordValid(notValidWord);
        } catch (NotValidWordException | WordDoesNotExistsException _) {}

        isFalse(result,
                STR."Ошибка! Ожидаемое не валидное слово '\{notValidWord}' фактически валидно");
    }

    @Test
    @DisplayName("Проверка отсеивания слов для подсказок с ранее неверно введенными буквами")
    public void checkFilterWordsByNotCorrectLetters() {
        try {
            wordleGame.checkUserWord("ведла");
            wordleGame.getHelp();
        } catch (TerminateGameException _) {}

        isFalse(wordleDictionary.getWords().stream()
                .anyMatch(word -> word.contains("л") || word.contains("а")),
                "Есть слова в списке с буквами 'л' или/и 'а'");
    }

    @Test
    @DisplayName("Проверка отсеивания слов в которых нет угаданных букв")
    public void checkFilterWordsByCorrectLetters() {
        try {
            wordleGame.checkUserWord("девла");
            wordleGame.getHelp();
        } catch (TerminateGameException _) {}

        isTrue(wordleDictionary.getWords().stream()
                .allMatch(word -> word.contains("в") && word.contains("е") && word.contains("д")),
                "Не у всех слов в списке есть буквы 'в' или/и 'е' или/и 'д'");
    }

    @Test
    @DisplayName("Проверка отсеивания слов в которых нет угаданных букв на своих позициях")
    public void checkFilterWordsByCorrectLettersPosition() {
        try {
            wordleGame.checkUserWord("ааара");
            wordleGame.getHelp();
        } catch (TerminateGameException _) {}

        isTrue(wordleDictionary.getWords().stream()
                        .allMatch(word -> String.valueOf(word.charAt(3)).equals("р")),
                "Не у всех слов в списке есть буква 'р' есть и стоит на верной позиции");
    }
}