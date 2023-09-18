package ru.synalice.cig;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeConvertor {

    private static final List<String> ALLOWED_PARAMETER_TYPES = Arrays.asList("String", "char", "Character", "int",
            "Integer", "float", "Float", "double", "Double", "long", "Long", "boolean", "Boolean", "byte", "Byte");
    private static final Map<Class<?>, Class<?>> primitiveToWrapperMapping;

    static {
        primitiveToWrapperMapping = new HashMap<>();
        primitiveToWrapperMapping.put(int.class, Integer.class);
        primitiveToWrapperMapping.put(float.class, Float.class);
        primitiveToWrapperMapping.put(double.class, Double.class);
        primitiveToWrapperMapping.put(long.class, Long.class);
        primitiveToWrapperMapping.put(boolean.class, Boolean.class);
        primitiveToWrapperMapping.put(byte.class, Byte.class);
        primitiveToWrapperMapping.put(char.class, Character.class);
    }

    static Object @NotNull [] converseArgumentsToTypes(Class<?>[] typesOfParams, int numberOfParams, String[] arguments) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object[] result = new Object[numberOfParams];

        for (int i = 0; i < numberOfParams; i++) {
            if (typesOfParams[i] == String.class) {
                result[i] = arguments[i];
                continue;
            }

            Class<?> type;
            if (typesOfParams[i].isPrimitive()) {
                type = primitiveToWrapperMapping.get(typesOfParams[i]);
            } else {
                type = typesOfParams[i];
            }

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
