import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasicApp {
    public static <T> List<T> filter(Predicate<T> predicate, List<T> items) {
        List<T> result = new ArrayList<T>();
        for (T item : items) {
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<Integer> inputData = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> outputData = filter(n -> n % 2 == 0, inputData);
        System.out.println(outputData);

        // public class BasicApp$1 implements Runnable {
        //             @Override
        //            public void run() {
        //
        //            }
        // }
        // new Thread(new BasicApp$1()).start();

        doSomething(() -> System.out.println("method1"));
        doSomething(() -> System.out.println("method2"));

    }

    public static void doSomething(Runnable r) {
        System.out.println("do something start");
        r.run();
        System.out.println("do something end");
    }
}
