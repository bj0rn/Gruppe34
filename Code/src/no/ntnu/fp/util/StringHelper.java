package no.ntnu.fp.util;

import java.util.ArrayList;
import java.util.List;

public class StringHelper {

	/**
	 * Returns the {@code String} a array of {@code String} with at most n
	 * {@code char}s in each cell  
	 * 
	 * @param str
	 * @param n
	 * @return
	 */
	
	public static String[] boxing(String str, int n) {
		
		List<String> lines = new ArrayList<String>();
		
		int pos = 0;
		int length = str.length();
		
		
		while (true) {
			String sub = "";
			
			int posn = pos + n;
			if (posn > length) {
				lines.add(str.substring(pos));
				break;
			} else if (str.charAt(posn) == ' ') {
				sub = str.substring(pos, posn);
				pos += n+1;
			} else {
				int tmp = posn;
				while (str.charAt(tmp) != ' ') {
					tmp--;
				}
				sub = str.substring(pos, tmp);
				pos = tmp+1;
			}
			
			lines.add(sub);
		}
		
		return lines.toArray(new String[] {});
		
	}
	
	public static String nullPaddNumber(int number) {
		
		if (number <= 9) {
			return "0" + number;
		} else  {
			return Integer.toString(number);
		}
		
	}
	
	public static void main(String[] args) {
		String str = "dette er skal bli en veldig lang møte tekst slik av den kommer på flere linjer.";
		
		String[] list = boxing(str, 40);
		
		for (String line : list) {
			System.out.println(line);
		}
		
	}
	
}
