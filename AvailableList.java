public class AvailableList{
	private Available head;
	private Available tail;
	public AvailableList(){
		head = null;
		tail = null;
	}
	public AvailableList(Available h, Available t){
		head = h;
		tail = t;
	}
	public Available getHead(){
		return head;
	}
	public Available getTail(){
		return tail;
	}
	public void setHead(Available h){
		head = h;
	}
	public void setTail(Available t){
		tail = t;
	}
	public String toString(){
		String ret = "";
		Available focus = head;
		while (focus != null) {
			ret += focus;
			focus = focus.getNext();
		}
		return ret;
	}
	public void addRecord(int recordNumber) {
		Available newEntry = new Available(recordNumber);
		if (head == null) {
			head = newEntry;
			tail = head;
		}
		else {
			tail.setNext(newEntry);
			tail = newEntry;
		}
	}
	public int getNextRecord(){
		if(head == null)
			return Globals.EMPTY_AVAILABLE_LIST;
		int ret = head.getRecordNumber();
		head = head.getNext();
		tail = head == null ? null : tail;
		return ret;
	}
}
