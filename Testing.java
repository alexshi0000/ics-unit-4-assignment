public class Testing{
	public static void main(String[] args) {
		Globals.availableList = new AvailableList();
		int error = FileIO.openMessagesFile(Globals.MESSAGES_FILE);
		if(error == 0){        
			String s = "";
			for(int i = 0; i < Globals.RECORD_DATA_LEN; i++)
				s += "a";
			Message a = new Message();
			a.setText(s);
			a.writeToMessagesFile();
			System.out.println(Globals.availableList);
			Message b = new Message();
			b.setText(s);
			b.writeToMessagesFile();
			Message c = new Message();
			c.setText(s);
			c.writeToMessagesFile();
			Message d = new Message();
			d.setText(s);
			d.writeToMessagesFile();
			Message e = new Message();
			e.setText(s);
			e.writeToMessagesFile();
			System.out.println(Globals.availableList);
		}
	}
}
