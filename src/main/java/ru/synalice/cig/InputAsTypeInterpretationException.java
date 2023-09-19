package ru.synalice.cig;

public class InputAsTypeInterpretationException extends Exception {
    private final String input;
    private final Class<?> type;

    public InputAsTypeInterpretationException(String input, Class<?> type) {
        this.input = input;
        this.type = type;
    }

    public String getInput() {
        return input;
    }

    public Class<?> getType() {
        return type;
    }
}
