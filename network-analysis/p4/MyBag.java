
package cs1501.p4;
//code from this Bag class is adapted from Robert Sedgewick, Algorithms, 4th Edition, Page 155, Alg. 1.4
public class MyBag<Item> {
    private Node<Item> first;
    private int n;

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    public MyBag() {
        first = null;
        n = 0;
    }

    public void add(Item item) {
        Node<Item> oldFirst = first;
        first = new Node<>();
        first.item = item;
        first.next = oldFirst;
        n++;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return n;
    }

    public Item get(Item item) {
        Node<Item> current = first;
        while (current != null) {
            if (current.item.equals(item)) {
                return current.item;
            }
            current = current.next;
        }
        return null;
    }

    public MyBagIterator iterator() { //custom iterator
        return new MyBagIterator(first);
    }

    protected class MyBagIterator { //custom iterator class
        private Node<Item> current;

        public MyBagIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new RuntimeException("NoSuchElementException");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}
