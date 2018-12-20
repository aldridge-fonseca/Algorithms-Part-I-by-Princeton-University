import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items; // store array of items
    private int n; // stores number of items present in queue

    public RandomizedQueue() {
        // construct an empty randomized queue
        items = (Item[]) new Object[2]; // cast need for java
        n = 0; // set size to 0
    }

    public boolean isEmpty() {
        // is the randomized queue empty?
        return n == 0;
    }

    public int size() {
        // return the number of items on the randomized queue
        return n;
    }

    public void enqueue(Item item) {
        // add the item
        if (item == null) throw new IllegalArgumentException("the argument contains null value");
        if (n == items.length) resize(2 * items.length);// if items is full then double size of array
        items[n++] = item; // save item at index and increment size
    }

    public Item dequeue() {
        // remove and return a random item
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        if (n == items.length / 4) resize(items.length / 2); // if array is quater full resize to half full
        int random = StdRandom.uniform(n);
        Item item = items[random];
        items[random] = items[--n]; //store the last element in place of random
        items[n] = null; //removal of loitering
        return item;

    }

    public Item sample() {
        // return a random item (but do not remove it)
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return items[StdRandom.uniform(n)];
    }

    public Iterator<Item> iterator() {
        // return an independent iterator over items in random orde
        return new <Item>ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int current;
        private int[] randoms;

        ArrayIterator() {
            current = 0;
            randoms = new int[n];
            for (int j = 0; j < n; j++) {
                randoms[j] = j;
            }
            StdRandom.shuffle(randoms);
        }

        @Override
        public boolean hasNext() {
            return current < n;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No next element");
            return items[randoms[current++]]; // returns items with random current index, increment random
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }//supported as it is optional of this assignment
    }

    private void resize(int size) {
        Item[] temp = (Item[]) new Object[size];
        for (int i = 0; i < n; i++) {
            temp[i] = items[i];
        }
        items = temp;
    }

    public static void main(String[] args) {
       // unit testing (optional)
//        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
//        for (int i = 0; i < 10000; i++) {
//            queue.enqueue(i);
//            queue.enqueue(-i);
//
//        }
//        System.out.println(queue.size());
//        for (Integer i : queue) {
//            System.out.println(i+","+queue.sample()+","+queue.size());
//        }
//
//
//        while (!queue.isEmpty()) System.out.println(queue.dequeue()+","+queue.size());
    }
}

