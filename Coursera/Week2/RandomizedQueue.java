import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private int numOfItems;
    private Item[] array;

    public RandomizedQueue() { // construct an empty randomized queue
        this.numOfItems = 0;
        this.array = (Item[]) new Object[1];
    }
    public boolean isEmpty()   { // is the randomized queue empty?
        return numOfItems ==0;
    }
    public int size()     { // return the number of items on the randomized queue
        return numOfItems;
    }
    public void enqueue(Item item)   {  // add the item
        if (item == null) throw new IllegalArgumentException("item can't be null");
        if (numOfItems>=array.length) resizeQueue(numOfItems+5);
        array[numOfItems++] = item;
    }
    public Item dequeue()     { // remove and return a random item
        if (isEmpty()) throw new NoSuchElementException("the queue is empty");
        if (numOfItems>0 && numOfItems<=array.length/4) resizeQueue(array.length/2);
        int index = StdRandom.uniform(numOfItems);
        Item item = array[index];
        numOfItems--;
        for (int i = index; i<array.length-1; i++)
            array[i] = array[i+1];
        array[numOfItems] = null;
        return item;
    }
    private void resizeQueue (int size) {
       Item[] newArray = (Item[]) new Object[size];
       for (int i=0; i<numOfItems; i++) {
           newArray[i] = array[i];
       }
       array = newArray;
    }

    public Item sample()        { // return a random item (but do not remove it)
        if (isEmpty()) throw new NoSuchElementException("the queue is empty");
        return array[StdRandom.uniform(numOfItems)];
    }
    public Iterator<Item> iterator()     {  // return an independent iterator over items in random order
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item> {
        private int current = 0;
        @Override
        public boolean hasNext() {
            return array[current] !=null;
        }
        public void remove () {
            throw new UnsupportedOperationException();
        }
        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("There are no more elements in queue");
            return (array[current++]);
        }
    }
    public static void main(String[] args)  {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.isEmpty();
        rq.size();
        rq.isEmpty();
        rq.size();
        rq.isEmpty();
        rq.isEmpty();
        rq.enqueue(839);
        rq.enqueue(814);
        rq.dequeue();
        rq.dequeue();
    } // unit testing (optional)
}
