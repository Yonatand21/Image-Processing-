/**
 * Represents a generic queue data structure.
 *
 * @param <T> the type of elements stored in the queue
 */
public class Queue<T>
{
    /**
     * The initial capacity of the queue.
     */
    private static final int INITIAL_CAPACITY = 10;

    /**
     * The array that holds the elements of the queue.
     */
    private T[] queue;

    /**
     * The index of the front of the queue.
     */
    private int front;

    /**
     * The index of the rear of the queue.
     */
    private int rear;

    /**
     * The number of elements in the queue.
     */
    private int size;

    /**
     * The current capacity of the queue.
     */
    private int capacity;

    /**
     * Constructs an empty queue with an initial capacity of 10.
     */
    @SuppressWarnings("unchecked")
    public Queue()
    {
        capacity = INITIAL_CAPACITY;
        queue = (T[]) new Object[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }

    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue is empty, false otherwise
     */
    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * Adds an element to the rear of the queue.
     *
     * @param value the value to enqueue
     */
    public void enqueue(T value)
    {
        if (size == capacity) {
            grow();
        }
        rear = (rear + 1) % capacity;
        queue[rear] = value;
        size++;
    }

    /**
     * Retrieves, but does not remove, the element at the front of the queue.
     *
     * @return the element at the front of the queue
     * @throws RuntimeException if the queue is empty
     */
    public T peek()
    {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        return queue[front];
    }

    /**
     * Retrieves and removes the element at the front of the queue.
     *
     * @return the element at the front of the queue
     * @throws RuntimeException if the queue is empty
     */
    public T dequeue()
    {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        T value = queue[front];
        queue[front] = null;
        front = (front + 1) % capacity;
        size--;
        return value;
    }

    /**
     * Increases the capacity of the queue when it is full.
     */
    @SuppressWarnings("unchecked")
    private void grow()
    {
        capacity *= 2;
        T[] newQueue = (T[]) java.lang.reflect.Array.newInstance(queue.getClass().getComponentType(), capacity);
        for (int i = 0; i < size; i++) {
            newQueue[i] = queue[(front + i) % size];
        }
        queue = newQueue;
        front = 0;
        rear = size - 1;
    }
}
