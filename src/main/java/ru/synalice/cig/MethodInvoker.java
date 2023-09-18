package ru.synalice.cig;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * This class receives a method and a list of its arguments and then manages
 * everything regarding an invocation of this method.
 */
class MethodInvoker {
    private static final List<String> ALLOWED_PARAMETER_TYPES = Arrays.asList("String", "char", "Character", "int",
            "Integer", "float", "Float", "double", "Double", "long", "Long", "boolean", "Boolean", "byte", "Byte");

    public static void invoke(Class<?> classOfTheMethod, @NotNull Method method, Class<?>[] typesOfParams, int numberOfParams, String... arguments) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Object[] argumentsAsTypes = converseArgumentsToTypes(typesOfParams, numberOfParams, arguments);
        method.setAccessible(true);
        method.invoke(classOfTheMethod, argumentsAsTypes);
    }

    private static Object @NotNull [] converseArgumentsToTypes(Class<?>[] typesOfParams, int numberOfParams, String[] arguments) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object[] result = new Object[numberOfParams];

        for (int i = 0; i < numberOfParams; i++) {
            Class<?> type = Class.forName("java.lang." + typesOfParams[i]);
            Method valueOfMethod = type.getMethod("valueOf", String.class);
            Object valueOfResult = valueOfMethod.invoke(null, arguments[i]);
            result[i] = valueOfResult;
        }

        return result;
    }

    public static void checkForIllegalParamTypes(Class<?> @NotNull [] typesOfParams, String methodName) throws IllegalParameterTypeException {
        for (Class<?> paramType : typesOfParams) {
            if (!ALLOWED_PARAMETER_TYPES.contains(paramType.getSimpleName())) {
                throw new IllegalParameterTypeException(methodName, paramType.getSimpleName());
            }
        }
    }
}
