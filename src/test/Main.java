import ru.synalice.cig.CIG;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CIG.run(new HashMap<>() {{
            put("SHOP", Main::shopRunnable);
            put("GREET", Main::greetRunnable);
        }});
    }

    private static void greetRunnable() {
        Scanner scanner = new Scanner(System.in);
        String greet = scanner.nextLine();
        System.out.println("Hello, " + greet + "!");
    }

    private static void shopRunnable() {
        Shop shop = new Shop();
        shop.listGoods();
    }
}
