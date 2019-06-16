import hashtable.*;

public class Main {

    public static void main(String[] args) {
//        Hashtable hashtable = new HashtableImpl(5);
        Hashtable hashtable = new DoubleHashtableImpl(5);

        hashtable.put(new Item(1, "Orange"), 150);
        hashtable.put(new Item(77, "Banana"), 100);
        hashtable.put(new Item(60, "Lemon"), 250);
        hashtable.put(new Item(21, "Potato"), 67);
        hashtable.put(new Item(55, "Milk"), 120);

        System.out.println("Size is " + hashtable.getSize());
        hashtable.display();

        System.out.println("Cost potato is " + hashtable.get(new Item(21, "Potato")));
        System.out.println("Cost banana is " + hashtable.get(new Item(77, "Banana")));

        hashtable.remove(new Item(21, "Potato"));
        hashtable.remove(new Item(77, "Banana"));
        System.out.println("Cost banana is " + hashtable.get(new Item(77, "Banana")));
        hashtable.display();
    }
}
