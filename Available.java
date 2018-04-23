public class Available{
	private int recordNumber = -1;
	private Available next = null;
	public Available(){
		next = null;
		recordNumber = -1;
	}
	public Available(int n){
		recordNumber = n;
		next = null;
	}
	public void setNext(Available n){
		next = n;
	}
	public void setRecordNumber(int n){
		recordNumber = n;
	}
	public int getRecordNumber(){
		return recordNumber;
	}
	public Available getNext(){
		return next;
	}
	public String toString(){
		return recordNumber+" ";
	}
}
