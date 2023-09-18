package ru.synalice.cig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A method marked with this annotation will be executed when user calls a
 * corresponding command. A command is passed into this annotation as a value.
 * <p>
 * Is a method contains any parameters, the program will prompt user to input them,
 * and they will be passed into a method when it's invoked.
 * <p>
 * Method's parameters must be one of the following types:
 * {@code char}, {@code Character}, {@code int}, {@code Integer}, {@code float},
 * {@code Float}, {@code double}, {@code Double}, {@code long}, {@code Long},
 * {@code boolean}, {@code Boolean}, {@code byte}, {@code Byte}, {@code String}.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RegisterCommand {
    String value();
}
