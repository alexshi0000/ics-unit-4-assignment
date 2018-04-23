public class TestTree{
	public static void main(String[] args) {
		Tree t = new Tree();
		t.insertNode(new TNode("20", 4, null, null, null));
		t.insertNode(new TNode("30", 1, null, null, null));
		t.insertNode(new TNode("08", 3, null, null, null));
		t.insertNode(new TNode("04", 1, null, null, null));
		t.insertNode(new TNode("10", 2, null, null, null));
		t.insertNode(new TNode("25", 1, null, null, null));
		t.printTree();
	}
}
