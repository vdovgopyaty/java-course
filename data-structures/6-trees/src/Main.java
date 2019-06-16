import tree.Tree;
import tree.TreeImpl;

public class Main {

    public static void main(String[] args) {
        Tree<Integer> tree = new TreeImpl<>(6);
        tree.add(60);
        tree.add(25);
        tree.add(66);
        tree.add(15);
        tree.add(5);
        tree.add(20);
        tree.add(45);
        tree.add(30);
        tree.add(32);
        tree.add(55);

        System.out.println("Find 32 is " + tree.find(32));
        System.out.println("Find 60 is " + tree.find(60));
        System.out.println("Find 25 is " + tree.find(25));
        System.out.println("Find 555 is " + tree.find(555));
        tree.display();

        System.out.println("Remove 25 is " + tree.remove(25));
        tree.display();
    }
}
