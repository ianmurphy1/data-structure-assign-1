package calc;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * @author Ian Murphy - 20057028
 *         Date: 15/02/14
 */
public class MyStack<Item> implements Iterable<Item> {

    private int count;
    private Frame<Item> top;

    public MyStack() {
        int count = 0;
        top = null;
    }

    public boolean isEmpty() { return top == null; }

    public void push(Item item) {
        Frame<Item> current = top;
        top = new Frame<Item>();
        top.item = item;
        top.next = current;
        count++;
    }

    public Item pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack is Empty!");
        Item item = top.item;
        top = top.next;
        count--;
        return item;
    }

    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack is Empty!");
        return top.item;
    }

    public int size() {
        return count;
    }


    @Override
    public Iterator<Item> iterator() { return new ListIterator<Item>(top); }

    private class ListIterator<Item> implements Iterator<Item> {

        private Frame<Item> current;

        public ListIterator(Frame<Item> first) { current = first; }

        @Override
        public boolean hasNext() { return current != null; }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("Stack is Empty!");
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() { throw new UnsupportedOperationException("Not supported, use pop()");}
    }


    private static class Frame<Item> {
        Item item;
        Frame<Item> next;
    }
}
