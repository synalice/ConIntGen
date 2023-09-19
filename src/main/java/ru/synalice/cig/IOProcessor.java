package ru.synalice.cig;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Manages all input/output operations
 */
class IOProcessor {
    private static final Scanner scanner = new Scanner(System.in);
    private static Map<String, String> commandsWithAbout = new HashMap<>();
    private static int LONGEST_COMMAND_LENGTH = 0;

    public static void extractKnownCommands(@NotNull Map<String, String> commandsWithAbout) {
        IOProcessor.commandsWithAbout = commandsWithAbout;
        IOProcessor.LONGEST_COMMAND_LENGTH = findLongestCommandLength(commandsWithAbout);
    }

    private static int findLongestCommandLength(@NotNull Map<String, String> commandsWithAbout) {
        int length = 0;
        for (var entry : commandsWithAbout.entrySet()) {
            if (entry.getKey().length() > LONGEST_COMMAND_LENGTH) {
                length = entry.getKey().length();
            }
        }
        return length;
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

            for (var entry : commandsWithAbout.entrySet()) {
                System.out.printf(
                        "- %-" + LONGEST_COMMAND_LENGTH + "s %s\n",
                        entry.getKey(), entry.getValue());
            }

            System.out.printf(
                    "- %-" + LONGEST_COMMAND_LENGTH + "s %s\n",
                    "HELP", "Вывод списка доступных команд");

            if (commandsWithAbout.get("END") == null) {
                System.out.printf(
                        "- %-" + LONGEST_COMMAND_LENGTH + "s %s\n",
                        "END", "Завершение работы");
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

        public static void cantInterpretInputAsType(String input, Class<?> type) {
            System.out.println(ERROR_PREFIX + "Невозможно привести " + input + " к типу " + type.getSimpleName());
        }
    }
}
