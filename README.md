# ICS Unit 4 Assigment
By Alex Shi and Mahir Rahman. The .java class that we created for this assignment is called BinTreeTester.

## Here is our Writeup/Documentation for the Binary Tree Test
#### Constants:
```java
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
```java
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
```java
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

#### Insertion of the First 50 Nodes:
```java
String[] firstStartingId = new String[STARTING_NODES];

for (int i = 0; i < STARTING_NODES; i++) {
        int numberEntry = (int)(Math.random() * (UPPER_BOUND - LOWER_BOUND) + LOWER_BOUND);
        while (globalTree.findNode(Integer.toString(numberEntry)) != null)
                numberEntry = (int)(Math.random() * (UPPER_BOUND - LOWER_BOUND) + LOWER_BOUND);
        TNode entry = new TNode(Integer.toString(numberEntry), 0, null, null, null);
        globalTree.insertNode(entry);
        firstStartingId[i] = entry.getIdentification();
        nodeInsertionCounter++;
        totalNodesInTree++;
}
```
This is the first segment in the main method, and is where we add the first 50 nodes that won’t be deleted. We create a String array called firstStartingId to hold the identifications to the 50 nodes we don’t want to delete. The integer numberEntry is the randomly generated identification for the new nodes. The while loop ensures that the random identification is unique from everything else that’s already in the tree. We then add the node to the tree and the array, and increment nodeInsertionCounter and totalNodesInTree. We do this in a for loop, 50 times for 50 nodes.

#### Randomly Inserting and Deleting TNodes from globalTree for at Least 15 Seconds:
```java
long startTime = System.currentTimeMillis(); //get the starting time before starting inserting and deleting

while (totalNodesInTree < INSERT_TH || nodeDeletionCounter < DELETE_TH || System.currentTimeMillis() - startTime < 15000) {
        /*
         * the conditions for this while loop is that this loop must run for at least 15 seconds and reach insertion/deletion
         * thresholds set in te constants part of the code.
         */
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
                String randIdentification = idArray.remove(randIdx);  // get the identification to remove from the linked list
                globalTree.deleteNode(globalTree.findNode(randIdentification));
                nodeDeletionCounter++;
                totalNodesInTree--;
        }
        System.out.println("total nodes: " + totalNodesInTree);
}

```
This while loop takes care of the random additions/deletions until the requirements are met. The loop will exit once the insertion/deletion thresholds are met, in our case 8000 and 400, and at least 15 seconds have passed. The integers insertAmount and deleteAmount are randomly generated and control how many nodes are inserted/deleted in one passing of the while loop. The first for loop creates a random unique identification and inserts it into the linked list, and at the same time inserting a node with that id into the tree. nodeInsertionCounter and totalNodesInTree are both incremented. The second for loop is for the random deletions. The integer randIdx is a randomly generated index within the size of the linked list. We remove the corresponding identification from the linked list. The remove method returns the identification we just deleted, which we use for the deleteNode method to delete the node from the tree. We draw the identification from the linked list to ensure that none of the first 50 nodes are deleted. nodeDeletionCounter is incremented while totalNodesInTree is decremented as a node has been removed.

#### Deletion of Extra TNodes Until STARTING_NODES:
```java
while (totalNodesInTree > STARTING_NODES) { 
int randIdx = (int)(Math.random() * idArray.size);
            String randIdentification = idArray.remove(randIdx); 
            globalTree.deleteNode(globalTree.findNode(randIdentification));
            nodeDeletionCounter++;
            totalNodesInTree--;
            System.out.println("total nodes: " + totalNodesInTree);
}
```
This segment deletes all the nodes that are not the starting nodes. The while loop will exit once there are 50 nodes left in the tree. The nodes are randomly deleted, using the linked list to ensure none of the original 50 are deleted. The process is the exact same as the previous segment of deleting code.

#### Final Checks:
```java
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
        //globalTree.printTree(0); //our criteria is if the in order traversal is sorted, then the test was successful
        if(startingNodesChecker()) //this method checks if the remaining nodes in the tree are sorted
                System.out.println("STARTING NODES ARE PROPERLY SORTED IN TREE");
        else
                System.out.println("OOPS SOMETHING WENT WRONG");
        
} else {
        System.out.println("OOPS SOMETHING WENT WRONG");
}
```
This last for loop checks that the identifications of all the nodes in the tree match the identifications in the array. If this is not the case, the boolean startingNodesPresent will become false and the loop will exit. If startingNodesPresent is still true, we print out the total amount of nodes inserted/deleted to ensure that they are in the tens of thousands and that there is exactly 50 difference between nodes inserted and deleted. We then call the startingNodesChecker method to ensure all the nodes are sorted, as this is our final constraint.

#### In-Depth Look at startingNodesChecker() &nbsp; Function:
```java
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
```
The integer idx is used as a pointer to the next available spot in the array order. The order array stores String variables that are the identifications of the TNodes of the STARTING_NODES. The array is filled using an in order traversal as seen in the below method startingNodesCheckerUtil(). Ultimately, we can then check if the filled array of TNode identifications is sorted, confirming that the STARTING_NODES are in their proper positions.
