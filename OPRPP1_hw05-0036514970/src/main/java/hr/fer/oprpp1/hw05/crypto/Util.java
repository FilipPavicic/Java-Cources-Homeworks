package hr.fer.oprpp1.hw05.crypto;

public class Util {
	
	/**
	 * Pretvara dani text koji predstavlja broj zapisan u heksadekatskom sustavu
	 * u polje bajtova. Npr hextobyte("01aE22") pretvara u  byte[] {1, -82, 34}
	 * 
	 * @param keyText text broja u heksadekatsakom sustavu
	 * @return polje bajtova.
	 * @throws IllegalArgumentException ako se dani tekst ne može pretvoriti
	 * u bajtove ili je tekst neparne duljine. 
	 *
	 */
	public static byte[] hextobyte(String keyText) {
		if(keyText.length() % 2 != 0 ) throw new IllegalArgumentException("Duljina teksta ne smije biti neparna");

		keyText = keyText.toLowerCase();
		byte[] bytes = new byte[keyText.length() / 2];
		for(int i = 0; i< keyText.length(); i = i+2) {
			int num = 16 * intFromHex(keyText.charAt(i)) + intFromHex(keyText.charAt(i+1));
			bytes[i/2] = (byte) num;
		}
		return bytes;
	}

	/**
	 * Predtvara polje bajtova u text koji predstavlja heksadekatsku vrijednost brojeva
	 * u polju. Npr : bytetohex(new byte[] {1, -82, 34}) pretvara u String "01ae22".
	 * 
	 * @param bytearray polje bajtova.
	 * @return String koji predstavlja heksadeksatsku vrijednost broja.
	 */
	public static String bytetohex(byte[] bytearray) {
		String hexValue = "";
		for (byte b : bytearray) {
			int value =b & 0xFF;
			hexValue += hexFromInt(value / 16);
			hexValue += hexFromInt(value % 16);
		}
		return hexValue;	
	}
	private static String hexFromInt(Integer i) {
		if(i >= 0 && i <= 9) return i.toString();
		switch (i) {
			case 10 : return "a";
			case 11 : return "b";
			case 12 : return "c";
			case 13 : return "d";
			case 14 : return "e";
			case 15 : return "f";
			default : throw new IllegalArgumentException("Unexpected value: " + i);
		}
	}

	private static int intFromHex(Character charAt) {
		if(Character.isDigit(charAt)) return Character.getNumericValue(charAt);
		switch (charAt) {
			case 'a' : return 10;
			case 'b' : return 11;
			case 'c' : return 12;
			case 'd' : return 13;
			case 'e' : return 14;
			case 'f' : return 15;
			default : throw new IllegalArgumentException(charAt+ " se ne može parsirati u broj");
		}
	}
}
