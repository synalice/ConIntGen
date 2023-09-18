package ru.synalice.cig;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This class receives a method and a list of its arguments and then manages
 * everything regarding an invocation of this method.
 */
class MethodInvoker {

    public static void invoke(Class<?> classOfTheMethod, @NotNull Method method, Class<?>[] typesOfParams, int numberOfParams, String... arguments) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Object[] argumentsAsTypes = TypeConvertor.converseArgumentsToTypes(typesOfParams, numberOfParams, arguments);
        method.setAccessible(true);
        method.invoke(classOfTheMethod, argumentsAsTypes);
    }

}
