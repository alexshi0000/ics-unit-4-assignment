public class SNode{
	private String name = "";
	private double mark = 0.0;
	private SNode next  = null;

	public SNode(){
		name = "";
		mark = 0.0;
		next = null;
	}

	public SNode(String name, double mark, SNode next){
		this.name = name;
		this.mark = mark;
		this.next = next;
	}

	public String getName(){return this.name;}
	public double getMark(){return this.mark;}
	public SNode getNext(){return this.next;}

	public void setName(String name){this.name = name;}
	public void setMark(double mark){this.mark = mark;}
	public void setNext(SNode next){this.next = next;}

	public String toString(){return this != null ? name + " " + mark : null;}
}
