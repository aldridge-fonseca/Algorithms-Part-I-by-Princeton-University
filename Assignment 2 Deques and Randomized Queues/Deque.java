
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item>
{
 private Node<Item> first,last; // stores first and last element of linked list  respectively
 private int n; // stores current size of linked list
    private static class  Node<Item>{
        // linked list class
        private Item item; // stores the data of each node
        private Node<Item> next; // stores next of node
        private Node<Item> prev; // stores prev of node
    }

    public Deque(){
    // construct an empty deque
    first = null;
    last = null;
    n = 0;    }
public boolean isEmpty()   {
    // is the deque empty?
    return n==0;
}
    public int size(){
    // return the number of items on the deque
    return n;
    }
    public void addFirst(Item item) {
    // add the item to the front
        if (item==null) throw new IllegalArgumentException("the argument contains null value");
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.prev = null;
        if (!isEmpty()) // have to connect oldfirst with first
        {
            first.next = oldfirst;
            oldfirst.prev =  first;
        }
        else //connect last to first as only one element present
        last =first;

        n++ ; // increment size of linked list
    }

    public void addLast(Item item){
    // add the item to the end
        if (item==null) throw new IllegalArgumentException("the argument contains null value");
        Node<Item> oldlast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        if(!isEmpty()) // have to connect  oldlast with last
        {
            oldlast.next = last;
            last.prev = oldlast;
        }
        else // connect last to first as only one element present
        first = last;
        n++ ;  // increment size of linekd list
    }
    public Item removeFirst(){
    // remove and return the item from the front
        if(isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = first.item;
        first  =first.next; // first changes to next node
        n-- ; // decrement size of linked list
        // loitering removal
        if (isEmpty()) last = null; // if queue is empty last needs no reference
        else first.prev = null; // first prev needs no reference
        return item;

    }
    public Item removeLast()   {
    // remove and return the item from the end
        if(isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = last.item;
        last = last.prev;
        n--;
        // loitering removal
        if (isEmpty()) first = null; // if queue is empty first needs no reference
        else last.next = null; // last next needs no reference
        return item;
    }
    public Iterator<Item> iterator() {
        // return an iterator over items in order from front to end
        return new ListIterator<Item>(first);
    }
    private class ListIterator<Item> implements Iterator<Item> {
    // an iterator, doesn't implement remove() since it's optional
        private Node<Item> current;
        public ListIterator(Node<Item> first) {
            current = first;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }//supported as it is optional of this assignment
        public boolean hasNext()  { return current != null; }//if current is null then the list does not have any next element
        public Item next(){
        if(!hasNext()) throw new NoSuchElementException("No next element");
        Item item = current.item;
        current = current.next;
            return item;
        }
    }

    public static void main(String[] args){   // unit testing (optional)
//        Deque<Integer> deque = new Deque<Integer>();
//        deque.addFirst(1);
//        deque.removeLast();
//        System.out.println(deque.isEmpty());
//        System.out.println(deque.isEmpty());
//        for (int i = 1; i <1000000 ; i++) {
//            deque.addFirst(i);
//            deque.addLast(-i*2);
//        }
//        while (!deque.isEmpty())
//        {
//            System.out.println(deque.removeFirst()+","+deque.removeLast()+","+deque.size()+","+deque.isEmpty());
//        }
//        System.out.println(deque.isEmpty());

}}


