import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int N;
    private Item[] arr;

    // construct an empty randomized queue
    public RandomizedQueue() {
        N = 0;
        arr = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

  //  @SuppressWarnings("unchecked")

    // add the item
    public void enqueue(Item item)
    {
        if(item == null)
            throw new IllegalArgumentException();

        if (arr.length == N) {
            Item[] tmp = arr;
            int oldLen = arr.length;
            arr = (Item[]) new Object[oldLen * 2];
            System.arraycopy(tmp, 0, arr, 0, N);
        }
        arr[N] = item;
        N ++;
    }

   /// @SuppressWarnings("unchecked")

    // remove and return a random item
    public Item dequeue()
    {
        if(N == 0)
            throw new java.util.NoSuchElementException();

        int rand = (int) (Math.random() * N);
        rand %= N;
        Item ret = arr[rand];
        arr[rand] = arr[N - 1];    // swap the rand element with the last element
        arr[N - 1] = null;     // erase the last element
        N--;

        if (arr.length <= N / 4) {
            Item[] tmp = arr;
            arr = (Item[]) new Object[N / 2];
            System.arraycopy(tmp, 0, arr, 0, N);
        }
        return ret;
    }

    // return a random item (but do not remove it)
    public Item sample()
    {
        if(N == 0)
            throw new java.util.NoSuchElementException();

        int rand = (int) (Math.random()*N);
        rand %= N;
        return arr[rand];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator()
    {
        return new RandomQueueIterator();
    }

    private class RandomQueueIterator implements Iterator<Item>
    {
        int counter_elements = N;   // a local variable to store number of elements.


        public boolean hasNext()
        {
            return counter_elements != 0;
        }
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
        public Item next()
        {
            int rand = (int)(Math.random()*counter_elements);
            if(counter_elements <= 0 || !hasNext())
                throw new java.util.NoSuchElementException();
            Item item = arr[rand % counter_elements];
            if(rand % counter_elements == counter_elements - 1)
            {
                counter_elements --;
                return item;
            }
            else
            {
                arr[rand%counter_elements] = arr[counter_elements-1];
                arr[counter_elements-1] = item;   // shift the random element to the end of the array
                counter_elements --;              // not make it null --> to keep the number of elements of the original arr.
                return item;
            }

        }

    }

    // unit testing (required)
    public static void main(String[] args)
    {
        RandomizedQueue<Integer> rqueue = new RandomizedQueue<Integer>();
        rqueue.enqueue(1);
        rqueue.enqueue(2);
        rqueue.enqueue(3);
        rqueue.enqueue(4);
        rqueue.enqueue(5);
        rqueue.enqueue(6);
        rqueue.enqueue(7);
        rqueue.enqueue(8);
        Iterator<Integer> it = rqueue.iterator();
        while (it.hasNext()) {
            int elt = it.next();
            System.out.println(elt + " ");
        }
    }

}
