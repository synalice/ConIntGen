# ConIntGen

This library generates a console interface, so you don't have to. All you need to do
is annotate you methods and write `Console.run(Main.class)` where you pass in a class
with annotated methods.

The library will also prompt you for the input of all parameters specified in
functions. It uses tons of reflection code under the hood! :-)

## Example

Just copy the snippet bellow and run it. You'll get the hang of it once you use it.

```java
import ru.synalice.cig.Console;
import ru.synalice.cig.RegisterCommand;

public class Main {
    public static void main(String[] args) {
        Console.run(Main.class);
    }

    @RegisterCommand(value = "GREET", about = "Greets the user")
    private static void greetRunnable(String greet, int number) {
        System.out.println("Hello, " + greet + "! Number: " + number);
    }

    @RegisterCommand(value = "SHOP", about = "Prints all products")
    private static void shopRunnable() {
        System.out.println("Apples");
        System.out.println("Oranges");
        System.out.println("Potatoes");
    }
}
```

## Installation
xxxxxxxxx

## Your help
Create and issue if there is anything wrong. There might be some bugs. Even some major
ones. I've yet to write tests for this.
