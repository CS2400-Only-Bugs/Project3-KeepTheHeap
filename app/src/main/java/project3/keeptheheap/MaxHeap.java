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
    public static int optimalSwapCount = 0;
            
    /**
     * Default constructor for MaxHeap. Initializes the heap with a default capacity.
     */
    public MaxHeap() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructor for MaxHeap with a specified initial capacity.
     * @param initialCapacity the initial capacity of the heap
     */
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

    /**
     * Checks if the given capacity exceeds the maximum capacity.
     * @throws IllegalStateException if the capacity exceeds the maximum capacity
     * @param capacity the capacity to check
     */
    public void checkCapacity(int capacity) {
        if (capacity > MAX_CAPACITY) {
            throw new IllegalStateException("Capacity larger than Max Capacity");
        }
    }

    /**
     * Doubles the capacity of the heap.
     */
    public void doubleCapacity() {
        int newCapacity = heap.length * 2;
        checkCapacity(newCapacity);
        heap = java.util.Arrays.copyOf(heap, newCapacity);
    }

    /**
     * Checks if the heap is initialized.
     * @return true if the heap is initialized, false otherwise
     */
    public boolean checkInitialized() {
        return initialized;
    }

    /**
     * Adds a new entry to the heap in a sequential manner.
     * @param newEntry the new entry to be added
     */
    @Override
    public void addSequential(T newEntry) {
        checkInitialized();
        if (lastIndex == heap.length) {
            doubleCapacity();
        }
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

    /**
     * Prints the heap and the number of swaps made during its creation.
     * This method is used for debugging and visualization purposes.
     */
    public void printHeap() {
        System.out.print("Heap built using sequential insertions: ");
        for (int i = 1; i <= lastIndex; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println();

        System.out.println("\nNumber of swaps in heap creation: " + sequentialSwapCount);
        System.out.println("\nHeap after 10 removals: ");
    }

    /**
     * Sorts the heap using heapsort algorithm.
     * @param fileName the name of the file containing the data to be sorted
     * @throws FileNotFoundException if the file is not found
     */
    public static void sequentialSort(String fileName) throws FileNotFoundException {
        Integer[] fileData = fileToArray(fileName);

        MaxHeap<Integer> maxheap = new MaxHeap<>(fileData.length);
        for (int i = 1; i < fileData.length; i++) {
            maxheap.addSequential(fileData[i]);
        }

        maxheap.printHeap();
    }

    /**
     * removes the maximum element from the heap
     * @return the maximum element from the heap
     */
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

    /**
     * @return the maximum element from the heap without removing it
     */
    @Override
    public T getMax() {
        checkInitialized();
        T root = null;
        if (!isEmpty()) {
            root = heap[1];
        }
        return root;
    }

    /**
     * @return true if the heap is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return lastIndex < 1;
    }

    /**
     * @return the number of elements in the heap
     */
    @Override
    public int getSize() {
        return lastIndex;
    }

    /**
     * clears the heap by removing all elements
     */
    @Override
    public void clear() {
        checkInitialized();
        while (lastIndex > -1) {
            heap[lastIndex] = null;
            lastIndex--;
        }
        lastIndex = 0;
    }

    /**
     * * @return the heap as an array
     * @param fileName the name of the file containing the data to be sorted
     * @return the heap as an array
     * @throws FileNotFoundException if the file is not found
     */
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
    
    /**
     * * Reheapifies the heap after removing the maximum element.
     * @param heap the heap to be reheapified
     * @param rootIndex the index of the root element
     * @param lastIndex the index of the last element
     * @param <T> the type of the elements in the heap
     */
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
                optimalSwapCount++;
                rootIndex = largerChildIndex;
                leftChildIndex = 2 * rootIndex + 1;
            } else {
                done = true;
            }
        }
        heap[rootIndex] = orphan;
    }

    /**
     * * Heapsort algorithm to sort the array.
     * @param array the array to be sorted
     * @param n the number of elements in the array
     * @param <T> the type of the elements in the array
     */
    void heapsort(T[] array, int n) {
        for (int rootIndex = n / 2 - 1; rootIndex >= 0; rootIndex--) {
            reheap(array, rootIndex, n - 1);
        }
    }

    /**
     * * Reheapifies the heap after removing the maximum element.
     * @param rootIndex the index of the root element
     * @param <T> the type of the elements in the heap
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

}
