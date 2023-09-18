import ru.synalice.cig.Console;
import ru.synalice.cig.RegisterCommand;

public class Main {
    public static void main(String[] args) {
        Console.run(Main.class);
    }

    @RegisterCommand("GREET")
    private static void greetRunnable(String greet, int a) {
        System.out.println("Hello, " + greet + "! Number: " + a);
    }

    @RegisterCommand("SHOP")
    private static void shopRunnable() {
        Shop shop = new Shop();
        shop.listGoods();
    }

    private static void imNotAnnotated() {
    }
}
