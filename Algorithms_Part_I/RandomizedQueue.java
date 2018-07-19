import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private class RandomizedQueueItem {
        private Item val;
        private RandomizedQueueItem l;
        private RandomizedQueueItem r;

        private RandomizedQueueItem() {
            val = null;
            l = null;
            r = null;
        }

        private RandomizedQueueItem(Item v) {
            val = v;
            l = null;
            r = null;
        }
    }

    private class RandomizedQequeIterator implements Iterator<Item> {
        private int[] vislist;
        private int currenti;

        public RandomizedQequeIterator() {
            vislist = StdRandom.permutation(sz);
        }

        public boolean hasNext() {
            return currenti < sz;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            // next()返回当前元素,并且将指针移向下个元素
            if (!this.hasNext()) {
                throw new java.util.NoSuchElementException();
            } else {
                Item retValue = items[vislist[currenti]];
                currenti++;
                return retValue;
            }
        }
    }

    private int sz = 0;
    private int allocsz = 10;
    private Item[] items;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[allocsz];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return sz == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return sz;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new java.lang.IllegalArgumentException();
        if (sz + 1 > allocsz) {
            allocsz *= 2;
            Item[] newArr = (Item[]) new Object[allocsz];
            java.lang.System.arraycopy(items, 0, newArr, 0, sz);
            items = newArr;
        }
        items[sz++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        int ri = StdRandom.uniform(sz);
        Item retItem = items[ri];
        items[ri] = items[sz - 1];
        sz--;
        if(sz % 100 == 0 && sz < allocsz/2){
            allocsz /= 2;
            Item[] newArr = (Item[]) new Object[allocsz];
            java.lang.System.arraycopy(items, 0, newArr, 0, sz);
            items = newArr;
        }
        return retItem;
    }

    // return a random item (but do not remove it){}
    public Item sample() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        return items[StdRandom.uniform(sz)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQequeIterator();
    }

    // unit testing (optional){}
    public static void main(String[] args) {
    }
}
