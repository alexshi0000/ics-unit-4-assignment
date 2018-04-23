import java.io.RandomAccessFile;
import java.util.Random;
import java.util.RandomAccess;

/*
 * Will eventually be an AVL Tree
 */
public class Tree {
	private TNode root = null;

	public Tree() {
		this.root = null;
	}

	public Tree(TNode r) {
		this.root = r;
	}

	public TNode getRoot() {
		return root;
	}

	public void setRoot(TNode root) {
		this.root = root;
	}

	public void buildFromMessagesFile(int whatId) {
		int recordNumber = 0;
		Record record = new Record();
		for (recordNumber = 0; recordNumber < Globals.totalRecordsInMessagesFile; recordNumber++) {
			record.readFromMessagesFile(recordNumber);
			if (record.getData().charAt(Globals.FIRST_RECORD_MARKER_POS) == Globals.FIRST_RECORD_MARKER) {
				Message message = new Message();
				message.readFromMessagesFile(recordNumber);

				String key = Globals.STR_NULL;
				if (whatId == Globals.SENDER_ID) {
					key = message.getIdSenderFirst();
				} else if (whatId == Globals.RECEIVER_ID) {
					key = message.getIdReceiverFirst();
				} else {
					System.out.println("***INVALID WHATKEY PARAMETER IN buildFromMessagesFile***");
				}

				TNode p = new TNode(key, recordNumber, null, null, null);
				insertNode(p);
			}
		}
	}

	public void insertNode(TNode p) {
		if (this.root == null) {
			root = p;
		} else if (p.getIdentification().compareTo(root.getIdentification()) < 0) {
			if (root.getLeft() == null) {
				root.setLeft(p);
				p.setParent(root);
			} else {
				Tree tree = new Tree(root.getLeft());
				tree.insertNode(p);
			}
		} else if (p.getIdentification().compareTo(root.getIdentification()) > 0) {
			if (root.getRight() == null) {
				root.setRight(p);
				p.setParent(root);
			} else {
				Tree tree = new Tree(root.getRight());
				tree.insertNode(p);
			}
		} else {
			System.out.println("Error: attempting to insert an identification that already exists in tree");
		}

		int leftHeight  = root.getLeft()  != null ? root.getLeft().getHeight() : 0;
		int rightHeight = root.getRight() != null ? root.getRight().getHeight() : 0;
		p.setHeight(Math.max(leftHeight + 1, rightHeight + 1));
	}

	public TNode findNode(String id) {
		if (root == null) {
			return null;
		} else if (id.equals(root.getIdentification())) {
			return root;
		} else if (root.getIdentification().compareTo(id) > 0) {
			Tree t = new Tree(root.getLeft());
			return t.findNode(id);
		} else if (root.getIdentification().compareTo(id) < 0) {
			Tree t = new Tree(root.getRight());
			return t.findNode(id);
		} else {
			return null;
		}
	}

	public TNode findNode(String partialKey, int where) {
		if (partialKey.length() == Globals.IDENTIFICATION_LEN) {
			return findNode(partialKey);
		} else if (root == null) {
			return null;
		}
		int n = partialKey.length();
		if (partialKey.compareTo(root.getIdentification().substring(0, n)) > 0) {
			Tree t = new Tree(root.getLeft());
			return t.findNode(partialKey, where);
		} else if (partialKey.compareTo(root.getIdentification().substring(0, n)) < 0) {
			Tree t = new Tree(root.getRight());
			return t.findNode(partialKey, where);
		} else {
			TNode p = root;
			if (where == 0) {
				TNode q = p.getLeft();
				while (q != null) {
					if (q.getIdentification().substring(0, n).equals(partialKey)) {
						p = q;
						q = q.getLeft();
					} else {
						q = q.getRight();
					}
				}
			} else {
				TNode q = p.getRight();
				while (q != null) {
					if (q.getIdentification().substring(0, n).equals(partialKey)) {
						p = q;
						q = q.getRight();
					} else {
						q = q.getLeft();
					}
				}
			}
			return p;
		}
	}

	public void writeLevel(int level, RandomAccessFile f) {
		if (level == 0) {
			try {
				if (root != null) {
					f.write(root.getIdentification().getBytes());
					f.writeInt(root.getRecordNumber());
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (root != null) {
			Tree tree = new Tree(root.getLeft());
			tree.writeLevel(level - 1, f);

			tree = new Tree(root.getRight());
			tree.writeLevel(level - 1, f);
		}
	}

	public void breadthFirstSave(String fileName) {
		try {
			RandomAccessFile f = new RandomAccessFile(fileName, "rw");
			f.setLength(0);

			for (int i = 0; i < height(); i++) {
				writeLevel(i, f);
			}

			f.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void breadthFirstRetrieve(String fileName) {
		try {
			RandomAccessFile f = new RandomAccessFile(fileName, "rw");

			int nodes = (int) (f.length() / (Globals.IDENTIFICATION_LEN + Globals.INT_LEN));

			TNode p = null;

			byte identification[] = new byte[Globals.IDENTIFICATION_LEN];
			String identificationString = Globals.STR_NULL;

			for (int i = 0; i < nodes; i++) {
				identificationString = Globals.STR_NULL;
				f.read(identification);


				for (int j = 0; j < identification.length; j++) {
					identificationString = identificationString + (char) identification[j];
				}

				p = new TNode(identificationString, f.readInt(), null, null, null);
				this.insertNode(p);
			}
		} catch (Exception e) {
			System.out.println("Couldn't retrieve tree file " + fileName);
		}
	}

	public void printTree() {
		if (this.root != null) {
			Tree t = new Tree(this.root.getLeft());
			t.printTree();

			System.out.println(this.root.toString());

			t = new Tree(this.root.getRight());
			t.printTree();
		}
	}

	public void printTree(int level) {
		if (this.root != null) {
			Tree t = new Tree(this.root.getLeft());
			t.printTree(level + 1);

			System.out.println(this.root.toString() + " in level " + level);

			t = new Tree(this.root.getRight());
			t.printTree(level + 1);
		}
	}

	public int height(){
		int treeHeight = 0;
		if(this.getRoot() != null){
			Tree lTree = new Tree(this.getRoot().getLeft());
			int lHeight = 1 + lTree.height();

			Tree rTree = new Tree(this.getRoot().getLeft());
			int rHeight = 1 + rTree.height();

			treeHeight = lHeight > rHeight ? lHeight : rHeight;
		}
		return treeHeight;
	}

	public void setParentsChildLink(TNode p, TNode q) {
		TNode parent = p.getParent();
		if (p == parent.getRight()) {
			parent.setRight(q);
		} else if (p == parent.getLeft()) {
			parent.setLeft(q);
		}
	}

	public void deleteNode(TNode p) {
		if (p.isLeaf()) {
			// Case 1
			if (p == root) {
				root = null;
			} else {
				setParentsChildLink(p, null);
			}
		} else if (p.getLeft() == null || p.getRight() == null) {
			// Cases 2 and 3
			TNode q = null;
			if (p.getLeft() != null) q = p.getLeft();
			else if (p.getRight() != null) q = p.getRight();

			if (p == root) root = q;
			else setParentsChildLink(p, q);

			q.setParent(p.getParent());
		} else {
			TNode q = p.getLeft();
			if(q.getRight() == null){
				//Case 4, 5a
				if(p == root){
					root = q;
				} else {
					setParentsChildLink(p, q);
				}

				q.setParent(p.getParent());
				q.setRight(p.getRight());
				q.getRight().setParent(q);
			} else {
				//Case 5b, 5c
				q = getRightMostNode(q);
				q.getParent().setRight(q.getLeft());
				if(q.getLeft() != null){
					q.getLeft().setParent(q.getParent());
				}

				if(p == root)
					root = q;
				else
					setParentsChildLink(p, q);

				q.setParent(p.getParent());
				q.setLeft(p.getLeft());
				p.getLeft().setParent(q);
				q.setRight(p.getRight());
				p.getRight().setParent(q);

			}
		}

		p.setLeft(null);
		p.setRight(null);
		p.setParent(null);
		p = null;
	}

	public TNode getRightMostNode(TNode curr) {
		TNode it = curr;
		while (it.getRight() != null) it = it.getRight();
		return it;
	}

	//@Override
	public String toString() {
		if (this.root == null) return "Empty Tree";
		return this.root.getIdentification();
	}
}
