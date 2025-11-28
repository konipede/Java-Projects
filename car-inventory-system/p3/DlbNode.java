package cs1501.p3;

/**
 * DlbNode class modified to include priority queues for car management by price and mileage.
 */
public class DlbNode {

    private char let; // Letter represented by this DlbNode
    private DlbNode right; // Leads to other alternatives for the current letter in the path
    private DlbNode down; // Leads to keys prefixed by the current path
    public int index; //index in pq
    public MyPQ priceHeap;
    public MyPQ mileHeap;

    
    /**
     * Constructor that accepts the letter for the new node to represent.
     * @param let The letter this node represents
     */
    public DlbNode(char let) {
        this.let = let;
        this.right = null;
        this.down = null;
        this.index = -1;
        priceHeap = null;
        mileHeap = null;
    }

        public void setPriceHeap(MyPQ heap){
            this.priceHeap = heap;
        }
        public void setmileHeap(MyPQ heap){
            this.mileHeap = heap;
        }

    // Getters and setters for node properties
    public char getLet() {
        return let;
    }

    public DlbNode getRight() {
        return right;
    }

    public void setRight(DlbNode r) {
        right = r;
    }

    public DlbNode getDown() {
        return down;
    }

    public void setDown(DlbNode d) {
        down = d;
    }
  
    
}
