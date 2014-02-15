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
    private Node<Item> top;

    public MyStack() {
        int count = 0;
        top = null;
    }

    public boolean isEmpty() { return top == null; }

    public void push(Item item) {
        Node<Item> current = top;
        top = new Node<Item>();
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

    @Override
    public Iterator<Item> iterator() { return new ListIterator<Item>(top); }

    private class ListIterator<Item> implements Iterator<Item> {

        private Node<Item> current;

        public ListIterator(Node<Item> first) { current = first; }

        @Override
        public boolean hasNext() { return top != null; }

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


    private static class Node<Item> {
        Item item;
        Node next;
    }
}
