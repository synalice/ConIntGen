import ru.synalice.cig.Console;
import ru.synalice.cig.RegisterCommand;

public class Main {
    public static void main(String[] args) {
        Console.run(Main.class);
    }

    @RegisterCommand("GREET")
    private static void greetRunnable(String greet, int number) {
        System.out.println("Hello, " + greet + "! Number: " + number);
    }

    @RegisterCommand("SHOP")
    private static void shopRunnable() {
        System.out.println("Apples");
        System.out.println("Oranges");
        System.out.println("Potatoes");
    }
}
