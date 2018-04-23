# ICS Unit 4 Assigment
Assignment for ics unit 4. by Alex Shi and Mahir Rahman.

## Here is our Writeup/Documentation for the Binary Tree Test
#### Constants:
```
public static final int LOWER_BOUND    = -1000000,
                        UPPER_BOUND    =  1000000,
                        STARTING_NODES = 50,
                        DELETE_LIMIT   = 250,
                        INSERT_LIMIT   = 250,
                        DELETE_TH      = 400,
                        INSERT_TH      = 8000; 

public static int nodeInsertionCounter = 0; 
public static int nodeDeletionCounter  = 0; 
public static int totalNodesInTree     = 0;
```
UPPER_BOUND and LOWER_BOUND are integers to limit the size of the random identification entries. STARTING_NODES is the number of beginning nodes that are not going to be deleted throughout the program. DELETE_LIMIT and INSERT_LIMIT is the max amount of nodes deleted/inserted at a time. DELETE_TH and INSERT_TH are the deletion and insertion thresholds. nodeInsertionCounter and nodeDeletionCounter are integers that counts all the nodes that have ever been added/deleted, they will only increase throughout the program. totalNodesInTree is an integer that is continuously updated to show the current number of nodes in the tree.

#### LinkedList:
```
public static LinkedList idArray = new LinkedList();

public static class LNode {
        String data;
        LNode next;
        public LNode(String data) {
                this.data = data;
                next = null;
        }
}

public static class LinkedList {
        LNode head;
        int size;
        public LinkedList() {
                head = null;
                size = 0;
        }
        public void insert(String data) {
                size++;
                if (head == null) {
                        head = new LNode(data);
                } else {
                        LNode focus = head;
                        while (focus.next != null) {
                                focus = focus.next;
                        }
                        focus.next = new LNode(data);
                }
        }
        public String remove(int n) {
                size--;
                String ret;
                if (n == 0 && head.next == null) {
                        ret = head.data;
                        head = null;
                        return ret;
                } else if (n == 0 && head.next != null) {
                        ret = head.data;
                        head = head.next;
                        return ret;
                } else {
                        LNode focus = head;
                        for (int i = 0; i < n-1; i++)
                                focus = focus.next;
                        ret = focus.next.data;
                        focus.next = focus.next.next;
                        return ret;
                }
        }
        public String toString() {
                String ret = "";
                LNode focus = head;
                while (focus != null) {
                        ret += focus.data + " -> ";
                        focus = focus.next;
                }
                return ret;
        }
}
```
We create a LinkedList called idArray to keep track of the identifications of the nodes we are allowed to delete. In this case, it’s every node after the first 50. The String variable data is the identification we’re storing. The method remove takes in an index n and returns the identification that was just removed. This way we have a way to store and retrieve random identifications given to TNodes easily without using the tree.

#### Personal Constraint:
```
public static int idx = 0;

public static boolean startingNodesChecker() {
        String[] order = new String[STARTING_NODES];
        startingNodesCheckerUtil(globalTree.getRoot(), order);
        System.out.println(Arrays.toString(order));
        for (int i = 1; i < STARTING_NODES; i++) {
                if (order[i].compareTo(order[i-1]) < 0)
                        return false;
        }
        return true;
}
public static void startingNodesCheckerUtil(TNode focus, String[] arr) {
        if (focus != null) {
                startingNodesCheckerUtil(focus.getLeft(), arr);
                arr[idx] = focus.getIdentification();
                idx++;
                startingNodesCheckerUtil(focus.getRight(), arr);
        }
}
```
The constraint we added is that the final 50 nodes must also be in order after the additions and deletions. This is due to the nature of the findNode method. If any of the 50 nodes are not in order then we know something went wrong, These methods are to check that the starting nodes are sorted correctly. This way we are checking not just if those 50 nodes exist, but also checking that they are correct.
