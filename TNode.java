public class TNode {
	private String identification = "";
	private int recordNumber = -1;
	private int height = 1;
	private TNode left = null;
	private TNode right = null;
	private TNode parent = null;

	public TNode(){
		this.identification = "";
		this.recordNumber = -1;
		this.left = this.right = this.parent = null;
	}

	public TNode(String s, int recordNumber, TNode left, TNode right, TNode parent){
		this.identification = s;
		this.recordNumber = recordNumber;
		this.left = left;
		this.right = right;
		this.parent = parent;
		this.height = 1;
	}

	public void setHeight(int h) {
		height = h;
	}

	public int getHeight() {
		return height;
	}

	public void setLeft(TNode left) {
		this.left = left;
	}

	public void setRecordNumber(int recordNumber) {
		this.recordNumber = recordNumber;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public void setParent(TNode parent) {
		this.parent = parent;
	}

	public void setRight(TNode right) {
		this.right = right;
	}

	public String getIdentification() {
		return this.identification;
	}

	public int getRecordNumber() {
		return recordNumber;
	}

	public TNode getLeft() {
		return left;
	}

	public TNode getParent() {
		return parent;
	}

	public TNode getRight() {
		return right;
	}

	public boolean isLeaf(){
		return left == null && right == null;
	}

	//@Override
	public String toString() {
		if(this == null)
			return "null";
		else
			return "Id: " + this.identification + " Record number: " + this.recordNumber;
	}
}
