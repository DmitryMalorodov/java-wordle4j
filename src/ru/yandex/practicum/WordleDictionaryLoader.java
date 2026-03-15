package ru.yandex.practicum;

import ru.yandex.practicum.exceptions.error.DownloadException;
import ru.yandex.practicum.exceptions.error.NoWordsInDictionaryException;

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
    private static List<String> dictionary;
    private static final int WORDS_LENGTH = 5;

    /**
     * Считывание слов из файла
     */
    private static void downloadDictionary() throws DownloadException, NoWordsInDictionaryException {
        List<String> words = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(PATH, StandardCharsets.UTF_8))) {
            while (reader.ready()) {
                words.add(reader.readLine());
            }
        } catch (IOException e) {
            throw new DownloadException("Не удалось загрузить словарь!");
        }

        if (words.isEmpty()) {
            throw new NoWordsInDictionaryException("В словаре нет ни одного слова!");
        }

        dictionary = words;
    }

    public WordleDictionary getWordleDictionary() throws DownloadException, NoWordsInDictionaryException {
        downloadDictionary();
        return new WordleDictionary(filterByFiveCharsQuantity().stream()
                .map(WordleDictionaryLoader::convertWord)
                .collect(Collectors.toList()));
    }

    /**
     * Фильтрация списка слов по 5 символам
     */
    private static List<String> filterByFiveCharsQuantity() {
        return WordleDictionaryLoader.dictionary.stream()
                .filter(word -> word.length() == WORDS_LENGTH)
                .toList();
    }

    /**
     * Метод приводит слово к нижнему регистру и меняет буквы ё на е
     * @param word - слово для конвертации
     * @return - обработанное слово
     */
    public static String convertWord(String word) {
        return word.toLowerCase().replace("ё", "е");
    }
}