package ru.yandex.practicum;

import ru.yandex.practicum.exceptions.NotValidWordException;
import ru.yandex.practicum.exceptions.TerminateGameException;

import java.util.*;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {

    private String answer;
    private int steps;
    private WordleDictionary dictionary;
    private Boolean isGameActive = true;

    private List<String> usersWords = new ArrayList<>(); // слова которые ввел пользователь и подсказанные ему слова
    private Map<Integer, String> matchedLetters = new HashMap<>(); // верные буквы в верной позиции
    private Set<String> existsLetters = new HashSet<>(); // верные буквы в неверной позиции
    private Set<String> wrongLetters = new HashSet<>(); // неверные буквы

    public WordleGame(String answer, WordleDictionary dictionary, int steps) {
        this.answer = answer;
        this.dictionary = dictionary;
        this.steps = steps;
    }

    public void decrementSteps() throws TerminateGameException {
        isGameActive = --steps != 0;

        if (!isGameActive) {
            throw new TerminateGameException("Количество попыток закончилось!");
        }
    }

    public Boolean getGameActive() {
        return isGameActive;
    }

    /**
     * Проверка соответствия, слова введенного пользователем, ответу
     * 1. Сразу сравнить слово пользователя с ответом и если слово совпадает, то завершить игру
     * 2. Если слово не угадано, то устанавливаем подсказки
     *      - '+' если буква есть в слове и на своем месте
     *      - '-' если буква отсутствует в слове
     *      - '^' если буква есть в слове, но на другом месте
     * @param userWord - слово пользователя
     * @return - подсказка об угаданных буквах или сообщение об успехе в случае верного слова
     */
    public String checkUserWord(String userWord) throws TerminateGameException {
        if (userWord.equals(answer)) {
            isGameActive = false;
            throw new TerminateGameException("Вы угадали слово!");
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < userWord.length(); i++) {
            if (String.valueOf(userWord.charAt(i)).equals(String.valueOf(answer.charAt(i)))) {
                builder.append("+");
                matchedLetters.put(i, String.valueOf(userWord.charAt(i)));
            }
            else if (answer.contains(String.valueOf(userWord.charAt(i)))) {
                builder.append("^");
                existsLetters.add(String.valueOf(userWord.charAt(i)));
            }
            else {
                builder.append("-");
                wrongLetters.add(String.valueOf(userWord.charAt(i)));
            }
        }

        usersWords.add(userWord);
        return builder.toString();
    }

    public boolean isWordValid(String word) throws NotValidWordException {
        boolean isWordValid = word.length() == 5 && word.matches("^[А-Яа-я]+$");
        if (!isWordValid) {
            throw new NotValidWordException("Введенное слово не корректно, введите слово из пяти русских букв");
        }

        return isWordValid;
        //return word.length() == 5 && word.matches("^[А-Яа-я]+$");
    }

    public String getHelp() {
        List<String> dictionaryWords = dictionary.getWords();

        //отсеивание слов из словаря с неверными буквами
        for (String word : new ArrayList<>(dictionaryWords)) {
            for (String letter : wrongLetters) {
                if (word.contains(letter)) {
                    dictionaryWords.remove(word);
                    break;
                }
            }
        }

        //отсеивание слов из словаря у которых нет угаданных букв
        for (String word : new ArrayList<>(dictionaryWords)) {
            for (String letter : existsLetters) {
                if (!word.contains(letter)) {
                    dictionaryWords.remove(word);
                    break;
                }
            }
        }

        for (String word : new ArrayList<>(dictionaryWords)) {
            for (Map.Entry<Integer, String> entry : matchedLetters.entrySet()) {
                if (!String.valueOf(word.charAt(entry.getKey())).equals(entry.getValue())) {
                    dictionaryWords.remove(word);
                    break;
                }
            }
        }

        dictionaryWords.removeAll(usersWords);

        return dictionaryWords.get(new Random().nextInt(0, dictionaryWords.size()));
    }
}