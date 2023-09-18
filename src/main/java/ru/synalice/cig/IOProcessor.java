package ru.synalice.cig;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Manages all input/output operations
 */
class IOProcessor {
    private static final Scanner scanner = new Scanner(System.in);
    static List<String> knownCommands = new ArrayList<>();

    public static void extractKnownCommands(@NotNull HashMap<String, Method> commandsWithMethods) {
        knownCommands.addAll(commandsWithMethods.keySet());
    }

    public static Optional<String> readUserInput() {
        StandardMessages.awaitingInputSymbol();
        return cleanUserInput(scanner.nextLine());
    }

    private static Optional<String> cleanUserInput(String userInput) {
        userInput = userInput.strip();

        if (userInput.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(userInput);
        }
    }

    public static String extractCommand(@NotNull String userInput) {
        return userInput.split(" ")[0];
    }

    public static String @NotNull [] extractArguments(@NotNull String userInput) {
        String[] splitUserInput = userInput.split(" ");

        if (splitUserInput.length > 1) {
            return Arrays.copyOfRange(splitUserInput, 1, splitUserInput.length);
        } else {
            return new String[]{};
        }
    }

    public static class StandardMessages {

        public static void printKnownCommands() {
            System.out.println("Список доступных команд:");

            for (String command : knownCommands) {
                System.out.println("- " + command);
            }

            System.out.println("- HELP");

            if (!knownCommands.contains("END")) {
                System.out.println("- END");
            }
        }

        public static void shuttingDown() {
            System.out.println("Завершение работы");
        }

        public static void awaitingInputSymbol() {
            System.out.print("> ");
        }

    }

    public static class ErrorMessages {
        private static final String ERROR_PREFIX = "ОШИБКА: ";
        public static void noCommandsFound() {
            System.out.println(ERROR_PREFIX + "Доступные команды не найдены");
        }

        public static void commandNotFound(String command) {
            System.out.println(ERROR_PREFIX + "Команда " + command + " не найдена");
        }

        public static void unevenNumberOfArguments(int required, int received) {
            System.out.println(ERROR_PREFIX + "Неверное число аргументов. Ожидается " + required + ", получено " + received);
        }

        public static void illegalParameterTypeUsed(String methodName, String illegalType) {
            System.out.println(ERROR_PREFIX + "Метод " + methodName + " содержит параметр запрещенного типа " + illegalType);
        }
    }
}
