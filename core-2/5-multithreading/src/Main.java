public class Main {
    public static void main(String[] args) {
        System.out.println("Start sequentialCalculate()");
        ArrayCalculator ac1 = new ArrayCalculator();
        long scMs = ac1.sequentialCalculate();
        System.out.println(scMs + " ms");

        System.out.println("Start parallelCalculate()");
        ArrayCalculator ac2 = new ArrayCalculator();
        long pcMs = 0;
        try {
            pcMs = ac2.parallelCalculate();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(pcMs + " ms");

        if (ArrayCalculator.compare(ac1, ac2)) {
            System.out.println("Arrays are the same");
            System.out.println("Time reduction ratio: " + (float) pcMs / scMs);
        } else {
            System.out.println("Arrays are not the same");
        }
    }
}
