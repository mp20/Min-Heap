import java.util.ArrayList;
import java.util.NoSuchElementException;


/**
 * Your implementation of a MinHeap.
 *
 * @author Ariya Nazari Forhsnia
 * @version 1.0
 * @userid aforoshani3
 * @GTID 903627990
 *
 * Collaborators: None
 *
 * Resources: None
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        for (T element: data) {
            if (element == null) {
                throw new IllegalArgumentException("an element is null");
            }
        }
        backingArray = (T[]) new Comparable[(data.size() * 2) + 1];
        for (int i = 0; i < data.size(); i++) {
            backingArray[i + 1] = data.get(i);
        }
        size = data.size();
        for (int i = 0; i < size / 2; i++) {
            downHeap(size / 2 - i);
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding.
     * 
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        if (size + 1 >= backingArray.length) {
            resize();
        }
        backingArray[size + 1] = data;
        size++;
        if (size > 1) {
            upHeapify(size);
        }
    }

    /**
     * swaps two different values given the index of both.
     * @param first the first index
     * @param second the second index
     */
    private void swap(int first, int second) {
        T temp;
        temp = backingArray[first];
        backingArray[first] = backingArray[second];
        backingArray[second] = temp;
    }

    /**
     * Resizes the array if its full.
     */
    private void resize() {
        T[] resized = (T[]) new Comparable[backingArray.length * 2];
        for (int i = 1; i <= size; i++) {
            resized[i] = backingArray[i];
        }
        backingArray = resized;
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after adding.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("heap is empty");
        }
        T toRemove = backingArray[1];
        if (size == 1) {
            backingArray[size] = null;
            size--;
        } else {
            backingArray[1] = backingArray[size];
            backingArray[size] = null;
            size--;
            downHeap(1);
        }
        return toRemove;
    }

    /**
     * downheaps given a starting position.
     * @param pos the position where the down heaping starts
     */
    private void downHeap(int pos) {
        if (!(pos > size / 2)) {
            //compare with two children
            if (rightChild(pos) > size)  {
                if (backingArray[leftChild(pos)].compareTo(backingArray[pos]) < 0) {
                    swap(pos, leftChild(pos));
                    downHeap(leftChild(pos));
                }
            } else {  //right child is bigger case
                if (backingArray[pos].compareTo(backingArray[leftChild(pos)]) > 0
                        || backingArray[pos].compareTo(backingArray[rightChild(pos)]) > 0) {
                    if (backingArray[leftChild(pos)].compareTo(backingArray[rightChild(pos)]) < 0) {
                        swap(pos, leftChild(pos));
                        downHeap(leftChild(pos));
                    } else {
                        swap(pos, rightChild(pos));
                        downHeap(rightChild(pos));
                    }
                }
            }
        }
    }

    /**
     * upheaps given a starting position.
     * @param pos the starting indedx
     */
    private void upHeapify(int pos) {
        if (pos > 1) {
            if (backingArray[pos].compareTo(backingArray[parent(pos)]) < 0) {
                swap(pos, parent(pos));
                upHeapify(parent(pos));
            }
        }
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("heap is empty");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return backingArray[1] == null;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        T[] resized = (T[]) new Comparable[INITIAL_CAPACITY];
        backingArray = resized;
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * checks wether a given index is a leaf
     * @param pos the position in the index
     * @return wether or not it is a leaf
     */
    private boolean isLeaf(int pos) {

        if (pos > (size / 2) && pos <= size) {
            return true;
        }

        return false;
    }

    /**
     * returns the index of the parent of node
     * @param pos the node we want to find he parent of
     * @return returns the index of the parent of the node
     */
    private int parent(int pos) {
        return pos / 2;
    }

    /**
     * returns the index of the left node of the node
     * @param pos the node we want to find the left node of
     * @return returns the index of the left node of the node
     */
    private int leftChild(int pos) {
        return (2 * pos);
    }

    /**
     * returns the index of the right node of the node
     * @param pos the node we want to find the right node of
     * @return returns the index of the right node of the node
     */
    private int rightChild(int pos) {
        return (2 * pos) + 1;
    }
}
