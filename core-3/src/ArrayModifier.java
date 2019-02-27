import java.util.ArrayList;
import java.util.Arrays;

public class ArrayModifier {

    public static final <T> void elementsSwap(T[] array, int index1, int index2) {
        T temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    public static <E> ArrayList<E> arrayToArrayList(E[] array) {
        return new ArrayList<>(Arrays.asList(array));
    }
}
