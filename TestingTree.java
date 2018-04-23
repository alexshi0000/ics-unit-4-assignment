public class TestingTree{
	public static void main(String[] args){
		Tree t = new Tree();
		t.insertNode(new TNode("034", 10, null, null, null));
		t.insertNode(new TNode("023", 10, null, null, null));
		t.insertNode(new TNode("164", 10, null, null, null));
		t.insertNode(new TNode("115", 10, null, null, null));
		t.insertNode(new TNode("137", 10, null, null, null));
		t.insertNode(new TNode("151", 10, null, null, null));
		t.insertNode(new TNode("128", 10, null, null, null));
		t.insertNode(new TNode("172", 10, null, null, null));
		t.insertNode(new TNode("004", 10, null, null, null));
		t.insertNode(new TNode("170", 10, null, null, null));
		t.insertNode(new TNode("120", 10, null, null, null));
		t.printTree(0);
	}
}
