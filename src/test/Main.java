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
