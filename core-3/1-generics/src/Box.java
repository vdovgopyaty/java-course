import Fruits.Fruit;

import java.util.ArrayList;
import java.util.Arrays;

public class Box<FruitType extends Fruit> {
    private ArrayList<FruitType> fruits;

    Box(FruitType... fruits) {
        this.fruits = new ArrayList<>(Arrays.asList(fruits));
    }

    public void add(FruitType... fruits) {
        this.fruits.addAll(
            new ArrayList<>(Arrays.asList(fruits))
        );
    }

    public void add(Box<FruitType> from) {
        if (this.fruits.equals(from.fruits)) {
            return;
        }
        this.fruits.addAll(from.fruits);
        from.fruits.clear();
    }

    public float getWeight() {
        float weight = 0;
        for (FruitType fruit : fruits) {
            weight += fruit.getWeight();
        }
        return weight;
    }

    public boolean compare(Box<FruitType> another) {
        return this.getWeight() == another.getWeight();
    }
}
