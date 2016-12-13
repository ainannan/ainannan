package cn.ainannan;

import java.util.UUID;

public class Test2 {
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			String str = UUID.randomUUID().toString();
			System.out.println(str + " - " + str.length());
			str = str.replaceAll("-", "");
			System.out.println(str + " - " + str.length());
			System.out.println();
		}
		
	}
}
