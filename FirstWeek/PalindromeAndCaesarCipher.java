/*
MICP Summber 2017
Author: Xinyi(Cindy) Chen
Email: xinyi.chencareer@gmail.com
Language: Java
*/

/**********WEEK ONE***********/
/*
1.1 A Palindrome is a number that reads the same forward as it does in reverse. For example, 12321 is a palindrome because it reads the same going from left to right as it does going right to left. Most numbers can be made into palindromes by taking the original number, reversing the digits, then adding the original number to the reversed digits. If that number isn’t a palindrome, then repeat the same process until you reach a palindrome. Most numbers can be made into palindromes by repeating this process, but some numbers don’t converge. During this process, if the number becomes larger than 1,000,000 and still doesn’t form a palindrome, then return false.
*/
/*
1. Examples
normal: 123 -> 123 + 321 = 444 ture
single number: 9 -> 9 + 9 = 18; 18 + 81 = 99 true
negative number: -10 not palindrome false
large number: 999,999, true

2. Pseudocode 
2.1 Brute Force way (recursion)
	makePalindrome(num):
		if num is negative:
			return false
		if num is Palindrome:
			return true
		if num > 1,000,000:
			return false
		num = original num + reversed num
		return isPalindrome(num)

2.2 Optimize (use while loop instead of recursion to save space)
	makePalindrome(num):
		if num is negative:
			return false
		while (num <= 1,000,000):
			if num is Palindrome:
				return true
			num = original num + reversed num

3.Implementation (optimized way)
See following unannotated code makePalindrome(int);

4. Test Cases
See following unannotated JUnit test code @Test;
*/
public class Solution{
	public boolean makePalindrome(int num) {
		if (num < 0) return false;
		while (num <=1000000) {
			int reversed = reverseDigits(num);
			if (num == reversed) 
				return true;//if reversed num = num then this num is palindrome
			else
				num += reversed;
		}
		return false;
	}

	public int reverseDigits(int num) {
		int reversed = 0;
		while (num > 0){
			int digit = num % 10;
			reversed *= 10;
			reversed += digit;
			num /= 10;
		}
		return reversed;
	}

	@Test 	/*All Pass*/
	public void testMakePalindrome() 
	{
	    assertEquals(false,makePalindrome(-2)); //negative
	    assertEquals(true,makePalindrome(0)); //edge case
	    assertEquals(true,makePalindrome(999999));//edge case
	    assertEquals(false,makePalindrome(1999999));//edge case
	    assertEquals(true,makePalindrome(123));//normal case
	    assertEquals(true,makePalindrome(199));// normal case
	}

}

/*
1.2 The code you will write is based on the “Caesar Cipher” where each letter is shifted a certain number of places left or right through the alphabet. The alphabet is treated as being circular so that the first letter follows after the last letter, and the last letter precedes the first letter.  These ideas will be applied separately to uppercase letters, lower case letters, and digits. For example, with a shift of 1, ‘A’ becomes ‘B’, ‘Z’ becomes ‘A’, ‘a’ becomes ‘b’, ‘z’ becomes ‘a’, ‘0’ becomes ‘1’, ‘9’ becomes ‘0’. Spaces, punctuation, and any other symbols are unaffected in this scheme. Your task is to write a function to encrypt a string using this Caesar Cipher. 

INPUT FORMAT
Your function will take an input string that begins with a number representing the shift. The number will be in the range -1000000000 to 1000000000 (negative 1 billion to 1 billion). The number is followed by a colon (‘:’). The rest of the line consists of a string of 1 to 200 arbitrary characters and represents a fragment of the text to be encrypted.
 
OUTPUT FORMAT
Output will be the corresponding encrypted text fragment
  
SAMPLE INPUT:
1:some text
 
SAMPLE OUTPUT:
tpnf ufyu
*/
/*
1. Examples
number: 199 -> 200
lowercase letters: azf -> bag
uppercase letters: AZF -> BAG
punctuation included: ,,a,f,z -> ,,b,g,a

2. Pseudocode
2.1 Brute Force
	caesarCipher(str):
		Iter char in str:
			if char is not letter or digit:
				continue;
			else:
				shift the char by shiftnum through a circular alphabet 
2.2 Optimize (using mod)
	caesarCipher(str):
		Iter char in str:
			if char is not letter or digit:
				continue;
			else if char is letter:
				shift by shiftnum % 26 through a circular alphabet 
			else if char is digit:
				shift by shiftnum % 10 through a circular alphabet 

3. Implementation 
See the folloing unannotated code;

4. Test Cases
See the folloing unannotated JUnit code;
*/
public class Solution{
	public String caesarCipher(String text) {
		if (text == null) return null;

		String[] strs = text.split(":");
		int shift = Integer.parseInt(strs[0]);
		StringBuilder encrypted =  new StringBuilder();

		for (int i = 0; i < strs[1].length(); i++) {
			char c = strs[1].charAt(i);
			if (Character.isLetterOrDigit(c)) {
				encrypted.append(shiftLetterOrDigit(c, shift));
			}else {
				encrypted.append(c);
			}
		} 
		return encrypted.toString();
	}

	public char shiftLetterOrDigit(char c, int shift) {
		char shifted = c;

		if (c >= 'A' && c <= 'Z') {
			shift = getRealShift(shift, 26);
			shifted = (char)(c + shift);
			while (shifted < 'A' || shifted > 'Z') {
				shifted = (char)((shifted - 'A') % 26 + 'A');
			}
		}else if (c >= 'a' && c <= 'z') {
			shift = getRealShift(shift, 26);
			shifted = (char)(c + shift);
			while (shifted < 'a' || shifted > 'z') {
				shifted = (char)((shifted - 'a') % 26 + 'a');
			}
		}else if (c >= '0' && c <= '9'){
			shift = getRealShift(shift, 10);
			shifted = (char)(c + shift);
			while (shifted < '0' || shifted > '9') {
				shifted = (char)((shifted - '0') % 10 + '0');
			}
		}
		return shifted;
	}

	public int getRealShift(int shift, int base) {
		shift %= base; //mod
		if (shift < 0) {
			shift = base + shift; //convert negative to positive
		}
		return shift;
	}

	@Test /*all pass*/
	public void testCaesarCipher() {
		 assertEquals("bca",caesarCipher("1:abz")); //letters
		 assertEquals("abz",caesarCipher("26:abz")); //letters
		 assertEquals("wxv",caesarCipher("100:abz")); //letters shift more than 26
		 assertEquals("10",caesarCipher("1:09")); //digits
		 assertEquals("09",caesarCipher("10:09")); //digits
		 assertEquals("98",caesarCipher("99:09")); //digits shift more than 10
		 assertEquals("fg,e31 qr;2",caesarCipher("2:de,c19 op;0")); //digits + letters and other symbols
		 assertEquals("st,r20 de;1",caesarCipher("-89:de,c19 op;0")); //digits 
		 
	}
}




















