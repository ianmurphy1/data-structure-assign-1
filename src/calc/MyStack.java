package calc;

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
public class MyStack<Item> implements Iterable<Item> {

    private int count;
    private Frame<Item> top;

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
    public void push(Item item) {
        Frame<Item> oldTop = top;
        top = new Frame<Item>();
        top.item = item;
        top.next = oldTop;
        count++;
    }

    /**
     * Returns and removes the last item added on the top of the Stack.
     *
     * @return Last item on the Stack.
     */
    public Item pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack is Empty!");
        Item item = top.item;
        top = top.next;
        count--;
        return item;
    }

    /**
     * Returns but does not remove the item last added to the Stack.
     *
     * @return Last item on the Stack.
     */
    public Item peek() {
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
        for (Item item : this)
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
    public Iterator<Item> iterator() { return new ListIterator<Item>(top); }


    /**
     * Iterator class that implements an Iterator.
     * Doesn't implement remove(), as it's operation is carried out by
     * pop() in MyStack.
     *
     * @param <Item>
     */
    private class ListIterator<Item> implements Iterator<Item> {

        private Frame<Item> current;

        public ListIterator(Frame<Item> first) { current = first; }

        @Override
        public boolean hasNext() { return current != null; }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
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
     * @param <Item>
     */
    private static class Frame<Item> {
        Item item;
        Frame<Item> next;
    }
}
