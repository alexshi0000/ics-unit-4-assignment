public class Error{
	public static void report(int errorNumber){
		String errorMessage = "";
		switch(errorNumber){
			case 00 : errorMessage = "Errpr initializing system";
					  break;
			case 01 : errorMessage = "01 error opeing messages file" + Globals.MESSAGES_FILE;
					  break;
			case 02 : errorMessage = "02 error opeing available list file" + Globals.AVAILABLE_LIST_FILE;
					  break;
			default : errorMessage = "Unkown error";
					  break;
		}
		System.out.println(errorMessage);
	}
}
