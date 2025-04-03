package project3.keeptheheap;

public interface MaxHeapInterface<T extends Comparable<? super T>>{
    public void addSequential(T newEntry);
    public void addOptimal(T newEntry);
    public T removeMax();
    public T getMax();
    public boolean isEmpty();
    public int getSize();
    public void clear();
}
