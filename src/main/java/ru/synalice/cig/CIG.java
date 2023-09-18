package ru.synalice.cig;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class CIG {
    private static final Class<RegisterCommand> ANNOTATION_CLASS = RegisterCommand.class;
    private final Method[] allDeclaredMethods;
    private final List<String> knownCommands = new ArrayList<>();
    private final HashMap<String, Method> commandAndMethod = new HashMap<>();

    private final Class<?> classWithAnnotations;

    public CIG(@NotNull Class<?> classWithAnnotations) {
        this.classWithAnnotations = classWithAnnotations;
        this.allDeclaredMethods = classWithAnnotations.getDeclaredMethods();

        extractAnnotatedMethods(allDeclaredMethods);
        extractKnownCommands();
    }

    private String cleanUserInput(String input) {
        return input.strip();
    }

    private void gracefulExit() {
        System.out.println("ИНФО: Завершение работы");
        System.exit(0);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        if (this.commandAndMethod.isEmpty()) {
            System.out.println("ИНФО: Доступных команд нет");
            gracefulExit();
        }

        printKnownCommands();
        System.out.println();

        // noinspection InfiniteLoopStatement
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            input = cleanUserInput(input);

            if (input.isEmpty()) {
                continue;
            }

            String[] splitInput = splitInputBySpace(input);

            String command = splitInput[0];
            Method methodToRun = this.commandAndMethod.get(command);
            int numberOfParams = methodToRun.getParameterCount();
            Class<?>[] paramTypes = methodToRun.getParameterTypes();

            if (numberOfParams == 0) {
                try {
                    methodToRun.setAccessible(true);
                    methodToRun.invoke(classWithAnnotations);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    System.out.println("ОШИБКА: " + e);
                }
                continue;
            }

            if (numberOfParams > splitInput.length - 1 || numberOfParams < splitInput.length - 1) {
                System.out.println("ОШИБКА: Неверное число аргументов. Ожидается " + numberOfParams + ", получено " + (splitInput.length - 1));
                continue;
            }

            List<Object> paramsForInvocation = new ArrayList<>();

            for (int i = 0; i < numberOfParams; i++) {
                try {
                    switch (paramTypes[i].getSimpleName()) {
                        case ("char"): {
                        }
                        case ("Character"): {
                            String tmp = String.valueOf(splitInput[i]);

                            if (tmp.length() > 1) {
                                System.err.println("ОШИБКА: Тип char/Character не может содержать больше одного символа");
                            } else {
                                paramsForInvocation.add(tmp);
                            }

                            break;
                        }

                        case ("String"): {
                            paramsForInvocation.add(String.valueOf(splitInput[i+1]));
                            break;
                        }

                        case ("boolean"): {
                        }
                        case ("Boolean"): {
                            paramsForInvocation.add(Boolean.valueOf(splitInput[i+1]));
                            break;
                        }

                        case ("int"): {
                        }
                        case ("Integer"): {
                            paramsForInvocation.add(Integer.valueOf(splitInput[i+1]));
                            break;
                        }

                        case ("double"): {
                        }
                        case ("Double"): {
                            paramsForInvocation.add(Double.valueOf(splitInput[i+1]));
                            break;
                        }

                        case ("long"): {
                        }
                        case ("Long"): {
                            paramsForInvocation.add(Long.valueOf(splitInput[i+1]));
                            break;
                        }

                        case ("byte"): {
                        }
                        case ("Byte"): {
                            paramsForInvocation.add(Byte.valueOf(splitInput[i+1]));
                            break;
                        }

                        default: {
                            System.err.println("ОШИБКА: Невозможно преобразовать ввод к типу " +
                                    paramTypes[i].getSimpleName());
                            break;
                        }
                    }
                } catch (NumberFormatException e) {
                    System.err.println("ОШИБКА: Невозможно преобразовать ввод к типу " +
                            paramTypes[i].getSimpleName());
                }
            }

            try {
                methodToRun.setAccessible(true);
                methodToRun.invoke(this.classWithAnnotations, paramsForInvocation.toArray());
            } catch (IllegalAccessException | InvocationTargetException e) {
                System.out.println("ОШИБКА: " + e);
            }
        }
    }

    private String[] splitInputBySpace(String input) {
        return input.split(" ");
    }

    private void extractAnnotatedMethods(Method[] methods) {
        for (Method method : methods) {
            if (method.isAnnotationPresent(ANNOTATION_CLASS)) {
                String command = method.getAnnotation(ANNOTATION_CLASS).value();
                this.commandAndMethod.put(command, method);
            }
        }
    }

    private void extractKnownCommands() {
        for (Method method : this.allDeclaredMethods) {
            if (method.isAnnotationPresent(ANNOTATION_CLASS)) {
                this.knownCommands.add(method.getAnnotation(ANNOTATION_CLASS).value());
            }
        }
    }

    private void printKnownCommands() {
        System.out.println("ИНФО: Список доступных команд:");

        for (String command : knownCommands) {
            System.out.println("- " + command);
        }

        if (!this.knownCommands.contains("END")) {
            System.out.println("- END");
        }
    }
}
