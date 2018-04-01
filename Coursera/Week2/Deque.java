import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
    private int size = 0;
    private Node first;
    private Node last;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    public Deque() {

    }

    public boolean isEmpty(){
        return size==0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {//add item to the start of list
        validation(item);
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        if (isEmpty()) last = first;
        else {
            first.next = oldFirst;
            oldFirst.previous = first;
        }
        size++;
    }

    public void addLast(Item item) {//add item to the endof list
        validation(item);
        Node oldLast = last;
        last = new Node();
        last.item = item;
        if (isEmpty()) first = last;
        else {
            oldLast.next = last;
            last.previous = oldLast;
        }
        size++;
    }

    public Item removeFirst() { //remove item from the start of list
        if (isEmpty()) throw new NoSuchElementException("The deque is empty");
        Item item = first.item;
        if (first.next != null) first = first.next;
        first.previous = null;
        size--;
        if (size == 0) {
            first = null;
            last = null;
        }
        return item;
    }
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("The deque is empty");
        Item item = last.item;
        if (last.previous != null) last = last.previous;
        last.next = null;
        size--;
        if (size == 0) {
            first = null;
            last = null;
        }
        return item;
    }

    private void validation (Item item) {
        if (item == null) throw new IllegalArgumentException("The item can't be null");
    }
    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() {
            return current !=null;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("There are no more items to return!");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> test10 = new Deque<>();
        test10.addFirst(1);
        test10.removeFirst();
        test10.addLast(2);
        test10.removeLast();
        test10.addFirst(3);
        test10.addFirst(4);
        test10.removeFirst();
        test10.addLast(6);
        test10.removeFirst();
        test10.addFirst(8);
        test10.removeLast();
        test10.removeLast();
        test10.addFirst(8);

        for(Integer s: test10)
        {
            System.out.print(s);
        }
    }
}
