import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class App1 {
    static class Person {
        private String name;
        private int age;
        private int salary;

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public int getSalary() {
            return salary;
        }

        public Person(String name, int age, int salary) {
            this.name = name;
            this.age = age;
            this.salary = salary;
        }
    }

    public static void main(String[] args) {
        String[] words = {"A", "A", "A", "A", "B", "B", "B", "C", "C", "D"};
        String result = Arrays.stream(words).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Comparator.comparingLong(e -> e.getValue()))
                .get().getKey();
        System.out.println(result);

//        Person[] persons = {new Person("Bob1", 30, 50000), new Person("Bob2", 40, 46000), new Person("Bob3", 20, 32000)};
//        Arrays.stream(persons).mapToDouble(Person::getSalary).average();
//
//        final int N = 2;
//        Arrays.stream(persons)
//                .sorted((o1, o2) -> o2.age - o1.age)
//                .limit(N)
//                .map(Person::getName)
//                .collect(Collectors.joining(", ", N + " самых страших сотрудника зовут: ", "."));
    }
}
