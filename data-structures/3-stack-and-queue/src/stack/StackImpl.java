package stack;

public class StackImpl<E> implements Stack<E> {

    private E[] data;
    private int size;
    private int maxSize;

    public StackImpl(int maxSize) {
        this.data = createArray(maxSize);
        this.maxSize = maxSize;
    }

    @SuppressWarnings("unchecked")
    private E[] createArray(int maxSize) {
        return (E[]) new Object[maxSize];
    }

    @Override
    public void push(E value) {
        if (isFull()) {
            throw new StackOverloadException();
        }
        data[size++] = value;
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }

        E value = data[--size];
        data[size] = null;
        return value;
    }

    @Override
    public E peek() {
        return data[size - 1];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == data.length;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void clear() {
        this.data = createArray(maxSize);
        this.size = 0;
    }
}
