package project3.keeptheheap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;;

public final class MaxHeap<T extends Comparable<? super T>>
        implements MaxHeapInterface<T> {
    private T[] heap;
    private int lastIndex;
    private boolean initialized = false;
    private static final int DEFAULT_CAPACITY = 25;
    private static final int MAX_CAPACITY = 10000;
    private int count = 0;

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
        T[] tempHeap = (T[]) new Comparable[initialCapacity + 1];
        heap = tempHeap;
        lastIndex = 0;
        initialized = true;
    }

    public MaxHeap(T[] data) {
        this(data.length);
        lastIndex = data.length;

        for (int index = 0; index < data.length; index++) {
            heap[index + 1] = data[index];
        }

        for (int rootIndex = lastIndex / 2; rootIndex > 0; rootIndex--) {
            reheap(rootIndex);
        }
        initialized = true;
    }

    public void checkCapacity(int capacity) {
        if (capacity > MAX_CAPACITY) {
            throw new IllegalStateException("Capacity larger than Max Capacity");
        }
    }

    public void doubleCapacity() {
        int newCapacity = heap.length * 2;
        checkCapacity(newCapacity);
        heap = java.util.Arrays.copyOf(heap, newCapacity);
    }

    public boolean checkInitialized() {
        return initialized;
    }

    public int getCount() {
        return count;
    }

    public T get(int index) {
        return heap[index];
    }

    @Override
    public void add(T newEntry) {
        checkInitialized();
        if (lastIndex + 1 == heap.length) {
            doubleCapacity();
        }
        int newIndex = lastIndex + 1;
        int parentIndex = newIndex / 2;
        while ((parentIndex > 0) && newEntry.compareTo(heap[parentIndex]) > 0) {
            heap[newIndex] = heap[parentIndex];
            newIndex = parentIndex;
            parentIndex = newIndex / 2;
            count++;
        } // end while
        heap[newIndex] = newEntry;
        lastIndex++;
    }

    public void printHeap10() {
        if (lastIndex > 11) {
            for (int i = 1; i <= 10; i++) {
                if (i == 1) {
                    System.out.print(this.get(i));
                } else {
                    System.out.print(" ," + this.get(i));
                }
            }
            System.out.println();
        }
    }

    @Override
    public T removeMax() {
        checkInitialized();
        T root = null;
        if (!isEmpty()) {
            root = heap[1];
            heap[1] = heap[lastIndex];
            lastIndex--;
            reheap(1);
        }
        return root;
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

    // Makes an array from the data.txt file once implemented
    @Override
    public Integer[] fileToArray(String fileName) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(fileName));
        int index = 0;
        Integer[] fileArray = new Integer[100];
        while (sc.hasNext()) {
            fileArray[index] = sc.nextInt();
            index++;
        }
        sc.close();
        return fileArray;
    }

    public void populateHeap(T[] data) {
        for (int i = 0; i < data.length; i++) {
            if (i+1 == heap.length) {
                doubleCapacity();
            }
            heap[i + 1] = data[i];
        }
    }

    private void reheap(int rootIndex) {
        boolean done = false;
        T orphan = heap[rootIndex];
        int leftChildIndex = 2 * rootIndex;
        while (!done && leftChildIndex <= lastIndex) {
            int largerChildIndex = leftChildIndex;
            int rightChildIndex = leftChildIndex + 1;
            if (rightChildIndex <= lastIndex && heap[rightChildIndex].compareTo(heap[largerChildIndex]) > 0) {
                largerChildIndex = rightChildIndex;
            }
            if (orphan.compareTo(heap[largerChildIndex]) < 0) {
                heap[rootIndex] = heap[largerChildIndex];
                rootIndex = largerChildIndex;
                leftChildIndex = 2 * rootIndex;
                count++;
            } else {
                done = true;
            }
        }
        heap[rootIndex] = orphan;
    }

    public void heapsort(int n) {
        for (int rootIndex = n / 2; rootIndex > 0; rootIndex--) {
            reheap(rootIndex);
        }
    }

}
