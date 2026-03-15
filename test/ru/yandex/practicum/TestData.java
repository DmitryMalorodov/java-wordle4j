package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import ru.yandex.practicum.exceptions.error.DownloadException;
import ru.yandex.practicum.exceptions.error.NoWordsInDictionaryException;

import java.util.List;

public class TestData {
    static final WordleDictionaryLoader loader = new WordleDictionaryLoader();
    static WordleDictionary wordleDictionary;
    static List<String> dictionary;
    static WordleGame wordleGame;

    @BeforeEach
    public void setUp() throws DownloadException, NoWordsInDictionaryException {
        wordleDictionary = loader.getWordleDictionary();
        dictionary = wordleDictionary.getWords();
        wordleGame = new WordleGame("ведро", wordleDictionary, 1);
    }
}
