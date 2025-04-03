package project3.keeptheheap;
import java.util.Arrays;

public final class MaxHeap<T extends Comparable<? super T>> 
            implements MaxHeapInterface<T> {
    private T[] heap;
    private int lastIndex;
    private boolean initialized = false;
    private static final int DEFAULT_CAPACITY = 25;
    private static final int MAX_CAPACITY = 10000;

    public MaxHeap() {
        this(DEFAULT_CAPACITY);
    }
    public MaxHeap(int initialCapacity) {
        if (initialCapacity < DEFAULT_CAPACITY) {
            initialCapacity = DEFAULT_CAPACITY;
        } else {
            try {
                checkCapacity(initialCapacity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @SuppressWarnings("unchecked")
        T[]tempHeap = (T[]) new Comparable[initialCapacity + 1];
        heap = tempHeap;
        lastIndex = 0;
        initialized = true;
    }

    public void checkCapacity(int initialCapacity) throws Exception {
        if (initialCapacity > MAX_CAPACITY) {
            throw new Exception("Initial Capacity larger than Max Capacity");
        }
    }

    public boolean checkInitialized() {
        return initialized;
    }

    @Override
    public void addSequential(T newEntry) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'addSequential'");
    }

    @Override
    public void addOptimal(T newEntry) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'addOptimal'");
    }

    @Override
    public T removeMax() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'removeMax'");
    }

    @Override
    public T getMax() {
        checkInitialized();
        T root = null;
        if (!isEmpty()) {
            root = heap[1];
        }
        return root;
    }

    @Override
    public boolean isEmpty() {
        return lastIndex < 1;
    }

    @Override
    public int getSize() {
        return lastIndex;
    }

    @Override
    public void clear() {
        checkInitialized();
        while (lastIndex > -1) {
            heap[lastIndex] = null;
            lastIndex--;
        }
        lastIndex = 0;
    }
    
}
