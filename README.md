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
