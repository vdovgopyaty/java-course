package hashtable;

public class DoubleHashtableImpl extends HashtableImpl {

    public static final int DOUBLE_HASH_CONST = 5;

    public DoubleHashtableImpl(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int getStep(Item item) {
        return hashFuncDouble(item);
    }

    private int hashFuncDouble(Item key) {
        return DOUBLE_HASH_CONST - (key.hashCode() % DOUBLE_HASH_CONST);
    }
}
