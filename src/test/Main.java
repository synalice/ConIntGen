import ru.synalice.cig.CIG;
import ru.synalice.cig.RegisterCommand;


public class Main {
    public static void main(String[] args) {
        CIG cig = new CIG(Main.class);
        cig.run();
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
