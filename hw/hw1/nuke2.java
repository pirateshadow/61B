import java.io.*;

public class nuke2{
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		String line = in.readLine();
		System.out.println(line.charAt(0)+line.substring(2));
	}
}