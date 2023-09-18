package ru.synalice.cig;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This class receives a method and a list of its arguments and then manages
 * everything regarding an invocation of this method.
 */
class MethodInvoker {
    private static final String[] ALLOWED_PARAMETER_TYPES = {"String", "char", "Character", "int", "Integer", "float",
            "Float", "double", "Double", "long", "Long", "boolean", "Boolean", "byte", "Byte"};
    public static void invoke(Method method, ParamsData paramsData, String... arguments) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Object[] argumentsAsTypes = converseArgumentsToTypes(paramsData, arguments);
    }

    private static Object @NotNull [] converseArgumentsToTypes(@NotNull ParamsData paramsData, String[] arguments) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object[] result = new Object[paramsData.nOfParams()];

        for (int i = 0; i < paramsData.nOfParams(); i++) {
            Class<?> type = Class.forName("java.lang." + paramsData.typesOfParams()[i]);
            Method valueOfMethod = type.getMethod("valueOf", String.class);
            Object valueOfResult = valueOfMethod.invoke(null, arguments[i]);
            result[i] = valueOfResult;
        }

        return result;
    }

    public static boolean paramsAreOfCorrectType(Class<?>[] types) {
        for (Class<?> type : types) {
            if (!type.getSimpleName().)
        }   
    }
}
