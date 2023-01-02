import java.util.Iterator;

public class Deque<Item> implements Iterable<Item>
{
    private Node first;
    private Node last;
    private int sz;
    private class Node
    {
        private Item item;
        private Node next;
        private Node prev;
    }

    public Deque()
    {
        first = null;
        last = null;
        sz = 0;
    }

    public int size()
    {
        return sz;
    }

    public void addFirst(Item item)
    {
        if(item == null)
            throw new IllegalArgumentException();

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;

        if(oldFirst == null)
        {
            first.next = null;
        }
        else
        {
            first.next = oldFirst;
            oldFirst.prev = first;
        }

        // If the inserted element is the first one --> last = first
        if(last == null)
            last = first;
        sz ++;
    }

    public boolean isEmpty()
    {
        return first == null;
    }

    public void addLast(Item item)
    {
        if(item == null)
            throw new IllegalArgumentException();

        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;

        if(oldLast == null)
        {
            last.prev = null;
        }
        else
        {
            last.prev = oldLast;
            oldLast.next = last;
        }

        // If the inserted element is the first one --> first = last
        if(first == null)
            first = last;
        sz ++;
    }

    public Item removeFirst()
    {
        if(sz <= 0)
            throw new java.util.NoSuchElementException();

        Item ret = first.item;
        first = first.next;

        // if the removed element is the last element ---> first & last must be null
        if(first == null)
            last = null;

        // the first element can't have a prev Node ---> its prev must be null
        if(first != null && first.prev != null)
            first.prev = null;

        sz --;
        return ret;
    }

    public Item removeLast()
    {
        if(sz <= 0)
            throw new java.util.NoSuchElementException();

        Item ret = last.item;
        last = last.prev;

        if(last == null)
            first = null;

        // the last Node can't have a next Node ---> its next must be null
        if(last != null && last.next != null)
            last.next = null;

        sz --;
        return ret;
    }

    public Iterator<Item> iterator() {return new DequeIterator();}

    private class DequeIterator implements Iterator<Item>
    {
        private Node current = first;

        public boolean hasNext()
        {
            return current != null;
        }
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
        public Item next()
        {
            if(sz <= 0 || !hasNext())
                throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> adeque = new Deque<Integer>();
        adeque.addFirst(1);
        adeque.addFirst(2);
        adeque.addFirst(3);
        adeque.addFirst(4);
        System.out.println(adeque.removeLast());
        System.out.println(adeque.removeLast());
    }

}
