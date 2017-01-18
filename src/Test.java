import java.util.Objects;

public class Test {

    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        String URL = "weqwrq";

        String[] qwer = URL.split("://");
        if (Objects.equals(qwer[0], "http")) {
            System.out.println("Все хорошо");
        } else if (Objects.equals(qwer[0], "https")) {
            System.out.println("Все хорошо");
        } else {
            String[] qwerr = URL.split("\\.");
            if (Objects.equals(qwerr[0], "www")) {
                System.out.println("Все хорошо");
            }else {
                System.out.println("[Please. print correct URL (http://<URL> or https://<URL> or www.<URL>]");
            }
        }
    }
}
