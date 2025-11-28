package cs1501.p3;

public class Dlb {
    public static DlbNode root;
    public StringBuilder sb; // StringBuilder for char searching
    public int count;

    // Constructor
    public Dlb() {
        root = null;
        sb = new StringBuilder();
    }

    public void add(String key) {
        key += '*'; // add * to indicate the end of a word
        root = add(root, key, 0); // call recursive helper method
        count++;
    }

    // recursive add helper method
    private DlbNode add(DlbNode node, String word, int index) {
        if (index == word.length()) return node; // if word is already contained
        if (node == null) node = new DlbNode(word.charAt(index));

        if (node.getLet() == word.charAt(index)) {
            node.setDown(add(node.getDown(), word, index + 1));
        } else {
            node.setRight(add(node.getRight(), word, index)); // search right for where word letters should go
        }
        return node;
    }

   

    public int get(String key) {
        DlbNode node = getNode(root, key + '*', 0);
        return node == null ? -1 : node.index;
    }

    protected  DlbNode getNode(DlbNode node, String key, int pos) {
        if (node == null) return null;
        if (pos == key.length()) return node;

        if (node.getLet() == key.charAt(pos)) {
            return getNode(node.getDown(), key, pos + 1);
        } else {
            return getNode(node.getRight(), key, pos);
        }
    }

    public boolean contains(String key) {
        key += '*'; // add * to key to search for word
        return contains(root, key, 0); // call recursive helper method
    }

    private boolean contains(DlbNode node, String word, int index) {
        if (node == null) return false; // reached end of word and word not found
        if (index == word.length() - 1) {
            if (node.getLet() == '*') return true; // if reached end of word and found * return true
        }
        if (node.getLet() == word.charAt(index)) {
            return contains(node.getDown(), word, index + 1); // letter matches with key index, search down
        } else {
            return contains(node.getRight(), word, index); // letter does not match with search index, search right node for correct letter
        }
    }


    public void updateIndex(String key, int newIndex) {
        key += '*'; // Add '*' to key to search for word
        updateIndex(root, key, 0, newIndex); // Call recursive helper method
    }

    private void updateIndex(DlbNode node, String word, int index, int newIndex) {
        if (node == null) return; // Reached end of word and word not found
        if (index == word.length() - 1) {
            if (node.getLet() == '*') {
                node.index = newIndex; // If reached end of word and found '*', update index
                return;
            }
        }
        if (node.getLet() == word.charAt(index)) {
            updateIndex(node.getDown(), word, index + 1, newIndex); // Letter matches with key index, search down
        } else {
            updateIndex(node.getRight(), word, index, newIndex); // Letter does not match with search index, search right node for correct letter
        }
    }

    public int getIndex(String key) {
        key += '*'; // Add '*' to key to search for word
        return getIndex(root, key, 0); // Call recursive helper method
    }
    
    private int getIndex(DlbNode node, String word, int index) {
        if (node == null) return -1;
        if (index == word.length() + 1) {
            if (node.getLet() == '*') {
                // If reached end of word and found '*', return index
                return node.index;
            }
        }
        if (node.getLet() == word.charAt(index)) {
            return getIndex(node.getDown(), word, index + 1);
        } else {
            return getIndex(node.getRight(), word, index);
        }
    }

    public boolean containsPrefix(String pre) {
        return containsPrefix(root, pre, 0); // call recursive contains prefix helper method
    }

    private boolean containsPrefix(DlbNode node, String pre, int index) {
        if (node == null) return false; // if reached end return, not found. return false
        if (index == pre.length() - 1) return true; // if reached end of prefix during search, return true
        if (node.getLet() == pre.charAt(index)) {
            return containsPrefix(node.getDown(), pre, index + 1); // if letter matches with node continue search down
        } else {
            return containsPrefix(node.getRight(), pre, index); // if letter does not match then search right node
        }
    }

    public int count() {
        return count; // return the count
    }
}




