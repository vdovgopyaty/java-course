import Fruits.Apple;
import Fruits.Orange;

public class Main {

    public static void main(String[] args) {
        String[] array = {"Generics", ", ", "Hello"};

        System.out.print("Исходный массив: ");
        for (String item : array) {
            System.out.print(item);
        }
        System.out.println();

        ArrayModifier.elementsSwap(array, 0, 2);

        System.out.print("Измененный массив: ");
        for (String item : array) {
            System.out.print(item);
        }
        System.out.println();

        System.out.println("Массив, преобразованный в ArrayList" + ArrayModifier.arrayToArrayList(array));
        System.out.println();


        Box<Apple> box1 = new Box<>(new Apple(), new Apple());
        box1.add(new Apple());
        System.out.println("Вес коробки 1: " + box1.getWeight());

        Box<Apple> box2 = new Box<>(new Apple());
        box2.add(new Apple(), new Apple());
        System.out.println("Вес коробки 2: " + box2.getWeight());

        if (box1.compare(box2)) {
            System.out.println("Вес коробок 1 и 2 равен");
        } else {
            System.out.println("Вес коробок 1 и 2 не равен");
        }

        box1.add(box2);
        System.out.println("Вес коробки 1: " + box1.getWeight());

        Box<Orange> box3 = new Box<>(new Orange());
        box3.add(new Orange(), new Orange());
        System.out.println("Вес коробки 3: " + box3.getWeight());
    }
}
