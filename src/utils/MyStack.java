package utils;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Custom Stack that uses a singly linked list to hold an Item on the
 * stack and to keep track of the items on the Stack.
 *
 * @author Ian Murphy - 20057028
 *
 */
public class MyStack<E> implements Iterable<E> {

    private int count;
    private Frame<E> top;

    public MyStack() {
        int count = 0;
        top = null;
    }

    public boolean isEmpty() { return top == null; }

    /**
     * Method that adds an item onto the Stack.
     *
     * @param item Item to be added.
     */
    public void push(E item) {
        Frame<E> oldTop = top;
        top = new Frame<E>();
        top.item = item;
        top.next = oldTop;
        count++;
    }

    /**
     * Returns and removes the last item added on the top of the Stack.
     *
     * @return Last item on the Stack.
     */
    public E pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack is Empty!");
        E item = top.item;
        top = top.next;
        count--;
        return item;
    }

    /**
     * Returns but does not remove the item last added to the Stack.
     *
     * @return Last item on the Stack.
     */
    public E peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack is Empty!");
        return top.item;
    }

    /**
     * Method that returns the amount of stack frames on the stack.
     *
     * @return The amount of frames on the Stack.
     */
    public int size() { return count; }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (E item : this)
            s.append(item + " ");
        return s.toString();
    }

    /**
     * Method that returns an iterator that iterates through the stack
     * on a First-In-Last-Out basis.
     *
     * @return Iterator that iterates LIFO order.
     */
    @Override
    public Iterator<E> iterator() { return new ListIterator<E>(top); }


    /**
     * Iterator class that implements an Iterator.
     * Doesn't implement remove(), as it's operation is carried out by
     * pop() in MyStack.
     *
     * @param <E>
     */
    private class ListIterator<E> implements Iterator<E> {

        private Frame<E> current;

        public ListIterator(Frame<E> first) { current = first; }

        @Override
        public boolean hasNext() { return current != null; }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            E item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() { throw new UnsupportedOperationException("Not supported, use pop()"); }
    }

    /**
     * Helper class, represents a stack frame.
     * Holds an object and a reference to the next Frame on the Stack.
     *
     * @param <E>
     */
    private static class Frame<E> {
        E item;
        Frame<E> next;
    }
}
