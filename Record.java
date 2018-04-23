import java.io.*;
public class Record{
	private byte[] data = new byte[Globals.RECORD_DATA_LEN];
	private int next = Globals.END_OF_MESSAGE;
	public Record(){
	next = Globals.END_OF_MESSAGE;
	for(int i = 0; i < data.length; i++)
		data[i] = (byte)Globals.BLANK;
	}
	public Record(String s, int nextRecord){
	setData(s, nextRecord);
	}
	public String getData(){
	String r = "";
	for(int i = 0; i < data.length; i++)
		r += (char)((data[i]+256)%256);
	return r;
	}
	public void setData(String txt, int nextRecord){
	for(int i = 0; i < data.length; i++){
		if(i < txt.length())
		data[i] = (byte)txt.charAt(i);
		else
		data[i] = (byte)Globals.BLANK;
	} 
	next = nextRecord;
	}
	public int readFromMessagesFile(int recordNumber){
	try{
		Globals.msg.seek(Globals.RECORD_LEN * recordNumber);
		int bytes = Globals.msg.read(data);
		this.next = Globals.msg.readInt();
		return Globals.PROCESS_OK;
	} catch (IOException e){
		return Globals.PROCESS_ERROR;
	}
	}
	public int writeToMessagesFile(int recordNumber, int mode){
	try {
		Globals.msg.seek(Globals.RECORD_LEN * recordNumber);
		Globals.msg.write(data);
		Globals.msg.writeInt(next);
		if(mode == Globals.APPEND)Globals.totalRecordsInMessagesFile++;
		return Globals.PROCESS_OK;
		
	} catch (IOException e){
		return Globals.PROCESS_ERROR;
	}
	}
	public int deleteFromMessagesFile(int recordNumber) {
	int error = readFromMessagesFile(recordNumber);
	if (error == Globals.PROCESS_OK) {
		data[0] = (byte) Globals.DELETED;
		error = writeToMessagesFile(recordNumber, Globals.MODIFY);
		if (error == Globals.PROCESS_OK) {
			Globals.availableList.addRecord(recordNumber);
		}
		else {
			System.out.println("ERROR WRITING TO MESSAGES FILE. IN METHOD WRTIETOMESSSAGESFILE");
		}
	}
	else {
		System.out.println("ERROR WRITING TO MESSAGES FILE. IN METHOD READFROMMESSSAGESFILE");
	}
	return error;
	}
	public String toString(){
	return getData()+next;
	}
	public int getNext(){
	return next;
	}
}
