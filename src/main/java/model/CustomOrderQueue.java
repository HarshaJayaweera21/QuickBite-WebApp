//package model;
//
//import java.util.Iterator;
//import java.util.NoSuchElementException;
//
//public class CustomOrderQueue implements Iterable<Order> {
//    private Order[] orders;
//    private int front; // Index of the front element
//    private int rear; // Index where the next element will be added
//    private int size; // Current number of elements
//    private int capacity; // Total capacity of the queue
//    private static final int DEFAULT_CAPACITY = 10;
//
//    public CustomOrderQueue() {
//        this.capacity = DEFAULT_CAPACITY;
//        this.orders = new Order[capacity];
//        this.front = 0;
//        this.rear = 0;
//        this.size = 0;
//    }
//
//    // Add an order to the queue
//    public boolean addOrder(Order order) {
//        if (order == null) {
//            return false;
//        }
//        if (size == capacity) {
//            resize(); // Double the capacity if full
//        }
//        orders[rear] = order;
//        rear = (rear + 1) % capacity; // Circular queue
//        size++;
//        return true;
//    }
//
//    // Peek at the front order without removing it
//    public Order peekOrder() {
//        if (isEmpty()) {
//            return null;
//        }
//        return orders[front];
//    }
//
//    // Remove and return the front order
//    public Order removeOrder() {
//        if (isEmpty()) {
//            return null;
//        }
//        Order order = orders[front];
//        orders[front] = null; // Help garbage collection
//        front = (front + 1) % capacity; // Circular queue
//        size--;
//        return order;
//    }
//
//    // Check if the queue is empty
//    public boolean isEmpty() {
//        return size == 0;
//    }
//
//    // Get the current size of the queue
//    public int size() {
//        return size;
//    }
//
//    // Resize the queue when it's full
//    private void resize() {
//        int newCapacity = capacity * 2;
//        Order[] newOrders = new Order[newCapacity];
//        for (int i = 0; i < size; i++) {
//            newOrders[i] = orders[(front + i) % capacity];
//        }
//        orders = newOrders;
//        front = 0;
//        rear = size;
//        capacity = newCapacity;
//    }
//
//    // Implement Iterable<Order> for JSP compatibility
//    @Override
//    public Iterator<Order> iterator() {
//        return new Iterator<Order>() {
//            private int current = front;
//            private int count = 0;
//
//            @Override
//            public boolean hasNext() {
//                return count < size;
//            }
//
//            @Override
//            public Order next() {
//                if (!hasNext()) {
//                    throw new NoSuchElementException();
//                }
//                Order order = orders[current];
//                current = (current + 1) % capacity;
//                count++;
//                return order;
//            }
//        };
//    }
//}

