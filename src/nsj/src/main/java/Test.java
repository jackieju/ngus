import java.util.Random;

public class Test {
	public static void main(String arg[]){
		//String s = "select * from #TABLENAME";
		//System.out.println(s.replaceAll("#TABLENAME", "123"));
//		Random r1 = new Random();
//		Random r2 = new Random();
//		for (int i = 1; i < 10; i++)
//			System.out.println(r1.nextInt(10)+", " + r2.nextInt());
//		
		String s[] = new String("clipboard, myweb").split(",");
		for (int i = 0; i < s.length; i++)
		System.out.println(s[i]);
	}
}
