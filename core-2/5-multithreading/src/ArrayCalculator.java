import java.util.Arrays;

public class ArrayCalculator {
    private final static int SIZE = 10_000_000;
    private float[] array;

    ArrayCalculator() {
        array = new float[SIZE];

        for (int i = 0; i < SIZE; i++) {
            array[i] = 1;
        }
    }

    public long sequentialCalculate() {
        long startTime = System.currentTimeMillis();
        calculate(array, 0);
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public long parallelCalculate() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        final int halfSize = SIZE / 2;
        float[] array1 = new float[halfSize];
        float[] array2 = new float[halfSize];

        System.arraycopy(array, 0, array1, 0, halfSize);
        System.arraycopy(array, halfSize, array2, 0, halfSize);

        Thread t1 = new Thread(() -> {
            calculate(array1, 0);
        });
        Thread t2 = new Thread(() -> {
            calculate(array2, halfSize);
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.arraycopy(array1, 0, array, 0, halfSize);
        System.arraycopy(array2, 0, array, halfSize, halfSize);
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    private static float[] calculate(float[] array, int startValue) {
        for (int i = startValue; i < startValue + array.length; i++) {
            array[i - startValue] = (float) (array[i - startValue] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        return array;
    }

    public static boolean compare(ArrayCalculator ac1, ArrayCalculator ac2) {
        int comparedValuesNumber = 5;
        System.out.println("Start compare()");
        System.out.println("Start values comparison:");
        for (int i = 0; i < comparedValuesNumber; i++) {
            System.out.print(ac1.array[i] + ", ");
        }
        System.out.println();
        for (int i = 0; i < comparedValuesNumber; i++) {
            System.out.print(ac2.array[i] + ", ");
        }
        System.out.println();
        System.out.println("End values comparison:");
        for (int i = 0; i < comparedValuesNumber; i++) {
            System.out.print(ac1.array[SIZE - i - 1] + ", ");
        }
        System.out.println();
        for (int i = 0; i < comparedValuesNumber; i++) {
            System.out.print(ac2.array[SIZE - i - 1] + ", ");
        }
        System.out.println();
        return Arrays.equals(ac1.array, ac2.array);
    }
}
