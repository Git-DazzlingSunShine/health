import org.junit.Test;

public class DemoTest {
    public static void main(String[] args) {

    }

    @Test
    public void test() {
        int x=1;
        int i = 9;
        while (i >= 1) {
            x = 2 * (x + 1);
            i--;
        }
        System.out.println(x);
    }


}
