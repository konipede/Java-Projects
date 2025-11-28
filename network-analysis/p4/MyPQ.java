package cs1501.p4;

//Code for this Indexable PQ class is adapted from extra material from Robert Sedgewick, Algorithms, 4th Edition, section 2.4, https://algs4.cs.princeton.edu/24pq/IndexMinPQ.java.html
public class MyPQ<Key extends Comparable<Key>> {
    
     
         private int[] pq;
    private int[] qp;         
   private Key[] keys;     
    protected int n;          
    private static final int m = 30;

    // uses 0 based indexing
    public MyPQ() {
        pq = new int[m]; 
        keys = (Key[]) new Comparable[m]; 
        qp = new int[m];        
            // Keys array
        n = 0;
        for (int i = 0; i < m; i++) qp[i] = -1; 
    }

  
    public void add(int i, Key key) {
        if (n == pq.length) {
            resize();
        }
       
        pq[n] = i; 
        keys[i] = key; 
        qp[i] = n; 
        swim(n); 
        n++;
    }

    // delete the min element in pq and return it
    public int deleteMin() {
        if (n == 0) return -1;
        int min = pq[0];
        exchange(0, --n);
        sink(0);
        qp[min] = -1; 
        pq[n] = -1; 
        keys[min] = null; 
        return min;
    }

    //sink method to maintain the heap order
    private void sink(int k) {
        while (2 * k + 1 < n) {
            int j = 2 * k + 1;
            if (j < n - 1 && greater(j, j + 1)) j++;
            if (!greater(k, j)) break;
            exchange(k, j);
            k = j;
        }
    }

    // Swim method to adjust heap order
    private void swim(int k) {
        while (k > 0 && greater((k - 1) / 2, k)) {
            exchange(k, (k - 1) / 2);
            k = (k - 1) / 2;
        }
    }

    // compare two WeightEdges based on their weight
    private boolean greater(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
    }

    // swap two elements in the pq
    private void exchange(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }


    public void changeKey(int i, Key key){
        keys[i] = key;
        swim(qp[i]);
        sink(qp[i]);
    }
    public void change(int i, Key key) {
        changeKey(i, key);
    }

    //resize pq so it can be as large as needed
    
    private void resize() {
        int capacity = pq.length * 2;
        int[] tempPQ = new int[capacity];
        int[] tempQP = new int[capacity];
        Key[] tempKeys = (Key[]) new Comparable[capacity];
    
        for (int i = 0; i < n; i++) {
            tempPQ[i] = pq[i];
        }
        for (int i = 0; i < n; i++) {
            tempQP[i] = qp[i];
        }
        for (int i = 0; i < n; i++) {
            tempKeys[i] = keys[i];
        }
        pq = tempPQ;
        qp = tempQP;
        keys = tempKeys;
    }
    

    public void decreaseKey(int w, Key key) {
       keys[w] = key;
       swim(qp[w]);
    }

    

    public boolean contains(int w) {
        return qp[w] != -1;
    }

    public boolean isEmpty() {
        return n == 0;
    }
}

