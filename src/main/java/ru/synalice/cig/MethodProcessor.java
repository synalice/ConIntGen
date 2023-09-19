package ru.synalice.cig;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Collects all annotated methods and what arguments of what types are needed for a
 * particular method.
 */
class MethodProcessor {
    private static final Class<RegisterCommand> ANNOTATION_CLASS = RegisterCommand.class;

    @Contract(pure = true)
    public static Optional<Map<String, Method>> extractCommandsWithMethods(@NotNull Class<?> classToScan) {
        Method[] methods = classToScan.getDeclaredMethods();
        Map<String, Method> commandsWithMethods = new HashMap<>();

        for (Method method : methods) {
            if (method.isAnnotationPresent(ANNOTATION_CLASS)) {
                String command = method.getAnnotation(ANNOTATION_CLASS).value();
                commandsWithMethods.put(command, method);
            }
        }

        if (commandsWithMethods.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(commandsWithMethods);
        }
    }

    // @Contract(pure = true)
    // public static Optional<Map<String, String>> extractCommandsWithAbout(@NotNull Class<?> classToScan) {
    //     Method[] methods = classToScan.getDeclaredMethods();
    //     Map<String, String> commandsWithAbout = new HashMap<>();
    //
    //     for (Method method : methods) {
    //         if (method.isAnnotationPresent(ANNOTATION_CLASS)) {
    //             String command = method.getAnnotation(ANNOTATION_CLASS).value();
    //             String about = method.getAnnotation(ANNOTATION_CLASS).about();
    //             commandsWithAbout.put(command, about);
    //         }
    //     }
    //
    //     if (commandsWithAbout.isEmpty()) {
    //         return Optional.empty();
    //     } else {
    //         return Optional.of(commandsWithAbout);
    //     }
    // }

    public static Optional<Method> matchCommandToMethod(@NotNull Map<String, Method> commandsWithMethods, String command) {
        return Optional.ofNullable(commandsWithMethods.get(command));
    }

    @Contract(pure = true)
    public static int getNumberOfParams(@NotNull Method method) {
        return method.getParameterCount();
    }

    @Contract(pure = true)
    public static Class<?> @NotNull [] getTypesOfParams(@NotNull Method method) {
        return method.getParameterTypes();
    }
}
