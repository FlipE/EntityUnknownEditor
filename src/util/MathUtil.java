package util;

public class MathUtil {

	/**
	 * Check if the given number is a power of two using a simple bitwise operation.
	 * If number AND number - 1 equals zero then number is a power of two.
	 * 
	 * Example with 4
	 * 
	 * 100
	 * 011
	 * ---
	 * 000
	 * 
	 * 4 is a power of 2
	 * 
	 * example with 3
	 * 
	 * 011
	 * 010
	 * ---
	 * 010
	 * 
	 * 3 is not a power of 2
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isPowerOfTwo(int num) {
		return (num != 0) && ((num & (num - 1)) == 0);
	}
	
}