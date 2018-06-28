import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private class DequeItem {
        private Item val;
        private DequeItem l;
        private DequeItem r;

        private DequeItem() {
            val = null;
            l = null;
            r = null;
        }

        private DequeItem(Item v) {
            val = v;
            l = null;
            r = null;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private DequeItem current;

        public DequeIterator() {
            current = FirstItem;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            // next()返回当前元素,并且将指针移向下个元素
            if (!this.hasNext()) {
                throw new java.util.NoSuchElementException();
            } else {
                Item retValue = current.val;
                current = current.r;
                return retValue;
            }
        }
    }

    private int sz = 0;
    private DequeItem FirstItem = null;
    private DequeItem LastItem = null;

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return sz == 0;
    }

    // return the number of items on the deque
    public int size() {
        return sz;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new java.lang.IllegalArgumentException();
        DequeItem newItem = new DequeItem(item);
        if (isEmpty()) {
            FirstItem = newItem;
            LastItem = newItem;
        } else {
            FirstItem.l = newItem;
            newItem.r = FirstItem;
            FirstItem = newItem;
        }
        sz++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null)
            throw new java.lang.IllegalArgumentException();

        DequeItem newItem = new DequeItem(item);
        if (isEmpty()) {
            FirstItem = newItem;
            LastItem = newItem;
        } else {
            LastItem.r = newItem;
            newItem.l = LastItem;
            LastItem = newItem;
        }
        sz++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        Item retItem = FirstItem.val;
        if (FirstItem == LastItem) {
            FirstItem = null;
            LastItem = null;
        } else {
            FirstItem.r.l = null;
            FirstItem = FirstItem.r;
        }
        sz--;
        return retItem;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        Item retItem = LastItem.val;
        if (FirstItem == LastItem) {
            FirstItem = null;
            LastItem = null;
        } else {
            LastItem.l.r = null;
            LastItem = LastItem.l;
        }
        sz--;
        return retItem;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (optional)
    public static void main(String[] args) {
        Deque<Integer> q = new Deque<Integer>();

        q.addFirst(1);
        q.addFirst(2);
        q.addFirst(3);
        q.addFirst(4);
        q.addFirst(5);
        q.removeLast();
        q.removeFirst();
        q.addFirst(99);
        q.addLast(100);
        StdOut.println("size: " + q.size());
        for (Integer e : q) {
            StdOut.println(e);
        }
    }
}
