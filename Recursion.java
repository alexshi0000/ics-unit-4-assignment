public class Recursion{
	
	public static long factorial(int n){
		return n == 0 ? 1 : factorial(n-1) * n; 
	}
	
	public static int pascal(int r, int c){
		if (c > r)
			return 0;
		if (r == 0 || c == 0)
			return 1;
		return pascal(r-1, c-1) + pascal(r-1, c);
	}
	
	public static double ballDistance(int n){
		if (n == 0)
		   return 0;
		return 16 + (0.7 * ballDistance(n-1));
	}
	
	public static boolean magicNumbers(int[][] num){
		if (valid(num)) {
			for (int i = 0; i < num.length; i++) {
				for (int j = 0; j < num[0].length; j++)
					System.out.print(num[i][j] + " ");
			}
		}    
	}
	
	public static void findPath(int x, int y, int r, int c, String s){
		if (x == r-1 && y == c-1) {
			System.out.println(s);
		}
		else {
			if (x < r)   
				findPath(x+1, y, r, c, s + " R");
			if (y < c)
				findPath(x, y+1, r, c, s + " D");
		}
	}
	
	public static void main(String[] args){
		
	}
}
