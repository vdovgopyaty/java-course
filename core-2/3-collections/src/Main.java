import java.util.*;

public class Main {

    public static void main(String[] args) {
        first();
        second();
    }

    public static void first() {
        String[] words = {"Апельсин", "Яблоко", "Арбуз", "Персик", "Яблоко", "Киви", "Мандарин", "Дыня", "Виноград",
                "Апельсин", "Абрикос", "Банан", "Грейпфрут", "Клубника", "Черешня", "Малина", "Банан", "Абрикос",
                "Яблоко", "Киви"};

        HashMap<String, Integer> wordMap = new HashMap<>();
        for (String word : words) {
            wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);
        }

        for(Map.Entry entry: wordMap.entrySet()) {
            System.out.println(entry.getKey() + " (" + entry.getValue() + ")");
        }
    }

    public static void second() {
        Phonebook pb = new Phonebook();
        pb.add("Иванов", "+79091234567");
        pb.add("Иванов", "+79091234567");
        pb.add("Иванов", "+79098313852");
        pb.add("Петров", "+79001244415");

        System.out.print("Иванов: ");
        for (String phone : pb.get("Иванов")) {
            System.out.print(phone + " ");
        }
        System.out.println();

        System.out.print("Петров: ");
        for (String phone : pb.get("Петров")) {
            System.out.print(phone + " ");
        }
    }
}
