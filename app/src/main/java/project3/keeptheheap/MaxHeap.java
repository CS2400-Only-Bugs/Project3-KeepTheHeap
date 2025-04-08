package project3.keeptheheap;

import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;;

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
        T[] tempHeap = (T[]) new Comparable[initialCapacity + 1];
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

    // Using Optimal Method (reheap and heapsort)
    // Makes an array from the data.txt file once implemented
    static Integer[] fileToArray(String fileName) throws FileNotFoundException {
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

    void reheap(T[] heap, int rootIndex, int lastIndex) {
        boolean done = false;
        T orphan = heap[rootIndex];
        int leftChildIndex = 2 * rootIndex + 1;
        while (!done && leftChildIndex <= lastIndex) {
            int largerChildIndex = leftChildIndex;
            int rightChildIndex = leftChildIndex + 1;
            if (rightChildIndex <= lastIndex && heap[rightChildIndex].compareTo(heap[largerChildIndex]) > 0) {
                largerChildIndex = rightChildIndex;
            }
            if (orphan.compareTo(heap[largerChildIndex]) < 0) {
                heap[rootIndex] = heap[largerChildIndex];
                rootIndex = largerChildIndex;
                leftChildIndex = 2 * rootIndex + 1;
            } else {
                done = true;
            }
        }
        heap[rootIndex] = orphan;
    }

    // Swaps an array at index a and b
    void swap(T[] array, int a, int b) {
        T tempArray = array[a];
        array[a] = array[b];
        array[b] = tempArray;
    }

    void heapsort(T[] array, int n) {
        for (int rootIndex = n / 2 - 1; rootIndex >= 0; rootIndex--) {
            reheap(array, rootIndex, n - 1);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        MaxHeap<Integer> sorter = new MaxHeap<>();
        Integer[] array = fileToArray("app/src/main/resources/data_sorted.txt");
        sorter.heapsort(array, array.length);

        System.out.println("Sorted:");
        for (int i : array) {
            System.out.print(i + " ");
        }
    }
}
