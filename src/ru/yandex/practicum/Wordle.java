package ru.yandex.practicum;

import ru.yandex.practicum.exceptions.NotValidWordException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {
    private static WordleDictionary wordleDictionary;
    private static String randomWord;
    private static WordleGame game;

    public static void main(String[] args) {
        wordleDictionary = new WordleDictionaryLoader().getWordleDictionary();
        randomWord = new WordleDictionaryLoader().getWordleDictionary().getRandomWord();
        game = new WordleGame(randomWord, wordleDictionary, 6);

        game();
    }

    private static void game() {
        try {
            while (game.getGameActive()) {
                System.out.println("Введите слово:");
                Scanner scanner = new Scanner(System.in);
                String userWord = scanner.nextLine().toLowerCase();

                if (userWord.isEmpty()) {
                    userWord = game.getHelp();
                    System.out.println(userWord);
                } else {
                    while (!game.isWordValid(userWord)) {
                        userWord = scanner.nextLine();
                    }
                }

                game.decrementSteps();

                System.out.println(game.checkUserWord(userWord));
            }
        } catch (NotValidWordException e) {
            System.out.println(e.getMessage());
            PrintLog.printLog(e.getMessage());
            game();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
