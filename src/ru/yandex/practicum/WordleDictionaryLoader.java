package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {
    private static final String PATH = "words_ru.txt";
    private static final List<String> dictionary = downloadDictionary();

    /**
     * Считывание слов из файла
     */
    private static List<String> downloadDictionary() {
        List<String> words = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(PATH, StandardCharsets.UTF_8))) {
            while (reader.ready()) {
                words.add(reader.readLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return words;
    }

    public WordleDictionary getWordleDictionary() {
        return new WordleDictionary(filterByFiveCharsQuantity().stream()
                .map(this::convertWord)
                .collect(Collectors.toList()));
    }

    /**
     * Фильтрация списка слов по 5 символам
     */
    private static List<String> filterByFiveCharsQuantity() {
        return WordleDictionaryLoader.dictionary.stream()
                .filter(word -> word.length() == 5)
                .toList();
    }

    /**
     * Метод приводит слово к нижнему регистру и меняет буквы ё на е
     * @param word - слово для конвертации
     * @return - обработанное слово
     */
    private String convertWord(String word) {
        return word.toLowerCase().replace("ё", "е");
    }
}