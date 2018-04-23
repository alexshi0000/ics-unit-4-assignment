import java.io.*;
public class Combinarics{
	public static void getCombo(String arr, int n, String curr){
		if(curr.length() < n){
			if(n > 0)
				System.out.println(curr);
			for(int i = 0; i < arr.length(); i++){
				getCombo(arr.substring(0,i)+arr.substring(i+1), n+1, curr + arr.charAt(i));
			}
		}
	}
	public static void main(String[] args){
		try{
			BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
			int n = Integer.parseInt(sc.readLine());
			String arg = sc.readLine();
			getCombo(arg, n, "");
		}catch(Exception e){
			System.out.println(e);
		}
	}
}
