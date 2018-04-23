public class EmailServer{
	/*public static void main(String[] args){
		System.out.println(Utils.bytesStrToLong("CATALYST"));
		System.out.println(Utils.longToBytesStr(4846247313457894228L));
	}*/
	public static void main(String[] args) {
		int error = -1;
		error = Init.initializeSystem();
		if (error == Globals.PROCESS_OK) {
			Message message = new Message();
			int recordNumber = 0;
			String identification = "JBON0007SUZIECUTE00000000";
			TNode p = Globals.senderIndex.findNode(identification);
			System.out.println(p);
			recordNumber = p.getRecordNumber();
			
			message.readFromMessagesFile(recordNumber);
			System.out.println(message);
			
		} else {
			Error.report(0);
		}
	}
}
