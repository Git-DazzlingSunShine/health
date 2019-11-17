import org.junit.Test;
import sun.tools.tree.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class LockTest {

    //value1：线程不安全
    private static int value1 = 0;
    //value2：使用乐观锁
    private static AtomicInteger value2 = new AtomicInteger(0);
    //value3：使用悲观锁
    private static int value3 = 0;

    private static synchronized void increaseValue3() {
        value3++;
    }

    public static void main(String[] args) throws Exception {
        //开启1000个线程，并执行自增操作
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 1000; ++i) {

        }

        //打印结果
        Thread.sleep(1000);
        System.out.println("线程不安全：" + value1);
        System.out.println("乐观锁(AtomicInteger)：" + value2);
        System.out.println("悲观锁(synchronized)：" + value3);
    }

    @Test
    public void test() {

    }

}
