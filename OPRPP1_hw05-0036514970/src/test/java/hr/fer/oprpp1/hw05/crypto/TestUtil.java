package hr.fer.oprpp1.hw05.crypto;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test; 

public class TestUtil {


	@Test
	public void testHextobyte() {
		assertArrayEquals(new byte[] {1, -82, 34}, Util.hextobyte("01aE22"));
		assertArrayEquals(new byte[] {}, Util.hextobyte(""));
		assertArrayEquals(new byte[] {0,0}, Util.hextobyte("0000"));
		assertArrayEquals(new byte[] {-128}, Util.hextobyte("80"));
		assertThrows(IllegalArgumentException.class,() -> Util.hextobyte("abc"));
		assertThrows(IllegalArgumentException.class,() -> Util.hextobyte("az"));
	}
	
	@Test
	public void testbytetohex() {
		assertEquals("01ae22", Util.bytetohex(new byte[] {1, -82, 34}));
		assertEquals("", Util.bytetohex(new byte[] {}));
		assertEquals("0000", Util.bytetohex(new byte[] {0,0}));
		assertEquals("80", Util.bytetohex(new byte[] {-128}));
	}
}
	