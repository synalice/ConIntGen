package ru.synalice.cig;

/**
 * The main class of this library. It has only one static method called
 * {@link #run(Class)} ()}, which starts the program.
 */
public class Console {
    /**
     * This method starts the console program. It scans {@code classToScan} class for
     * methods, annotated with {@link RegisterCommand}. It then maps these methods to
     * commands passed into {@link RegisterCommand} as value.
     * <p>
     * All parameters of annotated methods will be prompted for user input and must be
     * one of the following types:
     * {@code char}, {@code Character}, {@code int}, {@code Integer}, {@code float},
     * {@code Float}, {@code double}, {@code Double}, {@code long}, {@code Long},
     * {@code boolean}, {@code Boolean}, {@code byte}, {@code Byte}, {@code String}.
     *
     * @param classToScan
     *        This class will be scanned for methods containing
     *        {@link RegisterCommand} annotation.
     */
    public static void run(Class<?> classToScan) {
    }
}
