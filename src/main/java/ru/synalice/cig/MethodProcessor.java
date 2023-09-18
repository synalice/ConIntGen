package ru.synalice.cig;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Optional;

/**
 * Collects all annotated methods and what arguments of what types are needed for a
 * particular method.
 */
class MethodProcessor {
    private static final Class<RegisterCommand> ANNOTATION_CLASS = RegisterCommand.class;

    @Contract(pure = true)
    public static Optional<HashMap<String, Method>> extractCommandsWithMethods(@NotNull Class<?> classToScan) {
        Method[] methods = classToScan.getDeclaredMethods();
        HashMap<String, Method> annotatedMethods = new HashMap<>();

        for (Method method : methods) {
            if (method.isAnnotationPresent(ANNOTATION_CLASS)) {
                String command = method.getAnnotation(ANNOTATION_CLASS).value();
                annotatedMethods.put(command, method);
            }
        }

        if (annotatedMethods.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(annotatedMethods);
        }
    }

    public static Optional<Method> matchCommandToMethod(@NotNull HashMap<String, Method> commandsWithMethods, String command) {
        return Optional.ofNullable(commandsWithMethods.get(command));
    }

    public static MethodData getDataAboutMethod(Method method) {
        int nOfParams = method.getParameterCount();
        Class<?>[] typesOfParams = method.getParameterTypes();
        return new MethodData(nOfParams, typesOfParams);
    }
}
