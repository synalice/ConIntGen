package ru.synalice.cig;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * The main class of this library. It has only one static method called
 * {@link #run(Class)} ()}, which starts the program.
 */
public final class Console {
    // private static final MethodProcessor methodProcessor = new MethodProcessor();
    // private static final IOProcessor ioProcessor = new IOProcessor();
    private static final MethodInvoker methodInvoker = new MethodInvoker();

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
     * @param classToScan This class will be scanned for methods containing
     *                    {@link RegisterCommand} annotation.
     */
    public static void run(@NotNull Class<?> classToScan) {
        var commandsWithMethods = MethodProcessor.extractCommandsWithMethods(classToScan);

        if (commandsWithMethods.isEmpty()) {
            IOProcessor.DefaultPhrases.noCommandsFound();
            IOProcessor.DefaultPhrases.shuttingDown();
            return;
        }

        IOProcessor.extractKnownCommands(commandsWithMethods.get());
        IOProcessor.DefaultPhrases.printKnownCommands();

        while (true) {
            Optional<String> userInput = IOProcessor.readUserInput();

            if (userInput.isEmpty()) {
                continue;
            }

            String command = IOProcessor.extractCommand(userInput.get());
            String[] arguments = IOProcessor.extractArguments(userInput.get());
            Optional<Method> method = MethodProcessor.matchCommandToMethod(commandsWithMethods.get(), command);

            if (method.isEmpty()) {
                IOProcessor.DefaultPhrases.commandNotFound(command);
                continue;
            }

            ParamsData paramsData = MethodProcessor.getDataAboutMethod(method.get());

            if (arguments.length != paramsData.nOfParams()) {
                IOProcessor.DefaultPhrases.unevenNumberOfArguments(paramsData.nOfParams(), arguments.length);
                continue;
            }

            if (MethodInvoker.paramsAreOfCorrectType(paramsData.typesOfParams())) {
            }

            if (arguments.length == 0) {
                MethodInvoker.invoke(method.get(), paramsData);
            } else {
                MethodInvoker.invoke(method.get(), paramsData, arguments);
            }
        }
    }
}
