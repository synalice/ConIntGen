package ru.synalice.cig;

public class IllegalParameterTypeException extends Exception {
    private final String methodName;
    private final String illegalType;

    public String getMethodName() {
        return methodName;
    }

    public String getIllegalType() {
        return illegalType;
    }

    public IllegalParameterTypeException(String methodName, String illegalType) {
        this.methodName = methodName;
        this.illegalType = illegalType;
    }
}
