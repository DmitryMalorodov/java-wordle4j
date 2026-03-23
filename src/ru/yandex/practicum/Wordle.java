package ru.yandex.practicum;

import ru.yandex.practicum.exceptions.error.DownloadException;
import ru.yandex.practicum.exceptions.error.FileNotFoundException;
import ru.yandex.practicum.exceptions.error.NoWordsInDictionaryException;
import ru.yandex.practicum.exceptions.info.NotValidWordException;
import ru.yandex.practicum.exceptions.info.TerminateGameException;
import ru.yandex.practicum.exceptions.info.WordDoesNotExistsException;
import ru.yandex.practicum.helpers.PrintLog;

import java.util.Scanner;

import static ru.yandex.practicum.WordleDictionaryLoader.convertWord;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать заёгрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {
    private static final Scanner scanner = new Scanner(System.in);
    private static WordleGame game;
    private static final int STEPS = 6;

    public static void main(String[] args) {
        try {
            WordleDictionary wordleDictionary = new WordleDictionaryLoader().getWordleDictionary();
            String randomWord = wordleDictionary.getRandomWord();
            game = new WordleGame(randomWord, wordleDictionary, STEPS);
            game();
        } catch (DownloadException | NoWordsInDictionaryException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            PrintLog.printLog(e);
        }
    }

    private static void game() {
        while (game.getGameActive()) {
            try {
                System.out.println("Введите слово:");
                String userWord = convertWord(scanner.nextLine());

                if (userWord.isEmpty()) {
                    userWord = game.getHelp();
                    System.out.println(userWord);
                } else {
                    while (!game.isWordValid(userWord)) {
                        userWord = scanner.nextLine();
                    }
                }

                System.out.println(game.checkUserWord(userWord));
                game.decrementSteps();
            } catch (NotValidWordException | WordDoesNotExistsException | TerminateGameException e) {
                System.out.println(e.getMessage());
                PrintLog.printLog(e);
            }
        }
    }
}