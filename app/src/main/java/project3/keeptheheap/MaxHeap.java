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
    private int sequentialSwapCount = 0;
    private int optimalSwapCount = 0;

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
        checkInitialized();
        int newIndex = lastIndex + 1;
        int parentIndex = newIndex / 2;
        while ((parentIndex > 0) && newEntry.compareTo(heap[parentIndex]) > 0) {
            heap[newIndex] = heap[parentIndex];
            newIndex = parentIndex;
            parentIndex = newIndex / 2;
            sequentialSwapCount++;
        } // end while
        heap[newIndex] = newEntry;
        lastIndex++;
    }

    public static void sequentialSort(String fileName) throws FileNotFoundException {
        Integer[] fileData = fileToArray(fileName);

        MaxHeap<Integer> maxheap = new MaxHeap<>(fileData.length);
        for (int i = 0; i < fileData.length; i++) {
            maxheap.addSequential(fileData[i]);
        }

        System.out.print("Heap built using sequential insertions: ");
        for (int i = 0; i <= maxheap.lastIndex; i++) {
            System.out.print(maxheap.heap[i] + " ");
        }
        System.out.println("\nNumber of swaps in heap creation: " + maxheap.sequentialSwapCount);
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

    /*
     * void reheap(T[] heap, int rootIndex, int lastIndex) { //compare both reheap
     * methods
     * boolean done = false;
     * T orphan = heap[rootIndex];
     * int leftChildIndex = 2 * rootIndex + 1;
     * while (!done && leftChildIndex <= lastIndex) {
     * int largerChildIndex = leftChildIndex;
     * int rightChildIndex = leftChildIndex + 1;
     * if (rightChildIndex <= lastIndex &&
     * heap[rightChildIndex].compareTo(heap[largerChildIndex]) > 0) {
     * largerChildIndex = rightChildIndex;
     * }
     * if (orphan.compareTo(heap[largerChildIndex]) < 0) {
     * heap[rootIndex] = heap[largerChildIndex];
     * rootIndex = largerChildIndex;
     * leftChildIndex = 2 * rootIndex + 1;
     * } else {
     * done = true;
     * }
     * }
     * heap[rootIndex] = orphan;
     * }
     * 
     * void heapsort(T[] array, int n) {
     * for (int rootIndex = n / 2 - 1; rootIndex >= 0; rootIndex--) {
     * reheap(array, rootIndex, n - 1);
     * }
     * }
     */

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
            } else {
                done = true;
            }
        }
        heap[rootIndex] = orphan;
    }

    public static void main(String[] args) throws FileNotFoundException {
        sequentialSort("app/src/main/resources/data_sorted.txt");
    }
}
