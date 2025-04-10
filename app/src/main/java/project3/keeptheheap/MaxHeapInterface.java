package project3.keeptheheap;

import java.io.FileNotFoundException;

public interface MaxHeapInterface<T extends Comparable<? super T>> {
    public void add(T newEntry);

    public T removeMax();

    public T getMax();

    public boolean isEmpty();

    public int getSize();

    public void clear();

    public Integer[] fileToArray(String fileName) throws FileNotFoundException;
}
