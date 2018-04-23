public class SLList {
	private SNode head = null;
	private SNode tail = null;

	public SLList(){
		head = null;
		tail = null;
	}

	public SNode findNode(String key){
		SNode p;
		for(p = this.head; p != null && !key.equals(p.getName()); p = p.getNext());
		return p;
	}

	public void insertNode(SNode p){
		if(this.head == null) {
			this.head = p;
		}
		else {
			this.tail.setNext(p);
		}
		this.tail = p;
	}
	
	public void insertInOrder(SNode p) {
		if (head == null) {
			head = p;
			tail = p;
		}
		else if (head.getName().compareTo(p.getName()) > 0) {
			p.setNext(head);
			head = p;
		}
		else {
			SNode q = head;
			while(q.getNext() != null &&
				q.getNext().getName().compareTo(p.getName()) < 0)
					q = q.getNext();
			p.setNext(q.getNext());
			q.setNext(p);
			if (tail == q)
				tail = p;
		}
	}
	
	public void deleteNode(SNode p) {
		if (head == p) {
			tail = null;
			head = null;
		}
		else {
			SNode focus = head;
			while (focus.getNext() != null && focus.getNext() != p)
				focus = focus.getNext();
			if (focus.getNext() == null)
				
		}
	}

	public SNode getHead(){return this.head;}
	public SNode getTail(){return this.tail;}

	public void setHead(SNode head) {this.head = head;}
	public void setTail(SNode tail) {this.tail = tail;}

	public String toString() {
		String ret = "";
		for(SNode p = head; p != null; p = p.getNext())
			ret += p.toString() + "\n";
		return ret;
	}
}
