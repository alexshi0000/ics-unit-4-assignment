import java.util.Date;

public class Message{

	private String text;
	private char command;
	private String sender;
	private String receiver;
	private String dateTime;
	private char marker;
	private String subject;
	private char eosMarker;
	
	public Message(){
		text = null;
	}
	
	public Message(String s){
		setMessage(s);
	}
	
	public void setMessage(String s){
		command   = s.charAt(Globals.COMMAND_POS);
		sender    = s.substring(Globals.SENDER_POS, Globals.RECEIVER_POS); 
		receiver  = s.substring(Globals.RECEIVER_POS, Globals.DATE_TIME_POS);
		dateTime  = s.substring(Globals.DATE_TIME_POS, Globals.FIRST_RECORD_MARKER_POS); 
		marker    = s.charAt(Globals.FIRST_RECORD_MARKER_POS);
		subject   = s.substring(Globals.FIRST_RECORD_MARKER_POS+1, s.indexOf(Globals.END_OF_SUBJECT_MARKER));
		eosMarker = s.charAt(s.indexOf(Globals.END_OF_SUBJECT_MARKER));
		text      = s.substring(s.indexOf(Globals.END_OF_SUBJECT_MARKER) + 1);
	}
	
	public String getMessage(){
		return command + sender + receiver + dateTime + marker + subject + eosMarker + text;
	}
	
	public char getCommand(){
		return command;
	}
	
	public String getSender(){
		return sender;
	}
	
	public String getReceiver(){
		return receiver;
	}
	
	public String getDateTime(){
		return dateTime;
	}
	
	public char getMarker(){
		return marker;
	}
	
	public String getSubject(){
		return subject;
	}
	
	public char getEosMarker(){
		return eosMarker;
	}
	
	public String getId(){
		return sender + receiver + dateTime;
	}

	public void readFromMessagesFile(int recordNumber){
		String data = "";
		Record record = new Record();
		do{
			record.readFromMessagesFile(recordNumber);
			data += record.getData();
			recordNumber = record.getNext();
		}while(recordNumber != Globals.END_OF_MESSAGE);
		setMessage(data);
	}

	public String toString(){
		//Date date = new Date(Utils.bytesStrToLong(dateTime));
		return "Command      : " + command   + "\n" +
			   "Sender       : " + sender    + "\n" + //SearchAndSort
			   "Receiver     : " + receiver  + "\n" + 
			   "Date/Time    : " + dateTime  + "\n" +
			   "Marker       : " + marker    + "\n" +
			   "Subject      : " + subject   + "\n" +
			   "EOS Marker   : " + eosMarker + "\n" +
			   "Message text : " + text;
	}

	public void setText(String t){
		text = t;
	}

	public int writeToMessagesFile(){
		String s = text;
		int recordNumber = -1;
		int nextRecordNumber = -1;
		int startRecordNumber = Globals.totalRecordsInMessagesFile;
		Record record = new Record();

		if(Globals.availableList.getHead() == null)
			startRecordNumber = Globals.totalRecordsInMessagesFile;
		else
			startRecordNumber = Globals.availableList.getHead().getRecordNumber();

		while(s != ""){
			if(Globals.availableList.getHead() == null){
				recordNumber = Globals.totalRecordsInMessagesFile;
				if(s.length() < Globals.RECORD_DATA_LEN){
					record.setData(s, Globals.END_OF_MESSAGE);
					record.writeToMessagesFile(recordNumber, Globals.APPEND);
					s = "";
				}
				else{
					nextRecordNumber = recordNumber + 1;
					record.setData(s.substring(0, Globals.RECORD_DATA_LEN), nextRecordNumber);
					record.writeToMessagesFile(recordNumber, Globals.APPEND);
					s = s.substring(Globals.RECORD_DATA_LEN);
				}
			}
			else{
				recordNumber = Globals.availableList.getNextRecord();
				if(s.length() < Globals.RECORD_DATA_LEN){
					record.setData(s, Globals.END_OF_MESSAGE);
					record.writeToMessagesFile(recordNumber, Globals.MODIFY);
					s = "";
				}
				else{
					nextRecordNumber = Globals.availableList.getHead() == null ?
						Globals.END_OF_MESSAGE : Globals.availableList.getHead().getRecordNumber();
					record.setData(s.substring(0, Globals.RECORD_DATA_LEN), nextRecordNumber);
					record.writeToMessagesFile(recordNumber, Globals.MODIFY);
					s = s.substring(Globals.RECORD_DATA_LEN);
				}
			}
		}
		return startRecordNumber;
	}

	public void deleteFromMessagesFile(int recordNumber){
		Record record = new Record();
		while(recordNumber != Globals.END_OF_MESSAGE){
			//System.out.println(recordNumber);
			//record.readFromMessagesFile(recordNumber);
			record.deleteFromMessagesFile(recordNumber);
			recordNumber = record.getNext();
		}
	}
	
	public String getIdSenderFirst(){
		return sender + receiver + dateTime;
	}
	
	public String getIdReceiverFirst() {
		return receiver + sender + dateTime;
	}

	public static void main(String[] args){
		Globals.availableList = new AvailableList(null, null);
		int error = FileIO.openMessagesFile(Globals.MESSAGES_FILE);
		if(error == Globals.PROCESS_OK){
			Message message = new Message();
			message.readFromMessagesFile(0);
			System.out.println(message);
		}
		FileIO.closeMessagesFile();
	}
	/*
	public static void main(String[] args){
		Globals.availableList = new AvailableList(null, null);
		int error = FileIO.openMessagesFile(Globals.MESSAGES_FILE);
		if(error == Globals.PROCESS_OK){
			Message newText = new Message();
			newText.setText("hello");

			Message newText1 = new Message();
			newText1.setText("dude");
			int bytesWritten = newText.writeToMessagesFile();
			bytesWritten = newText1.writeToMessagesFile();

			Message largeText = new Message();
			String out = "";
			for(int i = 0; i < Globals.RECORD_LEN*2; i++)
				out += "dasds";
			largeText.setText(out);
			bytesWritten = largeText.writeToMessagesFile();
		}
		FileIO.closeMessagesFile();
	}

	public static void main(String[] args){
		int error = FileIO.openMessagesFile(Globals.MESSAGES_FILE);
		Record record1 = new Record("yo yo yo mr shi wtf", 3);
		Record record2 = new Record("jacob get your ass over here", 4);
		Record record3 = new Record("hello", -1);
		record1.writeToMessagesFile(0, Globals.APPEND);
		record2.writeToMessagesFile(3, Globals.APPEND);
		record3.writeToMessagesFile(4, Globals.APPEND);
		if(error == Globals.PROCESS_OK){
			Message theMessageBook = new Message();
			theMessageBook.readFromMessagesFile(0);
			System.out.println(theMessageBook);
		}
		FileIO.closeMessagesFile();
	}*/
}
