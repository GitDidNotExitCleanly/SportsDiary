package util;

import javax.swing.ImageIcon;

public class Util {
	
	public static boolean isNumber(String strValue) {
		if (strValue.length() == 0) {
			return false;
		}
		else if (strValue.length() > 1 && strValue.charAt(0) == '0') {
			return false;
		}
		else {
			boolean isNumber = true;
			for (int i=0;i<strValue.length();i++) {
				isNumber &= Character.isDigit(strValue.charAt(i));
			}
			return isNumber;
		}
	}
	
	public static ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = Util.class.getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
}
