package ru.synalice.cig;

import java.util.*;

public class CIG {
    private static final Scanner scanner = new Scanner(System.in);

    public static void run(Map<String, Runnable> commandsAndRunnables) {
        if (commandsAndRunnables.isEmpty()) {
            System.out.println("CIG: Доступных команд нет");
            gracefulExit();
        }

        Set<String> knownCommands = commandsAndRunnables.keySet();
        printKnownCommands(knownCommands);
        System.out.println();

        //noinspection InfiniteLoopStatement
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            input = cleanInput(input);

            if (input.isEmpty()) {
                continue;
            }

            String command = extractCommandFromInput(input);
            Optional<Runnable> runnable = matchCommandToRunnable(command, commandsAndRunnables);

            if (runnable.isEmpty() && command.equals("END")) {
                gracefulExit();
            } else if (runnable.isEmpty()) {
                System.err.println("CIG: ОШИБКА, команда " + command + " не найдена");
            } else {
                runnable.get().run();
            }
        }
    }

    // Вспомогательный метод. Выводит список доступных команд.
    private static void printKnownCommands(Set<String> commands) {
        System.out.println("Список доступных команд:");

        for (String command : commands) {
            System.out.println("- " + command);
        }
        System.out.println("- END");
    }

    // Вспомогательный метод. Находит Runnable, соответствующий указанной команде.
    private static Optional<Runnable> matchCommandToRunnable(String command, Map<String, Runnable> commandAndRunnable) {
        return Optional.ofNullable(commandAndRunnable.get(command));
    }

    // Вспомогательный метод. Очищает введенную пользователем строку.
    private static String cleanInput(String input) {
        return input.strip();
    }

    // Вспомогательный метод. Выделяет из ввода пользователя команду.
    private static String extractCommandFromInput(String input) {
        String command = "";

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) != ' ') {
                command = command.concat(String.valueOf(input.charAt(i)));
            } else {
                break;
            }
        }

        return command;
    }

    private  static void gracefulExit() {
        System.out.println("CIG: Завершение работы");
        System.exit(0);
    }
}
