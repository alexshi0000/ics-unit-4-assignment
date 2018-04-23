import java.util.*;
public class BinTreeTester {
        public static final int LOWER_BOUND    = 0,
                                UPPER_BOUND    = 1000000,
                                STARTING_NODES = 50,
                                DELETE_LIMIT   = 500,
                                INSERT_LIMIT   = 500,  //max amount of nodes deleted at a time
                                DELETE_TH      = 400,  //deletion threshold
                                INSERT_TH      = 8000; //insertion threshold

        public static int nodeInsertionCounter = 0; //count the number of nodes that were inserted
        public static int nodeDeletionCounter  = 0; //count the number of nodes that were deleted
        public static int totalNodesInTree     = 0; //count the number of nodes that are in the tree right now

        //this is our tree
        public static Tree globalTree          = new Tree();

        //lets use a linked list to keep track of all the identifications in the tree right now
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

        public static int idx = 0;
        //use this to check the starting nodes are sorted correctly within the tree
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

        public static void main(String[] args) {
                //add 50 nodes
                String[] firstStartingId = new String[STARTING_NODES];
                for (int i = 0; i < STARTING_NODES; i++) {
                        int numberEntry = (int)(Math.random() * (UPPER_BOUND - LOWER_BOUND) + LOWER_BOUND);
                        while (globalTree.findNode(Integer.toString(numberEntry)) != null) //while there is already an existing node in the current tree
                                numberEntry = (int)(Math.random() * (UPPER_BOUND - LOWER_BOUND) + LOWER_BOUND);
                        TNode entry = new TNode(Integer.toString(numberEntry), 0, null, null, null);
                        globalTree.insertNode(entry);
                        firstStartingId[i] = entry.getIdentification();
                        nodeInsertionCounter++;
                        totalNodesInTree++;
                }

                // testing out own list data structure
                /*
                idArray.insert("123");
                idArray.insert("3");
                idArray.insert("43");
                idArray.insert("12");

                System.out.println("original");
                System.out.println(idArray);

                System.out.println("removed: " + idArray.remove(2) + " size: " + idArray.size);
                System.out.println(idArray);

                System.out.println("removed: " + idArray.remove(0) + " size: " + idArray.size);
                System.out.println(idArray);

                System.out.println("removed: " + idArray.remove(1) + " size: " + idArray.size);
                System.out.println(idArray);

                idArray.insert("55");
                System.out.println("added: 55");
                System.out.println(idArray);*/

                //globalTree.printTree(0);
                long startTime = System.currentTimeMillis();
                while (totalNodesInTree < INSERT_TH || nodeDeletionCounter < DELETE_TH || System.currentTimeMillis() - startTime < 15000) {
                        int insertAmount = (int)(Math.random() * INSERT_LIMIT);
                        int deleteAmount = (int)(Math.random() * DELETE_LIMIT);
                        //doing insertion right here
                        for (int i = 0; i < insertAmount; i++) {
                                int numberEntry  = (int)(Math.random() * (UPPER_BOUND - LOWER_BOUND) + LOWER_BOUND);
                                while (globalTree.findNode(Integer.toString(numberEntry)) != null)
                                        numberEntry = (int)(Math.random() * (UPPER_BOUND - LOWER_BOUND) + LOWER_BOUND);
                                globalTree.insertNode(new TNode(Integer.toString(numberEntry), 0, null, null, null));
                                idArray.insert(Integer.toString(numberEntry));
                                nodeInsertionCounter++;
                                totalNodesInTree++;
                        }
                        //doing deletion right here
                        for (int i = 0; i < Math.min(idArray.size, deleteAmount); i++) {
                                int randIdx = (int)(Math.random() * idArray.size);
                                String randIdentification = idArray.remove(randIdx);  // get the identification to remove from the linked lsit using the randIdx
                                globalTree.deleteNode(globalTree.findNode(randIdentification));
                                nodeDeletionCounter++;
                                totalNodesInTree--;
                        }
                        System.out.println("total nodes: " + totalNodesInTree);
                }

                //delete all the new nodes added
                while (totalNodesInTree > STARTING_NODES) { //make sure all the nodes that are not starting nods are being purged
                        int randIdx = (int)(Math.random() * idArray.size);
                        String randIdentification = idArray.remove(randIdx);  // get the identification to remove from the linked lsit using the randIdx
                        globalTree.deleteNode(globalTree.findNode(randIdentification));
                        nodeDeletionCounter++;
                        totalNodesInTree--;
                        System.out.println("total nodes: " + totalNodesInTree); // deletion
                }

                boolean startingNodesPresent = true;
                for (int i = 0; i < STARTING_NODES && startingNodesPresent; i++) {
                        if (globalTree.findNode(firstStartingId[i]) == null)
                                startingNodesPresent = false;
                }

                if (startingNodesPresent) {
                        System.out.println("TEST COMPLETE, ALL STARTING NODES ARE PRESENT");
                        System.out.println("Total amount of nodes inserted in test: " + nodeInsertionCounter);
                        System.out.println("Total amount of nodes deleted in test:  " + nodeDeletionCounter);
                        System.out.println("The difference should be exactly the amount of starting nodes");
                        //globalTree.printTree(0); //our criteria is if the in order traversal is sorted, then the binary search tree operations were successful
                        if(startingNodesChecker())
                                System.out.println("STARTING NODES ARE PROPERLY SORTED IN TREE");
                        else
                                System.out.println("OOPS SOMETHING WENT WRONG");
                } else {
                        System.out.println("OOPS SOMETHING WENT WRONG");
                }
        }
}